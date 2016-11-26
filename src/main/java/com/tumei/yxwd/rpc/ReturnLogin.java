package com.tumei.yxwd.rpc;

import com.tumei.io.Session;
import com.tumei.io.protocol.BaseProtocol;
import com.tumei.io.protocol.ProtoAnnotation;
import org.bson.codecs.UuidCodec;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
@ProtoAnnotation(
        ProtoType = 65544
)
@Component
public class ReturnLogin extends BaseProtocol {
    private String result;
    private int timestamp;
    private String uuid;
    private int rate;
    private int zone;
    private String zonename;
    private String channelid;
    private int pay;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "ReturnLogin{" +
                "result='" + result + '\'' +
                ", timestamp=" + timestamp +
                ", uuid='" + uuid + '\'' +
                ", rate=" + rate +
                ", zone=" + zone +
                ", zonename='" + zonename + '\'' +
                ", channelid='" + channelid + '\'' +
                ", pay=" + pay +
                '}';
    }

    public String getZonename() {
        return zonename;
    }

    public void setZonename(String zonename) {
        this.zonename = zonename;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public ReturnLogin() {}
}
