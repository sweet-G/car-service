package com.zt.erp.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class FixOrderParts implements Serializable {
    private Integer id;

    private Integer orderId;

    private Integer partsId;

    private String partsNo;

    private String partsName;

    private Integer partsNum;

    private FixOrder fixOrder;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPartsId() {
        return partsId;
    }

    public void setPartsId(Integer partsId) {
        this.partsId = partsId;
    }

    public String getPartsNo() {
        return partsNo;
    }

    public void setPartsNo(String partsNo) {
        this.partsNo = partsNo;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public Integer getPartsNum() {
        return partsNum;
    }

    public void setPartsNum(Integer partsNum) {
        this.partsNum = partsNum;
    }

    public FixOrder getFixOrder() {
        return fixOrder;
    }

    public void setFixOrder(FixOrder fixOrder) {
        this.fixOrder = fixOrder;
    }

    @Override
    public String toString() {
        return "FixOrderParts{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", partsId=" + partsId +
                ", partsNo='" + partsNo + '\'' +
                ", partsName='" + partsName + '\'' +
                ", partsNum=" + partsNum +
                ", fixOrder=" + fixOrder +
                '}';
    }
}