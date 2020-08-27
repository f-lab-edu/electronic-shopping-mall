package com.hoon.electronic.service;

import com.hoon.electronic.domain.Category;
import com.hoon.electronic.domain.item.CreateItemDto;
import com.hoon.electronic.domain.item.Item;
import com.hoon.electronic.domain.item.ItemDto;
import com.hoon.electronic.domain.item.UpdateItemDto;
import com.hoon.electronic.repository.CategoryRepository;
import com.hoon.electronic.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public List<ItemDto> list(Long categoryId, Long cursorId) {
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.Direction.DESC, "id");

        Slice<Item> page = getItemList(categoryId, cursorId, pageRequest);

        return page.getContent().stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());
    }

    public Slice<Item> getItemList(Long categoryId, Long cursorId, PageRequest pageRequest) {
        if (cursorId == 0L) {   // 최초 호출
            return itemRepository.findItemList(categoryId, pageRequest);
        } else {
            return itemRepository.findItemListByCursorId(categoryId, cursorId, pageRequest);
        }
    }

    @Cacheable(value = "item", key = "#id")
    public ItemDto getItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 아이템입니다."));

        return new ItemDto(item);
    }

    @Transactional
    public Long saveItem(Long categoryId, CreateItemDto createItemDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("없는 카테고리입니다."));

        Item item = createItemDto.createEntity(category);

        itemRepository.save(item);

        return item.getId();
    }

    @Transactional
    public void updateItem(Long id, UpdateItemDto updateItemDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 아이템입니다."));

        Item updateItem = updateItemDto.createEntity();

        item.change(updateItem);
    }
}
