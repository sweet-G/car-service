package com.zt.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class Order implements Serializable {

    /**
     * 1.等待维修 2.维修中 3.质检中 4.结算中 5.完成
     *
     */
    public static final String ORDER_STATE_NEW="1";
    public static final String ORDER_STATE_SERVICE="2";
    public static final String ORDER_STATE_QUALIY="3";
    public static final String ORDER_STATE_MONEY="4";
    public static final String ORDER_STATE_DONE="5";


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

    /**
     * 1.等待维修 2.维修中 3.质检中 4.结算中 5.完成
     *
     */
    public String getStateName(){
        if(getState().equals(Order.ORDER_STATE_NEW)){
            return "等待维修";
        }
        if(getState().equals(Order.ORDER_STATE_SERVICE)){
            return "维修中";
        }
        if(getState().equals(Order.ORDER_STATE_QUALIY)){
            return "质检中";
        }
        if(getState().equals(Order.ORDER_STATE_MONEY)){
            return "结算中";
        }
        if(getState().equals(Order.ORDER_STATE_DONE)){
            return "完成";
        }
        return "";
    }

}
