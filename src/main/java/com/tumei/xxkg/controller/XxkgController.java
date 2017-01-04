package com.tumei.xxkg.controller;

import com.tumei.xxkg.model.center.*;
import com.tumei.xxkg.model.tm3.EmailsBean;
import com.tumei.xxkg.model.tm3.EmailsBeanRepository;
import com.tumei.xxkg.model.tm3.RoleBean;
import com.tumei.xxkg.model.tm3.RoleBeanRepository;
import com.tumei.xxkg.model.tmconf.GoodsBean;
import com.tumei.xxkg.model.tmconf.GoodsBeanRepository;
import com.tumei.xxkg.model.tmconf.HerosBean;
import com.tumei.xxkg.model.tmconf.HerosBeanRepository;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by niannian on 2016/12/27.
 */
@Controller
public class XxkgController {
    @Autowired
    public ServerBeanRepository server;
    @Autowired
    public GoodsBeanRepository good;
    @Autowired
    public RoleBeanRepository role;
    @Autowired
    public XxkgaccountBeanRepository account;
    @Autowired
    public HerosBeanRepository hero;
    @Autowired
    public EmailsBeanRepository email;
    @Autowired
    public SendnoticeBeanRepository notice;

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
        List<ServerBean> bean=server.findByType(2);//中心服务器
        map.addAttribute("zx",bean);
        String url=bean.get(0).gm;
//        String url="http://192.168.1.222:12003/getdeclare/";
        String para=content;
        if(para==null){//获得
            url+="/getdeclare/";
            String re=doGet(url);
            re= URLDecoder.decode(re);
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
        List<GoodsBean> bean = good.findAll();
        map.addAttribute("goods",bean);
        return "xxkg/email";
    }
    //邮件发送
    @ResponseBody
    @RequestMapping(value = "/sendemail",method = RequestMethod.POST)
    public void sendemails(@RequestParam String id,String serverid,String title,String content,String awards){
        Integer serid=Integer.parseInt(serverid);
        ServerBean bean=server.findBySerId(serid);
        String url=bean.getGm();
        String sec=bean.getPass();
        awards = awards.replaceAll("\\|","%7C");
        System.out.println("awards:"+awards);
        url=url+"/mail?id="+id+"&title="+title+"&content="+content+"&sec="+sec+"&awards="+awards;
        String re=doGet(url);
        System.out.println("---------------------------------------------");
        System.out.println(re);
    }
    //信息查询
    @RequestMapping(value = "/xxkg/info", method = RequestMethod.GET)
    public String xxkginfo(@RequestParam String account, ModelMap map) {
        map.addAttribute("name", account);
        Map m=new HashMap();
        m.put("id",666);
        m.put("nickname","宝宝金水");
        m.put("level",1);
        m.put("vip",1);
        m.put("vipexp","120");
        m.put("createtime","2016-12-12");
        m.put("logtime","2016-12-12");
        m.put("totaltime","100");
        m.put("icon",34);
        m.put("skin",12);
        m.put("newbie","320");
        Map a=new HashMap();
        a.put("account","xxx");
        map.addAttribute("ro",m);
        map.addAttribute("ac",a);
        List<GoodsBean> bean = good.findAll();
        map.addAttribute("goods",bean);
        List<HerosBean> he=hero.findAll();
        map.addAttribute("heros",he);
        return "xxkg/info";
    }
    //玩家信息查询
    @RequestMapping(value = "/userinfo", method = RequestMethod.POST)
    public String userinfo(@RequestParam String nickname,String name, ModelMap map) {
        try{
            Long id=Long.parseLong(nickname);
            RoleBean bean=role.findById(id);
            map.addAttribute("ro",bean);
            XxkgaccountBean acc=account.findById(id);
            map.addAttribute("ac",acc);
        }catch(Exception e){
            RoleBean bean=role.findByNickname(nickname);
            System.out.println("id:"+bean.id);
            map.addAttribute("ro",bean);
            Long id=bean.id;
            XxkgaccountBean acc=account.findById(id);
            map.addAttribute("ac",acc);
        }
        map.addAttribute("name",name);
        List<GoodsBean> bean = good.findAll();
        map.addAttribute("goods",bean);
        List<HerosBean> he=hero.findAll();
        map.addAttribute("heros",he);
        return "xxkg/info";
    }

    //禁言,解禁
    @ResponseBody
    @RequestMapping(value = "/nosay",method = RequestMethod.POST)
    public String nosay(@RequestParam Integer flag,Integer time,Integer action,Integer serid,String name){
        Long curr=System.currentTimeMillis();
        Long times;
        String re;
        ServerBean bean=server.findBySerId(serid);
        String sec=bean.getPass();
        String url=bean.getGm();
        switch (time){
            case 1: times=curr+24*60*60;break;
            case 3: times=curr+3*24*60*60;break;
            case 0: times=curr+365*24*60*60;break;
            default: times=curr-1000;break;
        }
        if(action==0){
            //禁言,禁止登陆
            url=url+"/nosay?flag="+flag+"&name="+name+"&sec="+sec+"&time="+times;
            System.out.println("url:"+url);
            re=doGet(url);
            System.out.println("ok return:"+re);
        }else{//解禁
            url=url+"/nosay?flag="+flag+"&name="+name+"&sec="+sec+"&time="+times;
            System.out.println("url:"+url);
            re=doGet(url);
            System.out.println("no return:"+re);
        }
        return re;
    }

    //增加物品
    @ResponseBody
    @RequestMapping(value = "/additem",method = RequestMethod.POST)
    public String additem(@RequestParam Long id,Integer serid,Integer key,Integer val){
        ServerBean bean=server.findBySerId(serid);
        String sec=bean.getPass();
        String url=bean.getGm();
        url=url+"/additem?id="+id+"&sec="+sec+"&key="+key+"&val="+val;
        String re=doGet(url);
        System.out.println("additem:"+re);
        return re;
    }
    //物品搜索
    @ResponseBody
    @RequestMapping(value = "/goods",method = RequestMethod.POST)
    public List<GoodsBean> goods(){
        List<GoodsBean> go=good.findAll();
        return go;
    }
    //英雄搜索
    @ResponseBody
    @RequestMapping(value = "/hero",method = RequestMethod.POST)
    public List<HerosBean> heros(){
        List<HerosBean> he=hero.findAll();
        return he;
    }
    //增加英雄
    @ResponseBody
    @RequestMapping(value = "/addhero",method = RequestMethod.POST)
    public String addhero(@RequestParam Long id,Integer serid,Integer key){
        ServerBean bean=server.findBySerId(serid);
        String sec=bean.getPass();
        String url=bean.getGm();
        url=url+"/addhero?id="+id+"&sec="+sec+"&key="+key;
        String re=doGet(url);
        System.out.println("addhero:"+re);
        return re;
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
        map.addAttribute("name", account);
        List<GoodsBean> bean=good.findAll();
        map.addAttribute("go",bean);
        return "xxkg/emails";
    }
    //发邮件
    @ResponseBody
    @RequestMapping(value = "/emails",method = RequestMethod.POST)
    public String emails(@RequestParam String title, String content, String awards, String create,String end,Integer level,Integer vip){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date cr = sdf.parse(create);
            Date en= sdf.parse(end);
            Long id=System.currentTimeMillis();
            Integer flag=1;
            EmailsBean em=new EmailsBean();
            em.setId(id);
            em.setTitle(title);
            em.setContent(content);
            em.setAwards(awards);
            em.setCreate(cr);
            em.setDate(en);
            em.setLevel(level);
            em.setVip(vip);
            em.setFlag(flag);
            email.save(em);
            System.out.println("-------------------");
            System.out.println(cr);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    //通知发送
    @RequestMapping(value = "/xxkg/message", method = RequestMethod.GET)
    public String xxkgmessage(@RequestParam String account, ModelMap map) {
        map.addAttribute("name", account);
        List<ServerBean> bean=server.findByType(1);
        map.addAttribute("se",bean);
        List<SendnoticeBean> sn=notice.findAll();
        map.addAttribute("sn",sn);
        return "xxkg/message";
    }

    //通知发送，刷新服务器配置
    @ResponseBody
    @RequestMapping(value = "/refresh",method = RequestMethod.POST)
    public String refresh(@RequestParam String serid){
        System.out.println("-------------------------------");
        System.out.println("serid:"+serid);
        String[] arr=serid.split(",");
        Integer len=arr.length;
        try{
            for(Integer i=0;i<len;i++) {
                Integer serverid = Integer.parseInt(arr[i]);
                System.out.println("serverid:" + serverid);
                ServerBean bean = server.findBySerId(serverid);
                String sec = bean.pass;
                String url = bean.gm;
                url = url + "/refresh?sec=" + sec;
                String re = doGet(url);
            }
            return "ok";
        }catch (Exception e){
            return "error";
        }
    }

    //通知发送，发送通知
    @ResponseBody
    @RequestMapping(value = "/notify",method = RequestMethod.POST)
    public String notify(@RequestParam String msg,String serid,Long times,Integer counts,String today,Long id) throws InterruptedException {
        String[] arr=serid.split(",");
        Integer len=arr.length;
        Integer coun=0;
        Long iid=id;
        SendnoticeBean be=new SendnoticeBean();
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date da= null;
//        try {
//            da = sdf.parse(today);
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//        }
//        Date da=new Date();
        be.setId(iid);
        be.setDate(today);
        be.setContent(msg);
        be.setCount(counts);
        be.setTimes(times);
        be.setStatu(1);
        notice.save(be);
        do {
            for(Integer i=0;i<len;i++) {
                Integer serverid = Integer.parseInt(arr[i]);
                ServerBean bean = server.findBySerId(serverid);
                String sec = bean.pass;
                String url = bean.gm;
                url = url + "/notify?sec=" + sec+"&msg="+msg;
                String re = doGet(url);
                System.out.println("re="+re);
            }
            System.out.println("count="+coun);
            FileWriter fw= null;
//            try {
//                fw = new FileWriter("doctemp/notice.txt");
//                fw.write(msg);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            coun++;
            Thread.sleep(times*1000);
        }while(coun<counts);
//        System.out.println("iid="+iid);
        SendnoticeBean f=notice.findById(iid);
        f.setStatu(2);
        notice.save(f);
        return "ok";
    }

    //通知发送，删除记录
    @ResponseBody
    @RequestMapping(value = "/notice/delete")
    public String noticedel(@RequestParam Long id){
        SendnoticeBean bean=notice.findById(id);
        notice.delete(bean);
        return "ok";
    }
    //礼包
    @RequestMapping(value = "/xxkg/gift", method = RequestMethod.GET)
    public String xxkggift(@RequestParam String account, ModelMap map) {
        map.addAttribute("name", account);
        return "xxkg/gift";
    }

    //条件查询
    @RequestMapping(value = "/xxkg/limite", method = RequestMethod.GET)
    public String xxkglimite(@RequestParam String account, ModelMap map) {
        map.addAttribute("name", account);
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
                String re= EntityUtils.toString(entity);
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
}