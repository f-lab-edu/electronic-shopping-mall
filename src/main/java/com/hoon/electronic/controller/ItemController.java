package com.hoon.electronic.controller;

import com.hoon.electronic.aop.LoginType;
import com.hoon.electronic.domain.item.CreateItemDto;
import com.hoon.electronic.domain.item.ItemDto;
import com.hoon.electronic.domain.item.UpdateItemDto;
import com.hoon.electronic.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hoon.electronic.domain.AccountPermissionLevel.ADMIN;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> list(@RequestParam("categoryId") Long categoryId,
                              @RequestParam("cursorId") Long cursorId) {
        if (cursorId == null) {
            throw new IllegalArgumentException("잘못된 cursorId 입니다.");
        }

        return itemService.list(categoryId, cursorId);
    }

    @LoginType(level = ADMIN)
    @PostMapping
    public HttpStatus create(@RequestParam("categoryId") Long categoryId,
                             @RequestBody CreateItemDto createItemDto) {

        itemService.saveItem(categoryId, createItemDto);

        return HttpStatus.CREATED;
    }

    @LoginType(level = ADMIN)
    @PutMapping("/{id}")
    public HttpStatus update(@PathVariable("id") Long id,
                             @RequestBody UpdateItemDto updateItemDto) {

        itemService.updateItem(id, updateItemDto);

        return HttpStatus.OK;
    }

}
