package com.tumei.yxwd.dbmodel;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
@Document(collection = "Role")
public class Role {
    @Field("id")
    public Integer id;
    public String nickname;
    public int icon;
    public int race;
    public int coin;
    public int gem;
    public int level;
    public int exp;
    public int viplevel;
    public int vipexp;
    public int sp;
    public String lasttime;

    public Role() {}

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", icon=" + icon +
                ", race=" + race +
                ", coin=" + coin +
                ", gem=" + gem +
                ", level=" + level +
                ", exp=" + exp +
                ", viplevel=" + viplevel +
                ", vipexp=" + vipexp +
                ", sp=" + sp +
                ", lasttime=" + lasttime +
                '}';
    }
}
