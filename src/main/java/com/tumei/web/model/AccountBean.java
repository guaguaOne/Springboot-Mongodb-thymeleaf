package com.tumei.web.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by leon on 2016/11/5.
 */
@Document(collection = "Account")
public class AccountBean {
    @Field("id")
    public Long id;
    public String account;

    public AccountBean() {}
    public AccountBean(Long id, String account)
    {
        this.id = id;
        this.account = account;
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "id=" + id +
                ", account='" + account + '\'' +
                '}';
    }
}
