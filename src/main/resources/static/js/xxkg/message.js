/**
 * Created by niannian on 2017/1/3.
 */
$(document).ready(function(){
    //刷新服务器
    $('#xxkg .right .message div.panel-body form button.refresh').click(function(){
        var arr=[];
        $('#xxkg .right .message div.panel-body form input:checked').each(function(index,elem){
            arr.push($(elem).val());
        })
        var str=arr.join(",");
        $.ajax({
            type:'post',
            url:'/refresh',
            data:{
                serid:str
            },
            success:function(msg){
                console.log(msg);
            }
        })
    })
    //发送通知
    $('#msg .modal-footer button.ok').click(function(){
        var content=$('#msg .modal-body textarea').val();
        var times=$('#msg .modal-body .times input').val();
        var type=$('#msg .modal-body .times select').val();
        type=parseInt(type);
        if(type==2){//分钟
            times=times*60;
        }else if(type==3){//小时
            times=times*3600;
        }
        var counts=$('#msg .modal-body .counts input').val();
        var arr=[];
        $('#xxkg .right .message div.panel-body form input:checked').each(function(index,elem){
            arr.push($(elem).val());
        })
        var str=arr.join(",");
        var date=new Date();
        var mon=date.getMonth()+1>10? date.getMonth()+1:'0'+(date.getMonth()+1);
        var day=date.getDate()>10? date.getDate():'0'+date.getDate();
        var d=date.getFullYear()+'-'+mon+'-'+day;
        var id=parseInt(date.getTime()/1000);
        $.ajax({
            type:'post',
            url:'/notify',
            data:{
                msg:content,
                serid:str,
                times:times,
                counts:counts,
                today:d,
                id:id
            },
            success:function (info) {
                console.log(info);
            }
        })
        var li=$("<li class='list-group-item'><div class='row'>" +
            "<div class='col-xs-8'><div class='col-xs-4'>"+d+"</div>" +
            "<div class='col-xs-8'>"+content+"</div></div>" +
            "<div class='col-xs-4'><div class='col-xs-3'>"+counts+"</div>" +
            "<div class='col-xs-3'>"+times+"</div>" +
            "<div class='col-xs-3'>进行中</div>" +
            "<div class='col-xs-3 delete' alt='"+id+"'>删除</div></div></div></li>");
        $('#xxkg .right .message .mess-box div.panel-body ul').append(li);
        $('#msg').hide();
    })
    //删除记录
    $('#xxkg .right .message div.panel-body div.delete').each(function(index,elem){
        $(this).click(function(){
            // alert($(elem).attr('alt'));
            var id=$(elem).attr('alt');
            $.ajax({
                type:'post',
                url:'/notice/delete',
                data:{
                    id:id
                },
                success:function(msg){
                    console.log(msg);
                    $(elem).parent().parent().parent(".list-group-item").remove();
                }
            })
        })
    })
})