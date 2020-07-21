package com.mobiquity.models;

import java.io.Serializable;

public class Product implements Serializable {

    private long id;
    private long categoryId;
    private String name;
    private String url;
    private String description;
    private SalesPrice salePrice;

    public Product(long id, long categoryId, String name, String url, String description, SalesPrice salePrice) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.url = url;
        this.description = description;
        this.salePrice = salePrice;
    }
    public Product(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SalesPrice getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(SalesPrice salePrice) {
        this.salePrice = salePrice;
    }
}
