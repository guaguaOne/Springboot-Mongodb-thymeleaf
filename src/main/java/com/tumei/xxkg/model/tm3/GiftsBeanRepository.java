package com.tumei.xxkg.model.tm3;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by niannian on 2017/1/5.
 */
public interface GiftsBeanRepository extends MongoRepository<GiftsBean,Long>{
    List<GiftsBean> findAll();
}
