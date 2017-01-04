package com.tumei.xxkg.model.center;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by niannian on 2017/1/3.
 */
public interface SendnoticeBeanRepository extends MongoRepository<SendnoticeBean,Long>{
    SendnoticeBean findById(Long id);
    List<SendnoticeBean> findAll();
}
