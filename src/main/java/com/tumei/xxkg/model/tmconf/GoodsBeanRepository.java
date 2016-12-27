package com.tumei.xxkg.model.tmconf;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by niannian on 2016/12/27.
 */
public interface GoodsBeanRepository extends MongoRepository<GoodsBean,Long>{
    GoodsBean findByGood(String good);
    GoodsBean findByKey(Integer key);
    List<GoodsBean> findAll();
}
