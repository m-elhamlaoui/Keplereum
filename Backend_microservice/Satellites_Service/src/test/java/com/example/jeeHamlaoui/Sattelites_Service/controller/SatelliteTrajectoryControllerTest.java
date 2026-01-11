package com.example.jeeHamlaoui.Sattelites_Service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteModel;
import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteTrajectory;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SatelliteTrajectoryRequest;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SatelliteTrajectoryResponse;
import com.example.jeeHamlaoui.Sattelites_Service.model.enumeration.SatelliteStatus;
import com.example.jeeHamlaoui.Sattelites_Service.model.util.OrbitalTrajectoryResponse;
import com.example.jeeHamlaoui.Sattelites_Service.model.util.Point3D;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteRepository;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteTrajectoryRepository;
import com.example.jeeHamlaoui.Sattelites_Service.service.SatelliteService;
import com.example.jeeHamlaoui.Sattelites_Service.service.SatelliteTrajectoryService;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SatelliteTrajectoryController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SatelliteTrajectoryControllerTest {
  @Autowired
  private SatelliteTrajectoryController satelliteTrajectoryController;

  @MockitoBean
  private SatelliteTrajectoryService satelliteTrajectoryService;

  /**
   * Test {@link SatelliteTrajectoryController#createTrajectory(SatelliteTrajectoryRequest)}.
   * <ul>
   *   <li>Then StatusCode return {@link HttpStatus}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#createTrajectory(SatelliteTrajectoryRequest)}
   */
  @Test
  @DisplayName("Test createTrajectory(SatelliteTrajectoryRequest); then StatusCode return HttpStatus")
  
  @MethodsUnderTest({"ResponseEntity SatelliteTrajectoryController.createTrajectory(SatelliteTrajectoryRequest)"})
  void testCreateTrajectory_thenStatusCodeReturnHttpStatus() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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

    SatelliteTrajectory satelliteTrajectory = new SatelliteTrajectory();
    satelliteTrajectory.setChangeReason("Just cause");
    satelliteTrajectory.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setId(1L);
    satelliteTrajectory.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory.setOrbitEccentricity(10.0d);
    satelliteTrajectory.setOrbitInclination(10.0d);
    satelliteTrajectory.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory.setOrbitPeriapsis(10.0d);
    satelliteTrajectory.setOrbitRightAscension(10.0d);
    satelliteTrajectory.setSatellite(satellite);
    satelliteTrajectory.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setStatus("Status");

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

    SatelliteTrajectory satelliteTrajectory2 = new SatelliteTrajectory();
    satelliteTrajectory2.setChangeReason("Just cause");
    satelliteTrajectory2.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory2.setId(1L);
    satelliteTrajectory2.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory2.setOrbitEccentricity(10.0d);
    satelliteTrajectory2.setOrbitInclination(10.0d);
    satelliteTrajectory2.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory2.setOrbitPeriapsis(10.0d);
    satelliteTrajectory2.setOrbitRightAscension(10.0d);
    satelliteTrajectory2.setSatellite(satellite2);
    satelliteTrajectory2.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory2.setStatus("Status");
    SatelliteTrajectoryRepository satelliteTrajectoryRepository = mock(SatelliteTrajectoryRepository.class);
    when(satelliteTrajectoryRepository.findTopBySatelliteOrderByEndTimeDesc(Mockito.<Satellite>any()))
        .thenReturn(satelliteTrajectory);
    when(satelliteTrajectoryRepository.save(Mockito.<SatelliteTrajectory>any())).thenReturn(satelliteTrajectory2);

    SatelliteModel satelliteModel3 = new SatelliteModel();
    satelliteModel3.setDesignTrajectoryPredictionFactor(10.0d);
    satelliteModel3.setDimensions("Dimensions");
    satelliteModel3.setDryMass(10.0d);
    satelliteModel3.setExpectedLifespan(1);
    satelliteModel3.setLaunchMass(10.0d);
    satelliteModel3.setManufacturer("Manufacturer");
    satelliteModel3.setName("Name");
    satelliteModel3.setPowerCapacity(10.0d);
    satelliteModel3.setWeight(10.0d);

    Satellite satellite3 = new Satellite();
    satellite3.setGroundStationId(1L);
    satellite3.setId(1L);
    satellite3.setLaunchDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satellite3.setModel(satelliteModel3);
    satellite3.setName("Name");
    satellite3.setNetworkNodeId("42");
    satellite3.setSatellite_id(1L);
    satellite3.setSensors(new HashSet<>());
    satellite3.setStatus(SatelliteStatus.LAUNCHED);
    satellite3.setTrajectories(new HashSet<>());
    Optional<Satellite> ofResult = Optional.of(satellite3);
    SatelliteRepository satelliteRepository = mock(SatelliteRepository.class);
    when(satelliteRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    SatelliteTrajectoryController satelliteTrajectoryController = new SatelliteTrajectoryController(
        new SatelliteTrajectoryService(satelliteTrajectoryRepository, new SatelliteService(satelliteRepository)));

    // Act
    ResponseEntity<SatelliteTrajectoryResponse> actualCreateTrajectoryResult = satelliteTrajectoryController
        .createTrajectory(new SatelliteTrajectoryRequest());

    // Assert
    verify(satelliteTrajectoryRepository).findTopBySatelliteOrderByEndTimeDesc(isA(Satellite.class));
    verify(satelliteRepository).findById(isNull());
    verify(satelliteTrajectoryRepository, atLeast(1)).save(Mockito.<SatelliteTrajectory>any());
    HttpStatusCode statusCode = actualCreateTrajectoryResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    SatelliteTrajectoryResponse body = actualCreateTrajectoryResult.getBody();
    assertEquals("Just cause", body.getChangeReason());
    assertEquals("Status", body.getStatus());
    assertEquals(10.0d, body.getOrbitArgumentOfPerigee().doubleValue());
    assertEquals(10.0d, body.getOrbitEccentricity().doubleValue());
    assertEquals(10.0d, body.getOrbitInclination().doubleValue());
    assertEquals(10.0d, body.getOrbitMeanAnomaly().doubleValue());
    assertEquals(10.0d, body.getOrbitPeriapsis().doubleValue());
    assertEquals(10.0d, body.getOrbitRightAscension().doubleValue());
    assertEquals(1L, body.getId().longValue());
    assertEquals(1L, body.getSatelliteId().longValue());
    assertEquals(200, actualCreateTrajectoryResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualCreateTrajectoryResult.hasBody());
    assertTrue(actualCreateTrajectoryResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link SatelliteTrajectoryController#getAllTrajectoriesById(Long)}.
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#getAllTrajectoriesById(Long)}
   */
  @Test
  @DisplayName("Test getAllTrajectoriesById(Long)")
  
  @MethodsUnderTest({"ResponseEntity SatelliteTrajectoryController.getAllTrajectoriesById(Long)"})
  void testGetAllTrajectoriesById() throws Exception {
    // Arrange
    when(satelliteTrajectoryService.getAllTrajectories(Mockito.<Long>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/trajectories");
    MockHttpServletRequestBuilder requestBuilder = getResult.param("satellite_id", String.valueOf(1L));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteTrajectoryController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link SatelliteTrajectoryController#getCurrentTrajectoryById(Long)}.
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#getCurrentTrajectoryById(Long)}
   */
  @Test
  @DisplayName("Test getCurrentTrajectoryById(Long)")
  
  @MethodsUnderTest({"ResponseEntity SatelliteTrajectoryController.getCurrentTrajectoryById(Long)"})
  void testGetCurrentTrajectoryById() throws Exception {
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

    SatelliteTrajectory satelliteTrajectory = new SatelliteTrajectory();
    satelliteTrajectory.setChangeReason("Just cause");
    satelliteTrajectory.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setId(1L);
    satelliteTrajectory.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory.setOrbitEccentricity(10.0d);
    satelliteTrajectory.setOrbitInclination(10.0d);
    satelliteTrajectory.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory.setOrbitPeriapsis(10.0d);
    satelliteTrajectory.setOrbitRightAscension(10.0d);
    satelliteTrajectory.setSatellite(satellite);
    satelliteTrajectory.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setStatus("Status");
    when(satelliteTrajectoryService.getCurrentTrajectoryById(Mockito.<Long>any())).thenReturn(satelliteTrajectory);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/trajectories/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteTrajectoryController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"id\":1,\"status\":\"Status\",\"startTime\":0.0,\"endTime\":0.0,\"orbitEccentricity\":10.0,\"orbitInclination\""
                    + ":10.0,\"orbitRightAscension\":10.0,\"orbitArgumentOfPerigee\":10.0,\"orbitMeanAnomaly\":10.0,\"orbitPeriapsis"
                    + "\":10.0,\"changeReason\":\"Just cause\"}"));
  }

  /**
   * Test {@link SatelliteTrajectoryController#updateTrajectory(Long, SatelliteTrajectory)}.
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#updateTrajectory(Long, SatelliteTrajectory)}
   */
  @Test
  @DisplayName("Test updateTrajectory(Long, SatelliteTrajectory)")
  
  @MethodsUnderTest({"ResponseEntity SatelliteTrajectoryController.updateTrajectory(Long, SatelliteTrajectory)"})
  void testUpdateTrajectory() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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

    SatelliteTrajectory satelliteTrajectory = new SatelliteTrajectory();
    satelliteTrajectory.setChangeReason("Just cause");
    satelliteTrajectory.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setId(1L);
    satelliteTrajectory.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory.setOrbitEccentricity(10.0d);
    satelliteTrajectory.setOrbitInclination(10.0d);
    satelliteTrajectory.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory.setOrbitPeriapsis(10.0d);
    satelliteTrajectory.setOrbitRightAscension(10.0d);
    satelliteTrajectory.setSatellite(satellite);
    satelliteTrajectory.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setStatus("Status");
    Optional<SatelliteTrajectory> ofResult = Optional.of(satelliteTrajectory);

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

    SatelliteTrajectory satelliteTrajectory2 = new SatelliteTrajectory();
    satelliteTrajectory2.setChangeReason("Just cause");
    satelliteTrajectory2.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory2.setId(1L);
    satelliteTrajectory2.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory2.setOrbitEccentricity(10.0d);
    satelliteTrajectory2.setOrbitInclination(10.0d);
    satelliteTrajectory2.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory2.setOrbitPeriapsis(10.0d);
    satelliteTrajectory2.setOrbitRightAscension(10.0d);
    satelliteTrajectory2.setSatellite(satellite2);
    satelliteTrajectory2.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory2.setStatus("Status");
    SatelliteTrajectoryRepository satelliteTrajectoryRepository = mock(SatelliteTrajectoryRepository.class);
    when(satelliteTrajectoryRepository.save(Mockito.<SatelliteTrajectory>any())).thenReturn(satelliteTrajectory2);
    when(satelliteTrajectoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    SatelliteTrajectoryController satelliteTrajectoryController = new SatelliteTrajectoryController(
        new SatelliteTrajectoryService(satelliteTrajectoryRepository,
            new SatelliteService(mock(SatelliteRepository.class))));

    SatelliteModel satelliteModel3 = new SatelliteModel();
    satelliteModel3.setDesignTrajectoryPredictionFactor(10.0d);
    satelliteModel3.setDimensions("Dimensions");
    satelliteModel3.setDryMass(10.0d);
    satelliteModel3.setExpectedLifespan(1);
    satelliteModel3.setLaunchMass(10.0d);
    satelliteModel3.setManufacturer("Manufacturer");
    satelliteModel3.setName("Name");
    satelliteModel3.setPowerCapacity(10.0d);
    satelliteModel3.setWeight(10.0d);

    Satellite satellite3 = new Satellite();
    satellite3.setGroundStationId(1L);
    satellite3.setId(1L);
    satellite3.setLaunchDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satellite3.setModel(satelliteModel3);
    satellite3.setName("Name");
    satellite3.setNetworkNodeId("42");
    satellite3.setSatellite_id(1L);
    satellite3.setSensors(new HashSet<>());
    satellite3.setStatus(SatelliteStatus.LAUNCHED);
    satellite3.setTrajectories(new HashSet<>());

    SatelliteTrajectory satelliteTrajectory3 = new SatelliteTrajectory();
    satelliteTrajectory3.setChangeReason("Just cause");
    satelliteTrajectory3.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory3.setId(1L);
    satelliteTrajectory3.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory3.setOrbitEccentricity(10.0d);
    satelliteTrajectory3.setOrbitInclination(10.0d);
    satelliteTrajectory3.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory3.setOrbitPeriapsis(10.0d);
    satelliteTrajectory3.setOrbitRightAscension(10.0d);
    satelliteTrajectory3.setSatellite(satellite3);
    satelliteTrajectory3.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory3.setStatus("Status");

    // Act
    ResponseEntity<SatelliteTrajectory> actualUpdateTrajectoryResult = satelliteTrajectoryController
        .updateTrajectory(1L, satelliteTrajectory3);

    // Assert
    verify(satelliteTrajectoryRepository).findById(eq(1L));
    verify(satelliteTrajectoryRepository).save(isA(SatelliteTrajectory.class));
    HttpStatusCode statusCode = actualUpdateTrajectoryResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualUpdateTrajectoryResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualUpdateTrajectoryResult.hasBody());
    assertSame(satelliteTrajectory2, actualUpdateTrajectoryResult.getBody());
  }

  /**
   * Test {@link SatelliteTrajectoryController#updateTrajectory(Long, SatelliteTrajectory)}.
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#updateTrajectory(Long, SatelliteTrajectory)}
   */
  @Test
  @DisplayName("Test updateTrajectory(Long, SatelliteTrajectory)")
  
  @MethodsUnderTest({"ResponseEntity SatelliteTrajectoryController.updateTrajectory(Long, SatelliteTrajectory)"})
  void testUpdateTrajectory2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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

    SatelliteTrajectory satelliteTrajectory = new SatelliteTrajectory();
    satelliteTrajectory.setChangeReason("Just cause");
    satelliteTrajectory.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setId(1L);
    satelliteTrajectory.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory.setOrbitEccentricity(10.0d);
    satelliteTrajectory.setOrbitInclination(10.0d);
    satelliteTrajectory.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory.setOrbitPeriapsis(10.0d);
    satelliteTrajectory.setOrbitRightAscension(10.0d);
    satelliteTrajectory.setSatellite(satellite);
    satelliteTrajectory.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setStatus("Status");
    Optional<SatelliteTrajectory> ofResult = Optional.of(satelliteTrajectory);
    SatelliteTrajectoryRepository satelliteTrajectoryRepository = mock(SatelliteTrajectoryRepository.class);
    when(satelliteTrajectoryRepository.save(Mockito.<SatelliteTrajectory>any())).thenThrow(new RuntimeException("foo"));
    when(satelliteTrajectoryRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
    SatelliteTrajectoryController satelliteTrajectoryController = new SatelliteTrajectoryController(
        new SatelliteTrajectoryService(satelliteTrajectoryRepository,
            new SatelliteService(mock(SatelliteRepository.class))));

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

    SatelliteTrajectory satelliteTrajectory2 = new SatelliteTrajectory();
    satelliteTrajectory2.setChangeReason("Just cause");
    satelliteTrajectory2.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory2.setId(1L);
    satelliteTrajectory2.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory2.setOrbitEccentricity(10.0d);
    satelliteTrajectory2.setOrbitInclination(10.0d);
    satelliteTrajectory2.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory2.setOrbitPeriapsis(10.0d);
    satelliteTrajectory2.setOrbitRightAscension(10.0d);
    satelliteTrajectory2.setSatellite(satellite2);
    satelliteTrajectory2.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory2.setStatus("Status");

    // Act
    ResponseEntity<SatelliteTrajectory> actualUpdateTrajectoryResult = satelliteTrajectoryController
        .updateTrajectory(1L, satelliteTrajectory2);

    // Assert
    verify(satelliteTrajectoryRepository).findById(eq(1L));
    verify(satelliteTrajectoryRepository).save(isA(SatelliteTrajectory.class));
    HttpStatusCode statusCode = actualUpdateTrajectoryResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualUpdateTrajectoryResult.getBody());
    assertEquals(404, actualUpdateTrajectoryResult.getStatusCodeValue());
    assertEquals(HttpStatus.NOT_FOUND, statusCode);
    assertFalse(actualUpdateTrajectoryResult.hasBody());
  }

  /**
   * Test {@link SatelliteTrajectoryController#updateTrajectory(Long, SatelliteTrajectory)}.
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#updateTrajectory(Long, SatelliteTrajectory)}
   */
  @Test
  @DisplayName("Test updateTrajectory(Long, SatelliteTrajectory)")
  
  @MethodsUnderTest({"ResponseEntity SatelliteTrajectoryController.updateTrajectory(Long, SatelliteTrajectory)"})
  void testUpdateTrajectory3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SatelliteTrajectoryController satelliteTrajectoryController = new SatelliteTrajectoryController(null);

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

    SatelliteTrajectory satelliteTrajectory = new SatelliteTrajectory();
    satelliteTrajectory.setChangeReason("Just cause");
    satelliteTrajectory.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setId(1L);
    satelliteTrajectory.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory.setOrbitEccentricity(10.0d);
    satelliteTrajectory.setOrbitInclination(10.0d);
    satelliteTrajectory.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory.setOrbitPeriapsis(10.0d);
    satelliteTrajectory.setOrbitRightAscension(10.0d);
    satelliteTrajectory.setSatellite(satellite);
    satelliteTrajectory.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setStatus("Status");

    // Act
    ResponseEntity<SatelliteTrajectory> actualUpdateTrajectoryResult = satelliteTrajectoryController
        .updateTrajectory(1L, satelliteTrajectory);

    // Assert
    HttpStatusCode statusCode = actualUpdateTrajectoryResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualUpdateTrajectoryResult.getBody());
    assertEquals(404, actualUpdateTrajectoryResult.getStatusCodeValue());
    assertEquals(HttpStatus.NOT_FOUND, statusCode);
    assertFalse(actualUpdateTrajectoryResult.hasBody());
  }

  /**
   * Test {@link SatelliteTrajectoryController#updateTrajectory(Long, SatelliteTrajectory)}.
   * <ul>
   *   <li>Given {@link SatelliteTrajectoryRepository} {@link CrudRepository#findById(Object)} return empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#updateTrajectory(Long, SatelliteTrajectory)}
   */
  @Test
  @DisplayName("Test updateTrajectory(Long, SatelliteTrajectory); given SatelliteTrajectoryRepository findById(Object) return empty")
  
  @MethodsUnderTest({"ResponseEntity SatelliteTrajectoryController.updateTrajectory(Long, SatelliteTrajectory)"})
  void testUpdateTrajectory_givenSatelliteTrajectoryRepositoryFindByIdReturnEmpty() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SatelliteTrajectoryRepository satelliteTrajectoryRepository = mock(SatelliteTrajectoryRepository.class);
    Optional<SatelliteTrajectory> emptyResult = Optional.empty();
    when(satelliteTrajectoryRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
    SatelliteTrajectoryController satelliteTrajectoryController = new SatelliteTrajectoryController(
        new SatelliteTrajectoryService(satelliteTrajectoryRepository,
            new SatelliteService(mock(SatelliteRepository.class))));

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

    SatelliteTrajectory satelliteTrajectory = new SatelliteTrajectory();
    satelliteTrajectory.setChangeReason("Just cause");
    satelliteTrajectory.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setId(1L);
    satelliteTrajectory.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory.setOrbitEccentricity(10.0d);
    satelliteTrajectory.setOrbitInclination(10.0d);
    satelliteTrajectory.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory.setOrbitPeriapsis(10.0d);
    satelliteTrajectory.setOrbitRightAscension(10.0d);
    satelliteTrajectory.setSatellite(satellite);
    satelliteTrajectory.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setStatus("Status");

    // Act
    ResponseEntity<SatelliteTrajectory> actualUpdateTrajectoryResult = satelliteTrajectoryController
        .updateTrajectory(1L, satelliteTrajectory);

    // Assert
    verify(satelliteTrajectoryRepository).findById(eq(1L));
    HttpStatusCode statusCode = actualUpdateTrajectoryResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualUpdateTrajectoryResult.getBody());
    assertEquals(404, actualUpdateTrajectoryResult.getStatusCodeValue());
    assertEquals(HttpStatus.NOT_FOUND, statusCode);
    assertFalse(actualUpdateTrajectoryResult.hasBody());
  }

  /**
   * Test {@link SatelliteTrajectoryController#updateTrajectory(Long, SatelliteTrajectory)}.
   * <ul>
   *   <li>Then calls {@link SatelliteTrajectoryService#updateTrajectory(Long, SatelliteTrajectory)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#updateTrajectory(Long, SatelliteTrajectory)}
   */
  @Test
  @DisplayName("Test updateTrajectory(Long, SatelliteTrajectory); then calls updateTrajectory(Long, SatelliteTrajectory)")
  
  @MethodsUnderTest({"ResponseEntity SatelliteTrajectoryController.updateTrajectory(Long, SatelliteTrajectory)"})
  void testUpdateTrajectory_thenCallsUpdateTrajectory() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

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

    SatelliteTrajectory satelliteTrajectory = new SatelliteTrajectory();
    satelliteTrajectory.setChangeReason("Just cause");
    satelliteTrajectory.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setId(1L);
    satelliteTrajectory.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory.setOrbitEccentricity(10.0d);
    satelliteTrajectory.setOrbitInclination(10.0d);
    satelliteTrajectory.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory.setOrbitPeriapsis(10.0d);
    satelliteTrajectory.setOrbitRightAscension(10.0d);
    satelliteTrajectory.setSatellite(satellite);
    satelliteTrajectory.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory.setStatus("Status");
    SatelliteTrajectoryService satelliteTrajectoryService = mock(SatelliteTrajectoryService.class);
    when(satelliteTrajectoryService.updateTrajectory(Mockito.<Long>any(), Mockito.<SatelliteTrajectory>any()))
        .thenReturn(satelliteTrajectory);
    SatelliteTrajectoryController satelliteTrajectoryController = new SatelliteTrajectoryController(
        satelliteTrajectoryService);

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

    SatelliteTrajectory satelliteTrajectory2 = new SatelliteTrajectory();
    satelliteTrajectory2.setChangeReason("Just cause");
    satelliteTrajectory2.setEndTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory2.setId(1L);
    satelliteTrajectory2.setOrbitArgumentOfPerigee(10.0d);
    satelliteTrajectory2.setOrbitEccentricity(10.0d);
    satelliteTrajectory2.setOrbitInclination(10.0d);
    satelliteTrajectory2.setOrbitMeanAnomaly(10.0d);
    satelliteTrajectory2.setOrbitPeriapsis(10.0d);
    satelliteTrajectory2.setOrbitRightAscension(10.0d);
    satelliteTrajectory2.setSatellite(satellite2);
    satelliteTrajectory2.setStartTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    satelliteTrajectory2.setStatus("Status");

    // Act
    ResponseEntity<SatelliteTrajectory> actualUpdateTrajectoryResult = satelliteTrajectoryController
        .updateTrajectory(1L, satelliteTrajectory2);

    // Assert
    verify(satelliteTrajectoryService).updateTrajectory(eq(1L), isA(SatelliteTrajectory.class));
    HttpStatusCode statusCode = actualUpdateTrajectoryResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualUpdateTrajectoryResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualUpdateTrajectoryResult.hasBody());
    assertSame(satelliteTrajectory, actualUpdateTrajectoryResult.getBody());
  }

  /**
   * Test {@link SatelliteTrajectoryController#deleteTrajectory(Long)}.
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#deleteTrajectory(Long)}
   */
  @Test
  @DisplayName("Test deleteTrajectory(Long)")
  
  @MethodsUnderTest({"ResponseEntity SatelliteTrajectoryController.deleteTrajectory(Long)"})
  void testDeleteTrajectory() throws Exception {
    // Arrange
    doNothing().when(satelliteTrajectoryService).deleteTrajectory(Mockito.<Long>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/trajectories/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteTrajectoryController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  /**
   * Test {@link SatelliteTrajectoryController#calculateOrbitalTrajectory(Long, double, double)} with {@code id}, {@code earthRadius}, {@code scaleFactor}.
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#calculateOrbitalTrajectory(Long, double, double)}
   */
  @Test
  @DisplayName("Test calculateOrbitalTrajectory(Long, double, double) with 'id', 'earthRadius', 'scaleFactor'")
  
  @MethodsUnderTest({
      "OrbitalTrajectoryResponse SatelliteTrajectoryController.calculateOrbitalTrajectory(Long, double, double)"})
  void testCalculateOrbitalTrajectoryWithIdEarthRadiusScaleFactor() throws Exception {
    // Arrange
    ArrayList<Double> cartesianValues = new ArrayList<>();
    when(satelliteTrajectoryService.calculateOrbitalTrajectoryBySatelliteId(Mockito.<Long>any(), anyDouble(),
        anyDouble())).thenReturn(new OrbitalTrajectoryResponse(cartesianValues, new ArrayList<>(), 10.0d));
    MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1/trajectories/calculate-orbit/{id}",
        1L);
    MockHttpServletRequestBuilder paramResult = postResult.param("earthRadius", String.valueOf(10.0d));
    MockHttpServletRequestBuilder requestBuilder = paramResult.param("scaleFactor", String.valueOf(10.0d));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteTrajectoryController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(
            MockMvcResultMatchers.content().string("{\"cartesianValues\":[],\"points3D\":[],\"periodSeconds\":10.0}"));
  }

  /**
   * Test {@link SatelliteTrajectoryController#calculateOrbitalTrajectory(Long, double, double)} with {@code id}, {@code earthRadius}, {@code scaleFactor}.
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#calculateOrbitalTrajectory(Long, double, double)}
   */
  @Test
  @DisplayName("Test calculateOrbitalTrajectory(Long, double, double) with 'id', 'earthRadius', 'scaleFactor'")
  
  @MethodsUnderTest({
      "OrbitalTrajectoryResponse SatelliteTrajectoryController.calculateOrbitalTrajectory(Long, double, double)"})
  void testCalculateOrbitalTrajectoryWithIdEarthRadiusScaleFactor2() throws Exception {
    // Arrange
    ArrayList<Point3D> points3D = new ArrayList<>();
    points3D.add(new Point3D(2.0d, 3.0d, 10.0d));
    when(satelliteTrajectoryService.calculateOrbitalTrajectoryBySatelliteId(Mockito.<Long>any(), anyDouble(),
        anyDouble())).thenReturn(new OrbitalTrajectoryResponse(new ArrayList<>(), points3D, 10.0d));
    MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1/trajectories/calculate-orbit/{id}",
        1L);
    MockHttpServletRequestBuilder paramResult = postResult.param("earthRadius", String.valueOf(10.0d));
    MockHttpServletRequestBuilder requestBuilder = paramResult.param("scaleFactor", String.valueOf(10.0d));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteTrajectoryController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"cartesianValues\":[],\"points3D\":[{\"x\":2.0,\"y\":3.0,\"z\":10.0}],\"periodSeconds\":10.0}"));
  }

  /**
   * Test {@link SatelliteTrajectoryController#calculateOrbitalTrajectory(double, double, double, double, double, double, double)} with {@code perigeeAltitude}, {@code eccentricity}, {@code inclination}, {@code longitudeOfAscendingNode}, {@code argumentOfPeriapsis}, {@code earthRadius}, {@code scaleFactor}.
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#calculateOrbitalTrajectory(double, double, double, double, double, double, double)}
   */
  @Test
  @DisplayName("Test calculateOrbitalTrajectory(double, double, double, double, double, double, double) with 'perigeeAltitude', 'eccentricity', 'inclination', 'longitudeOfAscendingNode', 'argumentOfPeriapsis', 'earthRadius', 'scaleFactor'")
  
  @MethodsUnderTest({
      "OrbitalTrajectoryResponse SatelliteTrajectoryController.calculateOrbitalTrajectory(double, double, double, double, double, double, double)"})
  void testCalculateOrbitalTrajectoryWithPerigeeAltitudeEccentricityInclinationLongitudeOfAscendingNodeArgumentOfPeriapsisEarthRadiusScaleFactor()
      throws Exception {
    // Arrange
    ArrayList<Double> cartesianValues = new ArrayList<>();
    when(satelliteTrajectoryService.calculateOrbitalTrajectory(anyDouble(), anyDouble(), anyDouble(), anyDouble(),
        anyDouble(), anyDouble(), anyDouble()))
        .thenReturn(new OrbitalTrajectoryResponse(cartesianValues, new ArrayList<>(), 10.0d));
    MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1/trajectories/calculate-orbit");
    MockHttpServletRequestBuilder paramResult = postResult.param("argumentOfPeriapsis", String.valueOf(10.0d));
    MockHttpServletRequestBuilder paramResult2 = paramResult.param("earthRadius", String.valueOf(10.0d));
    MockHttpServletRequestBuilder paramResult3 = paramResult2.param("eccentricity", String.valueOf(10.0d));
    MockHttpServletRequestBuilder paramResult4 = paramResult3.param("inclination", String.valueOf(10.0d));
    MockHttpServletRequestBuilder paramResult5 = paramResult4.param("longitudeOfAscendingNode", String.valueOf(10.0d));
    MockHttpServletRequestBuilder paramResult6 = paramResult5.param("perigeeAltitude", String.valueOf(10.0d));
    MockHttpServletRequestBuilder requestBuilder = paramResult6.param("scaleFactor", String.valueOf(10.0d));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteTrajectoryController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(
            MockMvcResultMatchers.content().string("{\"cartesianValues\":[],\"points3D\":[],\"periodSeconds\":10.0}"));
  }

  /**
   * Test {@link SatelliteTrajectoryController#calculateOrbitalTrajectory(double, double, double, double, double, double, double)} with {@code perigeeAltitude}, {@code eccentricity}, {@code inclination}, {@code longitudeOfAscendingNode}, {@code argumentOfPeriapsis}, {@code earthRadius}, {@code scaleFactor}.
   * <p>
   * Method under test: {@link SatelliteTrajectoryController#calculateOrbitalTrajectory(double, double, double, double, double, double, double)}
   */
  @Test
  @DisplayName("Test calculateOrbitalTrajectory(double, double, double, double, double, double, double) with 'perigeeAltitude', 'eccentricity', 'inclination', 'longitudeOfAscendingNode', 'argumentOfPeriapsis', 'earthRadius', 'scaleFactor'")
  
  @MethodsUnderTest({
      "OrbitalTrajectoryResponse SatelliteTrajectoryController.calculateOrbitalTrajectory(double, double, double, double, double, double, double)"})
  void testCalculateOrbitalTrajectoryWithPerigeeAltitudeEccentricityInclinationLongitudeOfAscendingNodeArgumentOfPeriapsisEarthRadiusScaleFactor2()
      throws Exception {
    // Arrange
    ArrayList<Point3D> points3D = new ArrayList<>();
    points3D.add(new Point3D(2.0d, 3.0d, 10.0d));
    when(satelliteTrajectoryService.calculateOrbitalTrajectory(anyDouble(), anyDouble(), anyDouble(), anyDouble(),
        anyDouble(), anyDouble(), anyDouble()))
        .thenReturn(new OrbitalTrajectoryResponse(new ArrayList<>(), points3D, 10.0d));
    MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/api/v1/trajectories/calculate-orbit");
    MockHttpServletRequestBuilder paramResult = postResult.param("argumentOfPeriapsis", String.valueOf(10.0d));
    MockHttpServletRequestBuilder paramResult2 = paramResult.param("earthRadius", String.valueOf(10.0d));
    MockHttpServletRequestBuilder paramResult3 = paramResult2.param("eccentricity", String.valueOf(10.0d));
    MockHttpServletRequestBuilder paramResult4 = paramResult3.param("inclination", String.valueOf(10.0d));
    MockHttpServletRequestBuilder paramResult5 = paramResult4.param("longitudeOfAscendingNode", String.valueOf(10.0d));
    MockHttpServletRequestBuilder paramResult6 = paramResult5.param("perigeeAltitude", String.valueOf(10.0d));
    MockHttpServletRequestBuilder requestBuilder = paramResult6.param("scaleFactor", String.valueOf(10.0d));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteTrajectoryController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"cartesianValues\":[],\"points3D\":[{\"x\":2.0,\"y\":3.0,\"z\":10.0}],\"periodSeconds\":10.0}"));
  }
}
