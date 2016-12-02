package com.tumei.yxwd.rpc;

import com.tumei.io.Session;
import com.tumei.io.protocol.BaseProtocol;
import com.tumei.io.protocol.ProtoAnnotation;
import com.tumei.yxwd.Game;
import com.tumei.yxwd.GameUser;
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

            // 通过客户端传递的参数决定使用何种方式进行登录的认证
            // 0:默认自己的帐号系统, 10: Anysdk的统一登录方式

            switch (this.Mode) {
                case 0:
                    break;
                case 10:
                    break;
            }
            // 登录成功后会话绑定角色
            Game.getInstance().OnAddUser(100001L, session);
            rl.Zonename = "测试协议";
            session.send(rl);
        } else { // 当前状态不允许登录
            session.warn("当前状态不允许登录: %s", status);
        }
    }
}
