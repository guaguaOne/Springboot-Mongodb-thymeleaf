package com.tumei.web.params;

/**
 * Created by leon on 2016/11/5.
 */
public class AccountReturn {
    private int code;
    private String msg;
    private Long uid;

    public AccountReturn() {
        this(0, "", 0l);
    }

    public AccountReturn(Long uid) {
        this(0, "", uid);
    }

    public AccountReturn(int code, String msg) {
        this(code, msg, 0L);
    }

    public AccountReturn(int code, String msg, Long uid) {
        this.code = code;
        this.msg = msg;
        this.uid = uid;
    }

    /**
     * 0: 成功
     * others: 由msg描述问题信息
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 如果code != 0 返回信息内容
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 注册成功或者登录成功返回玩家ID
     */
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }
}
