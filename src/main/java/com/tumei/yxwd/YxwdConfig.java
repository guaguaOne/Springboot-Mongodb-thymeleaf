package com.tumei.yxwd;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.MongoClientParser;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.jws.Oneway;

/**
 * Created by Administrator on 2016/11/24 0024.
 *
 * 游戏的特有配置
 */
@Configuration
@ConfigurationProperties(prefix = "yxwd")
public class YxwdConfig {
    private String version;
    private String gamedb;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Bean(autowire = Autowire.BY_NAME, name = "centerMongoTemplate")
    public MongoTemplate CenterMongoTemplate() throws Exception {
        MongoURI uri = new MongoURI("mongodb://leon:Fuckyou1@115.159.205.176");
        Mongo mongo = new Mongo(uri);
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, "account");
        return new MongoTemplate(factory);
    }

    @Bean(autowire = Autowire.BY_NAME, name = "gameMongoTemplate")
    public MongoTemplate GameMongoTemplate() throws Exception {
        MongoURI uri = new MongoURI("mongodb://leon:Fuckyou1@115.159.205.176");
        Mongo mongo = new Mongo(uri);
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, "y3");
        return new MongoTemplate(factory);
    }

    @Bean(autowire = Autowire.BY_NAME, name = "centerWebTemplate")
    public MongoTemplate CenterWebTemplate() throws Exception {
        MongoURI uri = new MongoURI("mongodb://leon:Fuckyou1@115.159.205.176");
        Mongo mongo = new Mongo(uri);
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, "account");
        return new MongoTemplate(factory);
    }
}
