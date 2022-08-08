package com.virtual.power_plant.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Entity
@Data
public class PostalArea extends BaseEntity {

    @Id
    @Column(name = "post_code")
    private Long postCode;

    private String areaName;

    public PostalArea(){}

    public PostalArea(Long postCode, String areaName) {
        this.postCode = postCode;
        this.areaName = areaName;
    }

}