package com.tumei.yxwd;

import com.google.common.cache.*;
import com.tumei.cache.CacheIt;
import com.tumei.yxwd.dbmodel.Role;
import com.tumei.yxwd.dbmodel.RoleRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/12/1 0001.

 * 数据访问层 所有非只读数据库的访问点，中间部署了guava缓存，缓存失效将会把数据再次保存到数据库中
 *
 */
@Component
@Order(90)
@ConfigurationProperties(prefix = "dao")
public class DataAccessor {
    private Log log = LogFactory.getLog(DataAccessor.class);

    @Autowired
    private CacheIt cacheIt;
    @Autowired
    private RoleRepository roleRepository;

    private LoadingCache<Integer, Role> roleLoadingCache;

    /**
     * 刷新的初始延迟
     */
    private String initdelay;

    /**
     * 刷新延迟
     */
    private String delay;

    /**
     * 注册各种缓存与数据库的对应
     */
    public void Initialize() {
        log.info(String.format("*** 初始延迟:%s, 间隔延迟:%s", initdelay, delay));
        long delay1 = Long.parseLong(initdelay);
        long delay2 = Long.parseLong(delay);
        cacheIt.Initialize(delay1, delay2);
        roleLoadingCache = cacheIt.cached(
                new CacheLoader<Integer, Role>() {
                    @Override
                    public Role load(Integer s) throws Exception {
                        // 从指定数据库中读取数据
                        return roleRepository.findById(s);
                    }
                },
                new RemovalListener<Integer, Role>() {
                    @Override
                    public void onRemoval(RemovalNotification<Integer, Role> removalNotification) {
                        if (removalNotification.getCause() == RemovalCause.EXPIRED ||
                                removalNotification.getCause() == RemovalCause.EXPLICIT) {
                                log.info("on removal: " + removalNotification.getKey() + " >>>");
                            roleRepository.save(removalNotification.getValue());
                        }
                    }
                }
        );
    }

    /**
     * 系统退出或者关闭的时候需要调用，确保更新到数据库
     */
    public void Dispose() {
        log.error("------------ dispose dao.... -------------" + System.currentTimeMillis());
        cacheIt.Dispose(); // 关闭定时器
        log.error("------------ dispose dao.... -------------" + System.currentTimeMillis());
    }

    /**
     * 查询角色, 每次对角色的变更都需要调用这个，以便刷新guava对键值的超时时间
     * @param id
     * @return
     * @throws Exception
     */
    public Role findRole(Long id) {
        try {
            return roleLoadingCache.get(id.intValue());
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 整体插入或者更新
     * @param role
     */
    public void saveRole(Role role) {
        if (role != null) {
            roleLoadingCache.put(role.id, role);
        }
    }

    public String getInitdelay() {
        return initdelay;
    }

    public void setInitdelay(String initdelay) {
        this.initdelay = initdelay;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }
}
