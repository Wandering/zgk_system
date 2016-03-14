/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  Department.java 2015-09-01 11:47:23 $
 */



package cn.thinkjoy.zgk.zgksystem.pojo;


public class DepartmentPojo extends UserPojo{
    private Long id; //部门Id
    private Long departmentCode;//部门Code
    private Long companyCode;//代理公司Code
    private Long parentCode; //上级部门Code
    private String departmentPhone; //部门电话
    private String departmentFax;//部门传真
    private String departmentPrincipal;//部门负责人
    private String departmentName;//部门名称
    private Integer seqSort; //部门排序
    private String description;//部门描述

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getDepartmentCode() {
        return departmentCode;
    }

    @Override
    public void setDepartmentCode(Long departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Long getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(Long companyCode) {
        this.companyCode = companyCode;
    }

    public Long getParentCode() {
        return parentCode;
    }

    public void setParentCode(Long parentCode) {
        this.parentCode = parentCode;
    }

    public String getDepartmentPhone() {
        return departmentPhone;
    }

    public void setDepartmentPhone(String departmentPhone) {
        this.departmentPhone = departmentPhone;
    }

    public String getDepartmentFax() {
        return departmentFax;
    }

    public void setDepartmentFax(String departmentFax) {
        this.departmentFax = departmentFax;
    }

    public String getDepartmentPrincipal() {
        return departmentPrincipal;
    }

    public void setDepartmentPrincipal(String departmentPrincipal) {
        this.departmentPrincipal = departmentPrincipal;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getSeqSort() {
        return seqSort;
    }

    public void setSeqSort(Integer seqSort) {
        this.seqSort = seqSort;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}

