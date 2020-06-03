package com.hoon.electronic.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class Mouse extends Item {

    private String sensitivityMethod;   //감응 방식(광, 레이저, 적외선)

    private String connectionMethod;    //연결 방식(유선, 무선, 블루투스)

    private String sensor;  //HERO 16K, HERO, PMW-3389

    @Builder
    public Mouse(Long id, String name, String brand, int price, String sensitivityMethod, String connectionMethod, String sensor) {
        super(id, name, brand, price);
        this.sensitivityMethod = sensitivityMethod;
        this.connectionMethod = connectionMethod;
        this.sensor = sensor;
    }
}
