package com.hoon.electronic.domain.item;

import com.hoon.electronic.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class CreateItemDto {

    private String name;

    private String brand;

    private int price;

    private List<CreateItemAttributeDto> createItemAttributeDtoList;

    public Item createEntity(Category category) {
        Item item = Item.builder()
                .name(name)
                .brand(brand)
                .price(price)
                .createDateTime(LocalDateTime.now())
                .category(category)
                .build();

        List<ItemAttribute> itemAttributeList = createItemAttributeDtoList.stream()
                .map(i -> new ItemAttribute(i.getAttribute(), i.getValue()))
                .collect(toList());

        for (ItemAttribute itemAttribute : itemAttributeList) {
            item.addItemAttribute(itemAttribute);
        }

        return item;
    }
}
