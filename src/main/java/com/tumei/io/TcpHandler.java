package com.tumei.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * Created by Administrator on 2016/11/23 0023.
 *
 */
public class TcpHandler extends ChannelInboundHandlerAdapter {

    private Log log = LogFactory.getLog(TcpHandler.class);

    private TcpServer server;

    private Session session;

    public TcpHandler(TcpServer _server) {
        server = _server;
    }

    /***
     * 连接首次激活
     * @param _ctx 管道上下文
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext _ctx) throws Exception {
        log.info(">>> 收到连接请求");
        session = server.OnConnected(_ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext _ctx) throws Exception {
        log.info(">>> 断开连接");
        server.OnDisconnected(session);
    }

    /**
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        session.info("收到请求数据:");
        NettyMessage nm = null;
        try {
            nm = (NettyMessage) msg;
            if (session != null) {
                session.OnReceive(nm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (nm != null) {
                nm.Dispose();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable reason) {
        log.error("exception caught", reason);
        ctx.close();
    }

}
