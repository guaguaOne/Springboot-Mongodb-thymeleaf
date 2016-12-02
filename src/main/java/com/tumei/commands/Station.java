package com.tumei.commands;

import com.tumei.yxwd.Game;
import org.crsh.cli.Command;
import org.crsh.cli.Usage;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class Station {
    @Usage("command line tool")
    @Command
    public String UserCount() {
        int count = Game.getInstance().getCount();
        return String.format("当前玩家数量:%d", count);
    }
}
