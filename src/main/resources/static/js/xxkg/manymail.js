/**
 * Created by niannian on 2016/12/12.
 */
$(document).ready(function(){
    $('#about-mails .search-awards .result li').each(function(index,elem){
        $(this).click(function(){
            var len=$('#put-result span.item').length;
            if(len<3){
                var name=$(elem).text();
                var id=$(elem).attr('alt');
                var item=$('<span class="item"><span class="name" alt="'+id+'">'+name+'</span><span contenteditable="true" class="badge" style="background: #8fd3f5;">2</span></span>');
                $('#put-result').append(item);
                $('#put-result span.name').each(function(){
                    $(this).click(function(){
                        $(this).parent('span.item').remove();
                    })
                })
            }else{
                alert('奖励不能超过3种');
            }
        })
    })
})