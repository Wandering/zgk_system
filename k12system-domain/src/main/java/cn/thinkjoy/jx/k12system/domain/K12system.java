/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  K12system.java 2015-09-01 18:24:43 $
 */



package cn.thinkjoy.jx.k12system.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class K12system extends CreateBaseDomain{
    private Long systemCode;
    private String systemName;
    private String systemUrl;
    private String systemLogo;
    private Integer seqSort;
    private String description;

	public K12system(){
	}
    public void setSystemCode(Long value) {
        this.systemCode = value;
    }

    public Long getSystemCode() {
        return this.systemCode;
    }
    public void setSystemName(String value) {
        this.systemName = value;
    }

    public String getSystemName() {
        return this.systemName;
    }
    public void setSystemUrl(String value) {
        this.systemUrl = value;
    }

    public String getSystemUrl() {
        return this.systemUrl;
    }
    public void setSystemLogo(String value) {
        this.systemLogo = value;
    }

    public String getSystemLogo() {
        return this.systemLogo;
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
			.append("SystemCode",getSystemCode())
			.append("SystemName",getSystemName())
			.append("SystemUrl",getSystemUrl())
			.append("SystemLogo",getSystemLogo())
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
		if(obj instanceof K12system == false) return false;
		if(this == obj) return true;
		K12system other = (K12system)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

