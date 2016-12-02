package com.tumei.yxwd;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by Administrator on 2016/11/24 0024.
 *
 * 游戏的特有配置
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.tumei.yxwd.dbmodel", mongoTemplateRef = "gameMongoTemplate")
public class GameDBRepos {

}
