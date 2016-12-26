package com.tumei.web.controller;
import com.mongodb.*;
import com.tumei.web.model.*;
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
import org.json.*;
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
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    public ServerBeanRepository server;
    @Autowired
    public GoodsBeanRepository goods;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication auth = sc.getAuthentication();
        String name = auth.getName();
        map.addAttribute("name", name);
        SecUserBean bean = repository.findByAccount(name);
        String role = bean.getRole();
        map.addAttribute("role", role);//授权
        DateTime time = bean.getCreatetime();
        map.addAttribute("createtime", time.toLocalDate());
        //获取已经注册用户
        Sort sort = new Sort(Sort.Direction.DESC, name);
        Pageable pageable = new PageRequest(page, size, sort);
        Page<SecUserBean> accounts = repository.findAll(pageable);
        map.addAttribute("currPage", page);
        map.addAttribute("list", accounts);
        return "admin/userinfo";
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
        if (bean != null) {
            return "已经存在相同的帐号名.";
        } else {
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

    //小小矿工首页
    @RequestMapping(value = "/xxkg", method = RequestMethod.GET)
    public String xxkg(@RequestParam String account, ModelMap map) {
        map.addAttribute("name", account);
        List<ServerBean> se = server.findAll();
        map.addAttribute("server", se);
        return "xxkg/index";
    }

    //服务器录入
    @RequestMapping(value = "/xxkg/inputserver", method = RequestMethod.POST)
    public String xxkginput(@RequestParam Integer id, String gm, String account, String pass, Integer type, ModelMap map) {
        ServerBean bean = server.findBySerId(id);
        if (bean != null) {
            return "该服务器已存在!";
        } else {
            bean = new ServerBean();
            bean.setSerId(id);
            bean.setGm(gm);
            bean.setAccount(account);
            bean.setPass(pass);
            bean.setType(type);
            server.save(bean);
            List<ServerBean> se = server.findAll();
            map.addAttribute("server", se);
            return "xxkg/index";
        }
    }

    //服务器修改
    @RequestMapping(value = "/xxkg/changeserver", method = RequestMethod.POST)
    public String xxkgchange(@RequestParam Integer id, String gm, String account, String pass, Integer type) {
        ServerBean bean = server.findBySerId(id);
        bean.setGm(gm);
        bean.setAccount(account);
        bean.setPass(pass);
        bean.setType(type);
        server.save(bean);
        return "/xxkg/index";
    }

    //服务器删除
    @RequestMapping(value = "/xxkg/deleteserver", method = RequestMethod.GET)
    public String xxkgdelete(@RequestParam Integer id) {
        ServerBean bean = server.findBySerId(id);
        server.delete(bean);
        return "/xxkg/index";
    }

    //公告修改
    @RequestMapping(value = "/xxkg/notice", method = RequestMethod.GET)
    public String xxkgnotice(@RequestParam String account,String content, ModelMap map) {
        map.addAttribute("name", account);
        ServerBean bean=server.findByType(2);//中心服务器
        map.addAttribute("zx",bean);
        String url=bean.gm;
//        String url="http://192.168.1.222:12003/getdeclare/";
        String para=content;
        if(para==null){//获得
            url+="/getdeclare/";
            String re=doGet(url);
            re=URLDecoder.decode(re);
            re=re.substring(1,re.length()-1);
            //匹配\\n
            String regex2 = "\\\\\\\\n";
            Pattern pat2 = Pattern.compile(regex2);
            Matcher matcher2 = pat2.matcher(re);
            while (matcher2.find()) {
                re = re.replaceAll(regex2,"<br>");
            }
            //匹配\
            String regex = "\\\\";
            Pattern pat = Pattern.compile(regex);
            Matcher matcher = pat.matcher(re);
            while (matcher.find()) {
                re = re.replaceAll(regex,"");
            }
            JSONObject val=new JSONObject(re);
            String html=val.getString("val");
            //匹配<color=
            String regex3 = "<color=";
            Pattern pat3 = Pattern.compile(regex3);
            Matcher matcher3 = pat3.matcher(html);
            while (matcher3.find()) {
                html = html.replaceAll(regex3,"<font color=");
            }
            //匹配color
            String regex4 = "color>";
            Pattern pat4 = Pattern.compile(regex4);
            Matcher matcher4 = pat4.matcher(html);
            while (matcher4.find()) {
                html = html.replaceAll(regex4,"font>");
            }
            map.addAttribute("json",html);
        }else{//发送
            url+="/moddeclare/"+para;
            System.out.println("--------------------------------------");
            System.out.println("url:"+url);
            String re=doGet(url);
        }
        return "xxkg/notice";
    }

    //邮件发送
    @RequestMapping(value = "/xxkg/email", method = RequestMethod.GET)
    public String xxkgemail(@RequestParam String account, ModelMap map) {
        map.addAttribute("name", account);
        Mongo m=new Mongo("192.168.1.109",27017);
        DB db=m.getDB("tmconf");
        DBCollection collection=db.getCollection("Items");
        BasicDBObject obj=new BasicDBObject();
        DBCursor cursor = collection.find(obj);
        List<DBObject> list=cursor.toArray();
        map.addAttribute("goods",list);
        System.out.println(db);
//        List<GoodsBean> bean = goods.findAll();
//        map.addAttribute("test",bean);
        return "xxkg/email";
    }
    //邮件发送
    @RequestMapping(value = "/sendemail",method = RequestMethod.POST)
    public String sendemails(@RequestParam String id,String serverid,String title,String content,String awards){
        Integer serid=Integer.parseInt(serverid);
        ServerBean bean=server.findBySerId(serid);
        String url=bean.getGm();
        String sec=bean.getPass();
        url=url+"/mail?id="+id+"&title="+title+"&content="+content+"&sec="+sec+"&awards="+awards;
//        String regex = "|";
//        Pattern pat = Pattern.compile(regex);
//        Matcher matcher = pat.matcher(url);
//        while (matcher.find()) {
//            url = url.replaceAll(regex,"%124");
//        }
//        String re=doGet(url);
//        System.out.println("---------------------------------------------");
//        System.out.println(re);
        return "xxkg/email";
    }

    //信息查询
    @RequestMapping(value = "/xxkg/info", method = RequestMethod.GET)
    public String xxkginfo(@RequestParam String account, ModelMap map) {
        map.addAttribute("name", account);
        return "xxkg/info";
    }

    //指令记录
    @RequestMapping(value = "/xxkg/write", method = RequestMethod.GET)
    public String xxkgwrite(@RequestParam String account, ModelMap map) {
        map.addAttribute("name", account);
        return "xxkg/write";
    }

    //邮件群发
    @RequestMapping(value = "/xxkg/emails", method = RequestMethod.GET)
    public String emails(@RequestParam String account, ModelMap map) {
        return "xxkg/emails";
    }

    //通知发送
    @RequestMapping(value = "/xxkg/infomation", method = RequestMethod.GET)
    public String xxkginfomation(@RequestParam String account, ModelMap map) {
        return "xxkg/infomation";
    }

    //礼包
    @RequestMapping(value = "/xxkg/gift", method = RequestMethod.GET)
    public String xxkggift(@RequestParam String account, ModelMap map) {
        return "xxkg/gift";
    }

    //条件查询
    @RequestMapping(value = "/xxkg/limite", method = RequestMethod.GET)
    public String xxkglimite(@RequestParam String account, ModelMap map) {
        return "xxkg/limite";
    }

    public String doGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建http
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                String re=EntityUtils.toString(entity);
                return re;
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
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
