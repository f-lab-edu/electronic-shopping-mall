package com.hoon.electronic.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @Setter
    private String value;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public ItemAttribute(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

}
