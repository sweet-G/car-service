package com.zt.tms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class StoreAccount implements Serializable {

    public static final String ACCOUNT_STATE_NORMAL = "正常";
    public static final String ACCOUNT_STATE_DISABLE = "禁用";
    public static final String ACCOUNT_INIT_PASSWORD = "000000";
    private Integer id;

    /**
     * 售票点登录账号
     */
    private String storeAccount;

    /**
     * 售票点密码
     */
    private String storePassword;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 登录时间
     */
    private Date updateTime;

    /**
     * 售票点状态
     */
    private String storeState;

    private Integer ticketStoreId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreAccount() {
        return storeAccount;
    }

    public void setStoreAccount(String storeAccount) {
        this.storeAccount = storeAccount;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
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

    public String getStoreState() {
        return storeState;
    }

    public void setStoreState(String storeState) {
        this.storeState = storeState;
    }

    public Integer getTicketStoreId() {
        return ticketStoreId;
    }

    public void setTicketStoreId(Integer ticketStoreId) {
        this.ticketStoreId = ticketStoreId;
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
        StoreAccount other = (StoreAccount) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStoreAccount() == null ? other.getStoreAccount() == null : this.getStoreAccount().equals(other.getStoreAccount()))
            && (this.getStorePassword() == null ? other.getStorePassword() == null : this.getStorePassword().equals(other.getStorePassword()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStoreState() == null ? other.getStoreState() == null : this.getStoreState().equals(other.getStoreState()))
            && (this.getTicketStoreId() == null ? other.getTicketStoreId() == null : this.getTicketStoreId().equals(other.getTicketStoreId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStoreAccount() == null) ? 0 : getStoreAccount().hashCode());
        result = prime * result + ((getStorePassword() == null) ? 0 : getStorePassword().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStoreState() == null) ? 0 : getStoreState().hashCode());
        result = prime * result + ((getTicketStoreId() == null) ? 0 : getTicketStoreId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", storeAccount=").append(storeAccount);
        sb.append(", storePassword=").append(storePassword);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", storeState=").append(storeState);
        sb.append(", ticketStoreId=").append(ticketStoreId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}