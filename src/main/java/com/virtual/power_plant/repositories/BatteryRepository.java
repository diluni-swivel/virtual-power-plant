package com.virtual.power_plant.repositories;

import com.virtual.power_plant.dtos.BatteryDto;
import com.virtual.power_plant.entities.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {
    @Query("SELECT DISTINCT new com.virtual.power_plant.dtos.BatteryDto(a.name, a.wattCapacity, b.postalCode) FROM Battery a LEFT JOIN PostalArea b WHERE b.postalCode BETWEEN :fromCode AND :toCode")
    List<BatteryDto> findAllByPostalCodeRange(Long fromCode, Long toCode);

}