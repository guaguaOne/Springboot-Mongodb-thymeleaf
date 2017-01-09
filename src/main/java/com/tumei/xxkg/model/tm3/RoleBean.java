package com.tumei.xxkg.model.tm3;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by niannian on 2016/12/28.
 */
@Document(collection = "Role")
public class RoleBean {
    @Id
    public String _id;
    public Long id;
    public String nickname;
    public Integer level;
    public Integer vip;
    public String vipexp;
    public String account;
    public String createtime;
    public String logtime;
    public String totaltime;
    public Integer icon;
    public Integer skin;
    public String newbie;
    public Integer totalcharge;

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getNickname(){return nickname;}
    public void setNickname(String nickname){this.nickname=nickname;}
    public Integer getLevel(){return level;}
    public void setLevel(Integer level){this.level=level;}
    public Integer getVip(){return vip;}
    public void setVip(Integer vip){this.vip=vip;}
    public String getVipexp(){return vipexp;}
    public void setVipexp(String vipexp){this.vipexp=vipexp;}
    public String getAccount(){return account;}
    public void setAccount(String account){this.account=account;}
    public String getCreatetime(){return createtime;}
    public void setCreatetime(String createtime){this.createtime=createtime;}
    public String getLogtime(){return logtime;}
    public void setLogtime(String logtime){this.logtime=logtime;}
    public String getTotaltime(){return totaltime;}
    public void setTotaltime(String totaltime){this.totaltime=totaltime;}
    public Integer getIcon(){return icon;}
    public void setIcon(Integer icon){this.icon=icon;}
    public Integer getSkin(){return skin;}
    public void setSkin(Integer skin){this.skin=skin;}
    public String getNewbie(){return newbie;}
    public void setNewbie(String newbie){this.newbie=newbie;}
    public Integer getTotalcharge(){return totalcharge;}
    public void setTotalcharge(Integer totalcharge){this.totalcharge=totalcharge;}
}
