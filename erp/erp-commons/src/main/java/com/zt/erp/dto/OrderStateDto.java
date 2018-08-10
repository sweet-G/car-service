package com.zt.erp.dto;

/**
 * @author zhangtian
 * @date 2018/8/9
 */

public class OrderStateDto {
    private Integer orderId;
    private Integer employeeId;
    private String state;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
