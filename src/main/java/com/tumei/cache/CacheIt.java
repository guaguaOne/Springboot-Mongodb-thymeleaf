package com.tumei.cache;

import com.google.common.cache.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
@Component
public class CacheIt {
    private Log log = LogFactory.getLog(CacheIt.class);
    private CacheBuilder<Object, Object> builder;

    private List<LoadingCache> caches;
    /***
     * 初始化缓存管理器
     */
    public void Initialize(long initDealy, long delay) {
        builder = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.SECONDS).expireAfterWrite(4, TimeUnit.SECONDS);
        caches = new ArrayList<LoadingCache>();

        // 启动一个线程的定时器，定时刷新刷存
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleWithFixedDelay(
            new Runnable() {
                @Override
                public void run() {
                    cleanUp(false);
                }
            }, initDealy, delay, TimeUnit.SECONDS);
    }

    /**
     *
     * 清理管理的所有LoadingCache,一般用于程序整体退出，或者预定时间点
     *
     */
    public void cleanUp(boolean force) {
        for (LoadingCache cache : caches) {
            if (force) {
                cache.invalidateAll();
            } else {
                cache.cleanUp();
            }
        }
    }

    /***
     * 便捷方法返回缓存数据集
     *
     * @param cacheLoader  读取的时候如何从数据库拉取
     * @param removeListener 失效的时候如何将数据返填到数据库
     * @param <K>  key 类型
     * @param <V>  value 类型
     * @return
     *          返回一个这样的缓存数据集合
     */
    public <K, V> LoadingCache<K, V> cached(CacheLoader<K, V> cacheLoader, RemovalListener<K, V> removeListener) {
        LoadingCache<K, V> cache = builder.removalListener(removeListener).build(cacheLoader);
        caches.add(cache);
        return cache;
    }

    public void Test() {
        LoadingCache<Integer, String> cache = cached(
                new CacheLoader<Integer, String>() {
                                                         @Override
                                                         public String load(Integer s) throws Exception {
                                                             // 从指定数据库中读取数据
                                                             log.info("on load: " + s);

                                                             return "haha";
                                                         }
                                                     },
                new RemovalListener<Integer, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<Integer, String> removalNotification) {
                        if (removalNotification.getCause() == RemovalCause.EXPIRED ||
                                removalNotification.getCause() == RemovalCause.EXPLICIT) {
                            log.info("on removal: " + removalNotification.getKey() + " >>> " + removalNotification.getValue());
                        }
                    }
                }
        );

        log.info("nifaksdfdsfkds");
        cache.put(1, "1123");
        try {
            cache.get(123);
            cache.put(1, "2323");
            cache.get(234);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
