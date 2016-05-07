/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: system
 * $Id:  Department.java 2016-04-15 15:26:03 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class Department extends CreateBaseDomain{
    private String areaCode;
    private Integer roleType;
    private Double wechatPrice;
    private Double webPrice;
    private Double salePrice;
    private String goodsAddress;
    private Long departmentCode;
    private Long companyCode;
    private Long parentCode;
    private String departmentPhone;
    private String departmentFax;
    private String departmentPrincipal;
    private String departmentName;
    private Integer seqSort;
    private String description;

	public Department(){
	}
    public void setAreaCode(String value) {
        this.areaCode = value;
    }

    public String getAreaCode() {
        return this.areaCode;
    }
    public void setRoleType(Integer value) {
        this.roleType = value;
    }

    public Integer getRoleType() {
        return this.roleType;
    }
    public void setWechatPrice(Double value) {
        this.wechatPrice = value;
    }

    public Double getWechatPrice() {
        return this.wechatPrice;
    }
    public void setWebPrice(Double value) {
        this.webPrice = value;
    }

    public Double getWebPrice() {
        return this.webPrice;
    }
    public void setSalePrice(Double value) {
        this.salePrice = value;
    }

    public Double getSalePrice() {
        return this.salePrice;
    }
    public void setGoodsAddress(String value) {
        this.goodsAddress = value;
    }

    public String getGoodsAddress() {
        return this.goodsAddress;
    }
    public void setDepartmentCode(Long value) {
        this.departmentCode = value;
    }

    public Long getDepartmentCode() {
        return this.departmentCode;
    }
    public void setCompanyCode(Long value) {
        this.companyCode = value;
    }

    public Long getCompanyCode() {
        return this.companyCode;
    }
    public void setParentCode(Long value) {
        this.parentCode = value;
    }

    public Long getParentCode() {
        return this.parentCode;
    }
    public void setDepartmentPhone(String value) {
        this.departmentPhone = value;
    }

    public String getDepartmentPhone() {
        return this.departmentPhone;
    }
    public void setDepartmentFax(String value) {
        this.departmentFax = value;
    }

    public String getDepartmentFax() {
        return this.departmentFax;
    }
    public void setDepartmentPrincipal(String value) {
        this.departmentPrincipal = value;
    }

    public String getDepartmentPrincipal() {
        return this.departmentPrincipal;
    }
    public void setDepartmentName(String value) {
        this.departmentName = value;
    }

    public String getDepartmentName() {
        return this.departmentName;
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
			.append("AreaCode",getAreaCode())
			.append("RoleType",getRoleType())
			.append("WechatPrice",getWechatPrice())
			.append("WebPrice",getWebPrice())
			.append("SalePrice",getSalePrice())
			.append("GoodsAddress",getGoodsAddress())
			.append("DepartmentCode",getDepartmentCode())
			.append("CompanyCode",getCompanyCode())
			.append("ParentCode",getParentCode())
			.append("DepartmentPhone",getDepartmentPhone())
			.append("DepartmentFax",getDepartmentFax())
			.append("DepartmentPrincipal",getDepartmentPrincipal())
			.append("DepartmentName",getDepartmentName())
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
		if(obj instanceof Department == false) return false;
		if(this == obj) return true;
		Department other = (Department)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

