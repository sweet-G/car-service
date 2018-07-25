package com.zt.entity;

import java.util.List;

public class Type {
    private Integer id;

    private String typeName;

    private List<Parts> partsList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Parts> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<Parts> partsList) {
        this.partsList = partsList;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", partsList=" + partsList +
                '}';
    }
}