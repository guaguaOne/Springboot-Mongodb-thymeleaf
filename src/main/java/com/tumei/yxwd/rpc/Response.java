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
@Scope("prototype")
public class Response extends BaseProtocol {
    private int Id;
    private String Account;
    private String Token;
    private int Mode;
    private String Sdk;
    private String App;
    private String Uin;
    private String Sess;

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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getMode() {
        return Mode;
    }

    public void setMode(int mode) {
        Mode = mode;
    }

    public String getSdk() {
        return Sdk;
    }

    public void setSdk(String sdk) {
        Sdk = sdk;
    }

    public String getApp() {
        return App;
    }

    public void setApp(String app) {
        App = app;
    }

    public String getUin() {
        return Uin;
    }

    public void setUin(String uin) {
        Uin = uin;
    }

    public String getSess() {
        return Sess;
    }

    public void setSess(String sess) {
        Sess = sess;
    }

    public Response() {}


    @Override
    public void onProcess(Session session) {
        super.process(session);

        ReturnLogin rl = new ReturnLogin();

        int status = session.LoggingSession(true);
        if (status == 0) {
//            session.AttachUser();
            rl.setZonename("测试协议");
            session.send(rl);
        }
    }
}
