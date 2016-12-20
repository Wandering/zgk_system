/*
 * Copyright (c) 2013-2014, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: zgksystem
 * $Id:  SaleProduct.java 2016-12-20 16:10:40 $
 */



package cn.thinkjoy.zgk.zgksystem.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import cn.thinkjoy.common.domain.BaseDomain;

import java.util.*;

public class SaleProduct extends BaseDomain{
    private String productName;
    private Integer type;
    private Double defaultSalePrice;
    private Double defaultPickupPrice;
    private String icon;
    private String intro;
    private Integer state;
    private String areaId;
    private Integer cardBusinessType;
    private String cardGrade;
    private String cardOfficial;

	public SaleProduct(){
	}
    public void setProductName(String value) {
        this.productName = value;
    }

    public String getProductName() {
        return this.productName;
    }
    public void setType(Integer value) {
        this.type = value;
    }

    public Integer getType() {
        return this.type;
    }
    public void setDefaultSalePrice(Double value) {
        this.defaultSalePrice = value;
    }

    public Double getDefaultSalePrice() {
        return this.defaultSalePrice;
    }
    public void setDefaultPickupPrice(Double value) {
        this.defaultPickupPrice = value;
    }

    public Double getDefaultPickupPrice() {
        return this.defaultPickupPrice;
    }
    public void setIcon(String value) {
        this.icon = value;
    }

    public String getIcon() {
        return this.icon;
    }
    public void setIntro(String value) {
        this.intro = value;
    }

    public String getIntro() {
        return this.intro;
    }
    public void setState(Integer value) {
        this.state = value;
    }

    public Integer getState() {
        return this.state;
    }
    public void setAreaId(String value) {
        this.areaId = value;
    }

    public String getAreaId() {
        return this.areaId;
    }
    public void setCardBusinessType(Integer value) {
        this.cardBusinessType = value;
    }

    public Integer getCardBusinessType() {
        return this.cardBusinessType;
    }
    public void setCardGrade(String value) {
        this.cardGrade = value;
    }

    public String getCardGrade() {
        return this.cardGrade;
    }
    public void setCardOfficial(String value) {
        this.cardOfficial = value;
    }

    public String getCardOfficial() {
        return this.cardOfficial;
    }

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ProductName",getProductName())
			.append("Type",getType())
			.append("DefaultSalePrice",getDefaultSalePrice())
			.append("DefaultPickupPrice",getDefaultPickupPrice())
			.append("Icon",getIcon())
			.append("Intro",getIntro())
			.append("State",getState())
			.append("AreaId",getAreaId())
			.append("CardBusinessType",getCardBusinessType())
			.append("CardGrade",getCardGrade())
			.append("CardOfficial",getCardOfficial())
			.toString();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}

	public boolean equals(Object obj) {
		if(obj instanceof SaleProduct == false) return false;
		if(this == obj) return true;
		SaleProduct other = (SaleProduct)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

