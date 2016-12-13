package com.tumei;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


/**
 * 英雄无敌 游戏的特有配置
 */
@Configuration
@ConfigurationProperties(prefix = "db")
public class MongoTemplateConfig {
    /**
     * 帐号数据库链接与名字
     */
    private String centerurl;
    private String centerdb;

    /**
     * 游戏数据库链接与名字
     */
    private String gameurl;
    private String gamedb;

    public String getCenterurl() {
        return centerurl;
    }

    public void setCenterurl(String centerurl) {
        this.centerurl = centerurl;
    }

    public String getCenterdb() {
        return centerdb;
    }

    public void setCenterdb(String centerdb) {
        this.centerdb = centerdb;
    }

    public String getGameurl() {
        return gameurl;
    }

    public void setGameurl(String gameurl) {
        this.gameurl = gameurl;
    }

    public String getGamedb() {
        return gamedb;
    }

    public void setGamedb(String gamedb) {
        this.gamedb = gamedb;
    }

    @Bean(autowire = Autowire.BY_NAME, name = "centerMongoTemplate")
    public MongoTemplate CenterMongoTemplate() throws Exception {
        MongoURI uri = new MongoURI(centerurl);
        Mongo mongo = new Mongo(uri);
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, centerdb);
        return new MongoTemplate(factory);
    }

    @Bean(autowire = Autowire.BY_NAME, name = "gameMongoTemplate")
    public MongoTemplate GameMongoTemplate() throws Exception {
        MongoURI uri = new MongoURI(gameurl);
        Mongo mongo = new Mongo(uri);
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, gamedb);
        return new MongoTemplate(factory);
    }
}
