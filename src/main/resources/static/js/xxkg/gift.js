/**
 * Created by niannian on 2017/1/4.
 */
$(document).ready(function(){
    //搜索物品
    $.ajax({
        type:'post',
        url:'/goods',
        success:function(msg){
            // console.log(msg);
            var len=msg.length;
            //搜索物品
            $('#init-awards .modal-header input').keyup(function(){
                $('#init-awards .modal-body').html("");
                var input=$(this).val();
                for(var i=0;i<len;i++){
                    var aim=msg[i].good;
                    var ke=msg[i].key;
                    ke=ke.toString();
                    if((aim.indexOf(input)>=0)||ke.indexOf(input)>=0){
                        // console.log(aim);
                        var li=$('<div class="col-xs-4" alt="'+ke+'">'+aim+'</div>');
                        $('#init-awards .modal-body').append(li);
                        (function (ai,k) {
                            li.click(function(){
                                var len=$('#init-awards .modal-footer div.sel span.name').length;
                                if(len>=3){
                                    alert("3个奖励，不能再多");
                                }else{
                                    var g=$('<span class="item"><span class="name" alt="'+k+'">'+ai+'</span><span class="number" contenteditable="true">100</span></span>');
                                    $('#init-awards .modal-footer div.sel').append(g);
                                    //删除选中
                                    $('#init-awards .modal-footer div.sel span.name').each(function(index,elem){
                                        $(this).click(function(){
                                            // alert(123);
                                            $(elem).parent().remove();
                                        })
                                    })
                                }
                            })
                        })(aim,ke);
                    }
                }
            })
            $('#bug-awards .modal-header input').keyup(function(){
                $('#bug-awards .modal-body').html("");
                var input=$(this).val();
                for(var i=0;i<len;i++){
                    var aim=msg[i].good;
                    var ke=msg[i].key;
                    ke=ke.toString();
                    if((aim.indexOf(input)>=0)||ke.indexOf(input)>=0){
                        // console.log(aim);
                        var li=$('<div class="col-xs-4" alt="'+ke+'">'+aim+'</div>');
                        $('#bug-awards .modal-body').append(li);
                        (function (ai,k) {
                            li.click(function(){
                                var len=$('#bug-awards .modal-footer div.sel span.name').length;
                                if(len>=3){
                                    alert("3个奖励，不能再多");
                                }else{
                                    var g=$('<span class="item"><span class="name" alt="'+k+'">'+ai+'</span><span class="number" contenteditable="true">100</span></span>');
                                    $('#bug-awards .modal-footer div.sel').append(g);
                                    //删除选中
                                    $('#bug-awards .modal-footer div.sel span.name').each(function(index,elem){
                                        $(this).click(function(){
                                            $(elem).parent().remove();
                                        })
                                    })
                                }
                            })
                        })(aim,ke);
                    }
                }
            })
            $('#normal-awards .modal-header input').keyup(function(){
                $('#normal-awards .modal-body').html("");
                var input=$(this).val();
                for(var i=0;i<len;i++){
                    var aim=msg[i].good;
                    var ke=msg[i].key;
                    ke=ke.toString();
                    if((aim.indexOf(input)>=0)||ke.indexOf(input)>=0){
                        // console.log(aim);
                        var li=$('<div class="col-xs-4" alt="'+ke+'">'+aim+'</div>');
                        $('#normal-awards .modal-body').append(li);
                        (function (ai,k) {
                            li.click(function(){
                                var len=$('#normal-awards .modal-footer div.sel span.name').length;
                                if(len>=3){
                                    alert("3个奖励，不能再多");
                                }else{
                                    var g=$('<span class="item"><span class="name" alt="'+k+'">'+ai+'</span><span class="number" contenteditable="true">100</span></span>');
                                    $('#normal-awards .modal-footer div.sel').append(g);
                                    //删除选中
                                    $('#normal-awards .modal-footer div.sel span.name').each(function(index,elem){
                                        $(this).click(function(){
                                            $(elem).parent().remove();
                                        })
                                    })
                                }
                            })
                        })(aim,ke);
                    }
                }
            })
        }
    })
    //选取物品
    $('#init-awards .modal-body>div').each(function(index,elem){
        $(this).click(function(){
            // alert($(elem).text());
            var len=$('#init-awards .modal-footer div.sel span.name').length;
            if(len>=3){
                alert("3个奖励，不能再多");
            }else{
                var key=$(elem).attr('alt');
                var name=$(elem).text();
                var g=$('<span class="item"><span class="name" alt="'+key+'">'+name+'</span><span class="number" contenteditable="true">100</span></span>');
                $('#init-awards .modal-footer div.sel').append(g);
                //删除选中
                $('#init-awards .modal-footer div.sel span.name').each(function(index,elem){
                    $(this).click(function(){
                        $(elem).parent(".item").remove();
                    })
                })
            }
        })
    })
    $('#bug-awards .modal-body>div').each(function(index,elem){
        $(this).click(function(){
            // alert($(elem).text());
            var len=$('#bug-awards .modal-footer div.sel span.name').length;
            if(len>=3){
                alert("3个奖励，不能再多");
            }else{
                var key=$(elem).attr('alt');
                var name=$(elem).text();
                var g=$('<span class="item"><span class="name" alt="'+key+'">'+name+'</span><span class="number" contenteditable="true">100</span></span>');
                $('#bug-awards .modal-footer div.sel').append(g);
                //删除选中
                $('#bug-awards .modal-footer div.sel span.name').each(function(index,elem){
                    $(this).click(function(){
                        $(elem).parent(".item").remove();
                    })
                })
            }
        })
    })
    $('#normal-awards .modal-body>div').each(function(index,elem){
        $(this).click(function(){
            // alert($(elem).text());
            var len=$('#normal-awards .modal-footer div.sel span.name').length;
            if(len>=3){
                alert("3个奖励，不能再多");
            }else{
                var key=$(elem).attr('alt');
                var name=$(elem).text();
                var g=$('<span class="item"><span class="name" alt="'+key+'">'+name+'</span><span class="number" contenteditable="true">100</span></span>');
                $('#normal-awards .modal-footer div.sel').append(g);
                //删除选中
                $('#normal-awards .modal-footer div.sel span.name').each(function(index,elem){
                    $(this).click(function(){
                        $(elem).parent(".item").remove();
                    })
                })
            }
        })
    })

    //初始化礼包确认
    $('#init-awards .modal-footer button.ok').click(function(index,elem){
        var str="";
        var temp=[];
        $('#init-awards .modal-footer div.sel span.item>span').each(function(index,elem){
            if(index%2==0){
                var key=$(elem).attr("alt");
                temp.push(key);
            }else{
                var num=$(elem).text();
                temp.push(num);
            }
            str+=$(elem).text();
        })
        $('#init-gift div.form-control').text(str);
        $('#init-awards').hide();
        var awards=temp.join(",");
        $('#init-gift input.awards').val(awards);
    })
    //初始化生成
    $('#gift .init-gift button').click(function(){
        var expire=$('#init-gift .date input').val();
        var num=$('#init-gift .num input').val();
        var awards=$('#init-gift input.awards').val();
        var d=new Date();
        var time=parseInt(d.getTime()/1000);
        console.log('mode='+time+';expire='+expire+';num='+num+';awards='+awards);
        $.ajax({
            type:'post',
            url:'/creategift',
            data:{
                mode:time,
                expire:expire,
                num:num,
                awards:awards
            },
            success:function(msg){
                console.log(msg);
                var len=msg.length;
                var temp=[];
                for(var i=0;i<len;i++){
                    temp.push(msg[i]);
                }
                var str=temp.join("\r\n");
                var to=today();
                $.ajax({
                    type:'post',
                    url:'/doc',
                    data:{
                        code:str,
                        name:time,
                        createtime:to,
                        id:time
                    },
                    success:function(info){
                        console.log(info);
                    }
                })
            }
        })
    });
    //初始化删除文档
    $('#gift .init-gift span.delete').each(function(index,elem){
        $(this).click(function(){
            var id=$(elem).attr('alt');
            var name=$(elem).parent().parent().find('div.docname').text();
            $.ajax({
                type:'post',
                url:"/doc/delete",
                data:{
                    id:id,
                    name:name
                },
                success:function(msg){
                    // console.log(msg);
                    $(elem).parent().parent("li.list-group-item").remove();
                }
            })
        })
    })
    //初始化下载文档
    // $('#gift .init-gift span.download a').each(function(index,elem){
    //     $(this).click(function(){
    //         var id=$(elem).attr('alt');
    //         $.ajax({
    //             type:'get',
    //             url:'/download',
    //             data:{
    //                 id:id
    //             },
    //             success:function(msg){
    //                 console.log(msg);
    //             }
    //         })
    //     })
    // })
    //普通礼包确认
    $('#normal-awards .modal-footer button.ok').click(function(index,elem){
        var str="";
        var temp=[];
        $('#normal-awards .modal-footer div.sel span.item>span').each(function(index,elem){
            if(index%2==0){
                var key=$(elem).attr("alt");
                temp.push(key);
            }else{
                var num=$(elem).text();
                temp.push(num);
            }
            str+=$(elem).text();
        })
        $('#normal-gift div.form-control').text(str);
        $('#normal-awards').hide();
        var awards=temp.join(",");
        $('#normal-gift input.awards').val(awards);
    })
    //普通生成
    $('#gift .normal-gift button').click(function(){
        var expire=$('#normal-gift .date input').val();
        var num=$('#normal-gift .num input').val();
        var awards=$('#normal-gift input.awards').val();
        var d=new Date();
        var time=parseInt(d.getTime()/1000);
        console.log('mode='+time+';expire='+expire+';num='+num+';awards='+awards);
        $.ajax({
            type:'post',
            url:'/creategift',
            data:{
                mode:time,
                expire:expire,
                num:num,
                awards:awards
            },
            success:function(msg){
                // console.log(msg);
                $('#gift .normal-gift ul').html("");
                var len=msg.length;
                var to=today();
                for(var i=0;i<len;i++){
                    var li=$("<li class='list-group-item'>" +
                        "<div class='col-xs-4'>"+msg[i]+"</div>" +
                        "<div class='col-xs-4'>"+time+"</div>" +
                        "<div class='col-xs-4'>"+to+"</div>" +
                        "<div class='clear'></div>" +
                        "</li>");
                    $('#gift .normal-gift ul').append(li);
                }
            }
        })
    });
    //BUG礼包确认
    $('#bug-awards .modal-footer button.ok').click(function(index,elem){
        var str="";
        var temp=[];
        $('#bug-awards .modal-footer div.sel span.item>span').each(function(index,elem){
            if(index%2==0){
                var key=$(elem).attr("alt");
                temp.push(key);
            }else{
                var num=$(elem).text();
                temp.push(num);
            }
            str+=$(elem).text();
        })
        $('#bug-gift div.form-control').text(str);
        $('#bug-awards').hide();
        var awards=temp.join(",");
        $('#bug-gift input.awards').val(awards);
    })
    //BUG生成
    $('#gift .bug-gift button').click(function(){
        var expire=$('#bug-gift .date input').val();
        var num=$('#bug-gift .num input').val();
        var awards=$('#bug-gift input.awards').val();
        var d=new Date();
        console.log('expire='+expire+';num='+num+';awards='+awards);
        $.ajax({
            type:'post',
            url:'/creategift',
            data:{
                mode:999,
                expire:expire,
                num:num,
                awards:awards
            },
            success:function(msg){
                console.log(msg);
                $('#gift .bug-gift ul').html("");
                var len=msg.length;
                var to=today();
                for(var i=0;i<len;i++){
                    var li=$("<li class='list-group-item'>" +
                        "<div class='col-xs-4'>"+msg[i]+"</div>" +
                        "<div class='col-xs-4'>"+999+"</div>" +
                        "<div class='col-xs-4'>"+to+"</div>" +
                        "<div class='clear'></div>" +
                        "</li>");
                    $('#gift .bug-gift ul').append(li);
                }
            }
        })
    });
    function today(){
        var d=new Date();
        var year=d.getFullYear();
        var mon=d.getMonth()+1>10? d.getMonth()+1:'0'+(d.getMonth()+1);
        var day=d.getDate()>10? d.getDate():'0'+d.getDate();
        var today=year+'-'+mon+'-'+day;
        return today;
    }
})