package com.zt.tms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import com.zt.tms.entity.*;
import com.zt.tms.mapper.TicketOutRecordMapper;
import com.zt.tms.mapper.TicketStoreMapper;
import com.zt.tms.service.TicketService;
import com.zt.tms.exception.ServiceException;
import com.zt.tms.mapper.TicketInRecordMapper;
import com.zt.tms.mapper.TicketsMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.internal.Ticket;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author zhangtian
 * @date 2018/9/3
 */
@Service(version = "1.0",timeout = 5000)
public class TicketServiceImpl implements TicketService {

    private Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketInRecordMapper ticketInRecordMapper;
    @Autowired
    private TicketsMapper ticketsMapper;
    @Autowired
    private TicketOutRecordMapper ticketOutRecordMapper;
    @Autowired
    private TicketStoreMapper ticketStoreMapper;

    /**
     * 入库
     *
     * @param ticketInRecord
     * @param account
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveStorage(TicketInRecord ticketInRecord, Account account) {
        BigInteger start = new BigInteger(ticketInRecord.getBeginTicketNum());
        BigInteger end = new BigInteger(ticketInRecord.getEndTicketNum());
        if(start.compareTo(end) >= 0){
            throw new ServiceException("起始票号必须小于截至票号");
        }

        //判断当前的入库票号的范围是否和之前入库的范围重合，如果重回则不能添加
        List<TicketInRecord> ticketInRecordList = ticketInRecordMapper.selectByExample(new TicketInRecordExample());
        for(TicketInRecord record : ticketInRecordList){
            BigInteger recordStart = new BigInteger(record.getBeginTicketNum());
            BigInteger recordEnd = new BigInteger(record.getEndTicketNum());

            boolean flag = (recordStart.compareTo(start) <= 0 && recordEnd.compareTo(start) >= 0) || (recordStart.compareTo(end) <= 0 && recordEnd.compareTo(end) >= 0);
            if(flag) {
                throw new ServiceException("票号区间重复，添加失败");
            }
        }

        //设置入库时间
        ticketInRecord.setCreateTime(new Date());
        //总票数号 = 截至票号 - 起始票号 + 1
        BigInteger totalNum = end.subtract(start).add(new BigInteger(String.valueOf(1)));
        ticketInRecord.setTotalNum(totalNum.intValue());

        //设置新增票号负责人
        ticketInRecord.setAccountId(account.getId());
        ticketInRecord.setAccountName(account.getAccountName());

        //设置入库的内容
        ticketInRecord.setContent(ticketInRecord.getBeginTicketNum()+"-"+ticketInRecord.getEndTicketNum());
        ticketInRecordMapper.insertSelective(ticketInRecord);

        logger.info("新增年票入库： {} 入库人：{}",ticketInRecord, account);

        //添加年票条数记录
        List<Tickets> ticketsList = new ArrayList<>();
        for(int i = 0; i < totalNum.intValue(); i++) {
            Tickets ticket = new Tickets();
            ticket.setCreateTime(new Date());
            ticket.setTicketInTime(new Date());
            ticket.setTicketNum(start.add(new BigInteger(String.valueOf(i))).toString());
            ticket.setTicketState(Tickets.TICKET_STATE_IN_STORE);
            ticketsList.add(ticket);
        }

        //批量保存年票记录
        ticketsMapper.batchInsert(ticketsList);
    }

    /**
     * 入库首页
     *
     * @param pageNo
     * @return
     */
    @Override
    public PageInfo<TicketInRecord> findTicketRecordByPageNo(Integer pageNo) {
        PageHelper.startPage(pageNo,10);

        TicketInRecordExample inRecordExample = new TicketInRecordExample();
        inRecordExample.setOrderByClause("id desc");

        List<TicketInRecord> ticketInRecordList = ticketInRecordMapper.selectByExample(inRecordExample);
        return new PageInfo<>(ticketInRecordList);
    }

    /**
     * 根据id删除年票
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delInRecordById(Integer id) {
        TicketInRecord inRecord = ticketInRecordMapper.selectByPrimaryKey(id);
        if(inRecord != null){
            //根据该记录查找年票
            List<Tickets> ticketList = ticketsMapper.findByBeginNumAndEndNumAndState(inRecord.getBeginTicketNum(),inRecord.getEndTicketNum(),Tickets.TICKET_STATE_IN_STORE);

            //判断年票数量和入库记录总数量是否相同，如果不同，则表示有的年票状态已经发生修改，不能删除
            if(!inRecord.getTotalNum().equals(ticketList.size())) {
                throw new ServiceException("该批次年票状态已经发生改变，不能删除");
            }

            //删除年票
            List<Long> delList = new ArrayList<>(Collections2.transform(ticketList, new Function<Tickets, Long>() {
                @Override
                public Long apply(Tickets t) {
                    return t.getId();
                }
            }));

            ticketsMapper.batchDeleteById(delList);
            //删除年票入库记录
            ticketInRecordMapper.deleteByPrimaryKey(id);
        }

    }

    /**
     * 出库首页
     *
     * @param pageNo
     * @return
     */
    @Override
    public PageInfo<TicketOutRecord> findTicketOutRecordByPageNo(Integer pageNo) {
        return findTicketOutRecordByPageNoAndQueryParam(pageNo,Maps.newHashMap());
    }

    /**
     * 出库记录
     *
     * @param ticketOutRecord
     * @param account
     */
    @Override
    @Transactional(rollbackFor =  RuntimeException.class)
    public void saveTicketOutRecord(TicketOutRecord ticketOutRecord, Account account) {
        //判断当前票段内是否有不是[已入库]状态的票，如果有则不能下发
        List<Tickets> ticketList = ticketsMapper.findByBeginNumAndEndNum(ticketOutRecord.getBeginTicketNum(),ticketOutRecord.getEndTicketNum());

        for(Tickets tickets : ticketList){
            if(!Tickets.TICKET_STATE_IN_STORE.equals(tickets.getTicketState())){
                throw  new ServiceException("该票段内有已经下发的票，请重新选择");
            }

            //获取当前下发的售票点对象，并赋值售票点名称
            TicketStore ticketStore = ticketStoreMapper.selectByPrimaryKey(ticketOutRecord.getStoreAccountId());
            ticketOutRecord.setStoreAccountName(ticketStore.getStoreName());

            //选择的总数量
            int totalSize = ticketList.size();
            //总价格
            BigDecimal totalPrice = ticketOutRecord.getPrice().multiply(new BigDecimal(totalSize));

            ticketOutRecord.setCreateTime(new Date());
            ticketOutRecord.setContent(ticketOutRecord.getBeginTicketNum() + " - " + ticketOutRecord.getEndTicketNum());
            ticketOutRecord.setOutAccountId(account.getId());
            ticketOutRecord.setOutAccountName(account.getAccountName());
            ticketOutRecord.setTotalNum(totalSize);
            ticketOutRecord.setState(TicketOutRecord.STATE_NO_PAY);
            ticketOutRecord.setTotalPrice(totalPrice);

            ticketOutRecordMapper.insertSelective(ticketOutRecord);

            logger.info("新增年票下发记录：{}", ticketOutRecord);

            tickets.setTicketState(Tickets.TICKET_STATE_OUT_STORE);
            tickets.setTicketOutTime(ticketOutRecord.getCreateTime().toString());
            ticketsMapper.updateByPrimaryKeySelective(tickets);

            logger.info("修改年票状态为已下发: {}", tickets );

        }

    }

    /**
     * 删除下发年票
     *
     * @param id
     */
    @Override
    public void delOutRecordById(Integer id) {
        TicketOutRecord record = ticketOutRecordMapper.selectByPrimaryKey(id);
        if(record != null) {
            //只有未支付的才可以删除
            if(TicketOutRecord.STATE_NO_PAY.equals(record.getState())) {
                ticketOutRecordMapper.deleteByPrimaryKey(id);
            }
        }
    }

    /**
     * 统计年票
     *
     * @return
     */
    @Override
    public Map<String, Long> countTicketByState() {
        return ticketsMapper.countByState();
    }

    private PageInfo<TicketOutRecord> findTicketOutRecordByPageNoAndQueryParam(Integer pageNo, HashMap<Object,Object> queryParam) {
        PageHelper.startPage(pageNo,10);

        TicketOutRecordExample ticketOutRecordExample = new TicketOutRecordExample();
        TicketOutRecordExample.Criteria criteria = ticketOutRecordExample.createCriteria();

        String state = (String) queryParam.get("state");
        if(StringUtils.isNotEmpty(state)){
            criteria.andStateEqualTo(state);
        }

        ticketOutRecordExample.setOrderByClause("id desc");

        List<TicketOutRecord> ticketOutRecords = ticketOutRecordMapper.selectByExample(ticketOutRecordExample);

        return new PageInfo<>(ticketOutRecords);

    }

}
