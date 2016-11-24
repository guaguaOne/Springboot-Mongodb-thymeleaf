package com.tumei.web.controller.yxwd;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.springframework.web.bind.annotation.*;

/**
 * Created by leon on 2016/11/4.
 */
//@RestController
@RequestMapping(value = "/yxwd")
public class YxwdController {
    private static final Log log = LogFactory.getLog(YxwdController.class);

    /***
     * 根据玩家Post的数据注册帐号
     * @param name
     * @param port
     * @return
     */
    @ApiOperation(value = "根据昵称获取ID", notes = "")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "玩家昵称", required = true,
                dataType = "String", paramType = "path",
                defaultValue = "红烧鸡翅膀"),
        @ApiImplicitParam(name = "port", value = "服务器id", required = true,
                dataType = "String", paramType = "path", defaultValue = "8390")
    })
    @RequestMapping(value = "/getid/{name}/{port}", method = RequestMethod.GET)
    public String GetID(@PathVariable String name, @PathVariable String port) {
        String url = String.format("http://106.75.16.197:%s/gm?action=104&0=%s", port, name);

        log.info(url);
        String result = HttpRequest(url, null);
        log.info(result);
        return result;
    }

    @ApiOperation(value = "封停玩家帐号", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "玩家ID", required = true,
                    dataType = "String", paramType = "path",
                    defaultValue = ""),
            @ApiImplicitParam(name = "flag", value = "1:封停，2:解封", required = true,
                    dataType = "String", paramType = "path",
                    defaultValue = "1"),
            @ApiImplicitParam(name = "port", value = "服务器id", required = true,
                    dataType = "String", paramType = "path", defaultValue = "8390")
    })
    @RequestMapping(value = "/forbid/{id}/{flag}/{port}", method = RequestMethod.GET)
    public String Forbid(@PathVariable String id, @PathVariable String flag,
                         @PathVariable String port) {
        String url = String.format("http://106.75.16.197:%s/gm?action=206&0=%s&1=%s"
                , port, id, flag);

        log.info(url);
        String result = HttpRequest(url, null);
        log.info(result);
        return result;
    }



    /**
     * 发送http 请求，并返回结果
     * @param url
     * @param data
     * @return
     */
    private String HttpRequest(String url, String data) {
        try {
            Content content = Request.Get(url).execute().returnContent();
            return content.toString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "Exception:" + ex.getMessage();
        }
    }
}
