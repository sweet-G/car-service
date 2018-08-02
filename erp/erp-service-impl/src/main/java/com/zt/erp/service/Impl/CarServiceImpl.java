package com.zt.erp.service.Impl;

import com.zt.erp.entity.Car;
import com.zt.erp.entity.CarExample;
import com.zt.erp.entity.Customer;
import com.zt.erp.entity.CustomerExample;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.mapper.CarMapper;
import com.zt.erp.mapper.CustomerMapper;
import com.zt.erp.service.CarService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangtian
 * @date 2018/8/2
 */
@Service
public class CarServiceImpl implements CarService {

    Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 新增车辆信息
     * @param car
     * @param customer
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addCarInfo(Car car, Customer customer) {
        //根据身份证查询客户是否存在
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andIdCardEqualTo(customer.getIdCard());
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        //如果不存在，添加客户，获得主键
        Integer customerId = null;
        if(customerList == null || customerList.size() == 0){
            customerMapper.insertSelective(customer);
            customerId = customer.getId();
        } else{
            customerId = customerList.get(0).getId();
        }
        //校验车辆信息是否存在
        CarExample carExample = new CarExample();
        carExample.createCriteria().andLicenceNoEqualTo(car.getLicenceNo());
        List<Car> carList = carMapper.selectByExample(carExample);

            if(carList != null && carList.size() > 0){
              throw new ServiceException("车辆信息已存在");
            }else{
                //不存在，添加车辆信息
                car.setCustomerId(customerId);
                carMapper.insertSelective(car);

                logger.info("新增车辆信息：{}",car);
            }
    }

    /**
     * 根据车牌号查找客户和车辆信息
     *
     * @param licenceNo
     * @return
     */
    @Override
    public Car findCarInfoWithCustomer(String licenceNo) {
        if(StringUtils.isEmpty(licenceNo)){
            throw  new ServiceException("参数异常");
        }

        Car car = carMapper.findWithCustomerByLicenseNo(licenceNo);
        return car;
    }
}
