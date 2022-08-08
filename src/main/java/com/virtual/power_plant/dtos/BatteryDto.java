package com.virtual.power_plant.dtos;

import lombok.Data;
import lombok.NonNull;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Data
public class BatteryDto {
    /**
     * This DTO is used to map incoming request body in addBatteries POST REST API.
     * All the variables in the object must be presented.
     */
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