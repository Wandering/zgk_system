package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.CreateBaseDomain;

/**
 * Created by douzy on 16/3/31.
 */
public class MarketParmas extends CreateBaseDomain {
    /**
     * 成本价
     */
    private Integer costPrice;
    /**
     * 代理商分成比例
     */
    private Integer splitPercentage;
    /**
     * 用户层级利润
     */
    private String levelProfits;
    /**
     * 层级
     */
    private byte splitLevel;

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
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

    public byte getSplitLevel() {
        return splitLevel;
    }

    public void setSplitLevel(byte splitLevel) {
        this.splitLevel = splitLevel;
    }
}
