package com.zt.erp.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zt.erp.dto.OrderInfoDto;
import com.zt.erp.dto.OrderStateDto;
import com.zt.erp.entity.*;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.mapper.*;
import com.zt.erp.quartz.CountTimeOut;
import com.zt.erp.service.FixOrderService;
import com.zt.erp.util.Constant;
import com.zt.erp.vo.FixOrderPartsVo;
import org.joda.time.DateTime;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/9
 */
public class FixOrderServiceImpl implements FixOrderService {

    private Logger logger = LoggerFactory.getLogger(FixOrderServiceImpl.class);
    @Autowired
    private FixOrderMapper fixOrderMapper;
    @Autowired
    private FixOrderPartsMapper fixOrderPartsMapper;
    @Autowired
    private SttleOrderMapper sttleOrderMapper;
    @Autowired
    private SttleOrderPartsMapper sttleOrderPartsMapper;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private CountTimeoutMapper countTimeoutMapper;
    @Autowired
    JmsTemplate jmsTemplate;

    /**
     * 根据消息队列的json新增fixOrder
     *
     * @param json
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createFixOrder(String json) {
        OrderInfoDto orderInfoDto = new Gson().fromJson(json,OrderInfoDto.class);

        //解析json数据并存放数据库
        FixOrder fixOrder = new FixOrder();
        fixOrder.setOrderId(orderInfoDto.getOrder().getId());
        fixOrder.setOrderType(orderInfoDto.getServiceType().getServiceName());
        fixOrder.setOrderTime(orderInfoDto.getOrder().getCreateTime());
        fixOrder.setState(orderInfoDto.getOrder().getState());
        fixOrder.setOrderMoney(orderInfoDto.getOrder().getFee());
        fixOrder.setOrderServiceHour(orderInfoDto.getServiceType().getServiceHour());
        fixOrder.setOrderServiceHourFee(new BigDecimal(Integer.parseInt(orderInfoDto.getServiceType().getServiceHour()) * Constant.HOUR_FEE));
        fixOrder.setOrderPartsFee(fixOrder.getOrderMoney().subtract(fixOrder.getOrderServiceHourFee()));
        fixOrder.setCarType(orderInfoDto.getOrder().getCar().getCarType());
        fixOrder.setCarColor(orderInfoDto.getOrder().getCar().getColor());
        fixOrder.setCarLicence(orderInfoDto.getOrder().getCar().getLicenceNo());
        fixOrder.setCustomerName(orderInfoDto.getOrder().getCustomer().getUserName());
        fixOrder.setCustomerTel(orderInfoDto.getOrder().getCustomer().getTel());

        fixOrderMapper.insert(fixOrder);
        logger.info("新增fixOrder：{}",fixOrder);

        //结算
        SttleOrder sttleOrder = new SttleOrder();
        sttleOrder.setOrderId(orderInfoDto.getOrder().getId());
        sttleOrder.setOrderType(orderInfoDto.getServiceType().getServiceName());
        sttleOrder.setOrderTime(orderInfoDto.getOrder().getCreateTime());
        sttleOrder.setState(orderInfoDto.getOrder().getState());
        sttleOrder.setOrderMoney(orderInfoDto.getOrder().getFee());
        sttleOrder.setOrderServiceHour(orderInfoDto.getServiceType().getServiceHour());
        sttleOrder.setOrderServiceHourFee(new BigDecimal(Integer.parseInt(orderInfoDto.getServiceType().getServiceHour()) * Constant.HOUR_FEE));
        sttleOrder.setOrderPartsFee(fixOrder.getOrderMoney().subtract(fixOrder.getOrderServiceHourFee()));
        sttleOrder.setCarType(orderInfoDto.getOrder().getCar().getCarType());
        sttleOrder.setCarColor(orderInfoDto.getOrder().getCar().getColor());
        sttleOrder.setCarLicence(orderInfoDto.getOrder().getCar().getLicenceNo());
        sttleOrder.setCustomerName(orderInfoDto.getOrder().getCustomer().getUserName());
        sttleOrder.setCustomerTel(orderInfoDto.getOrder().getCustomer().getTel());

        sttleOrderMapper.insertSelective(sttleOrder);
        logger.info("新增sttleOrder：{}",sttleOrder);


        for(Parts parts : orderInfoDto.getPartsList()){
            FixOrderParts fixOrderParts = new FixOrderParts();
            fixOrderParts.setOrderId(orderInfoDto.getOrder().getId());
            fixOrderParts.setPartsId(parts.getId());
            fixOrderParts.setPartsName(parts.getPartsName());
            fixOrderParts.setPartsNo(parts.getPartsNo());
            fixOrderParts.setPartsNum(parts.getNum());

            fixOrderPartsMapper.insertSelective(fixOrderParts);
            logger.info("新增fixOrderParts：{}",fixOrderParts);

            SttleOrderParts sttleOrderParts = new SttleOrderParts();
            sttleOrderParts.setOrderId(orderInfoDto.getOrder().getId());
            sttleOrderParts.setPartsId(parts.getId());
            sttleOrderParts.setPartsName(parts.getPartsName());
            sttleOrderParts.setPartsNo(parts.getPartsNo());
            sttleOrderParts.setPartsNum(parts.getNum());

            sttleOrderPartsMapper.insertSelective(sttleOrderParts);
            logger.info("新增sttleOrderParts：{}",sttleOrderParts);
        }

    }

    /**
     * 分页
     *
     * @param pageNo
     * @param maps
     * @return
     */
    @Override
    public PageInfo<FixOrder> findPageByMap(Integer pageNo, Map<String, Object> maps) {
        PageHelper.startPage(pageNo,Constant.PAGE_SIZE);

        List<FixOrder> fixOrderList = fixOrderMapper.findPageByMaps(maps);

        PageInfo<FixOrder> pageInfo = new PageInfo<>(fixOrderList);
        return pageInfo;
    }

    /**
     * 查询parts
     *
     * @return
     */
    @Override
    public List<FixOrder> findFixOrderListWithParts() {
        return fixOrderMapper.findListParts();
    }

    /**
     * 根据订单id和当前登录员工领取维修任务
     * @param id
     * @param employee
     * @throws ServiceException
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void taskReceive(Integer id, Employee employee) throws ServiceException{
        //判断当前员工是否有未完成的任务
        FixOrderExample fixOrderExample = new FixOrderExample();
        fixOrderExample.createCriteria().andFixEmployeeIdEqualTo(employee.getId()).andStateEqualTo(FixOrder.ORDER_STATE_FIXING);
        List<FixOrder> fixOrderList = fixOrderMapper.selectByExample(fixOrderExample);

        if(fixOrderList != null && fixOrderList.size() > 0) {
            throw new ServiceException("还有未完成的任务，不能接收新任务");
        }

        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(id);
        if(fixOrder == null){
            throw new ServiceException("参数错误或该订单不存在");
        }

        //修改状态并添加领取任务员工信息
        fixOrder.setState(FixOrder.ORDER_STATE_FIXING);
        fixOrder.setFixEmployeeId(employee.getId());
        fixOrder.setFixEmployeeName(employee.getEmployeeName());
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        //发送消息队列
        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setOrderId(id);
        orderStateDto.setEmployeeId(employee.getId());
        orderStateDto.setState(FixOrder.ORDER_STATE_FIXING);

        sendStateToMq(orderStateDto);

        //减少库存
        changePartsInventory(id,employee.getId());

        //添加超时任务
        sendFixOrderTimeoutTask(id,employee.getId(),Integer.parseInt(fixOrder.getOrderServiceHour()));
    }

    /**
     * 添加超时任务
     * @param orderId
     * @param employeeId
     * @param orderServiceHour
     */
    private void sendFixOrderTimeoutTask(Integer orderId, Integer employeeId, Integer orderServiceHour) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        try {
            JobDetail jobDetail = JobBuilder.newJob(CountTimeOut.class)
                    .withIdentity("fix:" + orderId + "-" + employeeId,"ficOrder")
                    .build();

            DateTime dateTime = new DateTime();
            //dateTime = dateTime.plusHours(orderServiceHour);
            dateTime = dateTime.plusMinutes(orderServiceHour);

            String cronExpression = dateTime.getSecondOfMinute()+ "" + dateTime.getMinuteOfHour()+ "" + dateTime.getHourOfDay()
                                    + "" + dateTime.getDayOfMonth()+ "" +dateTime.getMonthOfYear() + "?" +dateTime.getYear();

            CronScheduleBuilder  scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息队列
     * @param orderStateDto
     */
    private void sendStateToMq(OrderStateDto orderStateDto) {
        String json = new Gson().toJson(orderStateDto);
        jmsTemplate.send("state-queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(json);
            }
        });
    }

    /**
     * 配件库存流水
     * @param id
     * @param employeeId
     */
    private void changePartsInventory(Integer id, Integer employeeId) {
        FixOrderPartsExample fixOrderPartsExample = new FixOrderPartsExample();
        fixOrderPartsExample.createCriteria().andOrderIdEqualTo(id);
        List<FixOrderParts> fixOrderPartsList = fixOrderPartsMapper.selectByExample(fixOrderPartsExample);

        FixOrderPartsVo fixOrderPartsVo = new FixOrderPartsVo();
        fixOrderPartsVo.setEmployeeId(employeeId);
        fixOrderPartsVo.setFixOrderPartsList(fixOrderPartsList);

        String json = new Gson().toJson(fixOrderPartsVo);
        jmsTemplate.send("parts-queue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(json);
            }
        });

    }

    /**
     * 根据id查找fixOrder
     *
     * @param id
     * @return
     */
    @Override
    public FixOrder findFixOrderById(Integer id) {
        return fixOrderMapper.findfixOrderPartsById(id);
    }

    /**
     * 完成任务
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void taskDone(Integer id) {
        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(id);
        if(fixOrder == null){
            throw  new ServiceException("参数异常或订单不存在");
        }

        fixOrder.setState(FixOrder.ORDER_STATE_FIXED);
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setOrderId(id);
        orderStateDto.setState(FixOrder.ORDER_STATE_FIXED);

        sendStateToMq(orderStateDto);

        //删除超时任务
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.deleteJob(new JobKey("fix:" + fixOrder.getOrderId() + "-" + fixOrder.getFixEmployeeId(), "fixOrder"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询质检单
     *
     * @return
     */
    @Override
    public List<FixOrder> findCheckOrderListWithParts() {
        return fixOrderMapper.findCheckOrderWithParts();
    }

    /**
     * 根据订单id和当前登录员工领取质检任务
     *
     * @param id
     * @param employee
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void checkReceiveTask(Integer id, Employee employee) {
        //判断当前员工是否有未完成的任务
        FixOrderExample fixOrderExample = new FixOrderExample();
        fixOrderExample.createCriteria().andCheckEmployeeIdEqualTo(employee.getId()).andStateEqualTo(FixOrder.ORDER_STATE_CHECKING);
        List<FixOrder> fixOrderList = fixOrderMapper.selectByExample(fixOrderExample);

        if(fixOrderList != null && fixOrderList.size() > 0) {
            throw new ServiceException("还有未完成的任务，不能接收新任务");
        }

        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(id);
        if(fixOrder == null){
            throw new ServiceException("参数错误或该订单不存在");
        }

        //修改状态并添加领取任务员工信息
        fixOrder.setState(FixOrder.ORDER_STATE_CHECKING);
        fixOrder.setCheckEmployeeId(employee.getId());
        fixOrder.setCheckEmployeeName(employee.getEmployeeName());
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        //发送消息队列
        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setOrderId(id);
        orderStateDto.setEmployeeId(employee.getId());
        orderStateDto.setState(FixOrder.ORDER_STATE_CHECKING);

        sendStateToMq(orderStateDto);
    }

    /**
     * 根据id查询质检订单
     *
     * @param id
     * @return
     */
    @Override
    public FixOrder findCheckOrderById(Integer id) {
        return fixOrderMapper.findfixOrderPartsById(id);
    }

    /**
     * 完成质检任务
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void CheckTaskDone(Integer id) {
        FixOrder fixOrder = fixOrderMapper.selectByPrimaryKey(id);
        if(fixOrder == null){
            throw  new ServiceException("参数异常或订单不存在");
        }

        fixOrder.setState(FixOrder.ORDER_STATE_CHECKED);
        fixOrderMapper.updateByPrimaryKeySelective(fixOrder);

        OrderStateDto orderStateDto = new OrderStateDto();
        orderStateDto.setOrderId(id);
        orderStateDto.setState(FixOrder.ORDER_STATE_CHECKED);

        sendStateToMq(orderStateDto);
    }

    /**
     * 添加超时任务
     *
     * @param jobName
     */
    @Override
    public void addFixTimeOut(String jobName) {
        Integer orderId = Integer.valueOf(jobName.split(":")[1].split("-")[0]);
        Integer employeeId = Integer.valueOf(jobName.split(":")[1].split("-")[1]);

        CountTimeoutExample countTimeoutExample = new CountTimeoutExample();
        countTimeoutExample.createCriteria().andEmployeeIdEqualTo(employeeId)
                .andOrderIdEqualTo(orderId);

        CountTimeout countTimeout = new CountTimeout();
        countTimeout.setOrderId(orderId);
        countTimeout.setEmployeeId(employeeId);

        countTimeoutMapper.insertSelective(countTimeout);
    }


}
