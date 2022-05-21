package com.example.convenience_pos_system.domain;

public class SaleCartDetail {
    private Long mid;
    private Long pid;
    private String name;
    private int price;
    private int quantity;

    public SaleCartDetail(Long mid, Long pid, String name, int price, int quantity) {
        this.mid = mid;
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getMid() {
        return mid;
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

    public void setMid(Long mid) {
        this.mid = mid;
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
