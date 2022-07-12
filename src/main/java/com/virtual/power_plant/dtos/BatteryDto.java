package com.virtual.power_plant.dtos;

import lombok.Data;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Data
public class BatteryDto {
    private String name;
    private Double megaWattCapacity;
    private Long postCode;

    public BatteryDto() {

    }

    public BatteryDto(String name, Double megaWattCapacity, Long postCode) {
        this.name = name;
        this.megaWattCapacity = megaWattCapacity;
        this.postCode = postCode;
    }

}