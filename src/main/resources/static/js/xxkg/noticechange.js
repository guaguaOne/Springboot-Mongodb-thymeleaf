/**
 * Created by niannian on 2016/12/5.
 */
$(document).ready(function(){
    $('#picker').colpick({
        flat:false,
        layout:'hex',
        color:'hex',
        onSubmit:function(color,color2){
            // console.log(color2);
            $('.notice-change .col-xs-4 .col-pre').css('color','#'+color2);
        }
    });
    $('.notice-change .col-xs-8 .box').mouseup(function(){
        var color=$('.notice-change .col-xs-4 .col-pre').css('color');
        // console.log(color);
        selectWords(color);
    });
    function selectWords(color){
        var obj=window.getSelection();
        var range=obj.getRangeAt(0);
        var text=obj.toString();
        var node = document.createElement("font");
        node.style.color = color;
        node.innerText = text;
        var newnode=document.createDocumentFragment().appendChild(node);
        range.deleteContents();
        range.insertNode(newnode);
        console.log(range);
    }
})