package com.example.convenience_pos_system.domain;

import java.time.LocalDateTime;

public class ProductHistory {
    private Long id;
    private Long pid;
    private Long mid;
    private String name;
    private int price;
    private int quantity;
    private LocalDateTime datetime;
    private String method;

    public ProductHistory(Long pid, Long mid, String name, int price, int quantity, LocalDateTime datetime, String method) {
        this.pid = pid;
        this.mid = mid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.datetime = datetime;
        this.method = method;
    }

    public ProductHistory(Long mid, String name, int price, int quantity, String method) {
        this.pid = pid;
        this.mid = mid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.method = method;
    }

    public Long getId() {
        return id;
    }

    public Long getPid() {
        return pid;
    }

    public Long getMid() {
        return mid;
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

    public LocalDateTime getTime() {
        return datetime;
    }

    public String getMethod() {
        return method;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
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

    public void setTime(LocalDateTime time) {
        this.datetime = time;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
