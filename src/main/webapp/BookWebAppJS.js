/* 
 * 
 */

$(document).ready(function() {
    $(".editAuthor").hide();
    
    $('.editButton').click(function () {
        var authorId = $(this).attr("id");
        console.log(authorId);
        $('tr#'+authorId+'').toggle();
    })
});




