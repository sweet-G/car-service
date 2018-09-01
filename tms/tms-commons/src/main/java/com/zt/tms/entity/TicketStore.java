package com.zt.tms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class TicketStore implements Serializable {
    private Integer id;

    /**
     * 售票点名称
     */
    private String storeName;

    /**
     * 售票点负责人
     */
    private String storeManager;

    /**
     * 联系电话
     */
    private String storeTel;

    /**
     * 售票点地址
     */
    private String storeAddress;

    /**
     * 售票点坐标_经度
     */
    private String storeGeoLongitude;

    /**
     * 售票点坐标_纬度
     */
    private String storeGeoLatitude;

    /**
     * 营业执照照片
     */
    private String storeAttachment;

    /**
     * 负责人身份证照片
     */
    private String storeManagerAttachment;

    private Date createTime;

    private Date updateTime;

    private Integer storeAccountId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(String storeManager) {
        this.storeManager = storeManager;
    }

    public String getStoreTel() {
        return storeTel;
    }

    public void setStoreTel(String storeTel) {
        this.storeTel = storeTel;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreGeoLongitude() {
        return storeGeoLongitude;
    }

    public void setStoreGeoLongitude(String storeGeoLongitude) {
        this.storeGeoLongitude = storeGeoLongitude;
    }

    public String getStoreGeoLatitude() {
        return storeGeoLatitude;
    }

    public void setStoreGeoLatitude(String storeGeoLatitude) {
        this.storeGeoLatitude = storeGeoLatitude;
    }

    public String getStoreAttachment() {
        return storeAttachment;
    }

    public void setStoreAttachment(String storeAttachment) {
        this.storeAttachment = storeAttachment;
    }

    public String getStoreManagerAttachment() {
        return storeManagerAttachment;
    }

    public void setStoreManagerAttachment(String storeManagerAttachment) {
        this.storeManagerAttachment = storeManagerAttachment;
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

    public Integer getStoreAccountId() {
        return storeAccountId;
    }

    public void setStoreAccountId(Integer storeAccountId) {
        this.storeAccountId = storeAccountId;
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
        TicketStore other = (TicketStore) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStoreName() == null ? other.getStoreName() == null : this.getStoreName().equals(other.getStoreName()))
            && (this.getStoreManager() == null ? other.getStoreManager() == null : this.getStoreManager().equals(other.getStoreManager()))
            && (this.getStoreTel() == null ? other.getStoreTel() == null : this.getStoreTel().equals(other.getStoreTel()))
            && (this.getStoreAddress() == null ? other.getStoreAddress() == null : this.getStoreAddress().equals(other.getStoreAddress()))
            && (this.getStoreGeoLongitude() == null ? other.getStoreGeoLongitude() == null : this.getStoreGeoLongitude().equals(other.getStoreGeoLongitude()))
            && (this.getStoreGeoLatitude() == null ? other.getStoreGeoLatitude() == null : this.getStoreGeoLatitude().equals(other.getStoreGeoLatitude()))
            && (this.getStoreAttachment() == null ? other.getStoreAttachment() == null : this.getStoreAttachment().equals(other.getStoreAttachment()))
            && (this.getStoreManagerAttachment() == null ? other.getStoreManagerAttachment() == null : this.getStoreManagerAttachment().equals(other.getStoreManagerAttachment()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStoreAccountId() == null ? other.getStoreAccountId() == null : this.getStoreAccountId().equals(other.getStoreAccountId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStoreName() == null) ? 0 : getStoreName().hashCode());
        result = prime * result + ((getStoreManager() == null) ? 0 : getStoreManager().hashCode());
        result = prime * result + ((getStoreTel() == null) ? 0 : getStoreTel().hashCode());
        result = prime * result + ((getStoreAddress() == null) ? 0 : getStoreAddress().hashCode());
        result = prime * result + ((getStoreGeoLongitude() == null) ? 0 : getStoreGeoLongitude().hashCode());
        result = prime * result + ((getStoreGeoLatitude() == null) ? 0 : getStoreGeoLatitude().hashCode());
        result = prime * result + ((getStoreAttachment() == null) ? 0 : getStoreAttachment().hashCode());
        result = prime * result + ((getStoreManagerAttachment() == null) ? 0 : getStoreManagerAttachment().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStoreAccountId() == null) ? 0 : getStoreAccountId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", storeName=").append(storeName);
        sb.append(", storeManager=").append(storeManager);
        sb.append(", storeTel=").append(storeTel);
        sb.append(", storeAddress=").append(storeAddress);
        sb.append(", storeGeoLongitude=").append(storeGeoLongitude);
        sb.append(", storeGeoLatitude=").append(storeGeoLatitude);
        sb.append(", storeAttachment=").append(storeAttachment);
        sb.append(", storeManagerAttachment=").append(storeManagerAttachment);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", storeAccountId=").append(storeAccountId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}