package com.virtual.power_plant.controllers;

import com.virtual.power_plant.dtos.BatteryDto;
import com.virtual.power_plant.dtos.PowerPlantDto;
import com.virtual.power_plant.entities.PostalArea;
import com.virtual.power_plant.services.VirtualPowerPlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * created by Diluni
 * on 7/12/2022
 */
@RestController
@RequestMapping("power-plant")
public class VirtualPowerPlantController {

    @Autowired
    private VirtualPowerPlantService virtualPowerPlantService;

    /**
     * POST API facilitates to add list of batteries into the power plant.
     *
     * @param batteries BatteryDto obj List
     * @return Map obj
     */
    @RequestMapping(value = "/addBatteries", method = RequestMethod.POST)
    public Map<String, List<BatteryDto>> addBatteries(@RequestBody List<BatteryDto> batteries) {
        return virtualPowerPlantService.addBatteries(batteries);
    }

    /**
     * GET API facilitates to get the statistics with the list of batteries names,
     * which are implemented in the given post codes range of postal codes.
     *
     * @param fromCode
     * @param toCode
     * @return PowerPlantDto obj
     */
    @RequestMapping(value = "/getBatteries", method = RequestMethod.GET)
    public ResponseEntity<Object> getBatteries(@RequestParam(required = true) Long fromCode, @RequestParam(required = true) Long toCode) {
        try {
            return ResponseEntity.ok(virtualPowerPlantService.getBatteries(fromCode, toCode));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /**
     * POST API facilitates to add list of postal areas to the power plant.
     * In this solution PostalArea entity contains masterdata which must be added as prerequisite of the solution.
     *
     * @param postalAreas PostalArea obj List
     * @return ResponseEntity obj
     */
    @RequestMapping(value = "/addPostalAreas", method = RequestMethod.POST)
    public ResponseEntity addPostalAreas(@RequestBody List<PostalArea> postalAreas) {
        return virtualPowerPlantService.addPostalAreas(postalAreas);
    }

}