package com.hoon.electronic.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemAttributeDto {

    private String attribute;

    private String value;

    public ItemAttributeDto(ItemAttribute itemAttribute) {
        attribute = itemAttribute.getAttribute();
        value = itemAttribute.getValue();
    }
}
