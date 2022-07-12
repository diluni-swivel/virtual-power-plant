package com.virtual.power_plant.dtos;

import lombok.Data;

import java.util.List;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Data
public class PowerPlantDto {
    String range;
    Integer noOfBatteries;
    Double totalMWatts;
    Double avgMWatt;
    List<String> batteryNames;

    public PowerPlantDto() {

    }

    public PowerPlantDto(String range, Integer noOfBatteries, Double totalMWatts, Double avgMWatt, List<String> batteryNames) {
        this.range = range;
        this.noOfBatteries = noOfBatteries;
        this.totalMWatts = totalMWatts;
        this.avgMWatt = avgMWatt;
        this.batteryNames = batteryNames;
    }

}