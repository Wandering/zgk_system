/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  Menu.java 2015-09-01 11:47:24 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Menu extends CreateBaseDomain{
    private Long menuCode;
    private Long parentCode;
    private String menuName;
    private String menuUrl;
    private String menuIcon;
    private Integer seqSort;
    private String description;

	public Menu(){
	}
    public void setMenuCode(Long value) {
        this.menuCode = value;
    }

    public Long getMenuCode() {
        return this.menuCode;
    }
    public void setParentCode(Long value) {
        this.parentCode = value;
    }

    public Long getParentCode() {
        return this.parentCode;
    }
    public void setMenuName(String value) {
        this.menuName = value;
    }

    public String getMenuName() {
        return this.menuName;
    }
    public void setMenuUrl(String value) {
        this.menuUrl = value;
    }

    public String getMenuUrl() {
        return this.menuUrl;
    }
    public void setMenuIcon(String value) {
        this.menuIcon = value;
    }

    public String getMenuIcon() {
        return this.menuIcon;
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
			.append("MenuCode",getMenuCode())
			.append("ParentCode",getParentCode())
			.append("MenuName",getMenuName())
			.append("MenuUrl",getMenuUrl())
			.append("MenuIcon",getMenuIcon())
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
		if(obj instanceof Menu == false) return false;
		if(this == obj) return true;
		Menu other = (Menu)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

