package com.hoon.electronic.domain.cart;

import lombok.Getter;

@Getter
public class CartItemAttributeDto {

    private String attribute;

    private String value;

    public CartItemAttributeDto(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

}
