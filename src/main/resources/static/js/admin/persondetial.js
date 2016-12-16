/**
 * Created by niannian on 2016/12/16.
 */
$('#person-detial .person button').click(function(){
    var auth=[];
    $('#person-detial .person .add-pri input[type="checkbox"]:checked').each(function(index,elem){
        auth.push($(this).val());
    })
    var str=auth.join(',');
    var curr=$('nav ul>li.name a').text();
    var user=$("#person-detial ol li.curr").text();
    location.href="http://localhost:8000/setAuth?curr="+curr+"&account="+user+"&auth="+str;
})