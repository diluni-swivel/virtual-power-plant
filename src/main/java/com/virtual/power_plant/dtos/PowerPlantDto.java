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
    Double totalWatts;
    Double avgWatt;
    List<String> batteryNames;

    public PowerPlantDto(){

    }

    public PowerPlantDto(String range, Integer noOfBatteries, Double totalWatts, Double avgWatt, List<String> batteryNames) {
        this.range = range;
        this.noOfBatteries = noOfBatteries;
        this.totalWatts = totalWatts;
        this.avgWatt = avgWatt;
        this.batteryNames = batteryNames;
    }

}