package com.tumei.web.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 2016/11/5.
 */
@Document(collection = "SecUser")
public class SecUserBean {
    @Id
    public String id;
    public String account;
    public String passwd;
    public String role;
    public DateTime createtime;
    public boolean enabled;

    public String getId() {
        return id;
    }
    public void setId(String id) {
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

    public String getRole() {
        return role;
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < this.role.size(); ++i) {
//            sb.append(this.role.get(i).name());
//            if (i != this.role.size() - 1) {
//                sb.append(",");
//            }
//        }
//        return sb.toString();
    }

    public void setRole(String auth) {
        this.role = auth;
    }

    public void setRole(ROLE _role) {
        if (this.role != null && !this.role.isEmpty()) {
            String[] roles = this.role.split(",");
            for (String r : roles) {
                if (r.equalsIgnoreCase(_role.name())) {
                    return;
                }
            }

            this.role += "," + _role.name();
        } else {
            this.role = _role.name();
        }
    }

    public DateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(DateTime createtime) {
        this.createtime = createtime;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public SecUserBean() {}


}
