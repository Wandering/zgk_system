/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  Department.java 2015-09-01 11:47:23 $
 */



package cn.thinkjoy.zgk.zgksystem.pojo;


import cn.thinkjoy.zgk.zgksystem.domain.DepartmentProductRelation;

import java.util.List;

public class DepartmentPojo {
    private long id; //部门Id
    private long departmentCode;//部门Code
    private long companyCode;//代理公司Code
    private long parentCode; //上级部门Code
    private String departmentPhone; //部门电话
    private String departmentFax;//部门传真
    private String departmentPrincipal;//部门负责人
    private String departmentName;//部门名称
    private String description;//部门描述
    private int roleType;
    private String areaCode;
    private String goodsAddress;//取货地址
    private List<DepartmentProductRelation> products;//商品集合

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(long departmentCode) {
        this.departmentCode = departmentCode;
    }

    public long getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(long companyCode) {
        this.companyCode = companyCode;
    }

    public long getParentCode() {
        return parentCode;
    }

    public void setParentCode(long parentCode) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getGoodsAddress() {
        return goodsAddress;
    }

    public void setGoodsAddress(String goodsAddress) {
        this.goodsAddress = goodsAddress;
    }

    public List<DepartmentProductRelation> getProducts() {
        return products;
    }

    public void setProducts(List<DepartmentProductRelation> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "DepartmentPojo{" +
                "id=" + id +
                ", departmentCode=" + departmentCode +
                ", companyCode=" + companyCode +
                ", parentCode=" + parentCode +
                ", departmentPhone='" + departmentPhone + '\'' +
                ", departmentFax='" + departmentFax + '\'' +
                ", departmentPrincipal='" + departmentPrincipal + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", description='" + description + '\'' +
                ", roleType=" + roleType +
                ", areaCode='" + areaCode + '\'' +
                ", goodsAddress='" + goodsAddress + '\'' +
                ", products=" + products +
                '}';
    }
}

