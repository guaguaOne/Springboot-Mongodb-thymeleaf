package com.tumei;

import com.tumei.utils.JsonUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/10 0010.
 */
@Component
//@ConfigurationProperties(prefix = "runner")
public class RunnerBean implements CommandLineRunner, ApplicationContextAware {

    private ConfigurableApplicationContext ctx;
    private Log log = LogFactory.getLog(RunnerBean.class);


    @Override
    public void run(String... strings) throws Exception {
        // 1. 初始化json序列化方案
        JsonUtil.init();


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = (ConfigurableApplicationContext)applicationContext;
    }



}
