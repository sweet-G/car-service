package com.zt.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class Order implements Serializable {

    /**
     * 订单状态 1：新订单 2：已下发 3：维修中 4：维修完成 5：质检中 6：结算中 7：完成
     */
    public static final String ORDER_STATE_NEW = "1";
    public static final String ORDER_STATE_TRANS = "2";
    public static final String ORDER_STATE_FIXING = "3";
    public static final String ORDER_STATE_FIXED = "4";
    public static final String ORDER_STATE_CHECKING = "5";
    public static final String ORDER_STATE_SETTLEMENT = "6";
    public static final String ORDER_STATE_DONE = "7";



    private Integer id;

    /**
     * 订单总价
     */
    private BigDecimal fee;

    /**
     * 订单状态 
     */
    private String state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 车辆id
     */
    private Integer carId;

    /**
     * 工时费
     */
    private Integer serviceTypeId;

    private Car car;

    private Customer customer;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", fee=" + fee +
                ", state='" + state + '\'' +
                ", createTime=" + createTime +
                ", carId=" + carId +
                ", serviceTypeId=" + serviceTypeId +
                ", car=" + car +
                ", customer=" + customer +
                '}';
    }

    public String getStateName() {
        //订单状态 1：新订单 2：已下发 3：维修中 4：维修完成 5：质检中 6：结算中 7：完成
        if(getState().equals(ORDER_STATE_NEW)) {
            return "等待维修";
        } else if(getState().equals(ORDER_STATE_TRANS)) {
            return "已下发";
        } else if(getState().equals(ORDER_STATE_FIXING)) {
            return "维修中";
        } else if(getState().equals(ORDER_STATE_FIXED)) {
            return "维修完成";
        } else if(getState().equals(ORDER_STATE_CHECKING)) {
            return "质检中";
        } else if(getState().equals(ORDER_STATE_SETTLEMENT)) {
            return "结算中";
        } else if(getState().equals(ORDER_STATE_DONE)) {
            return "完成";
        }
        return "";
    }


}
