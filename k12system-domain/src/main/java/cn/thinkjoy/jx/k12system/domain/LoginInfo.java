/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  LoginInfo.java 2015-09-01 11:47:24 $
 */



package cn.thinkjoy.jx.k12system.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class LoginInfo extends CreateBaseDomain{
    private Long accountCode;
    private Long loginTime;
    private String accessToken;
    private Long invalidTime;
    private String description;

	public LoginInfo(){
	}
    public void setAccountCode(Long value) {
        this.accountCode = value;
    }

    public Long getAccountCode() {
        return this.accountCode;
    }
    public void setLoginTime(Long value) {
        this.loginTime = value;
    }

    public Long getLoginTime() {
        return this.loginTime;
    }
    public void setAccessToken(String value) {
        this.accessToken = value;
    }

    public String getAccessToken() {
        return this.accessToken;
    }
    public void setInvalidTime(Long value) {
        this.invalidTime = value;
    }

    public Long getInvalidTime() {
        return this.invalidTime;
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
			.append("LoginTime",getLoginTime())
			.append("AccessToken",getAccessToken())
			.append("InvalidTime",getInvalidTime())
			.append("Description",getDescription())
			.append("Creator",getCreator())
			.append("CreateDate",getCreateDate())
			.append("LastModDate",getLastModDate())
			.append("LastModifier",getLastModifier())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LoginInfo == false) return false;
		if(this == obj) return true;
		LoginInfo other = (LoginInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

