package com.hoon.electronic.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ItemDto {

    private Long id;

    private String name;

    private String brand;

    private int price;

    private LocalDateTime createDateTime;

    private List<ItemAttributeDto> itemAttributeDtoList;

    public ItemDto(Item item) {
        id = item.getId();
        name = item.getName();
        brand = item.getBrand();
        price = item.getPrice();
        createDateTime = item.getCreateDateTime();
        itemAttributeDtoList = item.getItemAttributeList().stream()
                .map(ItemAttributeDto::new)
                .collect(Collectors.toList());
    }
}
