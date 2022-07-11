package com.virtual.power_plant.services;

import com.virtual.power_plant.dtos.BatteryDto;
import com.virtual.power_plant.dtos.PowerPlantDto;

import java.util.List;
import java.util.Map;

/**
 * created by Diluni
 * on 7/12/2022
 */
public interface VirtualPowerPlantService {
    Map<String,List<BatteryDto>> addBatteries(List<BatteryDto> batteries);

    PowerPlantDto getBatteries(Long fromCode, Long toCode);

}
