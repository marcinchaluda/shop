package com.codecool.shop.model;

public abstract class BaseDescribedModel extends BaseModel {

    protected String name;
    protected String description;

    public BaseDescribedModel(String name) {
        this.name = name;
    }

    public BaseDescribedModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
