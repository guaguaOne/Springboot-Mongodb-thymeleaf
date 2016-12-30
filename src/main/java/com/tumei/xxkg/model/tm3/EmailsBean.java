package com.tumei.xxkg.model.tm3;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by niannian on 2016/12/29.
 */
@Document(collection = "MailEx")
public class EmailsBean {
    @Id
    String _id;
    Long id;
    String title;
    String content;
    String awards;
    DateTime date;
    Integer level;
    Integer flag;
    DateTime create;
    Integer vip;
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}
    public String getContent(){return content;}
    public void setContent(String content){this.content=content;}
    public String getAwards(){return awards;}
    public void setAwards(String awards){this.awards=awards;}
    public DateTime getDate(){return date;}
    public void setDate(DateTime date){this.date=date;}
    public Integer getLevel(){return level;}
    public void setLevel(Integer level){this.level=level;}
    public Integer getFlag(){return flag;}
    public void setFlag(Integer flag){this.flag=flag;}
    public DateTime getCreate(){return create;}
    public void setCreate(DateTime create){this.create=create;}
    public Integer getVip(){return vip;}
    public void setVip(Integer vip){this.vip=vip;}
}
