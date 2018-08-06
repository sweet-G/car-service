package com.zt.erp.controller;

import com.zt.erp.dto.ResponseBean;
import com.zt.erp.entity.Car;
import com.zt.erp.entity.Customer;
import com.zt.erp.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangtian
 * @date 2018/8/2
 */
@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/new")
    @ResponseBody
    public ResponseBean newCar(Car car, Customer customer){
        carService.addCarInfo(car,customer);
        car.setCustomer(customer);

        return ResponseBean.success(car);
    }

    @GetMapping("/check")
    @ResponseBody
    public ResponseBean checkCarInfo(String licenceNo){
        Car car = carService.findCarInfoWithCustomer(licenceNo);

        if(car != null){
            return ResponseBean.success(car);
        } else{
            return ResponseBean.error("暂未录入");
        }
    }
}
