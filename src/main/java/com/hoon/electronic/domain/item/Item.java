package com.hoon.electronic.domain.item;

import com.hoon.electronic.domain.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    /**
     * fetch = FetchType.LAZY
     * 해당 값을 호출하는 순간에 DB에서 값을 가져오도록 하는 옵션이다.
     * 기본값은 EAGER인데 기본값일 경우 쿼리를 호출할 때 원치 않는 필드의 값을
     * 가져오기 위해서 테이블을 조인하는 일이 발생한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * mappedBy
     * 양방향 연관관계를 맺기 위한 옵션이다.
     * <p>
     * 해당 옵션이 없다면 아이템과 아이템 속성 엔티티는 단방향 연관관계를 맺게 되는데
     * 이 경우 아이템 엔티티를 통해서 아이템 속성 값을 가져올 수가 없다.
     * <p>
     * 아이템 엔티티에서 아이템 속성 값을 가져오려면 양방향 연관관계를 맺어야 하는데
     * 이때 연관관계의 주인을 설정해야 한다.
     * <p>
     * 연관관계의 주인은 mappedBy 옵션을 사용하지 않고, 주인이 아닌 경우만 mappedBy
     * 옵션을 사용해 연관관계의 주인을 설정한다.
     * <p>
     * 주인은 DB의 데이터를 조회하거나 변경할 수 있고 반면에 주인이 아닌 경우는 데이터를
     * 조회만 가능하다.
     * <p>
     * cascade
     * 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장하기 위한 옵션
     */
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @Builder.Default    // Builder 어노테이션을 사용할 때 필드에서 값을 초기화하기 위해 사용한다.
    private List<ItemAttribute> itemAttributeList = new ArrayList<>();

    public void addItemAttribute(ItemAttribute itemAttribute) {
        itemAttribute.setItem(this);
        itemAttributeList.add(itemAttribute);
    }

    public void change(Item updateItem) {
        this.name = updateItem.getName();
        this.brand = updateItem.getBrand();
        this.price = updateItem.getPrice();

        List<ItemAttribute> updateItemItemAttributeList = updateItem.getItemAttributeList();

        for (int i = 0; i < itemAttributeList.size(); i++) {
            ItemAttribute itemAttribute = itemAttributeList.get(i);
            ItemAttribute updateItemAttribute = updateItemItemAttributeList.get(i);

            itemAttribute.setValue(updateItemAttribute.getValue());
        }
    }
}
