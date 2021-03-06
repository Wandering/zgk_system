/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  PostDataauthority.java 2015-10-20 14:39:49 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PostDataauthority extends CreateBaseDomain{
    private Long postCode;
    private Long areaId;
    private Long schoolId;

	public PostDataauthority(){
	}
    public void setPostCode(Long value) {
        this.postCode = value;
    }

    public Long getPostCode() {
        return this.postCode;
    }
    public void setAreaId(Long value) {
        this.areaId = value;
    }

    public Long getAreaId() {
        return this.areaId;
    }
    public void setSchoolId(Long value) {
        this.schoolId = value;
    }

    public Long getSchoolId() {
        return this.schoolId;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("PostCode",getPostCode())
			.append("AreaId",getAreaId())
			.append("SchoolId",getSchoolId())
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
		if(obj instanceof PostDataauthority == false) return false;
		if(this == obj) return true;
		PostDataauthority other = (PostDataauthority)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

