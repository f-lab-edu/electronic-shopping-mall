package com.hoon.electronic.controller;

import com.hoon.electronic.aop.LoginVerification;
import com.hoon.electronic.domain.item.CreateItemDto;
import com.hoon.electronic.domain.item.ItemDto;
import com.hoon.electronic.domain.item.UpdateItemDto;
import com.hoon.electronic.service.ItemService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "상품 목록", notes = "카테고리 별 상품 목록을 반환합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "categoryId",
                    value = "상품 카테고리 번호",
                    required = true),
            @ApiImplicitParam(
                    name = "cursorId",
                    value = "화면에 보여질 상품 번호",
                    required = true)
    })
    @GetMapping
    public List<ItemDto> list(@RequestParam("categoryId") Long categoryId,
                              @RequestParam("cursorId") Long cursorId) {
        if (cursorId == null) {
            throw new IllegalArgumentException("잘못된 cursorId 입니다.");
        }

        return itemService.list(categoryId, cursorId);
    }

    @GetMapping("/{id}")
    public ItemDto detail(@PathVariable("id") Long id) {

        return itemService.getItem(id);
    }

    @LoginVerification(level = ADMIN)
    @ApiOperation(value = "상품 등록", notes = "새로운 상품을 등록합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "categoryId",
                    value = "상품 카테고리 번호",
                    required = true),
            @ApiImplicitParam(
                    name = "createItemDto",
                    value = "등록할 상품 정보를 담은 오브젝트",
                    dataType = "CreateItemDto",
                    required = true)
    })
    @PostMapping
    public HttpStatus create(@RequestParam("categoryId") Long categoryId,
                             @RequestBody CreateItemDto createItemDto) {

        itemService.saveItem(categoryId, createItemDto);

        return HttpStatus.CREATED;
    }

    @LoginVerification(level = ADMIN)
    @ApiOperation(value = "상품 수정", notes = "상품 정보를 수정합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "id",
                    value = "상품 번호",
                    required = true),
            @ApiImplicitParam(
                    name = "updateItemDto",
                    value = "수정할 상품 정보를 담은 오브젝트",
                    dataType = "UpdateItemDto",
                    required = true)
    })
    @PutMapping("/{id}")
    public HttpStatus update(@PathVariable("id") Long id,
                             @RequestBody UpdateItemDto updateItemDto) {

        itemService.updateItem(id, updateItemDto);

        return HttpStatus.OK;
    }

}
