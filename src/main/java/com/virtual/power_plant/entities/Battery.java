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

    private Double mWattCapacity;

    //    we can further manage this solution with considering this flag
    private Boolean isDecommissioned = Boolean.FALSE;

    //    we can further develop this solution into multiple power plants
    private String powerPlantId = "power-plant-1";

    @ManyToOne
    @JoinColumn(name = "post_code", nullable = false)
    private PostalArea postCode;

    public Battery() {

    }

    public Battery(String name, Double mWattCapacity, PostalArea postCode) {
        this.name = name;
        this.mWattCapacity = mWattCapacity;
        this.postCode = postCode;
    }

}