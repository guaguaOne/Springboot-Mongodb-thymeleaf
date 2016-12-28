/**
 * Created by niannian on 2016/12/27.
 */
$(document).ready(function () {
    $("[name='my-checkbox']").bootstrapSwitch({
        state:true,
        onSwitchChange:function(e,state){
            console.log(state);
            if(state){//禁言
                $('#xxkg .right .about-xxkg .info .action-info div.no input.flag').val(0);
            }else{//禁止登陆
                $('#xxkg .right .about-xxkg .info .action-info div.no input.flag').val(1);
            }
        }
    });
})