/**
 * Created by niannian on 2016/12/13.
 */
//点击头像，跳到用户中心
$(document).ready(function () {
    $('nav ul li.face img').click(function(){
        var curr=$('nav ul li.name').text();
        location.href="http://localhost:8000/usercenter?curr="+curr;
    })
})
