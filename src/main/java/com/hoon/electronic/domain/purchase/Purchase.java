package com.hoon.electronic.domain.purchase;

import com.hoon.electronic.domain.Member;
import com.hoon.electronic.domain.payment.Payment;
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
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase {

    @Id
    @GeneratedValue
    @Column(name = "purchase_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    @Builder.Default
    private List<PurchaseItem> purchaseItemList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private LocalDateTime purchaseDateTime;

    /**
     * 구매 생성
     */
    public static Purchase from(Member member, List<PurchaseItem> purchaseItemList) {
        Purchase purchase = Purchase.builder()
                .member(member)
                .purchaseDateTime(LocalDateTime.now())
                .build();

        for (PurchaseItem purchaseItem : purchaseItemList) {
            purchase.addPurchaseItem(purchaseItem);
        }

        return purchase;
    }

    /**
     * 구매 - 구매 상품
     * 연관관계 편의 메소드
     */
    public void addPurchaseItem(PurchaseItem purchaseItem) {
        purchaseItem.setPurchase(this);
        purchaseItemList.add(purchaseItem);
    }

    /**
     * 전체 구매 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;

        for (PurchaseItem purchaseItem : purchaseItemList) {
            totalPrice += purchaseItem.getPurchasePrice();
        }

        return totalPrice;
    }
}
