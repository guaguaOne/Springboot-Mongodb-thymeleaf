package com.tumei.yxwd.rpc;

import com.tumei.io.Session;
import com.tumei.io.protocol.BaseProtocol;
import com.tumei.io.protocol.ProtoAnnotation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
@ProtoAnnotation(
        ProtoType = 16711683
)
@Component
public class RequestCenterInfo extends BaseProtocol {
    private short platform;
    private String account;
    private String digest;
    private String device;

    @Override
    public String toString() {
        return "RequestCenterInfo{" +
                "platform=" + platform +
                ", account='" + account + '\'' +
                ", digest='" + digest + '\'' +
                ", device='" + device + '\'' +
                '}';
    }

    public short getPlatform() {
        return platform;
    }

    public void setPlatform(short platform) {
        this.platform = platform;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public void onProcess(Session session) {
    }

    public RequestCenterInfo() {}
}
