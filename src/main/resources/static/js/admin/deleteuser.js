/**
 * Created by niannian on 2016/12/30.
 */
$(document).ready(function(){
    $('#delete li a.delete').each(function(index,elem){
        $(this).click(function(){
            var delacc=$(elem).attr('alt');
            $.ajax({
                type:'get',
                url:'/deleteuser',
                data:{
                    delacc:delacc
                },
                success:function(msg){
                    console.log(msg);
                    $(elem).parent("li").remove();
                }
            })
        });
    })
})