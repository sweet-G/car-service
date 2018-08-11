package com.zt.erp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 
 */
public class PartsStream implements Serializable {
    public static final Integer PARTS_STREAM_TYPE_IN = 1;
    public static final Integer PARTS_STREAM_TYPE_OUT = 2;

    /**
     * 备件流水ID
     */
    private Integer id;

    private Integer orderId;

    /**
     * 备件ID
     */
    private Integer partsId;

    /**
     * 所需数量
     */
    private Integer num;

    /**
     * 员工ID
     */
    private Integer employeeId;

    /**
     * 1.入库 2.出库
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    private Parts parts;
    private Employee employee;

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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Parts getParts() {
        return parts;
    }

    public void setParts(Parts parts) {
        this.parts = parts;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "PartsStream{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", partsId=" + partsId +
                ", num=" + num +
                ", employeeId=" + employeeId +
                ", type=" + type +
                ", createTime=" + createTime +
                ", parts=" + parts +
                ", employee=" + employee +
                '}';
    }
}