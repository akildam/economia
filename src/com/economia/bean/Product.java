package com.economia.bean;

/**
 * Product entity.
 */
public class Product {
    private String id;
    private String externalId;
    private String name;
    private Integer quantity; 
    private String measureUnity;
    private Integer deptId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMeasureUnity() {
        return measureUnity;
    }

    public void setMeasureUnity(String measureUnity) {
        this.measureUnity = measureUnity;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return getExternalId()+" : "+getName()+" : "+getDeptId();
    }
}
