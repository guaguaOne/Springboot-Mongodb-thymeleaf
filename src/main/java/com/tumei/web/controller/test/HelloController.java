package com.tumei.web.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by leon on 2016/11/5.
 * 这个是一个测试处理器
 * 1. Security
 * 2. Cache
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }

    @RequestMapping("/hello"  )
    public String hello(ModelMap map) {
//        Manager.TestConfig(this);

        map.addAttribute("host", "1dfdfs111");

        return "hello";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/xxkg/index")
    public String xxkg() {
        return "xxkg/index";
    }

    @RequestMapping("/xxkg/noticechange")
    public String xxkgnoticechange(ModelMap map) {
        map.addAttribute("greet","hello");
        return "xxkg/noticechange";
    }

    @RequestMapping("/xxkg/manymail")
    public String xxkgmanymail() {
        return "xxkg/manymail";
    }

    @RequestMapping("/xxkg/orderwrite")
    public String xxkgorderwrite() {
        return "xxkg/orderwrite";
    }

    @RequestMapping("/xxkg/searchinfo")
    public String xxkgsearchinfo() {
        return "xxkg/searchinfo";
    }

    @RequestMapping("/xxkg/sendemail")
    public String xxkgsendemail() {
        return "xxkg/sendemail";
    }

    @RequestMapping("/xxkg/sendmessage")
    public String xxkgsendmessage() {
        return "xxkg/sendmessage";
    }

    @RequestMapping("/xxkg/giftcreate")
    public String xxkggiftcreate() {
        return "xxkg/giftcreate";
    }

    @RequestMapping("/xxkg/conditionsearch")
    public String xxkgconditionsearch() {
        return "xxkg/conditionsearch";
    }
}
