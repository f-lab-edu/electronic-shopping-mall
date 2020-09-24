package com.hoon.electronic.service;

import com.hoon.electronic.domain.cart.CartItemAttributeDto;
import com.hoon.electronic.domain.cart.CartItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    CartService cartService;

    @Mock
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    ListOperations<String, Object> listOperations;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("장바구니 목록 조회")
    public void get_list() throws Exception {
        // 장바구니 상품 생성
        List<CartItemAttributeDto> cartItemAttributeDtoList = new ArrayList<>();
        cartItemAttributeDtoList.add(new CartItemAttributeDto("attr1", "value1"));
        cartItemAttributeDtoList.add(new CartItemAttributeDto("attr2", "value2"));

        CartItemDto cartItemDto = CartItemDto.builder()
                .id(1L)
                .name("test item")
                .brand("test brand")
                .price(999)
                .createDateTime(LocalDateTime.now())
                .cartItemAttributeDtoList(cartItemAttributeDtoList)
                .build();

        List<CartItemDto> list = new ArrayList<>();
        list.add(cartItemDto);

        // given
        String email = "test@example.com";
        String cartKey = "cart:" + email;
        given(listOperations.size(cartKey)).willReturn(10L);
        given(listOperations.range(cartKey, 0, 10)).willReturn(Collections.singletonList(list));

        // when
        cartService.getList(email);

        // then
        then(listOperations)
                .should()
                .range(cartKey, 0, 10);
    }

    @Test
    @DisplayName("장바구니 목록 조회 시 값(size)이 null인 경우")
    public void get_list_but_size_null() throws Exception {
        // given
        String email = "test@example.com";
        String cartKey = "cart:" + email;
        given(listOperations.size(cartKey)).willReturn(null);

        // when
        assertThrows(IllegalArgumentException.class, () -> {
            cartService.getList(email);
        });

        // then
        then(listOperations)
                .should(never())
                .range(cartKey, 0, 10);
    }

}