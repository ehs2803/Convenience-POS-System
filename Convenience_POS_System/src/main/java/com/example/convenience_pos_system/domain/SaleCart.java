package com.example.convenience_pos_system.domain;

public class SaleCart {
    private Long mid;
    private Long pid;
    private int quantity;

    public SaleCart(Long mid, Long pid, int quantity) {
        this.mid = mid;
        this.pid = pid;
        this.quantity = quantity;
    }

    public Long getMid() {
        return mid;
    }

    public Long getPid() {
        return pid;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
