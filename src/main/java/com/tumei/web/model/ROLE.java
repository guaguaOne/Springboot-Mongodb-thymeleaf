package com.tumei.web.model;

/**
 * Created by leon on 2016/12/12.
 */
public enum ROLE {
    USER,   // 空白权限，非敏感信息读取
    XXKG,   // 小小矿工相关权限
    YXWD,   // 英雄无敌相关权限
    ADMIN,  // 管理权限的权限
    OWNER   // 拥有者，最高权限
}
