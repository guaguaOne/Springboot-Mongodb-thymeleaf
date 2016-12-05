package com.tumei.web.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by leon on 2016/11/5.
 */
@Document(collection = "Account")
public class AccountBean {
    @Field("id")
    public Long id;
    public String account;
    public String passwd;
    public String digest;
    public Date createtime;
    public int zone;
    public int status;
    public Date forbidtime;
    public String source;
    public String idfa;

    public AccountBean() {}

    public AccountBean(Long id, String account, Date createtime, int zone, String source) {
        this.id = id;
        this.account = account;
        this.createtime = createtime;
        this.zone = zone;
        this.source = source;
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", passwd='" + passwd + '\'' +
                ", digest='" + digest + '\'' +
                ", createtime=" + createtime +
                ", zone=" + zone +
                ", status=" + status +
                ", forbidtime=" + forbidtime +
                ", source='" + source + '\'' +
                ", idfa='" + idfa + '\'' +
                '}';
    }
}
