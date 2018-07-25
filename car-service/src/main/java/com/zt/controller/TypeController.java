package com.zt.controller;

import com.github.pagehelper.PageInfo;
import com.zt.entity.Type;
import com.zt.service.PartsService;
import com.zt.service.TypeService;
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

        List<Type> typeList = partsService.findTypeList();
        PageInfo<Type> page = typeService.findPageWithMap(pageNo,maps);

        model.addAttribute("page",page);
        model.addAttribute("typeList",typeList);
        return "type/list";
    }

    @GetMapping("/{id:\\d+}/del")
    public String del(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        typeService.delTypeName(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/type";
    }

    @PostMapping("/new")
    public String addTYpe(Type type, RedirectAttributes redirectAttributes){
        typeService.saveType(type);
        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/type";
    }

}
