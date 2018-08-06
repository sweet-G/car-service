package com.zt.erp.controller;

import com.github.pagehelper.PageInfo;
import com.zt.erp.entity.Parts;
import com.zt.erp.entity.Type;
import com.zt.erp.exception.ServiceException;
import com.zt.erp.service.PartsService;
import com.zt.erp.service.TypeService;
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
 * @date 2018/7/25
 */

@Controller
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private PartsService partsService;
    @Autowired
    private TypeService typeService;

    @GetMapping
    public String list(@RequestParam(name = "p", defaultValue = "1",required = false) Integer pageNo,
                       @RequestParam(required = false) String keys,
                       Model model){

        Map<String, Object> maps = new HashMap<>();
        maps.put("keys",keys);

        PageInfo<Type> page = typeService.findPageWithMap(pageNo,maps);
        model.addAttribute("page",page);
        return "type/list";
    }

    @GetMapping("/{id:\\d+}/del")
    public String del(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        Type type = typeService.findById(id);
        List<Parts> partsList = partsService.findByTypeId(id);

        if(type == null){
            throw new ServiceException("资源未找到");
        }else if(partsList.size() != 0){
            redirectAttributes.addFlashAttribute("message","该类型下占有产品，不能删除");
        }else{
            typeService.delTypeName(id);
            redirectAttributes.addFlashAttribute("message","删除成功");
        }

        return "redirect:/type";
    }

    @PostMapping("/new")
    public String addTYpe(String addTypeName, RedirectAttributes redirectAttributes){
        Type type = typeService.findByName(addTypeName);

        if(type == null){
            type = new Type();
            type.setTypeName(addTypeName);
            typeService.saveType(type);
            redirectAttributes.addFlashAttribute("message","新增成功");
        }else{
            redirectAttributes.addFlashAttribute("message","该类型已存在");
        }
        return "redirect:/type";
    }

    @PostMapping("/edit")
    public String edit(Type type,RedirectAttributes redirectAttributes){
        Type type1 = typeService.findByName(type.getTypeName());

        if(type1 != null && !type1.getId().equals(type.getId())){
            redirectAttributes.addFlashAttribute("message","该类型已存在");
        } else {
            typeService.edit(type);
            redirectAttributes.addFlashAttribute("message","修改成功");
        }

        return "redirect:/type";
    }



}
