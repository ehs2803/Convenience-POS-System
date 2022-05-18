package com.example.convenience_pos_system.domain;

public class Product {
    private Long id;
    private String code;
    private String name;
    private int price;
    private int quantity;
    private int sell;

    public Product(String code, String name, int price, int quantity, int sell) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sell = sell;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSell(){
        return sell;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSell(int sell) { this.sell = sell; }
}
