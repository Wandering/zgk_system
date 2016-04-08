package cn.thinkjoy.zgk.zgksystem.domain;

import cn.thinkjoy.common.domain.BaseDomain;

/**
 * Created by douzy on 16/3/31.
 */
public class SplitPrice extends BaseDomain {
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户电话
     */
    private String userPhone;
    /**
     * 分成价格
     */
    private Double price;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     *用户类别  0: 供货商  1:用户
     */
    private byte type;
    /**
     * 分成状态  0:为分成 1:已分成
     */
    private byte status;
    /**
     * 创建时间
     */
    private Long createTime;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
