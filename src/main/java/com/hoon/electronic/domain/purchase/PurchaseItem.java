package com.hoon.electronic.domain.purchase;

import com.hoon.electronic.domain.item.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseItem {

    @Id
    @GeneratedValue
    @Column(name = "purchase_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    private int purchasePrice;

    /**
     * 구매상품 목록 생성
     */
    public static List<PurchaseItem> from(List<Item> itemList) {
        List<PurchaseItem> purchaseItemList = new ArrayList<>();

        for (Item item : itemList) {
            PurchaseItem purchaseItem = PurchaseItem.builder()
                    .item(item)
                    .purchasePrice(item.getPrice())
                    .build();

            purchaseItemList.add(purchaseItem);
        }

        return purchaseItemList;
    }
}
