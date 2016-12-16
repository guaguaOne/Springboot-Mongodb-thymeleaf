/**
 * Created by niannian on 2016/12/7.
 */
$(document).ready(function(){
    //增加英雄
    $('#choose-hero .modal-body .row .col-sm-2 p').each(function(index,elem){
        $(this).click(function(){
            var name=$(elem).text();
            var id=$(elem).attr('alt');
            $('div.col-xs-10 .user-action .hero button.name').text(name).attr('alt',id);
            $('#choose-hero').hide();
        })
    })
    //增加物品
    $('#choose-good .modal-body .row .col-sm-2 p').each(function(index,elem){
        $(this).click(function(){
            var name=$(elem).text();
            var id=$(elem).attr('alt');
            $('div.col-xs-10 .user-action .good button.name').text(name).attr('alt',id);
            $('#choose-good').hide();
        })
    })
})