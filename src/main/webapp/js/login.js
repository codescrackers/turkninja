function setupLoginForm(id) {
    $(document).ready(function() {
        var $toggler = $('#' + 'loginToggler' + id);
        var $form = $('#' + 'loginForm' + id);
        var $loginErrorLabel = $('#' + 'loginError' + id);
        var $loginBtn = $('#' + 'loginbtn' + id);
        var $loadingIndicator = $('#' + 'loadingIndicator' + id);

        $form.validate({
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                username: {
                    required: "Kullanıcı adı veya email giriniz"
                },
                password: {
                    required: "Şifre giriniz"
                }
            }
        });

        // show/hide login form
        $toggler.click(function(event){
            event.preventDefault();

            $form.slideToggle(300, function() {
                if ($form.is(":visible"))
                    $('#username' + id).focus();
            });
        });

        // ajax submit login form
        $form.on('submit', function(e) {
            e.preventDefault();
            var data =  $form.serializeArray();

            $loginErrorLabel.hide();

            if ($form.valid()) {
                $loginBtn.prop('disabled', true);
                $loadingIndicator.show();

                $.ajax({
                    type: 'post',
                    url: $form.attr('action'),
                    data: data,
                    success: function (data) {
                        if (data == 'ok') {
                            // reload page
                            window.location.reload(true);
                        }
                        else {
                            $loginBtn.prop('disabled', false);
                            $loadingIndicator.hide();
                            $loginErrorLabel.text("Giriş başarısız. Lütfen kullanıcı adı/email'inizi kontrol ediniz");
                            $loginErrorLabel.show();
                        }
                    },
                    error: function () {
                        $loginBtn.prop('disabled', true);
                        $loadingIndicator.hide();
                        $loginErrorLabel.text('İstek gönderilemedi.');
                        $loginErrorLabel.show();
                    }
                });
            }
        });
        
        
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
          });
        
    });
}