

$(document).ready(function() {
    $.validator.methods._remote = $.validator.methods.remote;
    var timer = 0;
    $.validator.methods.remote = function () {
        clearTimeout(timer);

        var args = arguments;

        timer = setTimeout(function() {
            $.validator.methods._remote.apply(this, args);
        }.bind(this), 500);

        return "pending";
    };

    $.validator.addMethod("username", function(value, element) {
        return this.optional( element ) || XRegExp("^[\\p{L}0-9\\._\\- ]+$").test( value );
    }, 'Sadece harfler, rakamlar, boşluk, ".", "-" ve "_" izin veriliyor.');

    $("#regForm").validate({
        rules: {
            username: {
                required: true,
                minlength: 3,
                username: true,
                remote: {
                    url: window.usernameCheckUrl,
                    type: "get",
                    data: {
                        username: function () {
                            return $("#username").val();
                        }
                    }
                }
            },
            email: {
                required: true,
                remote: {
                    url: window.emailCheckUrl,
                    type: "get",
                    data: {
                        email: function () {
                            return $("#email").val();
                        }
                    }
                }
            },
            password: {
                required: true,
                minlength: 6
            },
            password2: {
                required: true,
                equalTo: "#password"
            }
        },
        messages: {
            username: {
                required: "Kullanıcı adı giriniz",
                minlength: "Kullanıcı adı çok kısa",
                remote: function (params) {
                    return "Kullanıcı adı " + params + " zaten kayıtlı. Daha önce kaydoldunuz mu? Şifrenizi unuttuysanız admin ile iletişime geçin";
                }
            },
            email: {
                required: "Enter e-mail",
                remote: function (params) {
                    return "Email " + params + " zaten kayıtlı. Daha önce kaydoldunuz mu? Şifrenizi unuttuysanız admin ile iletişime geçin";
                }
            },
            password: {
                required: "Şifre giriniz",
                minlength: "Şifre çok kısa"
            },
            password2: {
                required: "Şifre giriniz",
                equalTo: "Şifreler eşleşmiyor"
            }
        }
    });
});
