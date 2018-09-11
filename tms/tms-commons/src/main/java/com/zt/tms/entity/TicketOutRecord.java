package com.zt.tms.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class TicketOutRecord implements Serializable {

    public static final String STATE_NO_PAY = "未支付";
    public static final String STATE_PAY = "已支付";

    private Integer id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 下发人
     */
    private String outAccountName;

    /**
     * 财务收款人
     */
    private String financeAccountName;

    /**
     * 状态 未支付|已支付
     */
    private String state;

    /**
     * 内容
     */
    private String content;

    /**
     * 起始票号
     */
    private String beginTicketNum;

    /**
     * 结束票号
     */
    private String endTicketNum;

    /**
     * 总数量
     */
    private Integer totalNum;

    /**
     * 单张票价
     */
    private BigDecimal price;

    /**
     * 总票价
     */
    private BigDecimal totalPrice;

    private String storeAccountName;

    private Integer storeAccountId;

    private Integer outAccountId;

    private Integer financeAccountId;

    /**
     * 支付方式  现金 | 银行卡
     */
    private String payType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOutAccountName() {
        return outAccountName;
    }

    public void setOutAccountName(String outAccountName) {
        this.outAccountName = outAccountName;
    }

    public String getFinanceAccountName() {
        return financeAccountName;
    }

    public void setFinanceAccountName(String financeAccountName) {
        this.financeAccountName = financeAccountName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBeginTicketNum() {
        return beginTicketNum;
    }

    public void setBeginTicketNum(String beginTicketNum) {
        this.beginTicketNum = beginTicketNum;
    }

    public String getEndTicketNum() {
        return endTicketNum;
    }

    public void setEndTicketNum(String endTicketNum) {
        this.endTicketNum = endTicketNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStoreAccountName() {
        return storeAccountName;
    }

    public void setStoreAccountName(String storeAccountName) {
        this.storeAccountName = storeAccountName;
    }

    public Integer getStoreAccountId() {
        return storeAccountId;
    }

    public void setStoreAccountId(Integer storeAccountId) {
        this.storeAccountId = storeAccountId;
    }

    public Integer getOutAccountId() {
        return outAccountId;
    }

    public void setOutAccountId(Integer outAccountId) {
        this.outAccountId = outAccountId;
    }

    public Integer getFinanceAccountId() {
        return financeAccountId;
    }

    public void setFinanceAccountId(Integer financeAccountId) {
        this.financeAccountId = financeAccountId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TicketOutRecord other = (TicketOutRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getOutAccountName() == null ? other.getOutAccountName() == null : this.getOutAccountName().equals(other.getOutAccountName()))
            && (this.getFinanceAccountName() == null ? other.getFinanceAccountName() == null : this.getFinanceAccountName().equals(other.getFinanceAccountName()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getBeginTicketNum() == null ? other.getBeginTicketNum() == null : this.getBeginTicketNum().equals(other.getBeginTicketNum()))
            && (this.getEndTicketNum() == null ? other.getEndTicketNum() == null : this.getEndTicketNum().equals(other.getEndTicketNum()))
            && (this.getTotalNum() == null ? other.getTotalNum() == null : this.getTotalNum().equals(other.getTotalNum()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getTotalPrice() == null ? other.getTotalPrice() == null : this.getTotalPrice().equals(other.getTotalPrice()))
            && (this.getStoreAccountName() == null ? other.getStoreAccountName() == null : this.getStoreAccountName().equals(other.getStoreAccountName()))
            && (this.getStoreAccountId() == null ? other.getStoreAccountId() == null : this.getStoreAccountId().equals(other.getStoreAccountId()))
            && (this.getOutAccountId() == null ? other.getOutAccountId() == null : this.getOutAccountId().equals(other.getOutAccountId()))
            && (this.getFinanceAccountId() == null ? other.getFinanceAccountId() == null : this.getFinanceAccountId().equals(other.getFinanceAccountId()))
            && (this.getPayType() == null ? other.getPayType() == null : this.getPayType().equals(other.getPayType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getOutAccountName() == null) ? 0 : getOutAccountName().hashCode());
        result = prime * result + ((getFinanceAccountName() == null) ? 0 : getFinanceAccountName().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getBeginTicketNum() == null) ? 0 : getBeginTicketNum().hashCode());
        result = prime * result + ((getEndTicketNum() == null) ? 0 : getEndTicketNum().hashCode());
        result = prime * result + ((getTotalNum() == null) ? 0 : getTotalNum().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getTotalPrice() == null) ? 0 : getTotalPrice().hashCode());
        result = prime * result + ((getStoreAccountName() == null) ? 0 : getStoreAccountName().hashCode());
        result = prime * result + ((getStoreAccountId() == null) ? 0 : getStoreAccountId().hashCode());
        result = prime * result + ((getOutAccountId() == null) ? 0 : getOutAccountId().hashCode());
        result = prime * result + ((getFinanceAccountId() == null) ? 0 : getFinanceAccountId().hashCode());
        result = prime * result + ((getPayType() == null) ? 0 : getPayType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", outAccountName=").append(outAccountName);
        sb.append(", financeAccountName=").append(financeAccountName);
        sb.append(", state=").append(state);
        sb.append(", content=").append(content);
        sb.append(", beginTicketNum=").append(beginTicketNum);
        sb.append(", endTicketNum=").append(endTicketNum);
        sb.append(", totalNum=").append(totalNum);
        sb.append(", price=").append(price);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", storeAccountName=").append(storeAccountName);
        sb.append(", storeAccountId=").append(storeAccountId);
        sb.append(", outAccountId=").append(outAccountId);
        sb.append(", financeAccountId=").append(financeAccountId);
        sb.append(", payType=").append(payType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}