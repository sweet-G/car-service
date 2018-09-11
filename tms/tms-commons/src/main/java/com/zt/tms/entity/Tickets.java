package com.zt.tms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Tickets implements Serializable {

    public static final String TICKET_STATE_IN_STORE = "已入库";
    public static final String TICKET_STATE_OUT_STORE = "已下发";
    public static final String TICKET_STATE_SALE = "已销售";
    public static final String TICKET_STATE_LOST = "已挂失";
    public static final String TICKET_STATE_OUT_DATE = "已过期";

    private Long id;

    /**
     * 年票号码
     */
    private String ticketNum;

    /**
     * 入库时间
     */
    private Date ticketInTime;

    /**
     * 年票状态
     */
    private String ticketState;

    private Date createTime;

    private Date updateTime;

    /**
     * 年票出库(下发)时间
     */
    private String ticketOutTime;

    /**
     * 售票点ID
     */
    private Integer storeAccountId;

    /**
     * 有效期起始时间
     */
    private Date ticketValidityStart;

    /**
     * 有效期截至时间
     */
    private Date ticketValidityEnd;

    /**
     * 顾客ID
     */
    private Long customerId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public Date getTicketInTime() {
        return ticketInTime;
    }

    public void setTicketInTime(Date ticketInTime) {
        this.ticketInTime = ticketInTime;
    }

    public String getTicketState() {
        return ticketState;
    }

    public void setTicketState(String ticketState) {
        this.ticketState = ticketState;
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

    public String getTicketOutTime() {
        return ticketOutTime;
    }

    public void setTicketOutTime(String ticketOutTime) {
        this.ticketOutTime = ticketOutTime;
    }

    public Integer getStoreAccountId() {
        return storeAccountId;
    }

    public void setStoreAccountId(Integer storeAccountId) {
        this.storeAccountId = storeAccountId;
    }

    public Date getTicketValidityStart() {
        return ticketValidityStart;
    }

    public void setTicketValidityStart(Date ticketValidityStart) {
        this.ticketValidityStart = ticketValidityStart;
    }

    public Date getTicketValidityEnd() {
        return ticketValidityEnd;
    }

    public void setTicketValidityEnd(Date ticketValidityEnd) {
        this.ticketValidityEnd = ticketValidityEnd;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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
        Tickets other = (Tickets) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTicketNum() == null ? other.getTicketNum() == null : this.getTicketNum().equals(other.getTicketNum()))
            && (this.getTicketInTime() == null ? other.getTicketInTime() == null : this.getTicketInTime().equals(other.getTicketInTime()))
            && (this.getTicketState() == null ? other.getTicketState() == null : this.getTicketState().equals(other.getTicketState()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getTicketOutTime() == null ? other.getTicketOutTime() == null : this.getTicketOutTime().equals(other.getTicketOutTime()))
            && (this.getStoreAccountId() == null ? other.getStoreAccountId() == null : this.getStoreAccountId().equals(other.getStoreAccountId()))
            && (this.getTicketValidityStart() == null ? other.getTicketValidityStart() == null : this.getTicketValidityStart().equals(other.getTicketValidityStart()))
            && (this.getTicketValidityEnd() == null ? other.getTicketValidityEnd() == null : this.getTicketValidityEnd().equals(other.getTicketValidityEnd()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTicketNum() == null) ? 0 : getTicketNum().hashCode());
        result = prime * result + ((getTicketInTime() == null) ? 0 : getTicketInTime().hashCode());
        result = prime * result + ((getTicketState() == null) ? 0 : getTicketState().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getTicketOutTime() == null) ? 0 : getTicketOutTime().hashCode());
        result = prime * result + ((getStoreAccountId() == null) ? 0 : getStoreAccountId().hashCode());
        result = prime * result + ((getTicketValidityStart() == null) ? 0 : getTicketValidityStart().hashCode());
        result = prime * result + ((getTicketValidityEnd() == null) ? 0 : getTicketValidityEnd().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ticketNum=").append(ticketNum);
        sb.append(", ticketInTime=").append(ticketInTime);
        sb.append(", ticketState=").append(ticketState);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", ticketOutTime=").append(ticketOutTime);
        sb.append(", storeAccountId=").append(storeAccountId);
        sb.append(", ticketValidityStart=").append(ticketValidityStart);
        sb.append(", ticketValidityEnd=").append(ticketValidityEnd);
        sb.append(", customerId=").append(customerId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}