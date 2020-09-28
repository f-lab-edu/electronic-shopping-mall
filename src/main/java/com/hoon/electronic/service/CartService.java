package com.hoon.electronic.service;

import com.hoon.electronic.domain.cart.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "redisTemplate")
    private ListOperations<String, Object> listOperations;

    /**
     * 장바구니에 상품 등록
     */
    public void register(CartItemDto cartItemDto, String loginMemberEmail) {
        String cartKey = generateCartKey(loginMemberEmail);

        listOperations.leftPush(cartKey, cartItemDto);
    }

    /**
     * 장바구니 상품 목록 조회
     */
    public List<Object> getList(String loginMemberEmail) {
        String cartKey = generateCartKey(loginMemberEmail);
        Long size = listOperations.size(cartKey);

        if (size == null) {
            throw new IllegalArgumentException("장바구니가 비어있습니다.");
        }

        return listOperations.range(cartKey, 0, size);
    }

    /**
     * 장바구니에서 특정 상품 삭제
     */
    public void deleteItem(Long id, String loginMemberEmail) {
        String cartKey = generateCartKey(loginMemberEmail);
        Object value = listOperations.index(cartKey, id);

        if (value == null) {
            throw new IllegalArgumentException("아이템 정보가 없습니다.");
        }

        listOperations.remove(cartKey, 1, value);
    }

    /**
     * 장바구니 비우기
     */
    public void clearItem(String loginMemberEmail) {
        String cartKey = generateCartKey(loginMemberEmail);
        redisTemplate.delete(cartKey);
    }

    /**
     * 레디스 키 값 생성
     * ex) "cart:test@example.com"
     */
    private String generateCartKey(String loginMemberEmail) {
        return "cart:" + loginMemberEmail;
    }
}
