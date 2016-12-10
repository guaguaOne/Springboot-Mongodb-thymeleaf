package com.tumei.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 *  Token的管理器，负责创建，检查token，并定时清理已经超时的token.
 *
 *  TODO:
 *  1. token能分应用，分不同的资源管理，当一个token创建的时候，带有可验证的资源范围，在检查的时候根据请求访问的资源给出结果.
 *  2. token的超时可以细化到不同的资源的超时上
 *
 */
@Service
public class TokenPool {
    private Log log = LogFactory.getLog(TokenPool.class);
    private int expire;
    private HashMap<Long, TokenInfo> tokens = new HashMap<Long, TokenInfo>();

    @PostConstruct
    public void Init() {
        expire = 3600000;
        log.info("启动TokenPool池:" + expire);
    }

    @PreDestroy
    public void Dispose() {

    }

    /**
     * fixedDelay 上次运行结束后300秒再次启动
     */
    @Scheduled(fixedDelay = 3000)
    public void Update() {
        expiredCheck();
    }

    private synchronized void expiredCheck() {
        DateTime now = DateTime.now();
//        log.info("=== 检查当前超时Token." + now.toString());
        try {
            List<Long> expiredList = new ArrayList<Long>();
            Iterator itr = tokens.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<Long, TokenInfo> entry = (Map.Entry<Long, TokenInfo>)itr.next();
                TokenInfo info = entry.getValue();
                if (now.getMillis() - info.getTime().getMillis() >= expire) {
                    expiredList.add(entry.getKey());
                }
            }
            // 删除超时的数据
            for (Long id : expiredList) {
                tokens.remove(id);
            }
        } catch (Exception ex) {
            log.error("检查Token超时出现异常:" + ex.toString());
        }
    }

    /***
     * 创建 token.
     * @param id
     * @param account
     * @param digest
     * @param zone
     * @param idfa
     * @return
     */
    public synchronized String insertToken(Long id, String account, String digest, int zone, String idfa) {
        TokenInfo token = TokenInfo.create(account, digest, zone);
        token.setIdfa(idfa);
        tokens.replace(id, token);
        return token.getToken();
    }

    /**
     * 校验token.
     * @param id
     * @param token
     * @param zone
     * @param idfa
     * @return
     */
    public synchronized int checkToken(Long id, String token, int zone, String idfa) {
        TokenInfo val = tokens.get(id);
        if (val == null) {
            return -1;
        }

        if ((DateTime.now().getMillis()- val.getTime().getMillis()) > expire) {
            tokens.remove(id);
            return 1;
        }

        if (!val.getToken().equalsIgnoreCase(token)) {
            return -2;
        }

        /* 没有必要在此处补填idfa */
        if (val.getIdfa() == null || val.getIdfa().isEmpty()) {
            // 修复idfa.
        }

        return 0;
    }
}
