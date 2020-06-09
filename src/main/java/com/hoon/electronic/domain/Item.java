package com.hoon.electronic.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private String brand;

    private int price;

    private LocalDateTime createDateTime;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * cascade
     * 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장하기 위한 옵션
     */
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemAttribute> itemAttributeList;

}
