package com.tumei.xxkg.model.center;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by niannian on 2017/1/5.
 */
@Document(collection = "Doc")
public class DocBean {
    @Id
    String _id;
    String docname;
    Date createtime;
    Long id;
    String url;
    public String getDocname(){return docname;}
    public void setDocname(String docname){this.docname=docname;}
    public Date getCreatetime(){return createtime;}
    public void setCreatetime(Date createtime){this.createtime=createtime;}
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getUrl(){return url;}
    public void setUrl(String url){this.url=url;}
}
