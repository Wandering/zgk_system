/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  SystemProduct.java 2015-09-01 11:47:25 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SystemProduct extends CreateBaseDomain{
    private Long systemCode;
    private Long productCode;

	public SystemProduct(){
	}
    public void setSystemCode(Long value) {
        this.systemCode = value;
    }

    public Long getSystemCode() {
        return this.systemCode;
    }
    public void setProductCode(Long value) {
        this.productCode = value;
    }

    public Long getProductCode() {
        return this.productCode;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SystemCode",getSystemCode())
			.append("ProductCode",getProductCode())
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
		if(obj instanceof SystemProduct == false) return false;
		if(this == obj) return true;
		SystemProduct other = (SystemProduct)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

