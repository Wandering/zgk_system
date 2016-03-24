/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  Department.java 2015-09-01 11:47:23 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Department extends CreateBaseDomain{
    private Long departmentCode;
    private Long companyCode;
    private Long parentCode;
    private String departmentPhone;
    private String departmentFax;
    private String departmentPrincipal;
    private String departmentName;
    private Integer seqSort;
    private String description;
    private String areaCode;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Department(){
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

