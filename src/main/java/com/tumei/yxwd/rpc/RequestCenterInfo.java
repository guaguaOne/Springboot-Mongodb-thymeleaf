package com.tumei.yxwd.rpc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tumei.io.Session;
import com.tumei.io.protocol.BaseProtocol;
import com.tumei.io.protocol.ProtoAnnotation;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
@ProtoAnnotation(
        ProtoType = 16711683
)
@Component
public class RequestCenterInfo extends BaseProtocol {
    public int Platform;
    public String Account;
    public String Digest;
    public String Device;

    @Override
    public String toString() {
        return "RequestCenterInfo{" +
                "Platform=" + Platform +
                ", Account='" + Account + '\'' +
                ", Digest='" + Digest + '\'' +
                ", Device='" + Device + '\'' +
                '}';
    }


    @Override
    public void onProcess(Session session) {

    }

    public RequestCenterInfo() {}
}
