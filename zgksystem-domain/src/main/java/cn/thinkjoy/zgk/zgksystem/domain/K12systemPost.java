/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  K12systemPost.java 2015-09-14 15:42:38 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class K12systemPost extends CreateBaseDomain{
    private Long postCode;
    private Long systemCode;

	public K12systemPost(){
	}
    public void setPostCode(Long value) {
        this.postCode = value;
    }

    public Long getPostCode() {
        return this.postCode;
    }
    public void setSystemCode(Long value) {
        this.systemCode = value;
    }

    public Long getSystemCode() {
        return this.systemCode;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("PostCode",getPostCode())
			.append("SystemCode",getSystemCode())
			.append("Status",getStatus())
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
		if(obj instanceof K12systemPost == false) return false;
		if(this == obj) return true;
		K12systemPost other = (K12systemPost)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

