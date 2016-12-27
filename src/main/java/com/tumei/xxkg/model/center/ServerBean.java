package com.tumei.xxkg.model.center;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by niannian on 2016/12/21.
 */
@Document(collection = "Server")
public class ServerBean {
    @Id
    public String id;
    public String gm;
    public String account;
    public String pass;
    public Integer type;
    public Integer serId;

    public String getId(){return id;}
    public void setId(String id){this.id=id;}
    public String getGm(){return gm;}
    public void setGm(String gm){this.gm=gm;}
    public String getAccount(){return account;}
    public void setAccount(String account){this.account=account;}
    public String getPass(){return pass;}
    public void setPass(String pass){this.pass=pass;}
    public Integer getType(){return type;}
    public void setType(Integer type){this.type=type;}
    public Integer getSerId(){return serId;}
    public void setSerId(Integer id){this.serId=id;}
    public ServerBean(){}

}
