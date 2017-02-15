package com.tumei.web.controller;
import com.mongodb.*;
import com.tumei.web.model.*;
import com.tumei.xxkg.model.center.ServerBean;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by leon on 2016/11/5.
 * 这个是一个测试处理器
 * 1. Security
 * 2. Cache
 */
@Controller
public class IndexController {
    @Autowired
    public SecUserBeanRepository repository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        String name = auth.getName();
        map.addAttribute("name", name);
        SecUserBean bean = repository.findByAccount(name);
        String role = bean.getRole();
        map.addAttribute("role", role);//授权
//        DateTime time = bean.getCreatetime();
//        map.addAttribute("createtime", time);
        //获取已经注册用户
        Sort sort = new Sort(Sort.Direction.DESC, name);
        Pageable pageable = new PageRequest(page, size, sort);
        Page<SecUserBean> accounts = repository.findAll(pageable);
        map.addAttribute("currPage", page);
        map.addAttribute("list", accounts);
        return "admin/userinfo";
    }

    //删除已注册用户
    @ResponseBody
    @RequestMapping(value = "/deleteuser",method = RequestMethod.GET)
    public String deleteuser(@RequestParam String delacc){
        SecUserBean bean=repository.findByAccount(delacc);
        repository.delete(bean);
        return "ok";
    }
    //用户详情
    @RequestMapping(value = "/persondetial", method = RequestMethod.GET)
    public String getAccount(@RequestParam(value = "account") String account, @RequestParam String curr, ModelMap map) {
        SecUserBean detial = repository.findByAccount(account);
        map.addAttribute("name", curr);
        map.addAttribute("detial", detial);
        //获取系统当前权限
        ROLE[] role = ROLE.values();
        map.addAttribute("role", role);
        return "admin/persondetial";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    //注册
    @RequestMapping(value = "/newuser", method = RequestMethod.GET)
    public String register(@RequestParam(value = "account") String account, @RequestParam(value = "passwd") String passwd, ModelMap map) {
        SecUserBean bean = repository.findByAccount(account);
        if(bean != null) {
            return "register";
        }else{
            bean = new SecUserBean();
            bean.setAccount(account);
            bean.setPasswd(passwd);
            bean.setRole(ROLE.USER);
            bean.setCreatetime(DateTime.now());
            repository.save(bean);
            map.addAttribute("role", "USER");
            map.addAttribute("name", account);
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
    public String setAuth(@RequestParam String account, @RequestParam String curr, @RequestParam String auth, ModelMap map) {
        SecUserBean userBean = repository.findByAccount(account);
        if (userBean == null) {
            return "指定的帐号不存在";
        }
        userBean.setRole(auth);
        repository.save(userBean);
        SecUserBean detial = repository.findByAccount(account);
        map.addAttribute("detial", detial);
        map.addAttribute("name", curr);
        ROLE[] role = ROLE.values();
        map.addAttribute("role", role);
        return "admin/persondetial";
    }

    //用户中心
    @RequestMapping(value = "/usercenter", method = RequestMethod.GET)
    public String userCenter(@RequestParam String curr, ModelMap map) {
        SecUserBean detial = repository.findByAccount(curr);
        map.addAttribute("name", curr);
        map.addAttribute("detial", detial);
        return "usercenter";
    }

    @RequestMapping(value = "/uploadFace", method = RequestMethod.POST)
    public String uploadFace(@RequestParam MultipartFile msg) {
        System.out.print("hhhh:" + msg);
        return "register";
    }
}
