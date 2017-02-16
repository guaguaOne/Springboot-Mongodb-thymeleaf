package com.tumei.yxwd.model.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by niannian on 2017/2/16.
 */
@Repository
public class ThirdReceiptsBeanRepository {
    @Qualifier("yxwdaccountMongoTemplate")
    @Autowired
    public MongoTemplate mt;
    public List<ThirdReceiptsBean> findByCon(String start, String end,Integer zone) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date=sdf.parse(start);
            Date date2=sdf.parse(end);
            if(zone==0){//全部
                List<ThirdReceiptsBean> re = mt.find(query(where("time").gt(date).lt(date2)), ThirdReceiptsBean.class);
                return re;
            }else {
                List<ThirdReceiptsBean> re = mt.find(query(where("time").gt(date).lt(date2).and("zone").is(zone)), ThirdReceiptsBean.class);
                return re;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
