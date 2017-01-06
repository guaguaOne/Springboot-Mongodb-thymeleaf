package com.tumei.xxkg.model.center;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by niannian on 2017/1/5.
 */
public interface DocBeanRepository extends MongoRepository<DocBean,Long>{
    List<DocBean> findAll();
    DocBean findById(Long id);
}
