package com.hoon.electronic.controller;

import com.hoon.electronic.aop.LoginVerification;
import com.hoon.electronic.domain.purchase.CreatePurchaseDto;
import com.hoon.electronic.service.PurchaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hoon.electronic.domain.AccountPermissionLevel.MEMBER;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @LoginVerification(level = MEMBER)
    @ApiOperation(value = "상품 구매", notes = "상품 구매를 진행합니다.")
    @ApiImplicitParam(
            name = "purchaseDto",
            value = "구매할 상품 정보를 담은 오브젝트",
            dataType = "CreatePurchaseDto",
            required = true)
    @PostMapping
    public HttpStatus purchase(@RequestBody CreatePurchaseDto purchaseDto) {

        purchaseService.register(purchaseDto);

        return HttpStatus.CREATED;
    }

}
