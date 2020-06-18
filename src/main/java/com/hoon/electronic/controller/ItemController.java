package com.hoon.electronic.controller;

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

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> list(@RequestParam("category_id") Long categoryId,
                              @RequestParam("cursor_id") Long cursorId) {

        return itemService.list(categoryId, cursorId);
    }

    @PostMapping
    public HttpStatus create(@RequestParam("category_id") Long categoryId,
                             @RequestBody CreateItemDto createItemDto) {

        itemService.saveItem(categoryId, createItemDto);

        return HttpStatus.CREATED;
    }

    @PutMapping("/{id}")
    public HttpStatus update(@PathVariable("id") Long id,
                             @RequestBody UpdateItemDto updateItemDto) {

        itemService.updateItem(id, updateItemDto);

        return HttpStatus.OK;
    }

}
