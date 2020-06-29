package com.hoon.electronic.domain.item;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UpdateItemDto {
    private String name;

    private String brand;

    private int price;

    private List<UpdateItemAttributeDto> updateItemAttributeDtoList;

    public Item createEntity() {
        return Item.builder()
                .name(name)
                .brand(brand)
                .price(price)
                .itemAttributeList(updateItemAttributeDtoList.stream()
                        .map(i -> new ItemAttribute(i.getAttribute(), i.getValue()))
                        .collect(Collectors.toList()))
                .build();
    }
}
