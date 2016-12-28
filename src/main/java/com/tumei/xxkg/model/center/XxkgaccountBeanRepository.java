package com.tumei.xxkg.model.center;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by niannian on 2016/12/28.
 */
public interface XxkgaccountBeanRepository extends MongoRepository<XxkgaccountBean,Long> {
    XxkgaccountBean findById(Long id);
}
