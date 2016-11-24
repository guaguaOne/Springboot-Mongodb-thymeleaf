package com.tumei.web;

import com.tumei.DemoApplication;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.xml.XmlConfiguration;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.net.URISyntaxException;

/**
 * Created by Administrator on 2016/11/8.
 */
public class Manager {
    private static CacheManager cacheManager;

    public static void Test() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().
                withCache("preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build();

        cacheManager.init();

        Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);

        Cache<Long, String> myCache = cacheManager.createCache("myCache",
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)).build());


        myCache.put(1L, "da one!");
        String value = myCache.get(1l);
        System.out.printf("find from cache: %s", value);
        cacheManager.removeCache("preConfigured");
        cacheManager.close();
    }

    public static void TestConfig(Object obj) {
        final java.net.URL url = obj.getClass().getResource("/cache_config.xml");
        Configuration xml = new XmlConfiguration(url);
        cacheManager = CacheManagerBuilder.newCacheManager(xml);
        cacheManager.init();

        Cache<String, Long> cache = cacheManager.getCache("foo", String.class, Long.class);
        cache.put("haha", 22L);

        Long value = cache.get("ha1ha");
        System.out.printf("find from cache: %s", value);
        cacheManager.close();
    }

    /**
     * 测试jsr101规范的使用缓存方法
     *
     */
    public static void TestJsr101() {
        CachingProvider cacheProvider = Caching.getCachingProvider();
        try {
            System.out.printf("类加载器: %s", DemoApplication.class.getClassLoader().toString());
            javax.cache.CacheManager manager = cacheProvider.getCacheManager(
                    DemoApplication.class.getResource("/cache_config.xml").toURI(),
                    DemoApplication.class.getClassLoader());

            javax.cache.Cache<String, Long> cache = manager.getCache("foo", String.class, Long.class);
            cache.put("haha", 2204L);
            Long value = cache.get("haha");
            System.out.printf("find from cache: %s", value);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
