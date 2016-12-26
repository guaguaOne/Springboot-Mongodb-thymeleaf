/**
 * Created by niannian on 2016/12/23.
 */
$(document).ready(function(){
    $('#xxkg .right .about-xxkg .row .xxkg-result ul li').each(function(index,elem){
        $(this).click(function(){
            var account=$('#xxkg .right .about-xxkg .row .forward .col-xs-10 .form-control span.item').length;
            if(account==3){
                alert("奖励小于3!");
            }else{
                var key=$(elem).attr("alt");
                var name=$(elem).text();
                var award=$('<span class="item"><span class="good-name" alt="'+key+'">'+name+'</span><span class="good-number" contenteditable="true">12</span></span>');
                $('#xxkg .right .about-xxkg .row .forward .col-xs-10 .form-control').append(award);
                $('#xxkg .right .about-xxkg .row .forward .col-xs-10 .form-control span.good-name').each(function(index,elem){
                    $(this).click(function(){
                        $(this).parent(".item").remove();
                    })
                })
            }
        })
    })
    //邮件发送
    $('#xxkg .right .about-xxkg .row button.email').click(function(){
        var id=$('#xxkg .right .about-xxkg .row .form-group input.id').val();
        var serverid=id%1000;
        var title=$('#xxkg .right .about-xxkg .row .form-group input.title').val();
        var content=$('#xxkg .right .about-xxkg .row .form-group textarea.content').val();
        var arr=[];
        $('#xxkg .right .about-xxkg .row .forward .col-xs-10 .form-control span.item span').each(function(index,elem){
            if($(this).attr("alt")){
                arr.push($(this).attr("alt"));
            }else{
                arr.push($(this).text());
            }
        });
        var awards=arr.join("|");
        if(awards.length==0){
            awards="20|1";
        }
        $.ajax({
            type:"post",
            url:"/sendemail",
            data:{
                id:id,
                title:title,
                content:content,
                awards:awards,
                serverid:serverid
            },
            success:function(msg){
                console.log(msg);
            }
        })
    })
})