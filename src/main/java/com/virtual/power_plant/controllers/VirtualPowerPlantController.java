package com.virtual.power_plant.controllers;

import com.virtual.power_plant.dtos.BatteryDto;
import com.virtual.power_plant.dtos.PowerPlantDto;
import com.virtual.power_plant.services.VirtualPowerPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * created by Diluni
 * on 7/12/2022
 */
@RestController
public class VirtualPowerPlantController {

    @Autowired
    private VirtualPowerPlantService virtualPowerPlantService;

    @RequestMapping(value = "/addBatteries", method = RequestMethod.POST)
    public Map<String, List<BatteryDto>> addBatteries(@RequestBody List<BatteryDto> batteries) {
        return virtualPowerPlantService.addBatteries(batteries);
    }

    @RequestMapping(value = "/getBatteries", method = RequestMethod.GET)
    public PowerPlantDto getBatteries(@RequestParam(required = true) Long fromCode, @RequestParam(required = true) Long toCode) {
        return virtualPowerPlantService.getBatteries(fromCode, toCode);
    }

}