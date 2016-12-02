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
}
