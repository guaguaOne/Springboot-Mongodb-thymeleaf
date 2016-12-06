package com.tumei.yxwd.dbmodel;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Document
public interface ItemRepository extends MongoRepository<Item, Integer> {
    Item findById(Integer id);
    List<Item> findAll();
}
