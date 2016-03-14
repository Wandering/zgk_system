/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  Product.java 2015-09-01 18:47:56 $
 */



package cn.thinkjoy.zgk.zgksystem.pojo;



public class ProductPojo extends UserPojo{
    private Long productId; //产品Id
    private Long productCode; //产品Code
    private String productName;//产品名称
    private String productLogo;//产品logo
    private String addressJson; //寻址地址
    private Integer seqSort; //产品排序
    private String description; //产品描述
    private String productIndexPage; //产品首页地址


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductCode() {
        return productCode;
    }

    public void setProductCode(Long productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLogo() {
        return productLogo;
    }

    public void setProductLogo(String productLogo) {
        this.productLogo = productLogo;
    }

    public String getAddressJson() {
        return addressJson;
    }

    public void setAddressJson(String addressJson) {
        this.addressJson = addressJson;
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

    public String getProductIndexPage() {
        return productIndexPage;
    }

    public void setProductIndexPage(String productIndexPage) {
        this.productIndexPage = productIndexPage;
    }
}

