package com.zt.erp.controller;

import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Parts;
import com.zt.erp.entity.PartsStream;
import com.zt.erp.entity.Type;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.service.PartsService;
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
 * @date 2018/7/23
 */
@Controller
@RequestMapping("/parts")
public class PartsController {

    @Autowired
    private PartsService partsService;

    @GetMapping
    public String list(@RequestParam(name = "p",defaultValue = "1",required = false) Integer pageNo,
                       @RequestParam(required = false) String partsName,
                       @RequestParam(required = false) Integer partsType,
                       @RequestParam(required = false) Integer partsInventory,
                       Model model){

        Map<String, Object> maps = new HashMap<>();
        maps.put("partsName",partsName);
        maps.put("partsType",partsType);
        maps.put("partsInventory",partsInventory);

        PageInfo<Parts> page = partsService.findPageWithTypeMap(pageNo,maps);
        List<Type> typeList = partsService.findTypeList();

        model.addAttribute("page",page);
        model.addAttribute("typeList",typeList);
        return "parts/list";
    }


    @GetMapping("/{id:\\d+}")
    public String findById(@PathVariable Integer id, Model model){
        Parts parts = partsService.findById(id);
        model.addAttribute("parts",parts);
        return "parts/detail";
    }

    @GetMapping("/{id:\\d+}/del")
    public String del(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        partsService.del(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/parts";
    }

    @GetMapping("/{id:\\d+}/edit")
    public String update(@PathVariable Integer id, Model model){
        Parts parts = partsService.findById(id);

        List<Type> typeList = partsService.findTypeList();
        model.addAttribute("typeList",typeList);
        model.addAttribute("parts",parts);
        return "parts/edit";

    }

    @PostMapping("/{id:\\d+}/edit")
    public String update(Parts parts, RedirectAttributes redirectAttributes) throws ServiceException {
        try {
            partsService.update(parts);
            redirectAttributes.addFlashAttribute("message","修改成功");
            return "redirect:/parts";
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
        }
        return null;
    }

    @GetMapping("/new")
    public String partsNew(Model model){
        List<Type> typeList = partsService.findTypeList();
        model.addAttribute("typeList",typeList);
        return "parts/new";
    }

    @PostMapping("/new")
    public String partsNew(Parts parts,RedirectAttributes redirectAttributes){
        Parts parts1 = partsService.findByPartsNo(parts.getPartsNo());
        if(parts1 !=null){
            redirectAttributes.addFlashAttribute("message","配件编号重复");
        } else{
            partsService.saveParts(parts);
            redirectAttributes.addFlashAttribute("message","入库成功");
        }
        return "redirect:/parts";
    }

    @ResponseBody
    @PostMapping("/check")
    public Object check(String partsNo){
        Parts parts = partsService.findByPartsNo(partsNo);
        return parts;
    }

    @GetMapping("/out")
    public String partsOut(@RequestParam(name = "p", defaultValue = "1",required = false) Integer pageNo,
                           @RequestParam(required = false) Integer orderId,
                           Model model){

        Map<String,Object> maps = new HashMap<>();
        maps.put("pageNo",pageNo);
        maps.put("orderId",orderId);

        PageInfo<PartsStream> page = partsService.findPartsStreamMaps(pageNo,maps);

        model.addAttribute("page",page);
        return "parts/out";
    }

}
