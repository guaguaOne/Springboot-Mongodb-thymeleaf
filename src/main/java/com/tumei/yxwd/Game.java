package com.tumei.yxwd;

import com.tumei.Entry;
import com.tumei.io.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/12/1 0001.
 *
 * 非Spring组件，游戏访问的中间层,管理所有的玩家
 */
@Component
@ConfigurationProperties(prefix = "game")
@Order(value = 100)
public class Game {
    private static Game instance;
    public static Game getInstance() {
        if (instance == null) {
            instance = Entry.getApplicationContext().getBean(Game.class);
        }
        return instance;
    }

    @Autowired
    public Game(DataAccessor dao) {
        instance = this;
        this.dao = dao;
        Initialize();
    }

    private Log log = LogFactory.getLog(Game.class);
    private ConcurrentHashMap<Long, GameUser> users = new ConcurrentHashMap<Long, GameUser>();
    /**
     * 数据访问层
     */
    private DataAccessor dao;

    /**
     * 初始化游戏所有模块
     * @return
     */
    public boolean Initialize() {
        dao.Initialize();
        return true;
    }

    /**
     * 游戏退出的处理
     */
    public void Dispose() {
        log.info(">>>>> Game.class 正在退出.");
        if (dao != null) {
            dao.Dispose();
            dao = null;
        }
    }

    /**
     * 会话认证成功后,创建和新增玩家
     *
     * @param session
     */
    public boolean OnAddUser(Long id, Session session) {
        GameUser user = new GameUser(id, session);
        if (user == null) {
            session.error("内存不足，无法生成新的实例.");
            return false;
        }
        session.Attach(user);
        // 传递数据库访问句柄
        user.setDao(dao);

        // 如果同一个用户有多个连接请求登录，需要让前一个退出
        GameUser old = users.put(user.getId(), user);
        if (old != null) {
            old.close(); // 关闭连接,老账号的连接还未完全退出，新的GameUser立刻就进入了
        }

        return user.join();
    }

    /**
     * 退出的可能有多种:
     * 1. 连接断开触发
     * 2. 同一个帐号登录挤掉老帐号
     * 3. 服务器直接命令断开
     *
     * users中的User可能与绑定的不同了, 所以删除的时候使用特殊的删除方法
     *
     * 该函数由GameUser中感知到连接断开后调用
     */
    public void OnDelUser(GameUser user) {
        /*
        * 等同于 if (users.containsKey(id) && users.get(id) == user) {
        *   users.remove(id)
        * }
        * */
        users.remove(user.getId(), user);
    }

    /**
     * 踢出玩家
     * @param id
     *  玩家ID
     */
    public void Kick(Long id) {
        GameUser user = users.get(id);
        if (user != null) {
            user.close();
        }
    }

    /**
     * 当前玩家数量
     * @return
     */
    public int getCount() {
        return users.size();
    }
}
