package com.example.convenience_pos_system.domain;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductAddDto {
    @NotBlank(message = "상품 코드는 필수 입력항목 입니다.")
    private String code;

    @NotBlank(message = "상품 이름은 필수 입력항목 입니다.")
    private String name;

    @NotNull(message = "상품 가격은 필수 입력항목 입니다.")
    @Range(min = 1, message = "가격은 1원 이상으로 입력해야 합니다.")
    private int price;

    @NotNull(message = "입고 수량은 필수입력항목 입니다.")
    @Range(min = 1, max = 100, message = "새제품 입고수량은 1~100개 입고 가능합니다.")
    private int quantity;

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
}
