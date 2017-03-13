$(document).ready(function() {
    var container = $("#pageContent");

    container.on('click', 'a[data-action="vote"]', function(event){
        event.preventDefault();

        var btn = $(this);

        btn.addClass('voted');

        var voteType = btn.attr('data-vote-type');

        $.ajax({
            type: 'post',
            url: btn.attr('data-href'),
            success: function (data) {
                if (data == 'ok') {
                    btn.parent().find('a[data-action="vote"]').attr('data-action', '');

                    var valElement = btn.siblings('.rating-value');

                    var val = parseInt(valElement.html());

                    if (voteType == 'like')
                        val++;
                    else
                      val--;

                    valElement.html(val);

                    if (val == 0 || val == 1 || val == -1) {
                        valElement.removeClass('rating-value-no rating-value-negative rating-value-positive');
                        valElement.addClass(val == 0 ? 'rating-value-no' : (val == -1 ? 'rating-value-negative' : 'rating-value-positive'));
                    }
                }
                else {
                    btn.removeClass('voted');

                    if (data == 'own_comment') {
                        showErrorDialog('Kendi yorumunuza oylama yapamazsınız!');
                    }
                    else {
                        showErrorDialog('Oylama başarısız. Sayfayı yeniden yüklemeyi deneyin.');
                    }
                }
            },
            error: function () {
                btn.removeClass('voted');
                showErrorDialog('Oylama başarısız. Sayfayı yeniden yüklemeyi deneyin.');
            }
        });
    });
});

