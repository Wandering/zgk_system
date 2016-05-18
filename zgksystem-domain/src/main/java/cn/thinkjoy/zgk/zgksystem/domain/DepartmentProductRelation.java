/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: system
 * $Id:  DepartmentProductRelation.java 2016-05-18 11:15:12 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class DepartmentProductRelation extends CreateBaseDomain{
    private Long productId;
    private String productName;
    private Long departmentCode;
    private Double salePrice;
    private Double pickupPrice;

	public DepartmentProductRelation(){
	}
    public void setProductId(Long value) {
        this.productId = value;
    }

    public Long getProductId() {
        return this.productId;
    }
    public void setProductName(String value) {
        this.productName = value;
    }

    public String getProductName() {
        return this.productName;
    }
    public void setDepartmentCode(Long value) {
        this.departmentCode = value;
    }

    public Long getDepartmentCode() {
        return this.departmentCode;
    }
    public void setSalePrice(Double value) {
        this.salePrice = value;
    }

    public Double getSalePrice() {
        return this.salePrice;
    }
    public void setPickupPrice(Double value) {
        this.pickupPrice = value;
    }

    public Double getPickupPrice() {
        return this.pickupPrice;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Creator",getCreator())
			.append("CreateDate",getCreateDate())
			.append("LastModDate",getLastModDate())
			.append("LastModifier",getLastModifier())
			.append("ProductId",getProductId())
			.append("ProductName",getProductName())
			.append("DepartmentCode",getDepartmentCode())
			.append("SalePrice",getSalePrice())
			.append("PickupPrice",getPickupPrice())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof DepartmentProductRelation == false) return false;
		if(this == obj) return true;
		DepartmentProductRelation other = (DepartmentProductRelation)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

