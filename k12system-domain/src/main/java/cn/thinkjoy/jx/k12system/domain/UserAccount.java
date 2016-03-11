/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  UserAccount.java 2015-09-01 18:16:38 $
 */



package cn.thinkjoy.jx.k12system.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserAccount extends CreateBaseDomain{
    private Long accountCode;
    private Long userCode;
    private Long identityCode;
    private String loginNumber;
    private String password;
    private Integer userType;
    private String description;

    public UserAccount(){
    }
    public void setAccountCode(Long value) {
        this.accountCode = value;
    }

    public Long getAccountCode() {
        return this.accountCode;
    }
    public void setUserCode(Long value) {
        this.userCode = value;
    }

    public Long getUserCode() {
        return this.userCode;
    }
    public void setIdentityCode(Long value) {
        this.identityCode = value;
    }

    public Long getIdentityCode() {
        return this.identityCode;
    }
    public void setLoginNumber(String value) {
        this.loginNumber = value;
    }

    public String getLoginNumber() {
        return this.loginNumber;
    }
    public void setPassword(String value) {
        this.password = value;
    }

    public String getPassword() {
        return this.password;
    }
    public void setUserType(Integer value) {
        this.userType = value;
    }

    public Integer getUserType() {
        return this.userType;
    }
    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("Id",getId())
                .append("AccountCode",getAccountCode())
                .append("UserCode",getUserCode())
                .append("IdentityCode",getIdentityCode())
                .append("LoginNumber",getLoginNumber())
                .append("Password",getPassword())
                .append("Status",getStatus())
                .append("UserType",getUserType())
                .append("Description",getDescription())
                .append("Creator",getCreator())
                .append("CreateDate",getCreateDate())
                .append("LastModDate",getLastModDate())
                .append("LastModifier",getLastModifier())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof UserAccount == false) return false;
        if(this == obj) return true;
        UserAccount other = (UserAccount)obj;
        return new EqualsBuilder()
                .append(getId(),other.getId())
                .isEquals();
    }
}

