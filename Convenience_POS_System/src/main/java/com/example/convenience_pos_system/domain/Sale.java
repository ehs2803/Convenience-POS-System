package com.example.convenience_pos_system.domain;

import java.time.LocalDateTime;

public class Sale {
    private Long id;
    private Long mid;
    private int price;
    private LocalDateTime datetime;

    public Sale(Long mid, int price, LocalDateTime datetime) {
        this.mid = mid;
        this.price = price;
        this.datetime = datetime;
    }

    public Long getId() {
        return id;
    }

    public Long getMid() {
        return mid;
    }

    public int getPrice() {
        return price;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
