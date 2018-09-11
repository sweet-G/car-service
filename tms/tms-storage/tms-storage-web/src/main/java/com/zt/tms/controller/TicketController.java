package com.zt.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zt.tms.dto.ResponseBean;
import com.zt.tms.entity.Account;
import com.zt.tms.entity.TicketOutRecord;
import com.zt.tms.entity.TicketStore;
import com.zt.tms.exception.ServiceException;
import com.zt.tms.service.TicketService;
import com.zt.tms.entity.TicketInRecord;
import com.zt.tms.service.TicketStoreService;
import com.zt.tms.shiro.ShiroUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/9/3
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Reference(version = "1.0")
    private TicketService ticketService;

    @Reference(version = "1.0")
    private TicketStoreService ticketStoreService;

    @GetMapping("/storage")
    public String ticketIn(Model model, @RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo){
        PageInfo<TicketInRecord> pageInfo = ticketService.findTicketRecordByPageNo(pageNo);

        model.addAttribute("pageInfo",pageInfo);
        return "ticket/in/home";
    }

    @GetMapping("/storage/new")
    public String add(Model model){
        String today = DateTime.now().toString("YYYY-MM-dd");
        model.addAttribute("today",today);
        return "ticket/in/new";
    }

    @PostMapping("/storage/new")
    public String add(TicketInRecord ticketInRecord, RedirectAttributes redirectAttributes){
        try {
            ticketService.saveStorage(ticketInRecord, new ShiroUtil().getAccount());
            redirectAttributes.addFlashAttribute("message","入库成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/ticket/storage";
    }

    @GetMapping("/storage/{id:\\d+}/del")
    public String delTicketIn(@PathVariable Integer id,RedirectAttributes redirectAttributes){
        try {
            ticketService.delInRecordById(id);
            redirectAttributes.addFlashAttribute("message","删除成功");
        } catch (ServiceException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/ticket/storage";
    }

    @GetMapping("/out")
    public String ticketOut(Model model,@RequestParam(name = "p",required = false,defaultValue = "1") Integer pageNo){
        PageInfo<TicketOutRecord> pageInfo = ticketService.findTicketOutRecordByPageNo(pageNo);

        model.addAttribute("page",pageInfo);
        return "/ticket/out/home";
    }

    @GetMapping("/out/new")
    public String addOut(Model model){
        String today = DateTime.now().toString("YYYY-MM-dd");
        //查找所有的售票点
        List<TicketStore> ticketStoreList = ticketStoreService.findAllTicketStore();

        model.addAttribute("today",today);
        model.addAttribute("ticketStoreList",ticketStoreList);
        return "/ticket/out/new";
    }

    @PostMapping("/out/new")
    public String addOut(TicketOutRecord ticketOutRecord, RedirectAttributes redirectAttributes){
        try {
            ticketService.saveTicketOutRecord(ticketOutRecord, new ShiroUtil().getAccount());
        } catch (ServiceException ex) {
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/ticket/out";

    }

    @GetMapping("/out/{id:\\d+}/del")
    @ResponseBody
    public ResponseBean delOut(@PathVariable Integer id, Model model){
        ticketService.delOutRecordById(id);
        return ResponseBean.success();
    }

    @GetMapping("/chart")
    public String chartHome(Model model) {
        Map<String,Long> resultMap = ticketService.countTicketByState();
        model.addAttribute("resultMap",resultMap);
        return "ticket/chart/home";
    }
}
