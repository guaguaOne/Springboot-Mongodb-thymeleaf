package com.tumei.io;

import com.google.common.base.Strings;
import com.tumei.RunnerBean;
import com.tumei.utils.JsonUtil;
import com.tumei.yxwd.YxwdConfig;
import com.tumei.io.protocol.BaseProtocol;
import com.tumei.io.protocol.ProtoAnnotation;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import sun.util.resources.cldr.sbp.CurrencyNames_sbp;

import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tcp服务器
 */
public class TcpServer implements ApplicationContextAware {
    private ConfigurableApplicationContext ctx;
    private Log log = LogFactory.getLog(RunnerBean.class);

    private String host;
    private int port;
    private int backlog;

    @Autowired
    private YxwdConfig gameConfig;

    /**
     * 处理接入的线程组
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * 针对每个连接的线程组
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    /**
     * 接受连接的异步管理
     */
    private ChannelFuture bossFuture;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public ChannelFuture getBossFuture() {
        return bossFuture;
    }

    public void setBossFuture(ChannelFuture bossFuture) {
        this.bossFuture = bossFuture;
    }

    /**
     * 将协议的ID与协议的类名对应起来，便于查找获取对应的协议
     */
    public HashMap<Integer, Class<? extends BaseProtocol> > protocols = new HashMap<Integer, Class<? extends BaseProtocol>> ();

    /**
     * 当前所有会话连接
     */
    private ConcurrentHashMap<String, Session> sessions = new ConcurrentHashMap<String, Session>();

    public TcpServer() {
        JsonUtil.init();
    }

    /**
     * 启动tcp服务器
     * @return
     */
    public boolean startServer() {
        log.info(String.format("开始监听地址:%s:%d, backlog:%d %s", host, port, backlog, gameConfig.getVersion()));

        initProtocols();

        TcpServer ts = this;
        ServerBootstrap sbs = new ServerBootstrap();
        sbs.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                new TcpDecoder(ByteOrder.LITTLE_ENDIAN, 65536, 4, 4, 0, 0, true)
                        );
                        socketChannel.pipeline().addLast(
                                new TcpEncoder()
                        );
                        socketChannel.pipeline().addLast(new TcpHandler(ts));
                    }
                }).option(ChannelOption.SO_BACKLOG, backlog)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        bossFuture = sbs.bind(host, port);

        if (bossFuture == null) {
            return false;
        }

        return true;
    }

    /**
     * 关闭服务器
     * @param wait
     *      等待完美关闭的最长时间
     */
    public void stopServer(int wait) {
        if (bossFuture != null) {
            bossFuture.channel().closeFuture();
        }

        if (bossGroup != null) {
            try {
                bossGroup.shutdownGracefully().wait(wait);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }

        if (workerGroup != null) {
            try {
                workerGroup.shutdownGracefully().wait(wait);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
    }

    /**
     * 在注入的Protocol中查询指定类型id的协议
     * @param _type
     *              协议的类型ID
     * @return
     */
    public Class<? extends BaseProtocol> getProtocol(int _type) {
        Class<? extends BaseProtocol> bp = protocols.get(_type);
        if (bp == null) {
            return null;
        }
        return bp;
    }
    /**
     * 根据注入的带有ProtoAnnotation的协议，整理出对应的类型结构
     */
    public void initProtocols() {
        log.info("初始化容器内协议");
        String[] names = ctx.getBeanNamesForAnnotation(ProtoAnnotation.class);
        for (String name : names) {
            BaseProtocol bp = (BaseProtocol)ctx.getBean(name);
            ProtoAnnotation annotation = bp.getClass().getAnnotation(ProtoAnnotation.class);
            if (annotation == null) {
                continue;
            }

            if (bp != null) {
                log.info("注册协议， 类型:" + annotation.ProtoType() + " 协议名:" + bp.getClass().getName());
                protocols.put(annotation.ProtoType(), bp.getClass());
            }
        }
    }

    /**
     * 检测到新的连接建立
     *
     * @param ctx
     * @return
     */
    public Session OnConnected(ChannelHandlerContext ctx) {
        Session session = new Session(this, ctx);
        sessions.put(session.getID(), session);
        session.OnAdd();
        return session;
    }

    /***
     * 检测到某个连接中断
     * @param session
     */
    public void OnDisconnected(Session session) {
        sessions.remove(session.getID());
        session.OnDelete();
    }

    /**
     * 获取当前会话连接个数
     * @return
     */
    public int getSessionCount() {
        return sessions.size();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = (ConfigurableApplicationContext)applicationContext;
    }
}
