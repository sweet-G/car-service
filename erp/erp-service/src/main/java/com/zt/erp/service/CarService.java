package com.zt.erp.service;

import com.zt.erp.entity.Car;
import com.zt.erp.entity.Customer;

/**
 * @author zhangtian
 * @date 2018/8/2
 */

public interface CarService {
    /**
     * 新增车辆信息
     * @param car
     * @param customer
     */
    void addCarInfo(Car car, Customer customer);

    /**
     * 根据车牌号查找客户和车辆信息
     * @param licenceNo
     * @return
     */
    Car findCarInfoWithCustomer(String licenceNo);
}
