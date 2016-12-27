package com.tumei.xxkg;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by niannian on 2016/12/27.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.tumei.xxkg.model.center", mongoTemplateRef = "xxkgcenterMongoTemplate")
public class CenterXxkgDBRepos {
}

