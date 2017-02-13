/**
 * Created by niannian on 2016/12/22.
 */
$(document).ready(function(){
    $('#xxkg .right .about-xxkg button').click(function(){
        var account=$('nav ul li.name a').text();
        var content=$('#xxkg .right .about-xxkg .row .notice').html();
        // console.log(content);
        content=content.replace(/font /g,"");
        content=content.replace(/font/g,"color");
        content=content.replace(/<br>/g,"\n");
        content=content.replace(/&nbsp;/g," ");
        content=content.replace(/<div>/g,"\n");
        content=content.replace(/<\/div>/g,"");
        content=content.replace(/"/g,"");
        // console.log(content);
        content=content.replace(/<color=#[0-9A-Za-z]{6}><\/color>/g,"");
        // content=content.replace(/#/g,"%23");
        // content=content.replace(/\//g,"%2F");
        // content=encodeURIComponent(content);
        console.log(content);
        $.ajax({
            type:"get",
            url:"/xxkg/notice",
            data:{
                account:account,
                content:content
            },
            success:function(msg){
                console.log("成功");
            }
        })
    })
    //选取字符串
    var move=false;
    $('#xxkg .right .about-xxkg .row .notice').mouseup(function(){
        if(move){
            var color=$('#xxkg .right .about-xxkg input.pick-color').val();
            selectWords(color);
            move=false;
        }
    });
    $('#xxkg .right .about-xxkg .row .notice').mousemove(function(e){
        if(e.which==1){
            move=true;
        }
    })
    function selectWords(color){
        var obj=window.getSelection();
        var range=obj.getRangeAt(0);
        var text=obj.toString();
        var node = document.createElement("font");
        // node.style.color = color;//rgb()
        node.setAttribute("color",color);//#453fed
        node.innerText = text;
        var newnode=document.createDocumentFragment().appendChild(node);
        range.deleteContents();
        range.insertNode(newnode);
    }
})