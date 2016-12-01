package com.tumei.io;

/**
 * Created by Administrator on 2016/12/1 0001.
 *
 * 针对不同的游戏，都需要实现共同的玩家接口
 *
 */
public interface IUser {
    Long getId();
    void setId(Long id);
    Session getSession();
    void setSession(Session session);
    boolean join();
    void _exit();
}
