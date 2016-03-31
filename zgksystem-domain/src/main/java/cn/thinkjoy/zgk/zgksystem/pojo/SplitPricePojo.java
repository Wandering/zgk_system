package cn.thinkjoy.zgk.zgksystem.pojo;

/**
 * Created by douzy on 16/3/31.
 */
public class SplitPricePojo implements Comparable<SplitPricePojo> {
    /**
     * 用户ID
     */
    private Integer accountId;
    /**
     * 分享层级
     */
    private Integer agentLevel;

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

    @Override
    public int compareTo(SplitPricePojo o) {
        return this.agentLevel.compareTo(o.getAgentLevel());
    }
}
