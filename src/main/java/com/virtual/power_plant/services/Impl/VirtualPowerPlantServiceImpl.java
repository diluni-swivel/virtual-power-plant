package com.virtual.power_plant.services.Impl;

import com.virtual.power_plant.dtos.BatteryDto;
import com.virtual.power_plant.dtos.PowerPlantDto;
import com.virtual.power_plant.entities.Battery;
import com.virtual.power_plant.entities.PostalArea;
import com.virtual.power_plant.repositories.BatteryRepository;
import com.virtual.power_plant.repositories.PostalAreaRepository;
import com.virtual.power_plant.services.VirtualPowerPlantService;
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

    @Autowired
    private BatteryRepository batteryRepository;

    @Autowired
    private PostalAreaRepository postalAreaRepository;

    @Override
    @Transactional
    public Map<String, List<BatteryDto>> addBatteries(List<BatteryDto> batteryDtos) {
        List<BatteryDto> failIncidents = new ArrayList<>();
        CopyOnWriteArrayList<BatteryDto> successIncidents = new CopyOnWriteArrayList<>(batteryDtos);
        Iterator<BatteryDto> iterator = successIncidents.iterator();
        try {
            successIncidents.forEach(batteryDto -> {
                Optional<PostalArea> postalArea = postalAreaRepository.findById(batteryDto.getPostCode());
                if (postalArea.isPresent() && batteryDto.getMegaWattCapacity() > 0.0) {
                    Battery battery = new Battery(batteryDto.getName(), batteryDto.getMegaWattCapacity(), postalArea.get());
                    batteryRepository.save(battery);
                } else {
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
            return new HashMap<>() {{
                put(e.getMessage(), batteryDtos);
            }};
        }
    }

    @Override
    public PowerPlantDto getBatteries(Long fromCode, Long toCode) {
        List<BatteryDto> batteries = batteryRepository.findAllByPostalCodeRange(fromCode, toCode);
        Double totalMWatts = batteries.stream().mapToDouble(BatteryDto::getMegaWattCapacity).sum();
        List<String> batteryNames = batteries.stream().map(battery -> battery.getName()).collect(Collectors.toList());
        Collections.sort(batteryNames);
        return new PowerPlantDto(fromCode + "-" + toCode, batteries.size(), totalMWatts, (totalMWatts / batteries.size()), batteryNames);
    }

    @Override
    @Transactional
    public ResponseEntity addPostalAreas(List<PostalArea> postalAreas) {
        try {
            postalAreaRepository.saveAllAndFlush(postalAreas);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}