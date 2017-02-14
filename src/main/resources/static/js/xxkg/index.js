/**
 * Created by niannian on 2016/12/2.
 */
$(document).ready(function(){
    $(function(){
        $("[data-toggle='popover']").popover({
            html:true,
            content:'<select class="form-control">'+
            '<option value="1" selected>Game逻辑服务器</option>'+
            '<option value="2">中心服务器</option>'+
            '<option value="3">Hub服务器</option>'+
            '<option value="4">矿区服务器</option></select>'
        });
    });

    $('#xxkg .right .box .type').each(function(index,elem){
        $(this).on('hide.bs.popover', function () {
            var text=$(this).next().find('div.popover-content').find('select').val();
            text=parseInt(text);
            switch(text){
                case 1: $(this).text('Game逻辑服务器');$(this).attr("alt",1); break;
                case 2: $(this).text('中心服务器');$(this).attr("alt",2); break;
                case 3: $(this).text('Hub服务器');$(this).attr("alt",3); break;
                case 4: $(this).text('矿区服务器');$(this).attr("alt",4); break;
                default:break;
            }

        })
    })

    //新增服务器
    $('#add button.ok').click(function(){
        var id=$('#add input.id').val();
        var gm=$('#add input.gm').val();
        var account=$('#add input.account').val();
        var pass=$('#add input.pass').val();
        var type=$('#add select.type').val();
        $.ajax({
            type:'post',
            url:'/xxkg/inputserver',
            data:{
                id:id,
                gm:gm,
                account:account,
                pass:pass,
                type:type
            },
            success:function(msg){
                console.log('成功');
                location.reload();
                $('#add').fadeOut(500);
            }
        })
    })

    //修改服务器
    $('#xxkg .right .box span.edit').each(function(index,elem){
        $(this).click(function(){
            var id=$(this).attr("alt");
            var gm=$(this).parent().siblings("div.gm").text();
            var account=$(this).parent().siblings("div.account").text();
            var pass=$(this).parent().siblings("div.pass").text();
            var type=$(this).parent().siblings("div.type").attr('alt');
            console.log(id,gm,account,pass,type);
            $.ajax({
                type:'post',
                url:'/xxkg/changeserver',
                data:{
                    id:id,
                    gm:gm,
                    account:account,
                    pass:pass,
                    type:type
                },
                success:function(msg){
                    // console.log(msg);
                    $('#a_success').fadeIn(300,function(){
                        $('#a_success').fadeOut(600);
                    });
                    console.log("成功！");
                },
                error:function(){
                    $('#a_fail').fadeIn(300,function(){
                        $('#a_fail').fadeOut(600);
                    });
                }
            })
        })
    })
    //删除服务器
    $('#xxkg .right .box span.delete').each(function(index,elem){
        $(this).click(function(){
            $(this).css("color","red");
            var id=$(this).attr("alt");
            $('#delete button.ok').click(function(){
                console.log(id);
                $.ajax({
                    type:"get",
                    url:"/xxkg/deleteserver",
                    data:{
                        id:id
                    },
                    success:function (msg) {
                        // console.log(msg);
                        $(elem).css("color","#666");
                        // location.reload();
                        $(elem).parent(".col-xs-2").parent(".row").remove();
                        $('#delete').fadeOut(100);
                    }
                })
            })
        })
    })
})