package com.example.jeeHamlaoui.Sattelites_Service.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteModel;
import com.example.jeeHamlaoui.Sattelites_Service.model.enumeration.SatelliteStatus;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteRepository;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SatelliteService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SatelliteServiceTest {
  @MockitoBean
  private SatelliteRepository satelliteRepository;

  @Autowired
  private SatelliteService satelliteService;

  /**
   * Test {@link SatelliteService#save(Satellite)}.
   * <p>
   * Method under test: {@link SatelliteService#save(Satellite)}
   */
  @Test
  @DisplayName("Test save(Satellite)")
  
  @MethodsUnderTest({"Satellite SatelliteService.save(Satellite)"})
  void testSave() {
    // Arrange
    SatelliteModel satelliteModel = new SatelliteModel();
    satelliteModel.setDesignTrajectoryPredictionFactor(10.0d);
    satelliteModel.setDimensions("Dimensions");
    satelliteModel.setDryMass(10.0d);
    satelliteModel.setExpectedLifespan(1);
    satelliteModel.setLaunchMass(10.0d);
    satelliteModel.setManufacturer("Manufacturer");
    satelliteModel.setName("Name");
    satelliteModel.setPowerCapacity(10.0d);
    satelliteModel.setWeight(10.0d);

    Satellite satellite = new Satellite();
    satellite.setGroundStationId(1L);
    satellite.setId(1L);
    satellite.setLaunchDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satellite.setModel(satelliteModel);
    satellite.setName("Name");
    satellite.setNetworkNodeId("42");
    satellite.setSatellite_id(1L);
    satellite.setSensors(new HashSet<>());
    satellite.setStatus(SatelliteStatus.LAUNCHED);
    satellite.setTrajectories(new HashSet<>());
    when(satelliteRepository.save(Mockito.<Satellite>any())).thenReturn(satellite);

    SatelliteModel satelliteModel2 = new SatelliteModel();
    satelliteModel2.setDesignTrajectoryPredictionFactor(10.0d);
    satelliteModel2.setDimensions("Dimensions");
    satelliteModel2.setDryMass(10.0d);
    satelliteModel2.setExpectedLifespan(1);
    satelliteModel2.setLaunchMass(10.0d);
    satelliteModel2.setManufacturer("Manufacturer");
    satelliteModel2.setName("Name");
    satelliteModel2.setPowerCapacity(10.0d);
    satelliteModel2.setWeight(10.0d);

    Satellite satellite2 = new Satellite();
    satellite2.setGroundStationId(1L);
    satellite2.setId(1L);
    satellite2.setLaunchDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satellite2.setModel(satelliteModel2);
    satellite2.setName("Name");
    satellite2.setNetworkNodeId("42");
    satellite2.setSatellite_id(1L);
    satellite2.setSensors(new HashSet<>());
    satellite2.setStatus(SatelliteStatus.LAUNCHED);
    satellite2.setTrajectories(new HashSet<>());

    // Act
    Satellite actualSaveResult = satelliteService.save(satellite2);

    // Assert
    verify(satelliteRepository).save(isA(Satellite.class));
    assertSame(satellite, actualSaveResult);
  }

  /**
   * Test {@link SatelliteService#findById(Long)}.
   * <p>
   * Method under test: {@link SatelliteService#findById(Long)}
   */
  @Test
  @DisplayName("Test findById(Long)")
  @MethodsUnderTest({"Optional SatelliteService.findById(Long)"})
  void testFindById() {
    // Arrange
    SatelliteModel satelliteModel = new SatelliteModel();
    satelliteModel.setDesignTrajectoryPredictionFactor(10.0d);
    satelliteModel.setDimensions("Dimensions");
    satelliteModel.setDryMass(10.0d);
    satelliteModel.setExpectedLifespan(1);
    satelliteModel.setLaunchMass(10.0d);
    satelliteModel.setManufacturer("Manufacturer");
    satelliteModel.setName("Name");
    satelliteModel.setPowerCapacity(10.0d);
    satelliteModel.setWeight(10.0d);

    Satellite satellite = new Satellite();
    satellite.setGroundStationId(1L);
    satellite.setId(1L);
    satellite.setLaunchDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satellite.setModel(satelliteModel);
    satellite.setName("Name");
    satellite.setNetworkNodeId("42");
    satellite.setSatellite_id(1L);
    satellite.setSensors(new HashSet<>());
    satellite.setStatus(SatelliteStatus.LAUNCHED);
    satellite.setTrajectories(new HashSet<>());
    Optional<Satellite> ofResult = Optional.of(satellite);
    when(satelliteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    Optional<Satellite> actualFindByIdResult = satelliteService.findById(1L);

    // Assert
    verify(satelliteRepository).findById(eq(1L));
    assertSame(ofResult, actualFindByIdResult);
  }

  /**
   * Test {@link SatelliteService#findAll()}.
   * <p>
   * Method under test: {@link SatelliteService#findAll()}
   */
  @Test
  @DisplayName("Test findAll()")
  
  @MethodsUnderTest({"List SatelliteService.findAll()"})
  void testFindAll() {
    // Arrange
    when(satelliteRepository.findAll()).thenReturn(new ArrayList<>());

    // Act
    List<Satellite> actualFindAllResult = satelliteService.findAll();

    // Assert
    verify(satelliteRepository).findAll();
    assertTrue(actualFindAllResult.isEmpty());
  }

  /**
   * Test {@link SatelliteService#deleteById(Long)}.
   * <p>
   * Method under test: {@link SatelliteService#deleteById(Long)}
   */
  @Test
  @DisplayName("Test deleteById(Long)")
  
  @MethodsUnderTest({"void SatelliteService.deleteById(Long)"})
  void testDeleteById() {
    // Arrange
    doNothing().when(satelliteRepository).deleteById(Mockito.<Long>any());

    // Act
    satelliteService.deleteById(1L);

    // Assert
    verify(satelliteRepository).deleteById(eq(1L));
  }

  /**
   * Test {@link SatelliteService#existsById(Long)}.
   * <ul>
   *   <li>Given {@link SatelliteRepository} {@link CrudRepository#existsById(Object)} return {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteService#existsById(Long)}
   */
  @Test
  @DisplayName("Test existsById(Long); given SatelliteRepository existsById(Object) return 'false'; then return 'false'")
  
  @MethodsUnderTest({"boolean SatelliteService.existsById(Long)"})
  void testExistsById_givenSatelliteRepositoryExistsByIdReturnFalse_thenReturnFalse() {
    // Arrange
    when(satelliteRepository.existsById(Mockito.<Long>any())).thenReturn(false);

    // Act
    boolean actualExistsByIdResult = satelliteService.existsById(1L);

    // Assert
    verify(satelliteRepository).existsById(eq(1L));
    assertFalse(actualExistsByIdResult);
  }

  /**
   * Test {@link SatelliteService#existsById(Long)}.
   * <ul>
   *   <li>Given {@link SatelliteRepository} {@link CrudRepository#existsById(Object)} return {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteService#existsById(Long)}
   */
  @Test
  @DisplayName("Test existsById(Long); given SatelliteRepository existsById(Object) return 'true'; then return 'true'")
  
  @MethodsUnderTest({"boolean SatelliteService.existsById(Long)"})
  void testExistsById_givenSatelliteRepositoryExistsByIdReturnTrue_thenReturnTrue() {
    // Arrange
    when(satelliteRepository.existsById(Mockito.<Long>any())).thenReturn(true);

    // Act
    boolean actualExistsByIdResult = satelliteService.existsById(1L);

    // Assert
    verify(satelliteRepository).existsById(eq(1L));
    assertTrue(actualExistsByIdResult);
  }

  /**
   * Test {@link SatelliteService#findByName(String)}.
   * <p>
   * Method under test: {@link SatelliteService#findByName(String)}
   */
  @Test
  @DisplayName("Test findByName(String)")
  
  @MethodsUnderTest({"Optional SatelliteService.findByName(String)"})
  void testFindByName() {
    // Arrange
    SatelliteModel satelliteModel = new SatelliteModel();
    satelliteModel.setDesignTrajectoryPredictionFactor(10.0d);
    satelliteModel.setDimensions("Dimensions");
    satelliteModel.setDryMass(10.0d);
    satelliteModel.setExpectedLifespan(1);
    satelliteModel.setLaunchMass(10.0d);
    satelliteModel.setManufacturer("Manufacturer");
    satelliteModel.setName("Name");
    satelliteModel.setPowerCapacity(10.0d);
    satelliteModel.setWeight(10.0d);

    Satellite satellite = new Satellite();
    satellite.setGroundStationId(1L);
    satellite.setId(1L);
    satellite.setLaunchDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satellite.setModel(satelliteModel);
    satellite.setName("Name");
    satellite.setNetworkNodeId("42");
    satellite.setSatellite_id(1L);
    satellite.setSensors(new HashSet<>());
    satellite.setStatus(SatelliteStatus.LAUNCHED);
    satellite.setTrajectories(new HashSet<>());
    Optional<Satellite> ofResult = Optional.of(satellite);
    when(satelliteRepository.findByName(Mockito.<String>any())).thenReturn(ofResult);

    // Act
    Optional<Satellite> actualFindByNameResult = satelliteService.findByName("Name");

    // Assert
    verify(satelliteRepository).findByName(eq("Name"));
    assertSame(ofResult, actualFindByNameResult);
  }

  /**
   * Test {@link SatelliteService#findAllByGroundStationId(Long)}.
   * <p>
   * Method under test: {@link SatelliteService#findAllByGroundStationId(Long)}
   */
  @Test
  @DisplayName("Test findAllByGroundStationId(Long)")
  
  @MethodsUnderTest({"List SatelliteService.findAllByGroundStationId(Long)"})
  void testFindAllByGroundStationId() {
    // Arrange
    when(satelliteRepository.findAllByGroundStationId(Mockito.<Long>any())).thenReturn(new ArrayList<>());

    // Act
    List<Satellite> actualFindAllByGroundStationIdResult = satelliteService.findAllByGroundStationId(1L);

    // Assert
    verify(satelliteRepository).findAllByGroundStationId(eq(1L));
    assertTrue(actualFindAllByGroundStationIdResult.isEmpty());
  }
}
