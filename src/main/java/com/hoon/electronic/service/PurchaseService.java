package com.hoon.electronic.service;

import com.hoon.electronic.domain.Member;
import com.hoon.electronic.domain.item.Item;
import com.hoon.electronic.domain.purchase.CreatePurchaseDto;
import com.hoon.electronic.domain.purchase.Purchase;
import com.hoon.electronic.domain.purchase.PurchaseItem;
import com.hoon.electronic.repository.ItemRepository;
import com.hoon.electronic.repository.MemberRepository;
import com.hoon.electronic.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    public final PurchaseRepository purchaseRepository;
    public final MemberRepository memberRepository;
    public final ItemRepository itemRepository;
    public final MemberService memberService;

    @Transactional
    public void register(CreatePurchaseDto purchaseDto) {
        // 멤버 조회
        List<Member> findMembers = memberRepository.findByEmail(purchaseDto.getMemberEmail());
        boolean isMember = memberService.isExistMember(findMembers);

        Member member;
        if (isMember) {
            member = findMembers.get(0);
        } else {
            throw new IllegalArgumentException("멤버가 없습니다.");
        }

        // 상품 목록 조회
        List<Item> findItemList = itemRepository.findItemListByItemIdList(purchaseDto.getItemIdList());
        if (findItemList.isEmpty()) {
            throw new IllegalArgumentException("상품 목록이 없습니다.");
        }

        // 구매상품 목록 생성
        List<PurchaseItem> purchaseItemList = PurchaseItem.from(findItemList);

        // 구매 생성
        Purchase purchase = Purchase.from(member, purchaseItemList);

        // 구매 등록
        purchaseRepository.save(purchase);

    }
}
