package com.tumei.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tumei.io.protocol.BaseProtocol;
import com.tumei.utils.ErrCode;
import com.tumei.yxwd.rpc.Challenge;
import com.tumei.utils.GzipUtil;
import com.tumei.utils.JsonUtil;
import com.tumei.utils.RandomUtil;
import com.tumei.yxwd.rpc.RequestCenterInfo;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.UUID;

/**
 *
 * 会话表示一个tcp连接相关的内容
 *
 */
public class Session {
    public static int Status_Logging = 1;
    public static int Status_Logged = 2;
    public static int Status_Logout = 3;

    /**
     * 是否压缩数据流
     */
    private static boolean Compress = true;

    private Log log = LogFactory.getLog(Session.class);

    /**
     * 会话的唯一编号
     */
    private String ID;

    /**
     * 当前会话状态
     */
    private int Status;

    /**
     * 绑定的玩家信息
     */
    private IUser Tag;

    /**
     * 对应的处理器上下文
     */
    private ChannelHandlerContext context;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    /**
     * 服务器
     */
    private TcpServer server;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Session(TcpServer _server, ChannelHandlerContext ctx) {
        server = _server;
        setID(UUID.randomUUID().toString());
        context = ctx;
    }

    /**
     * 绑定
     * @param _tag
     */
    public void Attach(IUser _tag) {
        Tag = _tag;
    }

    /**
     * 解除绑定
     */
    public void Detach() {
        Tag = null;
    }

    /**
     *
     * 服务器主动关闭这个Session
     */
    public void Close() {
        if (context != null) {
            context.close();
        }
    }

    /**
     * 首次建立连接 服务器发送8bit 随机Challenge
     *
     */
    public void MakeChallenge() {
        log.info("make challenge");
        Challenge challenge = new Challenge();
        challenge.Nonce = RandomUtil.randomDigest();
        send(challenge);
    }

    /**
     * ChannelHandler回调 会话生成时，迅速返回一个16bit的随机数用于会话通信等.
     */
    public void OnAdd() {
        MakeChallenge();
    }

    /**
     * ChannelHandler回调 会话结束时，如果会话认证通过，则有玩家信息，需要将玩家信息也退出
     *
     * 这个函数只会由网络层回调，所以他是线程安全的
     */
    public void OnDelete() {
        // 检查绑定的玩家帐号，进行对应的退出或者暂存工作
        if (Tag != null) {
            Tag._exit();
            Tag = null;
        }
    }

    /**
     * ChannelHandler回调，新的协议到来
     */
    public void OnReceive(NettyMessage nm) {

        Class<? extends BaseProtocol> bp = server.getProtocol(nm.getMsgType());
        if (bp != null) {
            try {
                // 将当前反序列化得到的具体类型push到Actor处理线程
                byte[] data = nm.getPayload();
                if (Compress) {
                    data = GzipUtil.Decompress(data);
                }

//                String s = new String(data, "UTF-8");
//                info("得到json:[%s]", s);
                BaseProtocol protocol = JsonUtil.Unmarshal(data, RequestCenterInfo.class);
                if (protocol != null) {
                    protocol.process(this);
                }
            } catch (IOException e) {
                error("协议[%d]反序列化失败[%s]", nm.getMsgType(), e.getMessage());
            }
        } else {
            error("--- 注册的协议集合中，无法找到协议编号:" + nm.getMsgType());
        }

    }

    /**
     * 发送协议
     * @param proto
     */
    public boolean send(BaseProtocol proto) {
        NettyMessage nm = new NettyMessage();
        nm.setMsgType(proto.get__msgType());

        try {
            byte[] data = JsonUtil.Marshal(proto);
            if (Compress) {
                data = GzipUtil.Compress(data);
                if (data == null) {
                    return false;
                }
            }

            nm.setMsgSize(data.length);
            nm.setPayload(data);

            if (context == null) {
                return false;
            }

            context.writeAndFlush(nm);
        } catch (JsonProcessingException e) {
            error("JsonProcessingException:", e);
            return false;
        } catch (Exception ex) {
            error("发送失败:", ex);
            return false;
        }

        return true;
    }

    /**
     * 锁定会话进行登录，防止通知进行多次登录验证
     * @param
     *          _force 是否设定当前状态为登录状态
     * @return
     */
    public int LoggingSession(boolean _force) {
        if (Status == Status_Logging) {
            return ErrCode.E_正在登录中.getCode();
        } else if (Status == Status_Logged) {
            return ErrCode.E_已经登录.getCode();
        }

        if (_force) {
            Status = Status_Logging;
        }

        return ErrCode.E_OK.getCode();
    }


    /** 一些Session下的日志处理 **/
    private String arrange(String format, Object...args) {
        return "[ID:" + ID + "]" + String.format(format, args);
    }

    public void debug(String format, Object...args) {
        log.debug(arrange(format, args));
    }
    public void info(String format, Object...args) {
        log.info(arrange(format, args));
    }
    public void warn(String format, Object...args) {
        log.warn(arrange(format, args));
    }
    public void error(String format, Object...args) {
        log.error(arrange(format, args));
    }
}
