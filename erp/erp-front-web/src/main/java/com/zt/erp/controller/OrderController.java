package com.zt.erp.controller;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zt.erp.dto.ResponseBean;
import com.zt.erp.entity.*;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.service.CarService;
import com.zt.erp.service.OrderService;
import com.zt.erp.service.PartsService;
import com.zt.erp.vo.OrderInfoVo;
import com.zt.erp.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/8/2
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PartsService partsService;

    @GetMapping("/new")
    public String newOrder() {
        return "order/new";
    }

    @PostMapping("/new")
    @ResponseBody
    public ResponseBean newOrder(String json){
        Gson gson = new Gson();
        OrderVo orderVo = gson.fromJson(json,OrderVo.class);

        orderService.saveOrder(orderVo);
        return ResponseBean.success();
    }

    @GetMapping("/service/types")
    @ResponseBody
    public ResponseBean serviceTypes(){
        List<ServiceType> serviceTypeList = orderService.findAllServiceType();
        return ResponseBean.success(serviceTypeList);
    }

    @GetMapping("/parts/types")
    @ResponseBody
    public ResponseBean partsType(){
        List<Type> typeList = orderService.findAllPartsType();
        return ResponseBean.success(typeList);
    }

    @GetMapping("/{id:\\d+}/parts")
    @ResponseBody
    public ResponseBean partsByType(@PathVariable Integer id){
        List<Parts> partsList = partsService.findPartsByType(id);
        return ResponseBean.success(partsList);
    }

    @GetMapping("/undone/list")
    public String findUndone(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo,
                                 @RequestParam(required = false) String licenceNo,
                                 @RequestParam(required = false) String tel,
                                 @RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String endTime,
                                 Model model){
        Map<String, Object> maps = new HashMap<>();
        maps.put("pageNo",pageNo);
        maps.put("licenceNo",licenceNo);
        maps.put("tel",tel);
        maps.put("startTime",startTime);
        maps.put("endTime",endTime);
        maps.put("exState",Order.ORDER_STATE_DONE);

        PageInfo<Order> page = orderService.findPageByParam(maps);

        model.addAttribute("type","");
        model.addAttribute("page",page);
        return "order/list";
    }

    @GetMapping("/done/list")
    public String findDone(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo,
                                 @RequestParam(required = false) String licenceNo,
                                 @RequestParam(required = false) String tel,
                                 @RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String endTime,
                                 Model model){
        Map<String, Object> maps = new HashMap<>();
        maps.put("pageNo",pageNo);
        maps.put("licenceNo",licenceNo);
        maps.put("tel",tel);
        maps.put("startTime",startTime);
        maps.put("endTime",endTime);
        maps.put("state",Order.ORDER_STATE_DONE);

        PageInfo<Order> page = orderService.findPageByParam(maps);

        model.addAttribute("type","done");
        model.addAttribute("page",page);
        return "order/hisList";
    }

    @GetMapping("/{id:\\d+}/detail")
    public String detail(@PathVariable Integer id, Model model) throws ServiceException {
        Order order = orderService.findOrderWithCarAndCustomerById(id);

        List<Parts> partsList = orderService.findOrderWithPartsById(order.getId());

        ServiceType serviceType = orderService.findOrderWithServiceTypeById(order.getServiceTypeId());

        model.addAttribute("order",order);
        model.addAttribute("partsList",partsList);
        model.addAttribute("serviceType",serviceType);
        return "order/detail";
    }

    @GetMapping("/{id:\\d+}/del")
    public String delOrder(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        orderService.del(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/order/undone/list";
    }

    @GetMapping("/{id:\\d+}/trans")
    @ResponseBody
    public ResponseBean transOrder(@PathVariable Integer id) throws ServiceException{
        try {
            orderService.findOrderWithTransById(id);
        } catch (ServiceException e) {
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.success();
    }

    @GetMapping("/{id:\\d+}/edit")
    public String editOrder(@PathVariable Integer id, Model model){
        model.addAttribute("orderId",id);
        return "order/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    @ResponseBody
    public ResponseBean editOrder(String json) throws ServiceException{
        Gson gson = new Gson();
        OrderVo orderVo = gson.fromJson(json, OrderVo.class);

        try {
            orderService.edit(orderVo);
        } catch (Exception e) {
            return ResponseBean.error(e.getMessage());
        }
        return ResponseBean.success();
    }

    @GetMapping("/{id:\\d+}/info")
    @ResponseBody
    public ResponseBean editOrderInfo(@PathVariable Integer id) throws ServiceException {
        try {
            Order order = orderService.findOrderWithCarAndCustomerById(id);

            List<Parts> partsList = orderService.findOrderWithPartsById(order.getId());

            ServiceType serviceType = orderService.findOrderWithServiceTypeById(order.getServiceTypeId());

            OrderInfoVo orderInfoVo = new OrderInfoVo();
            orderInfoVo.setOrder(order);
            orderInfoVo.setServiceType(serviceType);
            orderInfoVo.setPartsList(partsList);

            return ResponseBean.success(orderInfoVo);
        } catch (ServiceException e) {
            ResponseBean.error(e.getMessage());
        }
        return null;
    }


}
