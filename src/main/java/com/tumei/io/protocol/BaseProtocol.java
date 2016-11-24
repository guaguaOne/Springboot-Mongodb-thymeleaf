package com.tumei.io.protocol;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tumei.io.Session;

/**
 * 协议的基础类型
 *
 */
public class BaseProtocol {
   @JsonIgnore
   private int msgType;

   protected void preProcess(Session session) {
      session.debug(this.toString());
   }

   /**
    * 协议接收后的处理逻辑
    *
    */
   public void process(Session session) {}

   /**
    * 获取当前协议的编号
    * @return
    */
   public int getMsgType() {
      if (msgType == 0) {
         ProtoAnnotation annotation = getClass().getAnnotation(ProtoAnnotation.class);
         if (annotation != null) {
            msgType = annotation.ProtoType();
         }
      }

      return msgType;
   }
}
