package com.tumei.web.model;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by leon on 2016/11/5.
 */
@Document(collection = "SecUser")
public class SecUserBean {
    @Field("id")
    public Long id;
    public String account;
    public String passwd;
    public ROLE role;
    public DateTime createtime;
    public DateTime forbidtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public DateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(DateTime createtime) {
        this.createtime = createtime;
    }

    public DateTime getForbidtime() {
        return forbidtime;
    }

    public void setForbidtime(DateTime forbidtime) {
        this.forbidtime = forbidtime;
    }

    public SecUserBean() {}


}
