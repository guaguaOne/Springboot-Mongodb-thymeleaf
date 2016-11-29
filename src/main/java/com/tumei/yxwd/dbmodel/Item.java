package com.tumei.yxwd.dbmodel;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
@Document(collection = "Items")
public class Item {
    @Field("id")
    public Integer id;
    public String name;
    public String desc;

    /**
     * 是否碎片
     */
    public int frag;

    public int sale;

    public int grade;

    /**
     * 出现场景
     */
    public float[] sceneid;
}
