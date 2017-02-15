package com.tumei.yxwd;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by niannian on 2017/2/15.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.tumei.yxwd.model.account", mongoTemplateRef = "yxwdaccountMongoTemplate")
public class AccountYxwdDBRepos {
}