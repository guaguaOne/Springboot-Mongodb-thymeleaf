package com.tumei.xxkg.model.tm3;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by niannian on 2016/12/28.
 */
public interface RoleBeanRepository extends MongoRepository<RoleBean,Long>{
    RoleBean findById(Long id);
    RoleBean findByNickname(String nickname);
    List<RoleBean> findAll();
}
