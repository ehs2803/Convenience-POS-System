package com.example.convenience_pos_system.domain;

public class SaleDetail {
    private Long sid;
    private Long pid;
    private String name;
    private int price;
    private int quantity;

    public Long getSid() {
        return sid;
    }

    public Long getPid() {
        return pid;
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

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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
}
