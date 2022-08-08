package com.virtual.power_plant.services.Impl;

import com.virtual.power_plant.dtos.BatteryDto;
import com.virtual.power_plant.dtos.PowerPlantDto;
import com.virtual.power_plant.entities.PostalArea;
import com.virtual.power_plant.repositories.BatteryRepository;
import com.virtual.power_plant.repositories.PostalAreaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * created by Diluni
 * on 8/8/2022
 */
@ExtendWith(SpringExtension.class)
class VirtualPowerPlantServiceImplTest {

    @Mock
    private BatteryRepository batteryRepository;

    @Mock
    private static PostalAreaRepository postalAreaRepository;

    @InjectMocks
    private VirtualPowerPlantServiceImpl virtualPowerPlantServiceImpl;

    BatteryDto batteryDto1;

    BatteryDto batteryDto2;

    @BeforeEach
    void setUp() {
        PostalArea mockPostalArea = new PostalArea(60330L, "Pothuhera");
        Optional<PostalArea> postalArea = Optional.of(mockPostalArea);
        when(postalAreaRepository.findById(any())).thenReturn(postalArea);
        when(postalAreaRepository.findById(30000L)).thenReturn(Optional.empty());
        batteryDto1 = new BatteryDto("Battery_A", 25.0, 60330L);
        batteryDto2 = new BatteryDto("Battery_B", 10.5, 30000L);
    }

    @Test
    void addBatteries() {
        List<BatteryDto> batteryDtos = List.of(batteryDto1);
        Map<String, List<BatteryDto>> actualResult = virtualPowerPlantServiceImpl.addBatteries(batteryDtos);
        assertEquals(batteryDtos, actualResult.get("Created"));
        assertEquals(0, actualResult.get("Expectation Failed").size());
    }

    @Test
    void addBatteriesBothConditionsFalse() {
        List<BatteryDto> batteryDtos = List.of(new BatteryDto("Battery_C", -50.3, 60330L), batteryDto2);
        Map<String, List<BatteryDto>> actualResult = virtualPowerPlantServiceImpl.addBatteries(batteryDtos);
        assertEquals(0, actualResult.get("Created").size());
        assertEquals(batteryDtos, actualResult.get("Expectation Failed"));
    }

    @Test
    void addBatteriesSomeCasesSuccess() {
        List<BatteryDto> batteryDtos = List.of(batteryDto1, batteryDto2);
        Map<String, List<BatteryDto>> actualResult = virtualPowerPlantServiceImpl.addBatteries(batteryDtos);
        assertEquals(List.of(batteryDtos.get(0)), actualResult.get("Created"));
        assertEquals(List.of(batteryDtos.get(1)), actualResult.get("Expectation Failed"));
    }

    @Test
    void addBatteriesInvalidMegaWatts() {
        List<BatteryDto> batteryDtos = List.of(new BatteryDto("Battery_C", -50.3, 60330L), batteryDto1);
        Map<String, List<BatteryDto>> actualResult = virtualPowerPlantServiceImpl.addBatteries(batteryDtos);
        assertEquals(List.of(batteryDtos.get(1)), actualResult.get("Created"));
        assertEquals(List.of(batteryDtos.get(0)), actualResult.get("Expectation Failed"));
    }

    @Test
    void addBatteriesFindByIdException() {
        when(postalAreaRepository.findById(any())).thenThrow(new RuntimeException());
        List<BatteryDto> batteryDtos = List.of(batteryDto1);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            virtualPowerPlantServiceImpl.addBatteries(batteryDtos);
        });
        assertEquals("Exception is occurred while adding the batteries", exception.getMessage());
    }

    @Test
    void addBatteriesSaveException() {
        when(batteryRepository.save(any())).thenThrow(new RuntimeException());
        List<BatteryDto> batteryDtos = List.of(batteryDto1);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            virtualPowerPlantServiceImpl.addBatteries(batteryDtos);
        });
        assertEquals("Exception is occurred while adding the batteries", exception.getMessage());
    }

    @Test
    void getBatteries() {
        List<BatteryDto> batteryDtos = List.of(batteryDto1, new BatteryDto("Battery_D", 34.0, 60100L));
        when(batteryRepository.findAllByPostalCodeRange(any(), any())).thenReturn(batteryDtos);
        List<String> batteryNames = batteryDtos.stream().map(battery -> battery.getName()).collect(Collectors.toList());
        PowerPlantDto expectedPowerPlantDto = new PowerPlantDto(60000L + "-" + 70000L, batteryDtos.size(), 25.0 + 34.0, ((25.0 + 34.0) / batteryDtos.size()), batteryNames);
        PowerPlantDto actualPowerPlantDto = virtualPowerPlantServiceImpl.getBatteries(60000L, 70000L);
        assertEquals(expectedPowerPlantDto, actualPowerPlantDto);
    }

    @Test
    void getBatteriesException() {
        when(batteryRepository.findAllByPostalCodeRange(any(), any())).thenReturn(new ArrayList<>());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            virtualPowerPlantServiceImpl.getBatteries(60000L, 70000L);
        });
        assertEquals("No batteries implement in the postal code range " + 60000L + " - " + 70000L, exception.getMessage());
    }

    @Test
    void addPostalAreas() {
        List<PostalArea> mockPostalAreas = List.of(new PostalArea(60330L, "Pothuhera"),new PostalArea(70000L, "Kelaniya"));
        ResponseEntity actualResult = virtualPowerPlantServiceImpl.addPostalAreas(mockPostalAreas);
        assertEquals(201, actualResult.getStatusCodeValue());
    }

    @Test
    void addPostalAreasException() {
        List<PostalArea> mockPostalAreas = List.of(new PostalArea(60330L, "Pothuhera"),new PostalArea(70000L, "Kelaniya"));
        when(postalAreaRepository.saveAllAndFlush(any())).thenThrow(new RuntimeException());
        ResponseEntity actualResult = virtualPowerPlantServiceImpl.addPostalAreas(mockPostalAreas);
        assertEquals(500, actualResult.getStatusCodeValue());
    }

}