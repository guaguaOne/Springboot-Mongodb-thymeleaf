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
    public String Nonce;

    @Override
    public String toString() {
        return "Challenge{" +
                "Nonce='" + Nonce + '\'' +
                '}';
    }

    public Challenge() {}
}
