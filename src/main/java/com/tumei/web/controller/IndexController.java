package com.tumei.web.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by leon on 2016/11/5.
 * 这个是一个测试处理器
 * 1. Security
 * 2. Cache
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(ModelMap map) {
        SecurityContext sc = SecurityContextHolder.getContext();
        map.addAttribute("account", sc.getAuthentication().getName());
        return "hello";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

//    @RequestMapping("/logout")
//    public String logout() {
//        return "index";
//    }
}
