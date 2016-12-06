package com.tumei.yxwd.centermodel;

import com.tumei.yxwd.dbmodel.Item;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
public interface LoginerRepository extends MongoRepository<Loginer, Integer> {
    Loginer findById(Integer id);
    List<Loginer> findAll();
}
