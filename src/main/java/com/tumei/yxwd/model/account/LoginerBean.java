package com.tumei.yxwd.model.account;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by niannian on 2017/2/15.
 */
@Document(collection = "Loginer")
public class LoginerBean {
    @Id
    ObjectId _id;
    DateTime createtime;
    public DateTime getCreatetime(){return createtime;}
    public void setCreatetime(DateTime createtime){ this.createtime=createtime;}
}
