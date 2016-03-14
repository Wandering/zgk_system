/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  RolePost.java 2015-09-01 11:47:25 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RolePost extends CreateBaseDomain{
    private Long postCode;
    private Long roleCode;

	public RolePost(){
	}
    public void setPostCode(Long value) {
        this.postCode = value;
    }

    public Long getPostCode() {
        return this.postCode;
    }
    public void setRoleCode(Long value) {
        this.roleCode = value;
    }

    public Long getRoleCode() {
        return this.roleCode;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("PostCode",getPostCode())
			.append("RoleCode",getRoleCode())
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
		if(obj instanceof RolePost == false) return false;
		if(this == obj) return true;
		RolePost other = (RolePost)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

