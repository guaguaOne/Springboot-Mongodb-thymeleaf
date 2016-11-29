package com.tumei.yxwd.dbmodel;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 *
 */
public interface RoleRepository extends MongoRepository<Role, Integer> {
    Role findById(Integer id);
    List<Role> findAll();
}
