package com.tumei.yxwd1.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by niannian on 2017/1/9.
 */
@Controller
public class YxwdController {
    //注册查询
    @RequestMapping(value = "/yxwd",method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN','YXWD','OWNER','XXKG,YXWD')")
    public String yxwd(@RequestParam String account, ModelMap map){
        map.addAttribute("name",account);
        return "yxwd/index";
    }
    //充值查询
    @RequestMapping(value = "/yxwd/charge",method = RequestMethod.GET)
    public String yxwdcharge(@RequestParam String account, ModelMap map){
        map.addAttribute("name",account);
        return "yxwd/charge";
    }
    //活跃度查询
    @RequestMapping(value = "/yxwd/active",method = RequestMethod.GET)
    public String yxwdactive(@RequestParam String account, ModelMap map){
        map.addAttribute("name",account);
        return "yxwd/active";
    }
    //用户查询
    @RequestMapping(value = "/yxwd/user",method = RequestMethod.GET)
    public String yxwduser(@RequestParam String account, ModelMap map){
        map.addAttribute("name",account);
        return "yxwd/user";
    }
    //分区查询
    @RequestMapping(value = "/yxwd/area",method = RequestMethod.GET)
    public String yxwdarea(@RequestParam String account, ModelMap map){
        map.addAttribute("name",account);
        return "yxwd/area";
    }
    //邮件发送
    @RequestMapping(value = "/yxwd/email",method = RequestMethod.GET)
    public String yxwdemail(@RequestParam String account, ModelMap map){
        map.addAttribute("name",account);
        return "yxwd/email";
    }
}
