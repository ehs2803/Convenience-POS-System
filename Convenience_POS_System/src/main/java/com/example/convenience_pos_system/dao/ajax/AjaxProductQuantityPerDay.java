package com.example.convenience_pos_system.dao.ajax;

public class AjaxProductQuantityPerDay {
    private Long id;
    private String code;
    private String name;
    private int price;
    private int quantity;

    public AjaxProductQuantityPerDay(Long id, String code, String name, int price, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
