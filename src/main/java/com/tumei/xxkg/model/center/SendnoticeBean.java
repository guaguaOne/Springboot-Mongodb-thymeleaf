package com.tumei.xxkg.model.center;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by niannian on 2017/1/3.
 */
@Document(collection = "Sendnotice")
public class SendnoticeBean {
    @Id
    String _id;
    Long id;
    String date;
    String content;
    Integer count;
    Long times;
    Integer statu;
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getDate(){return date;}
    public void setDate(String date){this.date=date;}
    public String getContent(){return content;}
    public void setContent(String content){this.content=content;}
    public Integer getCount(){return count;}
    public void setCount(Integer count){this.count=count;}
    public Long getTimes(){return times;}
    public void setTimes(Long times){this.times=times;}
    public Integer getStatu(){return statu;}
    public void setStatu(Integer statu){this.statu=statu;}
}
