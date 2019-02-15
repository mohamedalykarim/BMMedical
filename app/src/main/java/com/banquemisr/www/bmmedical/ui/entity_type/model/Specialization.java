package com.banquemisr.www.bmmedical.ui.entity_type.model;

public class Specialization {
    String name;
    int resource;

    public Specialization(String name, int resource) {
        this.name = name;
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }
}
