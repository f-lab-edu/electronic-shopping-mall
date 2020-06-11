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
     * mappedBy
     * 양방향 연관관계를 맺기 위한 옵션이다.
     * 해당 옵션이 없다면 아이템과 아이템 속성 엔티티는 단방향 연관관계를 맺게 되는데
     * 이 경우 아이템 엔티티를 통해서 아이템 속성 값을 가져올 수가 없다.
     * 그래서 아이템 엔티티에서 아이템 속성 값을 가져올 수 있도록 아이템 -> 아이템 속성
     * 방향으로의 연관관계를 맺음으로써 양방향 연관관계를 맺도록 했다.
     * <p>
     * cascade
     * 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장하기 위한 옵션
     */
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemAttribute> itemAttributeList;

}
