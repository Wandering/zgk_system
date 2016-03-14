package cn.thinkjoy.zgk.zgksystem.pojo;

/**
 * Created by yhwang on 15/9/1.
 */
public class K12SystemPojo extends UserPojo{

    private Long systemId;

    private Long systemCode; //系统code

    private String systemName;//系统名称

    private String systemUrl;//系统链接

    private String systemLogo;//系统LOGO

    private String description; //系统描述

    public Long getSystemId() {
        return systemId;
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Long getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(Long systemCode) {
        this.systemCode = systemCode;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemUrl() {
        return systemUrl;
    }

    public void setSystemUrl(String systemUrl) {
        this.systemUrl = systemUrl;
    }

    public String getSystemLogo() {
        return systemLogo;
    }

    public void setSystemLogo(String systemLogo) {
        this.systemLogo = systemLogo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
