package com.hoon.electronic.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
@Getter
public class Monitor extends Item {

    private String size;

    private String panelForm;   //평면, 커브드

    private String panelType;   //IPS, VA, TN

    private String resolution;  //해상도

    @Builder
    public Monitor(Long id, String name, String brand, int price, String size, String panelForm, String panelType, String resolution) {
        super(id, name, brand, price);
        this.size = size;
        this.panelForm = panelForm;
        this.panelType = panelType;
        this.resolution = resolution;
    }
}
