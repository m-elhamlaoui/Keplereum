package com.example.jeeHamlaoui.Sattelites_Service.repository;

import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SatelliteModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SatelliteModelRepository extends JpaRepository<SatelliteModel, Long> {

}
