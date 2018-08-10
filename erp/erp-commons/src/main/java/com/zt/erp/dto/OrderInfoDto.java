package com.zt.erp.dto;

import com.zt.erp.entity.Order;
import com.zt.erp.entity.Parts;
import com.zt.erp.entity.ServiceType;

import java.util.List;

/**
 * @author zhangtian
 * @date 2018/8/9
 */

public class OrderInfoDto {
    private Order order;
    private ServiceType serviceType;
    List<Parts> partsList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }
}
