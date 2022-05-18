package com.example.convenience_pos_system.domain;

import java.time.LocalDateTime;

public class ProductStateHistory {
    private Long id;
    private Long mid;
    private Long pid;
    private String name;
    private String newname;
    private int price;
    private int newprice;
    private String state;
    private LocalDateTime datetime;

    public ProductStateHistory(Long mid, Long pid, String name, String newname, int price, int newprice, String state, LocalDateTime datetime) {
        this.mid = mid;
        this.pid = pid;
        this.name = name;
        this.newname = newname;
        this.price = price;
        this.newprice = newprice;
        this.state = state;
        this.datetime = datetime;
    }

    public Long getId() {
        return id;
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

    public String getNewname() {
        return newname;
    }

    public int getPrice() {
        return price;
    }

    public int getNewprice() {
        return newprice;
    }

    public String getState() {
        return state;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setNewname(String newname) {
        this.newname = newname;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setNewprice(int newprice) {
        this.newprice = newprice;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
