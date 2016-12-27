package com.tumei.xxkg.model.tmconf;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by niannian on 2016/12/27.
 */
@Document(collection = "Items")
public class GoodsBean {
    public String good;
    public Integer key;

    public String getGood(){return good;}
    public void setGood(String good){this.good=good;}
    public Integer getKey(){return key;}
    public void setKey(Integer key){this.key=key;}
    public GoodsBean(){}
}
