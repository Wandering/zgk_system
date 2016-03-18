/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  Roles.java 2015-09-01 11:47:25 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

public class Roles extends CreateBaseDomain<Long>{
    private Long roleCode;
    private Long parentCode;
    private String roleName;
    private Integer seqSort;
    private String description;

	public Roles(){
	}
    public void setRoleCode(Long value) {
        this.roleCode = value;
    }

    public Long getRoleCode() {
        return this.roleCode;
    }
    public void setParentCode(Long value) {
        this.parentCode = value;
    }

    public Long getParentCode() {
        return this.parentCode;
    }
    public void setRoleName(String value) {
        this.roleName = value;
    }

    public String getRoleName() {
        return this.roleName;
    }
    public void setSeqSort(Integer value) {
        this.seqSort = value;
    }

    public Integer getSeqSort() {
        return this.seqSort;
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
			.append("RoleCode",getRoleCode())
			.append("ParentCode",getParentCode())
			.append("RoleName",getRoleName())
			.append("Status",getStatus())
			.append("SeqSort",getSeqSort())
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
		if(obj instanceof Roles == false) return false;
		if(this == obj) return true;
		Roles other = (Roles)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

