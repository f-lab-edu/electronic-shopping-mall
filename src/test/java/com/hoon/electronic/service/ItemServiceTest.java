package com.hoon.electronic.service;

import com.hoon.electronic.domain.item.Item;
import com.hoon.electronic.domain.item.ItemAttribute;
import com.hoon.electronic.domain.item.ItemDto;
import com.hoon.electronic.repository.CategoryRepository;
import com.hoon.electronic.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    ItemService itemService;

    @Mock
    ItemRepository itemRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Test
    public void 아이템_조회() throws Exception {
        // 상품 생성
        List<ItemAttribute> itemAttributeList = new ArrayList<>();
        itemAttributeList.add(new ItemAttribute("attr1", "value1"));
        itemAttributeList.add(new ItemAttribute("attr2", "value2"));

        Item item = Item.builder()
                .name("test item")
                .brand("test brand")
                .price(999)
                .createDateTime(LocalDateTime.now())
                .itemAttributeList(itemAttributeList)
                .build();

        // given
        given(itemRepository.findById(1L)).willReturn(Optional.ofNullable(item));

        // when
        ItemDto itemDto = itemService.getItem(1L);

        // then
        then(itemRepository)
                .should()
                .findById(anyLong());
    }

    @Test
    public void 아이템_조회_없는_id() throws Exception {
        // given
        Optional<Item> item = Optional.empty();
        given(itemRepository.findById(1L)).willReturn(item);

        // when
        assertThrows(IllegalArgumentException.class, () -> {
            itemService.getItem(1L);
        });

        // then
        then(itemRepository)
                .should()
                .findById(anyLong());
    }
}