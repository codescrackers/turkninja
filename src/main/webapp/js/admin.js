$(document).ready(function() {
    var postsContainer = $("#postsContainer");

    postsContainer.on('click', 'a[data-action="hidePost"]', function(event){
        event.preventDefault();

        var postTitle = getPostTitle(this);

        var btn = $(this);

        var loadingIndicator = btn.closest('.post').find('.postaction-loading-indicator');

        var delBtn = btn.closest('.post-actions').find('a[data-action="deletePost"]');

        bootbox.dialog({
            title: 'Gönderiyi gizle',
            message: 'ABu gönderiyi diğer kullanıcılardan gizlmek istediğinizden emin misiniz? Gönderi : <b>' + postTitle + '</b> ',
            buttons: {
                cancel: {
                    label: 'İptal'
                },
                main: {
                    label: 'Gizle',
                    className: 'btn-primary',
                    callback: function() {
                        loadingIndicator.show();

                        $.ajax({
                            type: 'post',
                            url: btn.attr('data-href'),
                            success: function (data) {
                                loadingIndicator.hide();

                                if (data == 'ok') {
                                    btn.attr('data-action', 'unhidePost');
                                    btn.attr('data-href', btn.attr('data-href').replace('hide', 'unhide'));
                                    btn.html('unhide');

                                    btn.closest('.post').find('.post-content').append('<div class="hidden-post"><span>Not visible for users</span></div>');

                                    delBtn.show();
                                }
                                else {
                                    showErrorDialog('Hata: ' + data + '. Lütfen sayfayı yeniden yüklemeyi deneyin.');
                                }
                            },
                            error: function () {
                                loadingIndicator.hide();

                                showErrorDialog('İstek gönderimi balşarısız. Lütfen sayfayı yeniden yüklemeyi deneyin.');
                            }
                        });
                    }
                }
            }
        });
    });

    postsContainer.on('click', 'a[data-action="unhidePost"]', function(event){
        event.preventDefault();

        var btn = $(this);

        var loadingIndicator = btn.closest('.post').find('.postaction-loading-indicator');

        var delBtn = btn.closest('.post-actions').find('a[data-action="deletePost"]');

        loadingIndicator.show();

        $.ajax({
            type: 'post',
            url: btn.attr('data-href'),
            success: function (data) {
                loadingIndicator.hide();

                if (data == 'ok') {
                    btn.attr('data-action', 'hidePost');
                    btn.attr('data-href', btn.attr('data-href').replace('unhide', 'hide'));
                    btn.html('hide');

                    btn.closest('.post').find('.hidden-post').remove();

                    delBtn.hide();
                }
                else {
                    showErrorDialog('Hata: ' + data + '. Lütfen sayfayı yeniden yüklemeyi deneyin.');
                }
            },
            error: function () {
                loadingIndicator.hide();

                showErrorDialog('İstek gönderimi balşarısız. Lütfen sayfayı yeniden yüklemeyi deneyin.');
            }
        });
    });

    postsContainer.on('click', 'a[data-action="deletePost"]', function(event){
        event.preventDefault();

        var postTitle = getPostTitle(this);
        var postId = getPostId(this);

        var btn = $(this);

        var loadingIndicator = btn.closest('.post').find('.postaction-loading-indicator');

        bootbox.dialog({
            title: 'Gönderiyi sil',
            message: 'Gönderiyi silmeye emin misiniz? Gönderi :<b>' + postTitle + '</b>? Tekrar geri alma şansınız olmayacak.',
            buttons: {
                cancel: {
                    label: 'Cancel'
                },
                main: {
                    label: 'Delete',
                    className: 'btn-danger',
                    callback: function() {
                        loadingIndicator.show();

                        $.ajax({
                            type: 'post',
                            url: btn.attr('data-href'),
                            success: function (data) {
                                loadingIndicator.hide();

                                if (data == 'ok') {
                                    btn.closest('.post').remove();

                                    if (window.location.href.indexOf('posts/' + postId) > -1) {
                                        window.location.href = window.location.href.replace('/' + postId, '');
                                    }
                                }
                                else {
                                    showErrorDialog('Hata: ' + data + '. Lütfen sayfayı tekrar yüklemeyi deneyiniz.');
                                }
                            },
                            error: function () {
                                loadingIndicator.hide();

                                showErrorDialog('İstek gönderimi balşarısız. Lütfen sayfayı tekrar yüklemeyi deneyiniz.');
                            }
                        });
                    }
                }
            }
        });
    });
});

