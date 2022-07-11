package com.virtual.power_plant.dtos;

import lombok.Data;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Data
public class BatteryDto {
    private String name;
    private Double wattCapacity;
    private Long postal_code;

    public BatteryDto() {

    }

    public BatteryDto(String name, Double wattCapacity, Long postal_code) {
        this.name = name;
        this.wattCapacity = wattCapacity;
        this.postal_code = postal_code;
    }

}