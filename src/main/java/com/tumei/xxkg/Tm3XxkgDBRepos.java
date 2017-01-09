package com.tumei.xxkg;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by niannian on 2016/12/28.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.tumei.xxkg.model.tm3", mongoTemplateRef = "xxkgtm3MongoTemplate")
public class Tm3XxkgDBRepos{
}
