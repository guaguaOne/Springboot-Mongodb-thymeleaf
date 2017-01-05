package com.tumei.xxkg.model.tm3;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by niannian on 2017/1/5.
 */
@Document(collection = "Role.CodeAwards")
public class GiftsBean {
    @Id
    String _id;
    String code;
    String mode;
    ArrayList awards;
    Date expire;
    public String getCode(){return code;}
    public void setCode(String code){this.code=code;}
    public String getMode(){return mode;}
    public void setMode(String mode){this.mode=mode;}
    public ArrayList getAwards(){return awards;}
    public void setAwards(ArrayList awards){this.awards=awards;}
    public Date getExpire(){return expire;}
    public void setExpire(Date expire){this.expire=expire;}
}
