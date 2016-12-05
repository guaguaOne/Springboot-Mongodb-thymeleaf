package com.tumei.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
@Service
public class TokenPool {
    private Log log = LogFactory.getLog(TokenPool.class);
    private int expire;
    private HashMap<Long, TokenInfo> tokens = new HashMap<Long, TokenInfo>();

    @PostConstruct
    public void Init() {
        expire = 3600;
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
        Date now = new Date();
        log.info("=== 检查当前超时Token." + now.toString());
        try {
            List<Long> expiredList = new ArrayList<Long>();
            Iterator itr = tokens.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<Long, TokenInfo> entry = (Map.Entry<Long, TokenInfo>)itr.next();
                TokenInfo info = entry.getValue();
                if (now.getTime() - info.getTime().getTime() >= expire) {
                    expiredList.add(entry.getKey());
                }
            }
            // 删除超时的数据
            for (Long id : expiredList) {
                tokens.remove(id);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

        Date now = new Date();
        if ((now.getTime() - val.getTime().getTime()) > expire) {
            tokens.remove(id);
            return 1;
        }

        if (!val.getToken().equalsIgnoreCase(token)) {
            return -2;
        }

        if (val.getIdfa() == null || val.getIdfa().isEmpty()) {
            // 修复idfa.
        }

        return 0;
    }
}
