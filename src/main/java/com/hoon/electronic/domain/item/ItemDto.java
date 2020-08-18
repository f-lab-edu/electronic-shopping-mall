package com.hoon.electronic.domain.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;

    private String name;

    private String brand;

    private int price;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
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
