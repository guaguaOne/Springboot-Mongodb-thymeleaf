package com.tumei;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableScheduling
@EnableGlobalMethodSecurity(securedEnabled = true)
public class Entry {
    private static final Log log = LogFactory.getLog(Entry.class);

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
//        log.info("中文字体..");
        applicationContext = SpringApplication.run(Entry.class, args);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
