package com.hoon.electronic.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemAttribute {

    @Id
    @GeneratedValue
    @Column(name = "item_attribute_id")
    private Long id;

    private String attribute;

    private String value;

    @Setter
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public ItemAttribute(CreateItemAttributeDto dto) {
        this.attribute = dto.getAttribute();
        this.value = dto.getValue();
    }

}
