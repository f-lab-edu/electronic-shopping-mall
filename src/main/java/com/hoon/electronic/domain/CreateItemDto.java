package com.hoon.electronic.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateItemDto {

    private String name;

    private String brand;

    private int price;

    private List<CreateItemAttributeDto> createItemAttributeDtoList;

}
