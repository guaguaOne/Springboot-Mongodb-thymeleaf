package com.tumei.web.params;

public class RegisterAccount {
    private String name;
    private String password;
    private String mailbox;
    private String version; // 注册版本
    private String idfa; // 注册Idfa;
    private String osInfo; // 注册机型信息，可包括屏幕大小，机器制造商，内存大小等
    private String ext1;
    private String ext2;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getOsInfo() {
        return osInfo;
    }

    public void setOsInfo(String osInfo) {
        this.osInfo = osInfo;
    }
}
