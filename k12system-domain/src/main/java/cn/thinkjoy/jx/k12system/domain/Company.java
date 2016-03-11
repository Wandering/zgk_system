/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  Company.java 2015-09-01 11:47:22 $
 */



package cn.thinkjoy.jx.k12system.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class Company extends CreateBaseDomain{
    private Long companyCode;
    private Long productCode;
    private String companyName;
    private String companyShortName;
    private String companyLogo;
    private String companyAddress;
    private String zipCode;
    private String contactPhone;
    private String contactFax;
    private String contactPerson;
    private String email;
    private Integer seqSort;
    private String description;

	public Company(){
	}
    public void setCompanyCode(Long value) {
        this.companyCode = value;
    }

    public Long getCompanyCode() {
        return this.companyCode;
    }
    public void setProductCode(Long value) {
        this.productCode = value;
    }

    public Long getProductCode() {
        return this.productCode;
    }
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    public String getCompanyName() {
        return this.companyName;
    }
    public void setCompanyShortName(String value) {
        this.companyShortName = value;
    }

    public String getCompanyShortName() {
        return this.companyShortName;
    }
    public void setCompanyLogo(String value) {
        this.companyLogo = value;
    }

    public String getCompanyLogo() {
        return this.companyLogo;
    }
    public void setCompanyAddress(String value) {
        this.companyAddress = value;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }
    public void setZipCode(String value) {
        this.zipCode = value;
    }

    public String getZipCode() {
        return this.zipCode;
    }
    public void setContactPhone(String value) {
        this.contactPhone = value;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }
    public void setContactFax(String value) {
        this.contactFax = value;
    }

    public String getContactFax() {
        return this.contactFax;
    }
    public void setContactPerson(String value) {
        this.contactPerson = value;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }
    public void setEmail(String value) {
        this.email = value;
    }

    public String getEmail() {
        return this.email;
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
			.append("CompanyCode",getCompanyCode())
			.append("ProductCode",getProductCode())
			.append("CompanyName",getCompanyName())
			.append("CompanyShortName",getCompanyShortName())
			.append("CompanyLogo",getCompanyLogo())
			.append("CompanyAddress",getCompanyAddress())
			.append("ZipCode",getZipCode())
			.append("ContactPhone",getContactPhone())
			.append("ContactFax",getContactFax())
			.append("ContactPerson",getContactPerson())
			.append("Email",getEmail())
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
		if(obj instanceof Company == false) return false;
		if(this == obj) return true;
		Company other = (Company)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

