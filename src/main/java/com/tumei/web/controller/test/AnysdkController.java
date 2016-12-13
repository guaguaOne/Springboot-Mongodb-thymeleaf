//package com.tumei.web.controller.test;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.tumei.utils.JsonUtil;
//import com.tumei.web.TokenPool;
//import com.tumei.web.model.*;
//import io.swagger.annotations.ApiOperation;
//import org.apache.catalina.servlet4preview.http.HttpServletRequest;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.joda.time.DateTime;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.mongodb.core.FindAndModifyOptions;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Anysdk的登录回调与支付接口
// *
// * /logon_any: 登录，由anysdk发送来的请求
// * /pay_any: 支付，由anysdk发送来通知
// */
//@Controller
//public class AnysdkController {
//    private Log log = LogFactory.getLog(AnysdkController.class);
//
//    private String loginCheckUrl = "";
//    private int connectTimeOut = 30 * 1000;
//    private int timeOut = 30 * 1000;
//    private static final String userAgent = "px v1.0";
//
//    /***
//     * 账户系统
//     */
//    @Autowired
//    private AccountBeanRepository accountBeanRepository;
//
//    @Autowired
//    private RouterBeanRepository routerBeanRepository;
//
//    @Autowired
//    @Qualifier(value = "centerMongoTemplate")
//    private MongoTemplate template;
//
//    @Autowired
//    private TokenPool pool;
//
//    private String output(Long result, String msg) {
//        return output(result, msg, "");
//    }
//
//    class AuthErr {
//        public Long ret;
//        public String msg;
//        public String addr;
//
//        public AuthErr(Long ret, String msg, String addr) {
//            this.ret = ret;
//            this.msg = msg;
//            this.addr = addr;
//        }
//
//        @Override
//        public String toString() {
//            return "AuthErr{" +
//                    "ret=" + ret +
//                    ", msg='" + msg + '\'' +
//                    ", addr='" + addr + '\'' +
//                    '}';
//        }
//    }
//
//    /**
//     * 返回定制的错误格式
//     * @param result
//     * @param msg
//     * @param addr
//     * @return
//     */
//    private String output(Long result, String msg, String addr) {
//        AuthErr err = new AuthErr(result, msg, addr);
//        try {
//            return JsonUtil.Serialize(err);
//        } catch (JsonProcessingException e) {
//            log.error("返回认证结果出错，序列化错误:", e);
//        }
//        return "0|序列化错误|";
//    }
//
//    /**
//     * 被动接受anysdk登录通知消息，然后在服务器上生成对应的access_token，并返回.
//     * @return
//     *      返回当前玩家的基本信息与access_token
//     */
//    @ApiOperation(value = "登录回调", notes = "")
//    @RequestMapping(value = "/logon_any", method = RequestMethod.POST)
//    public @ResponseBody String Anysdk_Logon(HttpServletRequest request, HttpServletResponse response) {
//        log.info("申请新的帐号ID:" + NextVal());
//
//        Long id = NextVal();
//        AccountBean accountBean = new AccountBean(id, "hdfj33dk", DateTime.now(), 1, "fsdafs");
//        accountBean = accountBeanRepository.save(accountBean);
//        if (accountBean == null) {
//            log.error("插入失败");
//        }
//
//        log.info("插入新数据:" + accountBean.toString());
//        return "";
//
//
////        List<RouterBean> routerBeens = routerBeanRepository.findAll();
////        for (RouterBean bean : routerBeens) {
////            log.info("router: " + bean.toString());
////        }
////
////        String clientVersion = "";
////
////        try {
////            Map<String, String[]> params = request.getParameterMap();
////            if (!paramIsset(params)) {
////                return output(-1L, "参数不完整");
////            }
////
////            StringBuilder queryString = new StringBuilder();
////            for (String key : params.keySet()) {
////                String[] values = params.get(key);
////                for (int i = 0; i < values.length; i++) {
////                    String value = values[i];
////                    queryString.append(key).append("=").append(value).append("&");
////                }
////
////                if (key.equalsIgnoreCase("server_ext_for_login")) {
////                    try {
////                        clientVersion = URLDecoder.decode(values[0], "UTF-8");
////                    } catch (Exception e) {
////                        log.error("解析客户端传递的数据错误，无法获取版本信息", e);
////                    }
////                }
////            }
////
////            String query = queryString.substring(0, queryString.length() - 1);
////            URL url = new URL(loginCheckUrl);
////
////            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////            conn.setRequestProperty("User-Agent", userAgent);
////            conn.setReadTimeout(timeOut);
////            conn.setConnectTimeout(connectTimeOut);
////            conn.setRequestMethod("POST");
////            conn.setDoInput(true);
////            conn.setDoOutput(true);
////
////            OutputStream os = conn.getOutputStream();
////            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
////            writer.write(query);
////            writer.flush();
////            tryClose(writer);
////            tryClose(os);
////            conn.connect();
////
////            // 分析anysdk的认证返回值
////            InputStream is = conn.getInputStream();
////            String resp = stream2String(is);
////
////            log.info("anysdk认证返回结果:[" + resp + "]");
////            JsonNode tree = JsonUtil.getMapper().readTree(resp);
////            JsonNode status = tree.get("status");
////            if (status == null || !status.toString().equalsIgnoreCase("ok")) {
////                return output(-2L, "认证失败");
////            }
////
////            JsonNode common = tree.get("common");
////            if (common == null) {
////                return output(-3L, "认证失败");
////            }
////
////            String sdk = common.get("user_sdk").toString();
////            String channel = common.get("channel").toString();
////            String uin = common.get("uid").toString();
////            String sess = common.get("server_id").toString();
////            String acc = String.format("%s|%s", sdk, uin);
////
////            AccountBean accountBean = accountBeanRepository.findByAccount(acc);
////            // 帐号不存在时，主动帮助生成帐号
////            if (accountBean == null) {
////                // 获取当前应该路由的服务器
////                int zone = getServerID(acc, clientVersion);
////                log.info("当前客户端版本:" + clientVersion + ", 当前服务器id:" + zone);
////
////                if (zone == -1) { // 服务器正在维护中
////                    return output(-4L, "服务器正在维护中");
////                }
////
////                Long id = NextVal();
////                accountBean = new AccountBean(id, acc, new Date(), zone, channel);
////                accountBean = accountBeanRepository.insert(accountBean);
////                if (accountBean != null) {
////                    // 生成一个对应的Token,用于后续认证使用
////                    String token = pool.insertToken(id, acc, sess, zone, "");
////
////                    /**
////                     * 根据zone 获取当前服务器的地址, grpc服务会收到已经建立的连接
////                     */
////
////                    JsonNode ext = JsonNodeFactory.instance.textNode(String.format("%s|%s|%s", id, token, ""));
////                    ((ObjectNode)tree).set("ext", ext);
////                    log.info(tree.toString());
////                    return tree.toString();
////                }
////            } else { // 帐号已经存在
////                String token = pool.insertToken(accountBean.id, acc, sess, accountBean.zone, accountBean.idfa);
////
////                /**
////                 * 根据zone 获取当前服务器的地址
////                 */
////                JsonNode ext = JsonNodeFactory.instance.textNode(String.format("%s|%s|%s", accountBean.id, token, ""));
////                ((ObjectNode)tree).set("ext", ext);
////                log.info(tree.toString());
////                return tree.toString();
////            }
////        } catch (Exception ex) {
////            log.error("收到anysdk登录请求回调,处理错误:", ex);
////        }
////        return output(-9L, "认证错误");
//    }
//
//    /**
//     * http字节流读取
//     * @param is
//     * @return
//     */
//    private String stream2String(InputStream is) {
//        String line;
//        BufferedReader br = null;
//        try{
//            br = new BufferedReader(new java.io.InputStreamReader(is));
//            StringBuilder sb = new StringBuilder();
//            while((line = br.readLine()) != null) {
//                sb.append( line );
//            }
//            return sb.toString();
//        } catch(Exception ex) {
//            log.error("读取http请求出错:", ex);
//        } finally {
//            tryClose(br);
//        }
//        return "";
//    }
//
//    /**
//     *
//     * @param params
//     * @return
//     */
//    private boolean paramIsset(Map<String, String[]> params) {
//        return (params.containsKey("channel") && params.containsKey("uapi_key") && params.containsKey("uapi_secret"));
//    }
//
//    private void tryClose(Closeable os) {
//        try{
//            if(null != os) {
//                os.close();
//            }
//        } catch(Exception e) {
//            log.error("关闭流失败:", e);
//        }
//    }
//
//    /**
//     * 根据名字获取对应的下一个数值,用于获取新用户可用的ID
//     * @return
//     */
//    public Long NextVal() {
//        Query query = new Query(Criteria.where("name").is("userid"));
//        Update update = new Update().inc("nextval", 1);
//        IDBean idBean = template.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), IDBean.class);
//        return idBean.nextval;
//    }
//
//    /**
//     * 通过当前版本和当前帐号名获取可用的服务器
//     * @param account 申请注册的帐号名
//     * @param version 当前客户端传递的版本，用于苹果审核时，强制帐号注册到审核服
//     * @return
//     *  返回-1表示当前没有可用的服务器
//     */
//    public int getServerID(String account, String version) {
//        List<RouterBean> routers = routerBeanRepository.findAll();
//        // 先检验版本，传入的版本与任何一个服务器的版本列表比对成功后，就一定进入这个服务器
//        for (RouterBean bean : routers) {
//            if (bean.versions == null) {
//                continue;
//            }
//            String[] versions = bean.versions.split(";");
//            for (String ver : versions) {
//                if (ver.equalsIgnoreCase(version)) {
//                    return bean.id;
//                }
//            }
//        }
//
//        List<Integer> ids = new ArrayList<Integer>();
//
//        // 在检查名字匹配
//        for (RouterBean bean : routers) {
//            // 先检验版本，传入的版本与任何一个服务器的版本列表比对成功后，就一定进入这个服务器
//            if (bean.name.equalsIgnoreCase("*")) {
//                return bean.id;
//            }
//
//            if (account.startsWith(bean.name)) {
//                return bean.id;
//            }
//
//            if (bean.id != 999) {
//                ids.add(bean.id);
//            }
//        }
//
//        // 以上匹配都不满足，随机从服务器id列表中选取一个非999的;
//        if (ids.size() == 0) {
//            return -1;
//        }
//
//        return ids.get(0);
//    }
//
//}
