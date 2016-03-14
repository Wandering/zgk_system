/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  Resources.java 2015-09-01 11:47:24 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

public class Resources extends CreateBaseDomain{
    private Long resourceCode;
    private Long menuCode;
    private String resourceName;
    private String resourceUrl;
    private String resourceIcon;
    private Integer seqSort;
    private String description;

	public Resources(){
	}
    public void setResourceCode(Long value) {
        this.resourceCode = value;
    }

    public Long getResourceCode() {
        return this.resourceCode;
    }
    public void setMenuCode(Long value) {
        this.menuCode = value;
    }

    public Long getMenuCode() {
        return this.menuCode;
    }
    public void setResourceName(String value) {
        this.resourceName = value;
    }

    public String getResourceName() {
        return this.resourceName;
    }
    public void setResourceUrl(String value) {
        this.resourceUrl = value;
    }

    public String getResourceUrl() {
        return this.resourceUrl;
    }
    public void setResourceIcon(String value) {
        this.resourceIcon = value;
    }

    public String getResourceIcon() {
        return this.resourceIcon;
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
			.append("ResourceCode",getResourceCode())
			.append("MenuCode",getMenuCode())
			.append("ResourceName",getResourceName())
			.append("ResourceUrl",getResourceUrl())
			.append("ResourceIcon",getResourceIcon())
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
		if(obj instanceof Resources == false) return false;
		if(this == obj) return true;
		Resources other = (Resources)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

