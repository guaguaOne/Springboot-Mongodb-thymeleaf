/**
 * Created by niannian on 2016/12/14.
 */
$(document).ready(function(){
    $('#admin button').click(function(){
        var account=$('#admin .form-group input.account').val();
        var password=$('#admin .form-group input.password').val();
        $.ajax({
            type:'get',
            url:'/management/register',
            data:{
                account:account,
                passwd:password
            },
            success:function(msg){
                console.log(msg);
                if(msg.code==0){
                    $('#admin .alert-success').fadeIn(800,function(){
                        $(this).fadeOut(1000,function(){
                            location.href="/";
                        });
                    })
                }
                if(msg.code==-1){
                    $('#admin .alert-danger').fadeIn(800,function(){
                        $(this).fadeOut(1000);
                    })
                }
            },
            error:function(msg){
                console.log(msg);
            }
        })
    });
})