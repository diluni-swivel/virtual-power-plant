package com.virtual.power_plant.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Entity
@Data
public class Battery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batteryId;

    private String name;

    private Double wattCapacity;

    @ManyToOne
    @JoinColumn(name = "postal_code", nullable = false)
    private PostalArea postalCode;

    public Battery(){

    }

    public Battery(String name, Double wattCapacity, PostalArea postalCode) {
        this.name = name;
        this.wattCapacity = wattCapacity;
        this.postalCode = postalCode;
    }

}