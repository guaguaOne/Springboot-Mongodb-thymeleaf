package com.tumei.yxwd.centermodel;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
@Document(collection = "Loginer")
public class Loginer {
    @Field("id")
    public Integer id;
    public String account;
    public String digest;
}
