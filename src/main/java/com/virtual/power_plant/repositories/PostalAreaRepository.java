package com.virtual.power_plant.repositories;

import com.virtual.power_plant.entities.PostalArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * created by Diluni
 * on 7/12/2022
 */
@Repository
public interface PostalAreaRepository extends JpaRepository<PostalArea, Long> {
    /**
     * This repository maps DB PostalArea table with PostalArea entity in the application via Hibernate.
     */
}