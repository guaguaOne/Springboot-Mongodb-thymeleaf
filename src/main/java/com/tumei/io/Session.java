package com.tumei.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tumei.io.protocol.BaseProtocol;
import com.tumei.io.protocol.rpc.Challenge;
import com.tumei.utils.GzipUtil;
import com.tumei.utils.JsonUtil;
import com.tumei.utils.RandomUtil;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.UUID;

/**
 * Created by Administrator on 2016/11/24 0024.
 *
 * 会话表示一个tcp连接相关的内容
 */
public class Session {
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
     * 对应的处理器上下文
     */
    private ChannelHandlerContext context;

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
     * 首次建立连接 服务器发送8bit 随机Challenge
     *
     */
    public void MakeChallenge() {
        Challenge challenge = new Challenge();
        challenge.setNonce(RandomUtil.RandomDigest());
        send(challenge);
    }

    /**
     * ChannelHandler回调 会话生成
     */
    public void OnAdd() {
        MakeChallenge();
    }

    /**
     * ChannelHandler回调 会话结束
     */
    public void OnDelete() {
        // 检查绑定的玩家帐号，进行对应的退出或者暂存工作
    }

    /**
     * ChannelHandler回调，新的协议到来
     */
    public void OnReceive(NettyMessage nm) {
        BaseProtocol bp = server.getProtocol(nm.getMsgType());
        if (bp != null) {
            // 将当前反序列化得到的具体类型push到Actor处理线程
            bp.process(this);
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
        nm.setMsgType(proto.getMsgType());

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
