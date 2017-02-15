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
    /**
    * 物品数据库连接与名字
    * */
    private String xxkgconfurl;
    private String xxkgconfdb;

    /**
     * 小小矿工center
     **/
    private String xxkgcenterurl;
    private String xxkgcenterdb;

    /**
     * 小小矿工tm3
     **/
    private String xxkgtm3url;
    private String xxkgtm3db;

    /**
     * 英雄无敌account
     */
    private String yxwdaccounturl;
    private String yxwdaccountdb;

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
    //    -------------------------
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
    //    -------------------------
    public String getXxkgconfurl() {
        return xxkgconfurl;
    }

    public void setXxkgconfurl(String xxkgconfurl) {
        this.xxkgconfurl = xxkgconfurl;
    }

    public String getXxkgconfdb() {
        return xxkgconfdb;
    }

    public void setXxkgconfdb(String xxkgconfdb) {
        this.xxkgconfdb = xxkgconfdb;
    }
    //    -------------------------
    public void setXxkgcenterdb(String xxkgcenterdb) {
        this.xxkgcenterdb = xxkgcenterdb;
    }

    public String getXxkgcenterurl() {
        return xxkgcenterurl;
    }

    public void setXxkgcenterurl(String xxkgcenterurl) {
        this.xxkgcenterurl = xxkgcenterurl;
    }

    public String getXxkgcenterdb() {
        return xxkgcenterdb;
    }
    //    -------------------------
    public void setXxkgtm3db(String xxkgtm3db) {
        this.xxkgtm3db = xxkgtm3db;
    }

    public String getXxkgtm3url() {
        return xxkgtm3url;
    }

    public void setXxkgtm3url(String xxkgtm3url) {
        this.xxkgtm3url = xxkgtm3url;
    }

    public String getXxkgtm3db() {
        return xxkgtm3db;
    }
    //    -------------------------
    public void setYxwdaccountdb(String yxwdaccountdb) {
        this.yxwdaccountdb = yxwdaccountdb;
    }

    public String getYxwdaccounturl() {
        return yxwdaccounturl;
    }

    public void setYxwdaccounturl(String yxwdaccounturl) {
        this.yxwdaccounturl = yxwdaccounturl;
    }
    public String getYxwdaccountdb() {
        return yxwdaccountdb;
    }
    //  -----------------------------------

    @Bean(autowire = Autowire.BY_NAME, name = "xxkgtmconfMongoTemplate")
    public MongoTemplate XxkgtmconfMongoTemplate() throws Exception {
        MongoURI uri = new MongoURI(xxkgconfurl);
        Mongo mongo = new Mongo(uri);
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, xxkgconfdb);
        return new MongoTemplate(factory);
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

    @Bean(autowire = Autowire.BY_NAME, name = "xxkgcenterMongoTemplate")
    public MongoTemplate XxkgcenterMongoTemplate() throws Exception {
        MongoURI uri = new MongoURI(xxkgcenterurl);
        Mongo mongo = new Mongo(uri);
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, xxkgcenterdb);
        return new MongoTemplate(factory);
    }

    @Bean(autowire = Autowire.BY_NAME, name = "xxkgtm3MongoTemplate")
    public MongoTemplate Xxkgtm3MongoTemplate() throws Exception {
        MongoURI uri = new MongoURI(xxkgtm3url);
        Mongo mongo = new Mongo(uri);
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, xxkgtm3db);
        return new MongoTemplate(factory);
    }

    @Bean(autowire = Autowire.BY_NAME, name = "yxwdaccountMongoTemplate")
    public MongoTemplate YxwdAccountMongoTemplate() throws Exception {
        MongoURI uri = new MongoURI(yxwdaccounturl);
        Mongo mongo = new Mongo(uri);
        SimpleMongoDbFactory factory = new SimpleMongoDbFactory(mongo, yxwdaccountdb);
        return new MongoTemplate(factory);
    }
}
