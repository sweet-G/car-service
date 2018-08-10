package com.zt.erp.vo;

import com.zt.erp.entity.FixOrderParts;

import java.util.List;

/**
 * @author zhangtian
 * @date 2018/8/9
 */

public class FixOrderPartsVo {
    private Integer employeeId;
    private List<FixOrderParts> fixOrderPartsList;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public List<FixOrderParts> getFixOrderPartsList() {
        return fixOrderPartsList;
    }

    public void setFixOrderPartsList(List<FixOrderParts> fixOrderPartsList) {
        this.fixOrderPartsList = fixOrderPartsList;
    }
}
