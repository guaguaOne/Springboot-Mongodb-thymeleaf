package com.tumei.yxwd;

import com.google.common.cache.*;
import com.tumei.cache.CacheIt;
import com.tumei.yxwd.dbmodel.Role;
import com.tumei.yxwd.dbmodel.RoleRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2016/12/1 0001.
 *
 * 数据访问层 所有非只读数据库的访问点，中间部署了guava缓存，缓存失效将会把数据再次保存到数据库中
 *
 */
@Component
public class DataAccessor {
    private Log log = LogFactory.getLog(DataAccessor.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CacheIt cacheIt;

    private LoadingCache<Integer, Role> roleLoadingCache;

    /**
     * 注册各种缓存与数据库的对应
     */
    public void Initialize() {
        cacheIt.Initialize(10, 10);

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
     * 查询角色
     * @param id
     * @return
     * @throws Exception
     */
    public Role findRole(Long id) {
        try {
            return roleLoadingCache.get(id.intValue());
        } catch (ExecutionException e) {
            return null;
        }
    }

    /**
     * 整体更新
     * @param role
     */
    public void saveRole(Role role) {
        if (role != null) {
            roleLoadingCache.put(role.id, role);
        }
    }
}
