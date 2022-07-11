package com.virtual.power_plant.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Entity
@Data
public class PostalArea {

    @Id
    @Column(name = "postal_code")
    private Long postalCode;

    private String areaName;

}