package com.tumei.yxwd.rpc;

import com.tumei.io.protocol.BaseProtocol;
import com.tumei.io.protocol.ProtoAnnotation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/23 0023.
 */
@ProtoAnnotation(
        ProtoType = 65538
)
@Component
public class Challenge extends BaseProtocol {
    private String Nonce;

    public String getNonce() {
        return Nonce;
    }

    public void setNonce(String nonce) {
        Nonce = nonce;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "Nonce='" + Nonce + '\'' +
                '}';
    }

    public Challenge(String nonce) {
        Nonce = nonce;
    }

    public Challenge() {}
}
