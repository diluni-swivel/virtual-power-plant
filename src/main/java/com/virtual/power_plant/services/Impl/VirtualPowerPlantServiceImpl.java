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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        try {
//            List<Battery> batteryList = batteryDtos.stream().
//                    map(batteryDto -> new Battery(batteryDto.getName(), batteryDto.getWattCapacity(), postalAreaRepository.findById(batteryDto.getPostal_code()).get()))
//                    .collect(Collectors.toList());
//            batteryRepository.saveAllAndFlush(batteryList);

            batteryDtos.stream().forEach(batteryDto -> {
                Optional<PostalArea> postalArea = postalAreaRepository.findById(batteryDto.getPostal_code());
                if (postalArea.isPresent()) {
                    Battery battery = new Battery(batteryDto.getName(), batteryDto.getWattCapacity(), postalArea.get());
                    batteryRepository.save(battery);
                } else {
                    batteryDtos.remove(batteryDto);
                    failIncidents.add(batteryDto);
                }
            });
        } catch (Exception e) {
            return new HashMap<>() {{
                put(e.getMessage(), batteryDtos);
            }};
        }
        return new HashMap<>() {{
            put(HttpStatus.CREATED.getReasonPhrase(), batteryDtos);
            put(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), failIncidents);
        }};
    }

    @Override
    public PowerPlantDto getBatteries(Long fromCode, Long toCode) {
        List<BatteryDto> batteries = batteryRepository.findAllByPostalCodeRange(fromCode, toCode);
        Double totalWatts = batteries.stream().mapToDouble(BatteryDto::getWattCapacity).sum();
        List<String> batteryNames = batteries.stream().map(battery -> battery.getName()).collect(Collectors.toList());
        return new PowerPlantDto(fromCode + "-" + toCode, batteries.size(), totalWatts, (totalWatts / batteries.size()), batteryNames);
    }

}