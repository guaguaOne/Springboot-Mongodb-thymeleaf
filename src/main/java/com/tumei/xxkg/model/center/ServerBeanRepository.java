package com.tumei.xxkg.model.center;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by niannian on 2016/12/21.
 */
public interface ServerBeanRepository extends MongoRepository<ServerBean,Long> {
    ServerBean findBySerId(Integer id);
//    ServerBean findByType(Integer type);
    List<ServerBean> findByType(Integer type);
    List <ServerBean> findAll();
}
