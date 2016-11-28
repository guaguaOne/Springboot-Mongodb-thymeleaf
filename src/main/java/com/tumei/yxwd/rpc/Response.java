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
        ProtoType = 65539
)
@Component
public class Response extends BaseProtocol {
    public int Id;
    public String Account;
    public String Token;
    public int Mode;
    public String Sdk;
    public String App;
    public String Uin;
    public String Sess;

    @Override
    public String toString() {
        return "Response{" +
                "Id=" + Id +
                ", Account='" + Account + '\'' +
                ", Token='" + Token + '\'' +
                ", Mode=" + Mode +
                ", Sdk='" + Sdk + '\'' +
                ", App='" + App + '\'' +
                ", Uin='" + Uin + '\'' +
                ", Sess='" + Sess + '\'' +
                '}';
    }

    public Response() {}

    @Override
    public void onProcess(Session session) {
        super.process(session);

        ReturnLogin rl = new ReturnLogin();

        int status = session.LoggingSession(true);
        if (status == 0) {
//            session.AttachUser();
            rl.zonename = "测试协议";
            session.send(rl);
        }
    }
}
