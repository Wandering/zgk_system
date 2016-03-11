package cn.thinkjoy.jx.k12system.pojo;


import java.util.List;

import java.io.Serializable;

/**
 * Created by yhwang on 15/9/8.
 */
public class IdentityPojo implements Serializable{

    private Long id;

    private Long identityCode;

    private List<Long> systemCode;

    private String identityName;

    private String description;

    public List<Long> getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(List<Long> systemCode) {
        this.systemCode = systemCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(Long identityCode) {
        this.identityCode = identityCode;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
