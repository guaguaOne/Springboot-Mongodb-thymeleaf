package com.tumei.xxkg.model.tmconf;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by niannian on 2016/12/28.
 */
public interface HerosBeanRepository extends MongoRepository<HerosBean,Long> {
    List<HerosBean> findAll();
}
