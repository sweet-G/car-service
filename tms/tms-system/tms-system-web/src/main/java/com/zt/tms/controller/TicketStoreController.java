package com.zt.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zt.tms.config.QiniuStore;
import com.zt.tms.controller.exception.NotFoundException;
import com.zt.tms.entity.StoreAccount;
import com.zt.tms.entity.TicketStore;
import com.zt.tms.service.TicketStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * @author zhangtian
 * @date 2018/9/1
 */
@Controller
@RequestMapping("/ticketstore")
public class TicketStoreController {

    @Reference(version = "1.0")
    private TicketStoreService ticketStoreService;
    @Autowired
    private QiniuStore qiniuStore;


    @GetMapping
    public String home(@RequestParam(value = "p",defaultValue = "1",required = false) Integer pageNo,
                       @RequestParam(required = false,defaultValue = "") String storeName,
                       @RequestParam(required = false,defaultValue = "") String storeManager,
                       @RequestParam(required = false,defaultValue = "") String storeTel,
                       Model model){

        Map<String,Object> queryParam = Maps.newHashMap();
        queryParam.put("storeName",storeName);
        queryParam.put("storeManager",storeManager);
        queryParam.put("storeTel",storeTel);

        PageInfo<TicketStore> pageInfo = ticketStoreService.findAllTicketStoreByPage(pageNo,queryParam);
        model.addAttribute("pageInfo",pageInfo);
        return "store/home";
    }

    @GetMapping("/new")
    public String add(Model model){
        //获取七牛云
        String upToken = qiniuStore.getUploadToken();
        model.addAttribute("upToken",upToken);
        return "store/new";
    }

    @PostMapping("/new")
    public String add(TicketStore ticketStore, RedirectAttributes redirectAttributes){
        ticketStoreService.saveNewTicketStore(ticketStore);
        redirectAttributes.addFlashAttribute("message","新增售票点成功");
        return "redirect/ticketstore";
    }

    @GetMapping("/{id:\\d+}")
    public String info(@PathVariable Integer id, Model model){
        TicketStore ticketStore = ticketStoreService.findTicketStoreById(id);
        if(ticketStore == null){
            throw new NotFoundException();
        }

        //获取售票点关联的售票人信息
        StoreAccount storeAccount = ticketStoreService.findStoreAccountById(ticketStore.getId());

        model.addAttribute("storeAccount",storeAccount);
        model.addAttribute("ticketStore",ticketStore);
        return "store/info";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String edit(@PathVariable Integer id, Model model){
        TicketStore ticketStore = ticketStoreService.findTicketStoreById(id);

        if(ticketStore == null) {
            throw new NotFoundException();
        }

        //获取七牛云
        String uploadToken = qiniuStore.getUploadToken();

        model.addAttribute("uploadToken",uploadToken);
        model.addAttribute("ticketStore",ticketStore);
        return "store/edit";
    }

    @PostMapping("/{id:\\d+}/edit")
    public String edit(TicketStore ticketStore, RedirectAttributes redirectAttributes){
        ticketStoreService.updateTicketStore(ticketStore);
        redirectAttributes.addFlashAttribute("message","修改成功");
        return "redirect:/ticketstore";
    }

}
