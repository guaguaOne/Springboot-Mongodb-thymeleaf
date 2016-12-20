/**
 * Created by niannian on 2016/12/14.
 */
$(document).ready(function(){
    $('#admin button').click(function(){
        var account=$('#admin .form-group input.account').val();
        var password=$('#admin .form-group input.password').val();
        var passagin=$('#admin .form-group input.passagin').val();
        if(password==passagin){

            $.ajax({
                type:'get',
                url:'/newuser',
                data:{
                    account:account,
                    passwd:password
                },
                success:function(msg){
                    location.href="http://localhost:8000/newuser?account="+account+"&passwd="+password;
                }
            })
        }else{
            console.log(password);
            console.log(passagin);
            $('#admin .alert-warning').fadeIn();
            setTimeout(function(){
                $('#admin .alert-warning').fadeOut();
            },1000);
        }

    });
})