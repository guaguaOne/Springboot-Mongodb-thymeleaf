package com.tumei.xxkg.model.tm3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by niannian on 2016/12/28.
 */
@Repository
public class RoleBeanRepository{
    @Qualifier("xxkgtm3MongoTemplate")
    @Autowired
    public MongoTemplate mt;
    public List<RoleBean> findByVip(Integer left,Integer right){
        if(left==right){
            List<RoleBean> re=mt.find(query(where("vip").is(left)),RoleBean.class);
            return re;
        }else{
            List<RoleBean> re=mt.find(query(where("vip").lt(right).gt(left)),RoleBean.class);
            return re;
        }
    }
    public RoleBean findById(Long id){
        RoleBean re=mt.findOne(query(where("id").is(id)),RoleBean.class);
        return re;
    }
    public RoleBean findByNickname(String nickname){
        RoleBean re=mt.findOne(query(where("nickname").is(nickname)),RoleBean.class);
        return re;
    }
    public List<RoleBean> findByLevel(Integer left,Integer right){
        if(left==right){
            List<RoleBean> re=mt.find(query(where("level").is(left)),RoleBean.class);
            return re;
        }else{
            List<RoleBean> re=mt.find(query(where("level").lt(right).gt(left)),RoleBean.class);
            return re;
        }
    }
    public List<RoleBean> findByCharge(Integer left,Integer right){
        if(left==right){
            List<RoleBean> re=mt.find(query(where("totalcharge").is(left)),RoleBean.class);
            return re;
        }else{
            List<RoleBean> re=mt.find(query(where("totalcharge").lt(right).gt(left)),RoleBean.class);
            return re;
        }
    }
}
