package com.economia.bean;

/**
 * Department entity.
 */
public class Department {
    private Integer id;
    private String externalId;
    private String name;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Override
    public String toString() {
        return getId()+" : "+getExternalId() + " : " + getName(); //To change body of generated methods, choose Tools | Templates.
    }
}