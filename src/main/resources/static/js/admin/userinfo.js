/**
 * Created by niannian on 2016/12/13.
 */
$(document).ready(function(){
    // $('#user .right ul.list-group li.list-group-item a').each(function(index,elem){
    //     $(this).click(function(){
    //         $('#user .user-list').addClass('hide');
    //         $('#user .user-detail').removeClass('hide');
    //     })
    // })
//分页
    $('#user .user-list .pagination li').each(function(index,elem){
        $(this).click(function(){
            console.log(location.href);
            var page=parseInt($(elem).find('a').text())-1;
            location.href="http://localhost:8000/?page="+page+"&size=10";
        })
    })
//用户详情
    $('#user .user-list .list-group .list-group-item').each(function(index,elem){
        $(this).click(function(){
            var account=$(elem).find('a.account').text();
            var curr=$('nav ul>li.name a').text();
            var url=location.href;
            url=url.substr(0,22);
            console.log(curr);
            console.log(account);
            location.href=url+"persondetial?account="+account+"&curr="+curr;
        })
    })

})