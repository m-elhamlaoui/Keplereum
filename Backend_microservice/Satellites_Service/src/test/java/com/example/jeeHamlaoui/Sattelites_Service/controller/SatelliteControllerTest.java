package com.example.jeeHamlaoui.Sattelites_Service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteModel;
import com.example.jeeHamlaoui.Sattelites_Service.model.enumeration.SatelliteStatus;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteRepository;
import com.example.jeeHamlaoui.Sattelites_Service.service.SatelliteService;
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
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SatelliteController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SatelliteControllerTest {
  @Autowired
  private SatelliteController satelliteController;

  @MockitoBean
  private SatelliteService satelliteService;

  /**
   * Test {@link SatelliteController#createOrUpdateSatellite(Satellite)}.
   * <ul>
   *   <li>Then calls {@link CrudRepository#save(Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteController#createOrUpdateSatellite(Satellite)}
   */
  @Test
  @DisplayName("Test createOrUpdateSatellite(Satellite); then calls save(Object)")
  
  @MethodsUnderTest({"ResponseEntity SatelliteController.createOrUpdateSatellite(Satellite)"})
  void testCreateOrUpdateSatellite_thenCallsSave() {
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
    SatelliteRepository satelliteRepository = mock(SatelliteRepository.class);
    when(satelliteRepository.save(Mockito.<Satellite>any())).thenReturn(satellite);
    SatelliteController satelliteController = new SatelliteController(new SatelliteService(satelliteRepository));

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
    ResponseEntity<Satellite> actualCreateOrUpdateSatelliteResult = satelliteController
        .createOrUpdateSatellite(satellite2);

    // Assert
    verify(satelliteRepository).save(isA(Satellite.class));
    HttpStatusCode statusCode = actualCreateOrUpdateSatelliteResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(201, actualCreateOrUpdateSatelliteResult.getStatusCodeValue());
    assertEquals(HttpStatus.CREATED, statusCode);
    assertTrue(actualCreateOrUpdateSatelliteResult.hasBody());
    assertTrue(actualCreateOrUpdateSatelliteResult.getHeaders().isEmpty());
    assertSame(satellite, actualCreateOrUpdateSatelliteResult.getBody());
  }

  /**
   * Test {@link SatelliteController#createOrUpdateSatellite(Satellite)}.
   * <ul>
   *   <li>Then calls {@link SatelliteService#save(Satellite)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteController#createOrUpdateSatellite(Satellite)}
   */
  @Test
  @DisplayName("Test createOrUpdateSatellite(Satellite); then calls save(Satellite)")
  
  @MethodsUnderTest({"ResponseEntity SatelliteController.createOrUpdateSatellite(Satellite)"})
  void testCreateOrUpdateSatellite_thenCallsSave2() {
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
    SatelliteService satelliteService = mock(SatelliteService.class);
    when(satelliteService.save(Mockito.<Satellite>any())).thenReturn(satellite);
    SatelliteController satelliteController = new SatelliteController(satelliteService);

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
    ResponseEntity<Satellite> actualCreateOrUpdateSatelliteResult = satelliteController
        .createOrUpdateSatellite(satellite2);

    // Assert
    verify(satelliteService).save(isA(Satellite.class));
    HttpStatusCode statusCode = actualCreateOrUpdateSatelliteResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(201, actualCreateOrUpdateSatelliteResult.getStatusCodeValue());
    assertEquals(HttpStatus.CREATED, statusCode);
    assertTrue(actualCreateOrUpdateSatelliteResult.hasBody());
    assertTrue(actualCreateOrUpdateSatelliteResult.getHeaders().isEmpty());
    assertSame(satellite, actualCreateOrUpdateSatelliteResult.getBody());
  }

  /**
   * Test {@link SatelliteController#getSatelliteById(Long)}.
   * <ul>
   *   <li>Then status {@link StatusResultMatchers#isNotFound()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteController#getSatelliteById(Long)}
   */
  @Test
  @DisplayName("Test getSatelliteById(Long); then status isNotFound()")
  
  @MethodsUnderTest({"ResponseEntity SatelliteController.getSatelliteById(Long)"})
  void testGetSatelliteById_thenStatusIsNotFound() throws Exception {
    // Arrange
    Optional<Satellite> emptyResult = Optional.empty();
    when(satelliteService.findById(Mockito.<Long>any())).thenReturn(emptyResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/satellites/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  /**
   * Test {@link SatelliteController#getSatelliteById(Long)}.
   * <ul>
   *   <li>Then status {@link StatusResultMatchers#isOk()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteController#getSatelliteById(Long)}
   */
  @Test
  @DisplayName("Test getSatelliteById(Long); then status isOk()")
  
  @MethodsUnderTest({"ResponseEntity SatelliteController.getSatelliteById(Long)"})
  void testGetSatelliteById_thenStatusIsOk() throws Exception {
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
    when(satelliteService.findById(Mockito.<Long>any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/satellites/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"satellite_id\":1,\"name\":\"Name\",\"launchDate\":0.0,\"status\":\"LAUNCHED\",\"sensors\":[],\"trajectories\":[],"
                    + "\"model\":{\"name\":\"Name\",\"manufacturer\":\"Manufacturer\",\"weight\":10.0,\"dimensions\":\"Dimensions\",\"powerCapacity"
                    + "\":10.0,\"expectedLifespan\":1,\"designTrajectoryPredictionFactor\":10.0,\"launchMass\":10.0,\"dryMass\":10.0"
                    + ",\"satelliteModel_id\":null},\"networkNodeId\":\"42\",\"groundStationId\":1,\"id\":1}"));
  }

  /**
   * Test {@link SatelliteController#getAllSatellites()}.
   * <p>
   * Method under test: {@link SatelliteController#getAllSatellites()}
   */
  @Test
  @DisplayName("Test getAllSatellites()")
  
  @MethodsUnderTest({"ResponseEntity SatelliteController.getAllSatellites()"})
  void testGetAllSatellites() throws Exception {
    // Arrange
    when(satelliteService.findAll()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/satellites");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link SatelliteController#deleteSatellite(Long)}.
   * <ul>
   *   <li>Then status {@link StatusResultMatchers#isNoContent()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteController#deleteSatellite(Long)}
   */
  @Test
  @DisplayName("Test deleteSatellite(Long); then status isNoContent()")
  
  @MethodsUnderTest({"ResponseEntity SatelliteController.deleteSatellite(Long)"})
  void testDeleteSatellite_thenStatusIsNoContent() throws Exception {
    // Arrange
    doNothing().when(satelliteService).deleteById(Mockito.<Long>any());
    when(satelliteService.existsById(Mockito.<Long>any())).thenReturn(true);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/satellites/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  /**
   * Test {@link SatelliteController#deleteSatellite(Long)}.
   * <ul>
   *   <li>Then status {@link StatusResultMatchers#isNotFound()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteController#deleteSatellite(Long)}
   */
  @Test
  @DisplayName("Test deleteSatellite(Long); then status isNotFound()")
  
  @MethodsUnderTest({"ResponseEntity SatelliteController.deleteSatellite(Long)"})
  void testDeleteSatellite_thenStatusIsNotFound() throws Exception {
    // Arrange
    doNothing().when(satelliteService).deleteById(Mockito.<Long>any());
    when(satelliteService.existsById(Mockito.<Long>any())).thenReturn(false);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/satellites/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  /**
   * Test {@link SatelliteController#getSatelliteByName(String)}.
   * <ul>
   *   <li>Then status {@link StatusResultMatchers#isNotFound()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteController#getSatelliteByName(String)}
   */
  @Test
  @DisplayName("Test getSatelliteByName(String); then status isNotFound()")
  
  @MethodsUnderTest({"ResponseEntity SatelliteController.getSatelliteByName(String)"})
  void testGetSatelliteByName_thenStatusIsNotFound() throws Exception {
    // Arrange
    Optional<Satellite> emptyResult = Optional.empty();
    when(satelliteService.findByName(Mockito.<String>any())).thenReturn(emptyResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/satellites/name/{name}", "Name");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  /**
   * Test {@link SatelliteController#getSatelliteByName(String)}.
   * <ul>
   *   <li>Then status {@link StatusResultMatchers#isOk()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteController#getSatelliteByName(String)}
   */
  @Test
  @DisplayName("Test getSatelliteByName(String); then status isOk()")
  
  @MethodsUnderTest({"ResponseEntity SatelliteController.getSatelliteByName(String)"})
  void testGetSatelliteByName_thenStatusIsOk() throws Exception {
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
    when(satelliteService.findByName(Mockito.<String>any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/satellites/name/{name}", "Name");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"satellite_id\":1,\"name\":\"Name\",\"launchDate\":0.0,\"status\":\"LAUNCHED\",\"sensors\":[],\"trajectories\":[],"
                    + "\"model\":{\"name\":\"Name\",\"manufacturer\":\"Manufacturer\",\"weight\":10.0,\"dimensions\":\"Dimensions\",\"powerCapacity"
                    + "\":10.0,\"expectedLifespan\":1,\"designTrajectoryPredictionFactor\":10.0,\"launchMass\":10.0,\"dryMass\":10.0"
                    + ",\"satelliteModel_id\":null},\"networkNodeId\":\"42\",\"groundStationId\":1,\"id\":1}"));
  }

  /**
   * Test {@link SatelliteController#findAllByUserId(Long)}.
   * <p>
   * Method under test: {@link SatelliteController#findAllByUserId(Long)}
   */
  @Test
  @DisplayName("Test findAllByUserId(Long)")
  
  @MethodsUnderTest({"ResponseEntity SatelliteController.findAllByUserId(Long)"})
  void testFindAllByUserId() throws Exception {
    // Arrange
    when(satelliteService.findAllByGroundStationId(Mockito.<Long>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/api/v1/satellites/groundStation/{groundStation-id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }
}
