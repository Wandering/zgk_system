package cn.thinkjoy.zgk.zgksystem.pojo;

import java.io.Serializable;

/**
 * Created by yhwang on 15/10/23.
 */
public class ClientInfoPojo implements Serializable{
    private Long systemCode;//用户所进入系统code

    public Long getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(Long systemCode) {
        this.systemCode = systemCode;
    }
}
