package com.tumei.web.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by leon on 2016/11/5.
 */
@Document(collection = "Routers")
public class RouterBean {
    @Field("id")
    public Integer id;
    public String name;
    public String versions;

    public RouterBean() {}

    @Override
    public String toString() {
        return "RouterBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
