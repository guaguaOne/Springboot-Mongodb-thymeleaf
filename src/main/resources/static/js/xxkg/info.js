/**
 * Created by niannian on 2016/12/27.
 */
$(document).ready(function () {
    $.ajax({
        type:'post',
        url:'/goods',
        success:function(msg){
            // console.log(msg);
            var len=msg.length;
            //搜索物品
            $('#xxkg .right .about-xxkg .info .search-info .search-good input').keyup(function(){
                $('#xxkg .right .about-xxkg .info .search-info .search-good ul').html("");
                var input=$(this).val();
                for(var i=0;i<len;i++){
                    var aim=msg[i].good;
                    var ke=msg[i].key;
                    ke=ke.toString();
                    if((aim.indexOf(input)>=0)||ke.indexOf(input)>=0){
                        // console.log(aim);
                        var li=$('<li class="list-group-item" alt="'+ke+'">'+aim+'</li>');
                        $('#xxkg .right .about-xxkg .info .search-info .search-good ul').append(li);
                        (function (ai,k) {
                            li.click(function(){
                                $('#xxkg .right .about-xxkg .info .action-info div.goods .panel-body h3').html("");
                                var g=$('<span class="good-name" alt="'+k+'">'+ai+'</span><span class="good-number" contenteditable="true">100</span>');
                                $('#xxkg .right .about-xxkg .info .action-info div.goods .panel-body h3').append(g);
                            })
                        })(aim,ke);
                    }
                }
            })
        }
    })
    $.ajax({
        type:'post',
        url:'/hero',
        success:function(msg){
            // console.log(msg);
            var len=msg.length;
            //搜索英雄
            $('#xxkg .right .about-xxkg .info .search-info .search-hero input').keyup(function(){
                $('#xxkg .right .about-xxkg .info .search-info .search-hero ul').html("");
                var input=$(this).val();
                for(var i=0;i<len;i++){
                    var aim=msg[i].name;
                    var ke=msg[i].key;
                    ke=ke.toString();
                    if((aim.indexOf(input)>=0)||ke.indexOf(input)>=0){
                        // console.log(aim);
                        var li=$('<li class="list-group-item" alt="'+ke+'">'+aim+'</li>');
                        $('#xxkg .right .about-xxkg .info .search-info .search-hero ul').append(li);
                        (function (ai,k) {
                            li.click(function(){
                                $('#xxkg .right .about-xxkg .info .action-info div.hero .panel-body h3').html("");
                                var g=$('<span class="hero-name" alt="'+k+'">'+ai+'</span>');
                                $('#xxkg .right .about-xxkg .info .action-info div.hero .panel-body h3').append(g);
                            })
                        })(aim,ke);
                    }
                }
            })
        }
    })
    var flag=0;
    $("[name='my-checkbox']").bootstrapSwitch({
        state:true,
        onSwitchChange:function(e,state){
            console.log(state);
            if(state){//禁言
                flag=0;
            }else{//禁止登陆
                flag=1;
            }
        }
    });

    //禁言，禁止登陆
    $('#xxkg .right .about-xxkg .info .action-info div.no button.ok').click(function(){
        var id=$('#xxkg .right .about-xxkg .info .action-info div.no input.id').val();
        var name=$('#xxkg .right .about-xxkg .info .action-info div.no input.name').val();
        var serid=id%1000;
        var time=$('#xxkg .right .about-xxkg .info .action-info div.no select.time').val();
        var action=0;
        console.log("serid:"+serid+"name:"+name+"time:"+time+"flag:"+flag);
        $.ajax({
            type:'post',
            url:'/nosay',
            data:{
                serid:serid,
                name:name,
                time:time,
                flag:flag,
                action:action
            },
            success:function(msg){
                console.log(msg);
            }
        })
    })
    //解禁
    $('#xxkg .right .about-xxkg .info .action-info div.no button.no').click(function(){
        var id=$('#xxkg .right .about-xxkg .info .action-info div.no input.id').val();
        var name=$('#xxkg .right .about-xxkg .info .action-info div.no input.name').val();
        var serid=id%1000;
        var time=100;
        var action=1;
        console.log("serid:"+serid+"name:"+name+"time:"+time+"flag:"+flag);
        $.ajax({
            type:'post',
            url:'/nosay',
            data:{
                serid:serid,
                name:name,
                time:time,
                flag:flag,
                action:action
            },
            success:function(msg){
                console.log(msg);
            }
        })
    })

    //增加玩家物品
    $('#xxkg .right .about-xxkg .info .action-info div.goods button.ok').click(function(){
        var id=$('#xxkg .right .about-xxkg .info .action-info div.goods input.id').val();
        var serid=id%1000;
        var key=$('#xxkg .right .about-xxkg .info .action-info div.goods span.good-name').attr("alt");
        var val=$('#xxkg .right .about-xxkg .info .action-info div.goods span.good-number').text();
        console.log("id:"+id+"serid:"+serid+"key:"+key+"val:"+val);
        $.ajax({
            type:'post',
            url:'/additem',
            data:{
                id:id,
                serid:serid,
                key:key,
                val:val
            },
            success:function(msg){
                console.log(msg);
            }
        })
    })
    //增加玩家英雄
    $('#xxkg .right .about-xxkg .info .action-info div.hero button.ok').click(function(){
        var id=$('#xxkg .right .about-xxkg .info .action-info div.hero input.id').val();
        var serid=id%1000;
        var key=$('#xxkg .right .about-xxkg .info .action-info div.hero span.hero-name').attr("alt");
        console.log("id:"+id+"serid:"+serid+"key:"+key);
        $.ajax({
            type:'post',
            url:'/addhero',
            data:{
                id:id,
                serid:serid,
                key:key
            },
            success:function(msg){
                console.log(msg);
            }
        })
    })
    //选取物品
    $('#xxkg .right .about-xxkg .info .search-info .search-good ul li').each(function(index,elem){
        $(this).click(function(){
            // alert($(elem).text());
            $('#xxkg .right .about-xxkg .info .action-info div.goods .panel-body h3').html("");
            var key=$(elem).attr('alt');
            var name=$(elem).text();
            var g=$('<span class="good-name" alt="'+key+'">'+name+'</span><span class="good-number" contenteditable="true">100</span>');
            $('#xxkg .right .about-xxkg .info .action-info div.goods .panel-body h3').append(g);
        })
    })
    //选取英雄
    $('#xxkg .right .about-xxkg .info .search-info .search-hero ul li').each(function(index,elem){
        $(this).click(function(){
            // alert($(elem).text());
            $('#xxkg .right .about-xxkg .info .action-info div.hero .panel-body h3').html("");
            var key=$(elem).attr('alt');
            var name=$(elem).text();
            var g=$('<span class="good-name" alt="'+key+'">'+name+'</span>');
            $('#xxkg .right .about-xxkg .info .action-info div.hero .panel-body h3').append(g);
        })
    })
})