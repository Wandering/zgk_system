package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;

import java.math.BigDecimal;

/**
 * Created by douzy on 16/3/31.
 */
public class MarketParmas extends CreateBaseDomain {
    /**
     * 成本价
     */
    private BigDecimal costPrice;
    /**
     * 分成比例 购买用户的前两级
     */
    private Integer splitPercentage;
    /**
     * 各层级利润
     */
    private String levelProfits;

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getSplitPercentage() {
        return splitPercentage;
    }

    public void setSplitPercentage(Integer splitPercentage) {
        this.splitPercentage = splitPercentage;
    }

    public String getLevelProfits() {
        return levelProfits;
    }

    public void setLevelProfits(String levelProfits) {
        this.levelProfits = levelProfits;
    }
}
