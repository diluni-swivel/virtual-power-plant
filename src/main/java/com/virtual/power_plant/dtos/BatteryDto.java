package com.virtual.power_plant.dtos;

import lombok.Data;
import lombok.NonNull;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Data
public class BatteryDto {
    @NonNull
    private String name;
    @NonNull
    private Double megaWattCapacity;
    @NonNull
    private Long postCode;

    public BatteryDto() {

    }

    public BatteryDto(String name, Double megaWattCapacity, Long postCode) {
        this.name = name;
        this.megaWattCapacity = megaWattCapacity;
        this.postCode = postCode;
    }

}