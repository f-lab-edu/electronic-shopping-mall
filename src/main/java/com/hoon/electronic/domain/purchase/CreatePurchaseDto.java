package com.hoon.electronic.domain.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreatePurchaseDto {

    private String memberEmail;

    private List<Long> itemIdList;

}
