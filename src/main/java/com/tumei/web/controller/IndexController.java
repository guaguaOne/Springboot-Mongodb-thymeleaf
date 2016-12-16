package com.tumei.web.controller;
import com.google.gson.JsonObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * Created by leon on 2016/11/5.
 * 这个是一个测试处理器
 * 1. Security
 * 2. Cache
 */
@Controller
public class IndexController{

    @RequestMapping("/")
    public String index(ModelMap map) throws IOException {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        String name=auth.getName();
        Object principal=auth.getPrincipal();
        map.addAttribute("name",name);
        if(principal instanceof UserDetails){
            map.addAttribute("role",1);//授权
        }else{
            map.addAttribute("role",0);//普通
        }

        return "admin/userinfo";
    }


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/xxkg/index")
    public String xxkg() {
        return "xxkg/index";
    }

    @RequestMapping("/xxkg/login")
    public String xxkglogin() {
        return "xxkg/login";
    }

    @RequestMapping("/xxkg/noticechange")
    public String xxkgnoticechange(ModelMap map) {
        map.addAttribute("greet","winter");
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

    //    英雄无敌
    @RequestMapping("/yxwd/index")
    public String yxwd() {
        return "yxwd/index";
    }

    @RequestMapping("/yxwd/login")
    public String yxwdlogin() {
        return "yxwd/login";
    }

    //    管理用户权限
    @RequestMapping("/admin")
    public String admin() {
        return "admin/index";
    }

    @RequestMapping("/admin/userinfo")
    public String adminuserinfo() {
        return "admin/userinfo";
    }
}
