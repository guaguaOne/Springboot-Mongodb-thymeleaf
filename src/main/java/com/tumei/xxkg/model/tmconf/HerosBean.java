package com.tumei.xxkg.model.tmconf;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by niannian on 2016/12/28.
 */
@Document(collection = "Heroes")
public class HerosBean {
    public Integer key;
    public String name;
    public Integer getKey(){return key;}
    public void setKey(Integer key){this.key=key;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
}
