/**
 * Created by niannian on 2016/12/2.
 */
$(document).ready(function(){
    $(function(){
        $("[data-toggle='popover']").popover({
            html:true,
            content:'<select class="form-control">'+
            '<option value="1" selected>Game逻辑服务器</option>'+
            '<option value="2">中心服务器</option>'+
            '<option value="3">Hub服务器</option>'+
            '<option value="4">矿区服务器</option></select>'
        });
    });

    $('#server  .server-type').each(function(index,elem){
        $(this).on('hide.bs.popover', function () {
            var text=$(this).next().find('div.popover-content').find('select').val();
            text=parseInt(text);
            switch(text){
                case 1: $(this).text('Game逻辑服务器'); break;
                case 2: $(this).text('中心服务器'); break;
                case 3: $(this).text('Hub服务器'); break;
                case 4: $(this).text('矿区服务器'); break;
                default:break;
            }

        })
    })
})