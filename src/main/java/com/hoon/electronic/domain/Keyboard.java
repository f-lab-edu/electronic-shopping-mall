package com.hoon.electronic.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class Keyboard extends Item {

    private String connectionMethod;    //연결 방식(유선, 무선, 블루투스)

    private String contactMethod;   //접점 방식(멤브레인, 펜타그래프, 기계식)

    private String keyboardType;    //텐키리스, 분리형, 접이식

    @Builder
    public Keyboard(Long id, String name, String brand, int price, String connectionMethod, String contactMethod, String keyboardType) {
        super(id, name, brand, price);
        this.connectionMethod = connectionMethod;
        this.contactMethod = contactMethod;
        this.keyboardType = keyboardType;
    }
}
