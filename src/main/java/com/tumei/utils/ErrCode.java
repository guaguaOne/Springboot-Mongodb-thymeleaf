package com.tumei.utils;

/**
 */
public class ErrCode {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private ErrCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static final ErrCode E_OK = new ErrCode(0, "正常");
    public static final ErrCode E_FAIL = new ErrCode(1, "失败");

    /* 登录 注册 相关 */
    public static final ErrCode E_正在登录中 = new ErrCode(1000, "正在登录中");
    public static final ErrCode E_已经登录 = new ErrCode(1001, "已经登录");
    public static final ErrCode E_错误的密码 = new ErrCode(1002, "错误的帐号名或密码");

    /* 游戏相关 */
}
