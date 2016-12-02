package com.tumei.yxwd;
import com.tumei.io.IUser;
import com.tumei.io.Session;
import com.tumei.yxwd.dbmodel.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * GameUser本身保存极少的数据，主要提供各种操作，实际的数据由缓存自身维护, 这个就好比MVC中Controller
 */
public class GameUser implements IUser {
    /**
     * 玩家角色id
     */
    private Long id;
    private Session session;
    private DataAccessor dao;

    /* 有些数据经常使用的存储一份在外部 不需要查询缓存 */
    /**
     * 角色昵称
     */
    private String name;
    /**
     * 帐号名
     */
    private String account;
    /**
     * 设备唯一标识符
     */
    private String idfa;
    /**
     * 等级
     */
    private int level;
    /**
     * vip等级
     */
    private int vip;
    /**
     * 帐号等级
     */
    private int gmlevel;
    /* ********* END ********* */

    public GameUser(Long _id, Session _session) {
        id = _id;
        session = _session;
    }

    /**
     * 游戏玩家进入, 连接的建立，触发Session调用
     */
    @Override
    public boolean join() {
        /* 游戏玩家进入后，会进行自身的检查和处理 */
        synchronized (this)
        {
            // 读取基本信息，填充本类的一些缓存信息
            Role role = dao.findRole(id);
            if (role == null) {
                error("无法查询的玩家(%ld)的基本信息.", id);
                return false;
            }

            // ...
            info("查询到玩家: %s", role.nickname);
        }
        return true;
    }

    /***
     * 游戏玩家退出 用于只会由于连接的断开，触发session来调用
     * (注意不要在游戏逻辑中调用这个函数，仅供Session调用)
     */
    @Override
    public void _exit() {
        // 这个删除未必是有效的，如果用户主动退出有效，被动的时候可能GameUser已经不存在或者切换成另外的玩家了
        Game.getInstance().OnDelUser(this);

        // 玩家退出时，将自身暂存的信息进行保存处理。
        synchronized (this)
        {
            info("玩家(%s)断开连接", id);
        }
    }

    /**
     * 关闭连接: 用于游戏逻辑中关闭用户
     */
    public void close() {
        synchronized (this)
        {
            if (session != null) {
                session.Close();
                session = null;
            }
        }
    }

    @Override
    public String toString() {
        return "GameUser{" +
                "id=" + id +
                ", session=" + session +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", idfa='" + idfa + '\'' +
                ", level=" + level +
                ", vip=" + vip +
                ", gmlevel=" + gmlevel +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getGmlevel() {
        return gmlevel;
    }

    public void setGmlevel(int gmlevel) {
        this.gmlevel = gmlevel;
    }

    public DataAccessor getDao() {
        return dao;
    }

    public void setDao(DataAccessor dao) {
        this.dao = dao;
    }


    /** 一些Session下的日志处理 **/
    private Log log = LogFactory.getLog(GameUser.class);

    private String arrange(String format, Object...args) {
        return "[user:" + id + " name:" + name + "]" + String.format(format, args);
    }
    public void debug(String format, Object...args) {
        log.debug(arrange(format, args));
    }
    public void info(String format, Object...args) {
        log.info(arrange(format, args));
    }
    public void warn(String format, Object...args) {
        log.warn(arrange(format, args));
    }
    public void error(String format, Object...args) {
        log.error(arrange(format, args));
    }
}
