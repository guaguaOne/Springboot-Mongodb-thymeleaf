/**
 * Created by niannian on 2016/12/8.
 */
$(document).ready(function(){
    $('#login-register .row .col-xs-8 .register button').click(function(){
        $.ajax({
            type:'get',
            url:'/management/register',
            data:{
                account:000,
                passwd:000
            },
            success:function(msg){
                console.log(msg);
            },
            error:function(msg){
                console.log(msg);
            }
        })
    })
})