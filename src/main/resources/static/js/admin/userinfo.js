/**
 * Created by niannian on 2016/12/13.
 */
$(document).ready(function(){
    $('#user .right ul.list-group li.list-group-item a').each(function(index,elem){
        $(this).click(function(){
            $('#user .user-list').addClass('hide');
            $('#user .user-detail').removeClass('hide');
        })
    })

    // $.ajax({
    //     type:'get',
    //     url:'/management/getAccount',
    //     data:{
    //         account:"leon",
    //         flag:2//登录注册跳转 2查询某个用户
    //     },
    //     success:function(msg){
    //         console.log(msg);
    //         // console.log(msg.role);
    //         // if(msg.role=="USER"){
    //         //     $('#user').addClass('hide');
    //         //     $('#welcome').show();
    //         // }
    //         // if(msg.role=="ADMIN"){
    //         //     //初始化数据
    //         //     $('#welcome').hide();
    //         //     $('#user').show();
    //         //     $.ajax({
    //         //         type:'get',
    //         //         url:'/management/getAccounts',
    //         //         data:{
    //         //             page:0,
    //         //             size:10
    //         //         },
    //         //         success:function(info){
    //         //             console.log(info.content);
    //         //             var users=info.content;
    //         //             var len=users.length;
    //         //             for(var i=0;i<len;i++){
    //         //                 var li=$("<li class='list-group-item'><a href='#'><span class='account'>"+users[i]['account']+"</span>---创建于"+users[i]['createtime']['year']+"年"+users[i]['createtime']['monthOfYear']+"月"+users[i]['createtime']['dayOfMonth']+"日</a></li>");
    //         //                 $('#user .user-list .list-group').append(li);
    //         //             }
    //         //             $('#user .user-list .list-group .list-group-item a').each(function(index,elem){
    //         //                 $(this).click(function(){
    //         //                     var curr=$(elem).find('span.account').text();
    //         //                     $('#user .user-list').hide();
    //         //                     $('#user .user-detail').show();
    //         //                     // console.log(curr);
    //         //                     $('#user .right .breadcrumb li.curr').text(curr);
    //         //                     $.ajax({
    //         //                         type:'get',
    //         //                         url:'/management/getAccount',
    //         //                         data:{
    //         //                             account:curr,
    //         //                             flag:2
    //         //                         },
    //         //                         success:function(data){
    //         //                             var mess=$("<li class='list-group-item'>账号："+data.account+"</li>" +
    //         //                                 "<li class='list-group-item'>密码："+data.passwd+"</li>" +
    //         //                                 "<li class='list-group-item'>注册时间："+data.createtime.year+"-"+data.createtime.monthOfYear+"-"+data.createtime.dayOfMonth+"</li>" +
    //         //                                 "<li class='list-group-item'>当前权限："+data.role+"</li>");
    //         //                             $('#user .user-detail .person .person-info ul.list-group').append(mess);
    //         //                         }
    //         //                     })
    //         //                 })
    //         //             })
    //         //         }
    //         //     })
    //         // }
    //         // $('nav ul.nav li.name a').text(msg.account);
    //     }
    // })
    //分页查询
    $('#user .user-list .pagination li').each(function(index,elem){
        $(this).click(function(){
            $('#user .user-list .list-group').html('');
            var page=parseInt($(elem).find('a').text())-1;
            console.log(page);
            $.ajax({
                type:'get',
                url:'/management/getAccounts',
                data:{
                    page:page,
                    size:10
                },
                success:function(info){
                    console.log(info.content);
                    var users=info.content;
                    var len=users.length;
                    for(var i=0;i<len;i++){
                        var li=$("<li class='list-group-item'><a href='#'><span class='account'>"+users[i]['account']+"</span>---创建于"+users[i]['createtime']['year']+"年"+users[i]['createtime']['monthOfYear']+"月"+users[i]['createtime']['dayOfMonth']+"日</a></li>");
                        $('#user .user-list .list-group').append(li);
                    }
                    $('#user .user-list .list-group .list-group-item a').each(function(index,elem){
                        $(this).click(function(){
                            var curr=$(elem).find('span.account').text();
                            $('#user .user-list').hide();
                            $('#user .user-detail').show();
                            console.log(curr);
                            $('#user .right .breadcrumb li.curr').text(curr);
                            $.ajax({
                                type:'get',
                                url:'/management/getAccount',
                                data:{
                                    account:curr,
                                    flag:2
                                },
                                success:function(data){
                                    var mess=$("<li class='list-group-item'>账号："+data.account+"</li>" +
                                        "<li class='list-group-item'>密码："+data.passwd+"</li>" +
                                        "<li class='list-group-item'>注册时间："+data.createtime.year+"-"+data.createtime.monthOfYear+"-"+data.createtime.dayOfMonth+"</li>" +
                                        "<li class='list-group-item'>当前权限："+data.role+"</li>");
                                    $('#user .user-detail .person .person-info ul.list-group').append(mess);
                                }
                            })
                        })
                    })
                }
            })
        })
    })

    //授权
    // $('')
})