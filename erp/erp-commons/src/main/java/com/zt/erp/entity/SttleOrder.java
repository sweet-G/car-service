package com.zt.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class SttleOrder implements Serializable {
    /**
     * 订单号
     */
    private Integer orderId;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单日期
     */
    private Date orderTime;

    /**
     * 订单状态：2：已下发 3：维修中 4：维修完成 5：质检中 6：结算中
     */
    private String state;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 订单工时
     */
    private String orderServiceHour;

    /**
     * 订单工时费
     */
    private BigDecimal orderServiceHourFee;

    /**
     * 订单配件费用
     */
    private BigDecimal orderPartsFee;

    /**
     * 车类型
     */
    private String carType;

    /**
     * 车颜色
     */
    private String carColor;

    /**
     * 车牌号
     */
    private String carLicence;

    /**
     * 车主名称
     */
    private String customerName;

    /**
     * 车主电话
     */
    private String customerTel;

    /**
     * 维修人员id
     */
    private Integer fixEmployeeId;

    private Integer checkEmployeeId;

    private Integer sttleEmployeeId;

    private String fixEmployeeName;

    private String checkEmployeeName;

    private String sttleEmployeeName;

    private static final long serialVersionUID = 1L;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderServiceHour() {
        return orderServiceHour;
    }

    public void setOrderServiceHour(String orderServiceHour) {
        this.orderServiceHour = orderServiceHour;
    }

    public BigDecimal getOrderServiceHourFee() {
        return orderServiceHourFee;
    }

    public void setOrderServiceHourFee(BigDecimal orderServiceHourFee) {
        this.orderServiceHourFee = orderServiceHourFee;
    }

    public BigDecimal getOrderPartsFee() {
        return orderPartsFee;
    }

    public void setOrderPartsFee(BigDecimal orderPartsFee) {
        this.orderPartsFee = orderPartsFee;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarLicence() {
        return carLicence;
    }

    public void setCarLicence(String carLicence) {
        this.carLicence = carLicence;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public Integer getFixEmployeeId() {
        return fixEmployeeId;
    }

    public void setFixEmployeeId(Integer fixEmployeeId) {
        this.fixEmployeeId = fixEmployeeId;
    }

    public Integer getCheckEmployeeId() {
        return checkEmployeeId;
    }

    public void setCheckEmployeeId(Integer checkEmployeeId) {
        this.checkEmployeeId = checkEmployeeId;
    }

    public Integer getSttleEmployeeId() {
        return sttleEmployeeId;
    }

    public void setSttleEmployeeId(Integer sttleEmployeeId) {
        this.sttleEmployeeId = sttleEmployeeId;
    }

    public String getFixEmployeeName() {
        return fixEmployeeName;
    }

    public void setFixEmployeeName(String fixEmployeeName) {
        this.fixEmployeeName = fixEmployeeName;
    }

    public String getCheckEmployeeName() {
        return checkEmployeeName;
    }

    public void setCheckEmployeeName(String checkEmployeeName) {
        this.checkEmployeeName = checkEmployeeName;
    }

    public String getSttleEmployeeName() {
        return sttleEmployeeName;
    }

    public void setSttleEmployeeName(String sttleEmployeeName) {
        this.sttleEmployeeName = sttleEmployeeName;
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
        SttleOrder other = (SttleOrder) that;
        return (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getOrderType() == null ? other.getOrderType() == null : this.getOrderType().equals(other.getOrderType()))
            && (this.getOrderTime() == null ? other.getOrderTime() == null : this.getOrderTime().equals(other.getOrderTime()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getOrderMoney() == null ? other.getOrderMoney() == null : this.getOrderMoney().equals(other.getOrderMoney()))
            && (this.getOrderServiceHour() == null ? other.getOrderServiceHour() == null : this.getOrderServiceHour().equals(other.getOrderServiceHour()))
            && (this.getOrderServiceHourFee() == null ? other.getOrderServiceHourFee() == null : this.getOrderServiceHourFee().equals(other.getOrderServiceHourFee()))
            && (this.getOrderPartsFee() == null ? other.getOrderPartsFee() == null : this.getOrderPartsFee().equals(other.getOrderPartsFee()))
            && (this.getCarType() == null ? other.getCarType() == null : this.getCarType().equals(other.getCarType()))
            && (this.getCarColor() == null ? other.getCarColor() == null : this.getCarColor().equals(other.getCarColor()))
            && (this.getCarLicence() == null ? other.getCarLicence() == null : this.getCarLicence().equals(other.getCarLicence()))
            && (this.getCustomerName() == null ? other.getCustomerName() == null : this.getCustomerName().equals(other.getCustomerName()))
            && (this.getCustomerTel() == null ? other.getCustomerTel() == null : this.getCustomerTel().equals(other.getCustomerTel()))
            && (this.getFixEmployeeId() == null ? other.getFixEmployeeId() == null : this.getFixEmployeeId().equals(other.getFixEmployeeId()))
            && (this.getCheckEmployeeId() == null ? other.getCheckEmployeeId() == null : this.getCheckEmployeeId().equals(other.getCheckEmployeeId()))
            && (this.getSttleEmployeeId() == null ? other.getSttleEmployeeId() == null : this.getSttleEmployeeId().equals(other.getSttleEmployeeId()))
            && (this.getFixEmployeeName() == null ? other.getFixEmployeeName() == null : this.getFixEmployeeName().equals(other.getFixEmployeeName()))
            && (this.getCheckEmployeeName() == null ? other.getCheckEmployeeName() == null : this.getCheckEmployeeName().equals(other.getCheckEmployeeName()))
            && (this.getSttleEmployeeName() == null ? other.getSttleEmployeeName() == null : this.getSttleEmployeeName().equals(other.getSttleEmployeeName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getOrderType() == null) ? 0 : getOrderType().hashCode());
        result = prime * result + ((getOrderTime() == null) ? 0 : getOrderTime().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getOrderMoney() == null) ? 0 : getOrderMoney().hashCode());
        result = prime * result + ((getOrderServiceHour() == null) ? 0 : getOrderServiceHour().hashCode());
        result = prime * result + ((getOrderServiceHourFee() == null) ? 0 : getOrderServiceHourFee().hashCode());
        result = prime * result + ((getOrderPartsFee() == null) ? 0 : getOrderPartsFee().hashCode());
        result = prime * result + ((getCarType() == null) ? 0 : getCarType().hashCode());
        result = prime * result + ((getCarColor() == null) ? 0 : getCarColor().hashCode());
        result = prime * result + ((getCarLicence() == null) ? 0 : getCarLicence().hashCode());
        result = prime * result + ((getCustomerName() == null) ? 0 : getCustomerName().hashCode());
        result = prime * result + ((getCustomerTel() == null) ? 0 : getCustomerTel().hashCode());
        result = prime * result + ((getFixEmployeeId() == null) ? 0 : getFixEmployeeId().hashCode());
        result = prime * result + ((getCheckEmployeeId() == null) ? 0 : getCheckEmployeeId().hashCode());
        result = prime * result + ((getSttleEmployeeId() == null) ? 0 : getSttleEmployeeId().hashCode());
        result = prime * result + ((getFixEmployeeName() == null) ? 0 : getFixEmployeeName().hashCode());
        result = prime * result + ((getCheckEmployeeName() == null) ? 0 : getCheckEmployeeName().hashCode());
        result = prime * result + ((getSttleEmployeeName() == null) ? 0 : getSttleEmployeeName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", orderType=").append(orderType);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", state=").append(state);
        sb.append(", orderMoney=").append(orderMoney);
        sb.append(", orderServiceHour=").append(orderServiceHour);
        sb.append(", orderServiceHourFee=").append(orderServiceHourFee);
        sb.append(", orderPartsFee=").append(orderPartsFee);
        sb.append(", carType=").append(carType);
        sb.append(", carColor=").append(carColor);
        sb.append(", carLicence=").append(carLicence);
        sb.append(", customerName=").append(customerName);
        sb.append(", customerTel=").append(customerTel);
        sb.append(", fixEmployeeId=").append(fixEmployeeId);
        sb.append(", checkEmployeeId=").append(checkEmployeeId);
        sb.append(", sttleEmployeeId=").append(sttleEmployeeId);
        sb.append(", fixEmployeeName=").append(fixEmployeeName);
        sb.append(", checkEmployeeName=").append(checkEmployeeName);
        sb.append(", sttleEmployeeName=").append(sttleEmployeeName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}