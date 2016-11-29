package com.tumei.yxwd.dbmodel;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 *
 */
public interface ItemRepository extends MongoRepository<Item, Integer> {
    Item findById(Integer id);
    List<Item> findAll();
}
