package com.tumei.io;

import io.netty.buffer.ByteBuf;

public class NettyMessage {
    /**
     * 消息类型ID，用于定位真实的处理类
     */
    private int msgType;
    /**
     * 消息Body的真实长度
     */
    private int msgSize;
    /**
     * 负载
     */
    private byte[] payload;

    public void Encode(ByteBuf _buf) {
        _buf.writeInt(msgType);
        _buf.writeInt(msgSize);
        if (payload != null) {
            _buf.writeBytes(payload, 0, msgSize); //
        }
    }

    public static NettyMessage Decode(ByteBuf _buf) {
        NettyMessage msg = new NettyMessage();
        msg.setMsgType(_buf.readInt());
        msg.setMsgSize(_buf.readInt());
        if (_buf.readableBytes() >= msg.msgSize) {
            msg.setPayload(new byte[msg.msgSize]);
            _buf.readBytes(msg.payload, 0, msg.msgSize);
        }
        return msg;
    }

    public NettyMessage() {
    }

    /**
     * @return the msgType
     */
    public int getMsgType() {
        return msgType;
    }

    /**
     * @param msgType
     *            the msgType to set
     */
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    /**
     * @return the msgSize
     */
    public int getMsgSize() {
        return msgSize;
    }

    /**
     * @param msgSize
     *            the msgSize to set
     */
    public void setMsgSize(int msgSize) {
        this.msgSize = msgSize;
    }

    /**
     * @return the payload
     */
    public byte[] getPayload() {
        return payload;
    }

    /**
     * @param payload
     *            the payload to set
     */
    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public void Dispose() {
        if (payload != null) {
//			ReferenceCountUtil.release(payload);
            payload = null;
        }
    }
}
