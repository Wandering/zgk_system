/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  UserInfo.java 2015-09-01 11:47:26 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.util.*;

public class UserInfo extends CreateBaseDomain<Long>{
    private Long userCode;
    private Long departmentCode;
    private Long postCode;
    private String userName;
    private String phone;
    private String email;
    private Integer gender;
    private Date birthday;
    private Integer seqSort;
    private String description;

	public UserInfo(){
	}
    public void setUserCode(Long value) {
        this.userCode = value;
    }

    public Long getUserCode() {
        return this.userCode;
    }
    public void setDepartmentCode(Long value) {
        this.departmentCode = value;
    }

    public Long getDepartmentCode() {
        return this.departmentCode;
    }
    public void setPostCode(Long value) {
        this.postCode = value;
    }

    public Long getPostCode() {
        return this.postCode;
    }
    public void setUserName(String value) {
        this.userName = value;
    }

    public String getUserName() {
        return this.userName;
    }
    public void setPhone(String value) {
        this.phone = value;
    }

    public String getPhone() {
        return this.phone;
    }
    public void setEmail(String value) {
        this.email = value;
    }

    public String getEmail() {
        return this.email;
    }
    public void setGender(Integer value) {
        this.gender = value;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setBirthday(Date value) {
        this.birthday = value;
    }

    public Date getBirthday() {
        return this.birthday;
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
			.append("UserCode",getUserCode())
			.append("DepartmentCode",getDepartmentCode())
			.append("PostCode",getPostCode())
			.append("UserName",getUserName())
			.append("Phone",getPhone())
			.append("Email",getEmail())
			.append("Gender",getGender())
			.append("Birthday",getBirthday())
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
		if(obj instanceof UserInfo == false) return false;
		if(this == obj) return true;
		UserInfo other = (UserInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

