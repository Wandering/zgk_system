/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: k12system
 * $Id:  Product.java 2015-09-01 18:47:56 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Product extends CreateBaseDomain{
    private Long productCode;
    private String productName;
    private String productLogo;
    private String addressJson;
    private Integer seqSort;
    private String description;
    private String productIndexPage;

    public Product(){
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductIndexPage() {
        return productIndexPage;
    }

    public void setProductIndexPage(String productIndexPage) {
        this.productIndexPage = productIndexPage;
    }

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("Id", getId())
                .append("ProductCode", getProductCode())
                .append("ProductName", getProductName())
                .append("ProductLogo", getProductLogo())
                .append("AddressJson", getAddressJson())
                .append("Status", getStatus())
                .append("SeqSort", getSeqSort())
                .append("Description", getDescription())
                .append("ProductIndexPage", getProductIndexPage())
                .append("Creator", getCreator())

                .append("CreateDate", getCreateDate())
                .append("LastModDate", getLastModDate())
                .append("LastModifier", getLastModifier())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof Product == false) return false;
        if(this == obj) return true;
        Product other = (Product)obj;
        return new EqualsBuilder()
                .append(getId(), other.getId())
                .isEquals();
    }
}

