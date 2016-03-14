/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  RoleMenu.java 2015-09-01 11:47:24 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

public class RoleMenu extends CreateBaseDomain{
    private Long menuCode;
    private Long roleCode;

	public RoleMenu(){
	}
    public void setMenuCode(Long value) {
        this.menuCode = value;
    }

    public Long getMenuCode() {
        return this.menuCode;
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
			.append("MenuCode",getMenuCode())
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
		if(obj instanceof RoleMenu == false) return false;
		if(this == obj) return true;
		RoleMenu other = (RoleMenu)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

