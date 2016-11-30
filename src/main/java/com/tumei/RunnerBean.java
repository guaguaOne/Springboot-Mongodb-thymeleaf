package com.tumei;

import com.mongodb.Mongo;
import com.tumei.cache.CacheIt;
import com.tumei.io.TcpServer;
import com.tumei.utils.JsonUtil;
import com.tumei.yxwd.YxwdConfig;
import com.tumei.yxwd.centermodel.Loginer;
import com.tumei.yxwd.centermodel.LoginerRepository;
import com.tumei.yxwd.dbmodel.Item;
import com.tumei.yxwd.dbmodel.ItemRepository;
import com.tumei.yxwd.dbmodel.Role;
import com.tumei.yxwd.dbmodel.RoleRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
@Component
//@ConfigurationProperties(prefix = "runner")
public class RunnerBean implements CommandLineRunner, ApplicationContextAware {

    private ConfigurableApplicationContext ctx;
    private Log log = LogFactory.getLog(RunnerBean.class);

    @Autowired
    private ItemRepository repository;

    @Autowired
    private LoginerRepository repo;

    @Override
    public void run(String... strings) throws Exception {
        // 1. 初始化json序列化方案
        JsonUtil.init();

        // 2. 启动缓存
        CacheIt cacheIt = ctx.getBean(CacheIt.class);
        cacheIt.Initialize(10, 10);
        cacheIt.Test();

        Item item = repository.findById(160020);
        if (item != null) {
            log.info("Item:" + item.name + " sceneid:" + item.sceneid[0]);
        }

        Loginer loginer = repo.findById(100001);
        if (loginer != null) {
            log.info("Loginer:" + loginer.account + " digest:" + loginer.digest);
        }

        // 3. 启动服务器
        TcpServer ts = ctx.getBean(TcpServer.class);
        if (!ts.startServer()) {
            log.error("无法启动Tcp连接......");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = (ConfigurableApplicationContext)applicationContext;
    }

}
