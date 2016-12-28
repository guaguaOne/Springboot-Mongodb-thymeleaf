package com.tumei.xxkg.model.center;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by niannian on 2016/12/28.
 */
@Document(collection = "Account")
public class XxkgaccountBean {
    @Id
    public String _id;
    public Long id;
    public String account;
    public String getAccount(){return account;}
    public void setAccount(String account){this.account=account;}
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
}
