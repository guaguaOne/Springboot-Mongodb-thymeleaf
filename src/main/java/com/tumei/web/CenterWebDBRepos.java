package com.tumei.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by Administrator on 2016/11/24 0024.
 *
 * 游戏的特有配置
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.tumei.web", mongoTemplateRef = "centerWebTemplate")
public class CenterWebDBRepos {
}
