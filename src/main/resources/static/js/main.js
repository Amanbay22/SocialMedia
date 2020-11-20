$(document).ready(function () {

    $('.nBtn, .eBtn').on('click', function (event) {
        event.preventDefault();

        var href = $(this).attr('href');
        var text = $(this).text(); //return New or Edit

        if (text === 'Edit') {
            $.get(href, function (post) {
                $('.myForm #id').val(post.id);
                $('.myForm #title').val(post.title);
                $('.myForm #shortContent').val(post.shortContent);
                $('.myForm #content').val(post.content);
            });
            $('.myForm #exampleModal').modal();
        } else {
            $('.myForm #example1Modal').modal();
        }
    });

    $('.delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });
});

