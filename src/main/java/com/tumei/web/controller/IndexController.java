package com.tumei.web.controller;
import com.tumei.web.controller.platform.WebController;
import com.tumei.web.model.*;
import org.apache.catalina.Manager;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by leon on 2016/11/5.
 * 这个是一个测试处理器
 * 1. Security
 * 2. Cache
 */
@Controller
public class IndexController{
    @Autowired
    public SecUserBeanRepository repository;
    @Autowired
    public ServerBeanRepository server;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map,@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size ){
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        String name=auth.getName();
        map.addAttribute("name",name);
        SecUserBean bean = repository.findByAccount(name);
        String role=bean.getRole();
        map.addAttribute("role",role);//授权
        DateTime time=bean.getCreatetime();
        map.addAttribute("createtime",time.toLocalDate());
        //获取已经注册用户
        Sort sort = new Sort(Sort.Direction.DESC, name);
        Pageable pageable = new PageRequest(page, size, sort);
        Page<SecUserBean> accounts= repository.findAll(pageable);
        Iterable<SecUserBean> all=repository.findAll();
        map.addAttribute("all",all);
        map.addAttribute("currPage",page);
        map.addAttribute("list",accounts);
        return "admin/userinfo";
    }
    //用户详情
    @RequestMapping(value = "/persondetial",method = RequestMethod.GET)
    public String getAccount(@RequestParam(value = "account")String account,@RequestParam String curr,ModelMap map){
        SecUserBean detial=repository.findByAccount(account);
        map.addAttribute("name",curr);
        map.addAttribute("detial",detial);
        //获取系统当前权限
        ROLE[] role=ROLE.values();
        map.addAttribute("role",role);
        return "admin/persondetial";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    //注册
    @RequestMapping(value = "/newuser",method = RequestMethod.GET)
    public String register(@RequestParam(value = "account") String account,@RequestParam(value = "passwd") String passwd,ModelMap map) {
        SecUserBean bean = repository.findByAccount(account);
        if (bean != null)
        {
            return "已经存在相同的帐号名.";
        }
        else
        {
            bean = new SecUserBean();
            bean.setAccount(account);
            bean.setPasswd(passwd);
            bean.setRole(ROLE.USER);
            bean.setCreatetime(DateTime.now());
            repository.save(bean);
            map.addAttribute("role","USER");
            map.addAttribute("name",account);
            return "login";
        }
    }
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    //增加权限
    @RequestMapping(value = "/setAuth", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String setAuth(@RequestParam String account,@RequestParam String curr, @RequestParam String auth,ModelMap map) {
        SecUserBean userBean = repository.findByAccount(account);
        if (userBean == null) {
            return "指定的帐号不存在";
        }
        userBean.setRole(auth);
        repository.save(userBean);
        SecUserBean detial=repository.findByAccount(account);
        map.addAttribute("detial",detial);
        map.addAttribute("name",curr);
        ROLE[] role=ROLE.values();
        map.addAttribute("role",role);
        return "admin/persondetial";
    }

    //用户中心
    @RequestMapping(value = "/usercenter",method = RequestMethod.GET)
    public String userCenter(@RequestParam String curr,ModelMap map){
        SecUserBean detial=repository.findByAccount(curr);
        map.addAttribute("name",curr);
        map.addAttribute("detial",detial);
        return "usercenter";
    }

    @RequestMapping(value = "/uploadFace",method = RequestMethod.POST)
    public String uploadFace(@RequestParam MultipartFile msg){
        System.out.print("hhhh:"+msg);
        return "register";
    }
    //小小矿工首页
    @RequestMapping(value = "/xxkg",method = RequestMethod.GET)
    public String xxkg(@RequestParam String account,ModelMap map) {
        map.addAttribute("name",account);
        return "xxkg/index";
    }
    //服务器录入
    @RequestMapping(value = "/xxkg/inputserver",method = RequestMethod.POST)
    public String xxkginput(@RequestParam Integer id,String gm,String account,String pass,Integer type, ModelMap map) {
        ServerBean bean=server.findBySerId(id);
        if(bean != null){
            return "该服务器已存在!";
        }else{
            bean=new ServerBean();
            bean.setSerId(id);
            bean.setGm(gm);
            bean.setAccount(account);
            bean.setPass(pass);
            bean.setType(type);
            server.save(bean);
//            map.addAttribute("server",);
            return "xxkg/index";
        }
    }
    //公告修改
    @RequestMapping(value = "/xxkg/notice", method = RequestMethod.GET)
    public String xxkgnotice(@RequestParam String account,ModelMap map) {
        map.addAttribute("name",account);
        return "xxkg/notice";
    }
    //邮件发送
    @RequestMapping(value = "/xxkg/email", method = RequestMethod.GET)
    public String xxkgemail(@RequestParam String account,ModelMap map) {
        map.addAttribute("name",account);
        return "xxkg/email";
    }
    //信息查询
    @RequestMapping(value = "/xxkg/info", method = RequestMethod.GET)
    public String xxkginfo(@RequestParam String account,ModelMap map) {
        map.addAttribute("name",account);
        return "xxkg/info";
    }
    //指令记录
    @RequestMapping(value = "/xxkg/write", method = RequestMethod.GET)
    public String xxkgwrite(@RequestParam String account,ModelMap map) {
        map.addAttribute("name",account);
        return "xxkg/write";
    }
    //邮件群发
    @RequestMapping(value = "/xxkg/emails", method = RequestMethod.GET)
    public String emails(@RequestParam String account,ModelMap map) {
        return "xxkg/emails";
    }
    //通知发送
    @RequestMapping(value = "/xxkg/infomation", method = RequestMethod.GET)
    public String xxkginfomation(@RequestParam String account,ModelMap map) {
        return "xxkg/infomation";
    }
    //礼包
    @RequestMapping(value = "/xxkg/gift", method = RequestMethod.GET)
    public String xxkggift(@RequestParam String account,ModelMap map) {
        return "xxkg/gift";
    }
    //条件查询
    @RequestMapping(value = "/xxkg/limite", method = RequestMethod.GET)
    public String xxkglimite(@RequestParam String account,ModelMap map) {
        return "xxkg/limite";
    }

    //    英雄无敌
    @RequestMapping("/yxwd")
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
