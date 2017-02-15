package com.tumei.yxwd.model.account;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by niannian on 2017/2/15.
 */
@Document(collection = "Loginer")
public class LoginerBean {
    @Id
    String account;
    Integer id;
    String digest;
    DateTime createtime;
    public String getAccount(){return account;}
    public void setAccount(String account){ this.account=account;}
    public Integer getId(){return id;}
    public void setId(Integer id){ this.id=id;}
    public String getDigest(){return digest;}
    public void setDigest(String digest){ this.digest=digest;}
    public DateTime getCreatetime(){return createtime;}
    public void setCreatetime(DateTime createtime){ this.createtime=createtime;}
}
