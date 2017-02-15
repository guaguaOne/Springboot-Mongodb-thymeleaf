package com.tumei.yxwd.model.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by niannian on 2017/2/15.
 */
//public interface LoginerBeanRepository extends MongoRepository<LoginerBean,Long>{
//    LoginerBean findByAccount(String account);
//    List<LoginerBean> findAll();
//    LoginerBean findById(Integer id);
//
//}
@Repository
public class LoginerBeanRepository {
    @Qualifier("yxwdaccountMongoTemplate")
    @Autowired
    public MongoTemplate mt;
    public List<LoginerBean> findByCreatetime() {
        List<LoginerBean> re = mt.find(query(where("createtime").lt("2015-03-11").gt("2016-11-12")), LoginerBean.class);
        return re;
    }
}
