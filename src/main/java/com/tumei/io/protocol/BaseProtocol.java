package com.tumei.io.protocol;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tumei.io.Session;

/**
 * 协议的基础类型
 */
public class BaseProtocol {
    @JsonIgnore
    private int __msgType;

    /***
     * golang 前端传送过来的多余数据
     */
    public int PROTOCOL_TYPE;
    public int PROTOCOL_SIZE;
    public int PROTOCOL_PRIO;

    /**
     * 预处理
     * @param session
     */
    protected void preProcess(Session session) {
        session.info(this.toString());
    }

    /**
     * 后处理
     * @param session
     */
    protected void postProcess(Session session) {
    }

    /**
     * 协议接收后的处理逻辑
     */
    public void process(Session session) {
        preProcess(session);
        onProcess(session);
        postProcess(session);
    }

    public void onProcess(Session session) {

    }

    /**
     * 获取当前协议的编号
     *
     * @return
     */
    public int get__msgType() {
        if (__msgType == 0) {
            ProtoAnnotation annotation = getClass().getAnnotation(ProtoAnnotation.class);
            if (annotation != null) {
                __msgType = annotation.ProtoType();
            }
        }

        return __msgType;
    }
}
