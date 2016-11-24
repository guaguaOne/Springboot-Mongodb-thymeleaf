package com.tumei.io;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by leon on 2016/10/29.
 */
public class TcpEncoder extends MessageToMessageEncoder<NettyMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
        try {
            ByteBuf buf = Unpooled.buffer(8 + msg.getMsgSize());
            msg.Encode(buf);
            out.add(buf);
        } finally {
            msg.Dispose();
        }
    }
}

