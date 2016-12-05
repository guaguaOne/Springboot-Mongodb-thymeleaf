package com.tumei.web.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by leon on 2016/11/5.
 */
@Document(collection = "COUNTERS")
public class IDBean {
    public Long nextval;
    public String name;

    public IDBean() {}
}
