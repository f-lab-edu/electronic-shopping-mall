package com.hoon.electronic.controller;

import com.hoon.electronic.aop.LoginVerification;
import com.hoon.electronic.domain.cart.CartItemDto;
import com.hoon.electronic.service.CartService;
import com.hoon.electronic.util.HttpSessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.hoon.electronic.domain.AccountPermissionLevel.MEMBER;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @LoginVerification(level = MEMBER)
    @PostMapping
    public HttpStatus add(@RequestBody CartItemDto cartItemDto, HttpSession session) {
        String loginMemberEmail = HttpSessionUtil.getLoginMemberEmail(session);

        cartService.register(cartItemDto, loginMemberEmail);

        return HttpStatus.CREATED;
    }

    @LoginVerification(level = MEMBER)
    @GetMapping
    public List<Object> list(HttpSession session) {
        String loginMemberEmail = HttpSessionUtil.getLoginMemberEmail(session);

        return cartService.getList(loginMemberEmail);
    }

    @LoginVerification(level = MEMBER)
    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable("id") Long id, HttpSession session) {
        String loginMemberEmail = HttpSessionUtil.getLoginMemberEmail(session);

        cartService.deleteItem(id, loginMemberEmail);

        return HttpStatus.OK;
    }

    @LoginVerification(level = MEMBER)
    @DeleteMapping("/clear")
    public HttpStatus clear(HttpSession session) {
        String loginMemberEmail = HttpSessionUtil.getLoginMemberEmail(session);

        cartService.clearItem(loginMemberEmail);

        return HttpStatus.OK;
    }
}
