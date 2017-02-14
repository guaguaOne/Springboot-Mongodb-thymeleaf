/**
 * Created by niannian on 2016/12/23.
 */
$(document).ready(function(){
    comm();
    var arr=[];
    $('#xxkg .right .about-xxkg .row .xxkg-result ul li').each(function(index,elem) {
        var name = $(this).text();
        var key = $(this).attr("alt");
        var irr = {
            "key": key,
            "name": name
        };
        arr.push(irr);
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
                $('#a_success').fadeIn(300,function(){
                    $('#a_success').fadeOut(600);
                });
            },
            error:function(msg){
                $('#a_fail').fadeIn(300,function(){
                    $('#a_fail').fadeOut(600);
                });
            }
        })
    })

    //模糊搜索
    $('#xxkg .right .about-xxkg .row .search .col-xs-2').click(function(){
        // console.log(arr);
        $('#xxkg .right .about-xxkg .row .xxkg-result ul').html("");
        var val=$('#xxkg .right .about-xxkg .row .search input').val();
        for(var j=0;j<arr.length;j++){
            var name=arr[j].name;
            var key=arr[j].key;
            if((name.indexOf(val)!=-1)||(key.indexOf(val)!=-1)){
                var li=$("<li class='list-group-item' alt='"+key+"'>"+name+"</li>");
                $('#xxkg .right .about-xxkg .row .xxkg-result ul').append(li);
            }
        }
        comm();
    })

    function comm(){
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
    }
})