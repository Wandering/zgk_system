/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  RoleResources.java 2015-09-01 11:47:25 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

public class RoleResources extends CreateBaseDomain{
    private Long roleCode;
    private Long resourceCode;

	public RoleResources(){
	}
    public void setRoleCode(Long value) {
        this.roleCode = value;
    }

    public Long getRoleCode() {
        return this.roleCode;
    }
    public void setResourceCode(Long value) {
        this.resourceCode = value;
    }

    public Long getResourceCode() {
        return this.resourceCode;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("RoleCode",getRoleCode())
			.append("ResourceCode",getResourceCode())
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
		if(obj instanceof RoleResources == false) return false;
		if(this == obj) return true;
		RoleResources other = (RoleResources)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

