$(document).ready(function() {
    var converter = Markdown.getSanitizingConverter();

    converter.hooks.chain("preConversion", function (text) {
        return text.replace(/===cut===/g, '');
    });

    var editor = new Markdown.Editor(converter);

    editor.run();

    $("#wmd-button-row").append(
        '<li class="wmd-button" style="left: 400px; top: -10px;"><a id="insertSeparator" class="btn btn-default btn-sm">short/full text separator</a> </li>');

    jQuery.fn.extend({
        insertAtCaret: function(myValue){
            return this.each(function(i) {
                if (document.selection) {
                    this.focus();
                    var sel = document.selection.createRange();
                    sel.text = myValue;
                    this.focus();
                }
                else if (this.selectionStart || this.selectionStart == '0') {
                    var startPos = this.selectionStart;
                    var endPos = this.selectionEnd;
                    var scrollTop = this.scrollTop;
                    this.value = this.value.substring(0, startPos)+myValue+this.value.substring(endPos,this.value.length);
                    this.focus();
                    this.selectionStart = startPos + myValue.length;
                    this.selectionEnd = startPos + myValue.length;
                    this.scrollTop = scrollTop;
                } else {
                    this.value += myValue;
                    this.focus();
                }
            });
        }
    });

    $('#insertSeparator').click(function(){
        var $textarea = $('#wmd-input');
        $textarea.insertAtCaret('===cut===');
    });

    $("#postForm").validate({
        rules: {
            title: {
                required: true,
                minlength: 3
            },
            tags: {
                required: true
            },
            text: {
                required: true,
                minlength: 50
            }
        }
    });

    $(window).bind('beforeunload', function(){
        if ($.trim($('#wmd-input').val()) != '')
            return 'Postunuzu göndermediniz.';
    });

    $(document).on("submit", "form", function(event){
        $(window).off('beforeunload');
    });
    
    $("#profileForm").validate({
        rules: {
            websiteLink: {
                url: true
            },
            aboutText: {
                maxlength: 1000
            }
        }
    });
    
    console.log("works");
    
    var uploadMainphotoBtn = $('#uploadmainPhoto');
    var removeMainphotoBtn = $('#removemainPhoto');
    var pbMain = $('#mainPhotoUploadProgress').find('.progress-bar');
    var mainPhotoImg = $('#mainPhotoImg');
    var mainPhotoErrorLabel = $('#mainPhotoError');
    var mainPhotoSuccessLabel = $('#mainPhotoSuccess');

    $('#mainPhotoFileUploadInput').fileupload({
        url: window.mainPhotoUploadUrl,
        dataType: "json",
        send: function (e, data) {
            pbMain.css('width', '0');
            pbMain.switchClass('progress-bar-danger', 'progress-bar-success', 0);
            pbMain.parent().show();

            mainPhotoErrorLabel.hide();
            mainPhotoSuccessLabel.hide();

            uploadMainphotoBtn.addClass('disabled');
            removeMainphotoBtn.addClass('disabled');
        },
        done: function (e, data) {
            if (data.result.status == 'ok') {
            	console.log(data.result.link);
                mainPhotoImg.attr('src', window.imgBaseUrl + data.result.link);

                removeMainphotoBtn.show();

                mainPhotoSuccessLabel.show();
            }
            else {
                pbMain.switchClass('progress-bar-success', 'progress-bar-danger');

                var errMsg = 'Yükleme başarısız, ' + data.result.status;

                if (data.result.status == 'invalid_format') {
                    errMsg = "Sadece JPG ve PNG'ye izin veriliyor.";
                }

                mainPhotoErrorLabel.text(errMsg);
                mainPhotoErrorLabel.show();
            }
        },
        fail: function (e, data) {
            pbMain.switchClass('progress-bar-success', 'progress-bar-danger');
            console.log(data);
            mainPhotoErrorLabel.text("Resim yükleme başarısız. Lütfrn resmin PNG veya JPG olduğundan ve and 1 MB'ı geçmediğinden emin olunuz");
            mainPhotoErrorLabel.show();
        },
        always: function (e, data) {
            uploadMainphotoBtn.removeClass('disabled');
            removeMainphotoBtn.removeClass('disabled');
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            pbMain.css('width', progress + '%');
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');

    removeMainphotoBtn.click(function() {
        pbMain.parent().hide();

        mainPhotoErrorLabel.hide();
        mainPhotoSuccessLabel.hide();

        uploadMainphotoBtn.addClass('disabled');
        removeMainphotoBtn.addClass('disabled');

        var loadingIndicator = $('.loading-indicator');
        loadingIndicator.show();

        $.ajax({
            type: 'post',
            url: removeMainphotoBtn.attr('data-href'),
            success: function (data) {
                if (data == 'ok') {
                    mainPhotoImg.attr('src', window.noPhotoImgUrl);

                    removeMainphotoBtn.hide();
                }
                else {
                    mainPhotoErrorLabel.text('Hata: ' + data + '. Sayfayı yeniden yüklemeyi deneyiniz.');
                }
            },
            error: function () {
                mainPhotoErrorLabel.text('İstek gönderimi başarısız. Sayfayı yeniden yüklemeyi deneyiniz.');
            },
            complete: function() {
                loadingIndicator.hide();

                uploadMainphotoBtn.removeClass('disabled');
                removeMainphotoBtn.removeClass('disabled');
            }
        });
    });
    
   
});

