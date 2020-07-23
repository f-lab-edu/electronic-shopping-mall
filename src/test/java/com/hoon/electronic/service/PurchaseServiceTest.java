package com.hoon.electronic.service;

import com.hoon.electronic.domain.purchase.CreatePurchaseDto;
import com.hoon.electronic.domain.Member;
import com.hoon.electronic.domain.item.Item;
import com.hoon.electronic.domain.item.ItemAttribute;
import com.hoon.electronic.repository.ItemRepository;
import com.hoon.electronic.repository.MemberRepository;
import com.hoon.electronic.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @InjectMocks
    PurchaseService purchaseService;

    @Mock
    PurchaseRepository purchaseRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    MemberService memberService;

    @Test
    public void 상품구매() throws Exception {
        String email = "tester@example.com";

        // 멤버 생성
        List<Member> findMembers = createMemberList(email);

        // 아이템 id 목록 생성
        List<Long> itemIdList = createItemIdList();

        // 아이템 목록 생성
        List<Item> itemList = createItemList();

        // given
        given(memberRepository.findByEmail(email)).willReturn(findMembers);
        given(memberService.isExistMember(findMembers)).willReturn(true);
        given(itemRepository.findItemListByItemIdList(itemIdList)).willReturn(itemList);

        // when
        purchaseService.register(new CreatePurchaseDto(email, itemIdList));

        // then
        then(purchaseRepository)
                .should()
                .save(any());
    }

    @Test
    public void 상품구매_멤버_없음() throws Exception {
        String email = "tester@example.com";

        // 멤버 생성
        List<Member> findMembers = null;

        // 아이템 id 목록 생성
        List<Long> itemIdList = createItemIdList();

        // given
        given(memberRepository.findByEmail(email)).willReturn(findMembers);

        // when
        try {
            purchaseService.register(new CreatePurchaseDto(email, itemIdList));
            fail("멤버가 없어서 예외가 발생해야 한다.");
        } catch (NullPointerException e) {
            // PASS
        }

        // then
        then(itemRepository)
                .should(never())
                .findItemListByItemIdList(anyList());

        then(purchaseRepository)
                .should(never())
                .save(any());
    }

    @Test
    public void 상품구매_상품_목록_없음() throws Exception {
        String email = "tester@example.com";

        // 멤버 생성
        List<Member> findMembers = createMemberList(email);

        // 아이템 id 목록 생성
        List<Long> itemIdList = null;

        // 아이템 목록 생성
        List<Item> itemList = null;

        // given
        given(memberRepository.findByEmail(email)).willReturn(findMembers);
        given(memberService.isExistMember(findMembers)).willReturn(true);
        given(itemRepository.findItemListByItemIdList(itemIdList)).willReturn(itemList);

        try {
            purchaseService.register(new CreatePurchaseDto(email, itemIdList));
            fail("상품 목록이 없어서 예외가 발생해야 한다.");
        } catch (NullPointerException e) {
            // PASS
        }

        // then
        then(purchaseRepository)
                .should(never())
                .save(any());
    }

    private List<Item> createItemList() {
        List<ItemAttribute> itemAttributeList = new ArrayList<>();
        itemAttributeList.add(new ItemAttribute("attr1", "1"));
        itemAttributeList.add(new ItemAttribute("attr2", "2"));
        itemAttributeList.add(new ItemAttribute("attr3", "3"));

        Item item1 = Item.builder()
                .name("item1")
                .price(100)
                .itemAttributeList(itemAttributeList)
                .build();

        Item item2 = Item.builder()
                .name("item2")
                .price(200)
                .itemAttributeList(itemAttributeList)
                .build();

        List<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);
        return itemList;
    }

    private List<Long> createItemIdList() {
        List<Long> itemIdList = new ArrayList<>();
        itemIdList.add(1L);
        itemIdList.add(2L);
        return itemIdList;
    }

    private List<Member> createMemberList(String email) {
        Member member = Member.builder()
                .email(email)
                .build();

        List<Member> findMembers = new ArrayList<>();
        findMembers.add(member);
        return findMembers;
    }
}
