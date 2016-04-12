package cn.thinkjoy.zgk.zgksystem.pojo;

import java.io.Serializable;

/**
 * Created by douzy on 16/3/31.
 */
public class SplitPricePojo implements Comparable<SplitPricePojo>, Serializable {
    /**
     * 用户ID
     */
    private Integer accountId;
    /**
     * 分享层级
     */
    private Integer agentLevel;

    private String accountPhone;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(Integer agentLevel) {
        this.agentLevel = agentLevel;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    @Override
    public int compareTo(SplitPricePojo o) {
        return this.agentLevel.compareTo(o.getAgentLevel());
    }
}
