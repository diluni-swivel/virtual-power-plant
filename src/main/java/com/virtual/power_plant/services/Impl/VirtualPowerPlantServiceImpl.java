package com.virtual.power_plant.services.Impl;

import com.virtual.power_plant.dtos.BatteryDto;
import com.virtual.power_plant.dtos.PowerPlantDto;
import com.virtual.power_plant.entities.Battery;
import com.virtual.power_plant.entities.PostalArea;
import com.virtual.power_plant.repositories.BatteryRepository;
import com.virtual.power_plant.repositories.PostalAreaRepository;
import com.virtual.power_plant.services.VirtualPowerPlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Service
public class VirtualPowerPlantServiceImpl implements VirtualPowerPlantService {

    Logger logger = LoggerFactory.getLogger(VirtualPowerPlantServiceImpl.class);

    @Autowired
    private BatteryRepository batteryRepository;

    @Autowired
    private PostalAreaRepository postalAreaRepository;

    /**
     * Implementation of adding list of batteries into the power plant.
     *
     * @param batteryDtos
     * @return Map obj
     */
    @Override
    @Transactional
    public Map<String, List<BatteryDto>> addBatteries(List<BatteryDto> batteryDtos) {
        logger.info("Enter into addBatteries service implementation");
        List<BatteryDto> failIncidents = new ArrayList<>();
        CopyOnWriteArrayList<BatteryDto> successIncidents = new CopyOnWriteArrayList<>(batteryDtos);
        Iterator<BatteryDto> iterator = successIncidents.iterator();
        try {
            successIncidents.forEach(batteryDto -> {
                logger.info("Incoming battery name>>>> " + batteryDto.getName());
                Optional<PostalArea> postalArea = postalAreaRepository.findById(batteryDto.getPostCode());
                if (postalArea.isPresent() && batteryDto.getMegaWattCapacity() > 0.0) {
                    logger.info("Validations succeeded in the battery>>>> " + batteryDto.getName());
                    Battery battery = new Battery(batteryDto.getName(), batteryDto.getMegaWattCapacity(), postalArea.get());
                    batteryRepository.save(battery);
                    logger.info("Battery has been successfully saved>>>> " + batteryDto.getName());
                } else {
                    logger.info("Validations failed in the battery>>>> " + batteryDto.getName());
                    successIncidents.remove(batteryDto);
                    failIncidents.add(batteryDto);
                }
                iterator.hasNext();
            });
            return new HashMap<>() {{
                put(HttpStatus.CREATED.getReasonPhrase(), successIncidents);
                put(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), failIncidents);
            }};
        } catch (Exception e) {
            logger.error("addBatteries request service has been failed");
            throw new RuntimeException("Exception is occurred while adding the batteries");
        }
    }

    /**
     * Implementation of getting the statistics with the list of batteries names,
     * which are implemented in the given post code range.
     *
     * @param fromCode
     * @param toCode
     * @return PowerPlantDto obj
     */
    @Override
    public PowerPlantDto getBatteries(Long fromCode, Long toCode) {
        logger.info("Enter into getBatteries service implementation-- fromCode>>>> " + fromCode + " toCode>>>> " + toCode);
        List<BatteryDto> batteries = batteryRepository.findAllByPostalCodeRange(fromCode, toCode);
        if (batteries.size() > 0) {
            logger.info("Batteries found in the requested postal code range-- fromCode>>>> " + fromCode + " toCode>>>> " + toCode);
            Double totalMWatts = batteries.stream().mapToDouble(BatteryDto::getMegaWattCapacity).sum();
            List<String> batteryNames = batteries.stream().map(battery -> battery.getName()).collect(Collectors.toList());
            Collections.sort(batteryNames);
            return new PowerPlantDto(fromCode + "-" + toCode, batteries.size(), totalMWatts, (totalMWatts / batteries.size()), batteryNames);
        } else {
            logger.error("No batteries found in the requested postal code range-- fromCode>>>> " + fromCode + " toCode>>>> " + toCode);
            throw new RuntimeException("No batteries implement in the postal code range " + fromCode + " - " + toCode);
        }
    }

    /**
     * Implementation of adding list of postal areas to the power plant.
     *
     * @param postalAreas
     * @return ResponseEntity obj
     */
    @Override
    @Transactional
    public ResponseEntity addPostalAreas(List<PostalArea> postalAreas) {
        logger.info("Enter into addPostalAreas service implementation");
        try {
            postalAreaRepository.saveAllAndFlush(postalAreas);
            logger.info("Saved all the postal areas successfully");
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception is occurred while saving all the postal areas");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}