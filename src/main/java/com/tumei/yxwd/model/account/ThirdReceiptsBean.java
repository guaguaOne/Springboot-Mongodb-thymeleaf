package com.tumei.yxwd.model.account;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by niannian on 2017/2/16.
 */
@Document(collection = "ThirdReceipts")
public class ThirdReceiptsBean {
    @Id
    ObjectId _id;
    Integer id;
    Integer amount;
    Integer zone;
    DateTime time;
    public Integer getId(){return id;}
    public void setId(Integer id){this.id=id;}
    public Integer getAmount(){return amount;}
    public void setAmount(Integer amount){this.id=amount;}
    public Integer getZone(){return zone;}
    public void setZone(Integer zone){this.zone=zone;}
    public DateTime getTime(){return time;}
    public void setTime(DateTime time){this.time=time;}
}
