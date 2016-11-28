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
    public String result;
    public int timestamp;
    public String uuid;
    public int rate;
    public int zone;
    public String zonename;
    public String channelid;
    public int pay;

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


    public ReturnLogin() {}
}
