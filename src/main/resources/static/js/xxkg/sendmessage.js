/**
 * Created by niannian on 2016/12/12.
 */
$(document).ready(function(){
    //选择Game逻辑服务器
    $('#about-message .page-body button.btn-info').each(function(index,elem){
        $(this).click(function(){
            if($(this).is('.active')){
                $(this).removeClass('active');
            }else{
                $(this).addClass('active');
            }
        })
    })
    //参数设置，单位选择
    $('#about-message .row .input-group-btn .dropdown-menu>li').each(function(index,elem){
        $(this).click(function(){
            var val= $(elem).text();
            var type= $(elem).attr('alt');
            $('#about-message .row .input-group-btn button span.unit').text(val).attr('alt',type);
        })
    })
})