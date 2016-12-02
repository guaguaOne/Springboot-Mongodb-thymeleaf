package com.tumei.yxwd.rpc;

import com.tumei.io.protocol.BaseProtocol;
import com.tumei.io.protocol.ProtoAnnotation;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
@ProtoAnnotation(
        ProtoType = 65544
)
@Component
public class ReturnLogin extends BaseProtocol {
    public String Result;
    public int Timestamp;
    public String Uuid;
    public int Rate;
    public int Zone;
    public String Zonename;
    public String Channelid;
    public int Pay;

    @Override
    public String toString() {
        return "ReturnLogin{" +
                "Result='" + Result + '\'' +
                ", timestamp=" + Timestamp +
                ", uuid='" + Uuid + '\'' +
                ", rate=" + Rate +
                ", zone=" + Zone +
                ", zonename='" + Zonename + '\'' +
                ", channelid='" + Channelid + '\'' +
                ", pay=" + Pay +
                '}';
    }

    public ReturnLogin() {}
}
