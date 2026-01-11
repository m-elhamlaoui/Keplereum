package com.example.jeeHamlaoui.Sattelites_Service.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Sattelites_Service.model.Satellite;
import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteModel;
import com.example.jeeHamlaoui.Sattelites_Service.model.Sensor;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SensorRequest;
import com.example.jeeHamlaoui.Sattelites_Service.model.dto.SensorResponse;
import com.example.jeeHamlaoui.Sattelites_Service.model.enumeration.SatelliteStatus;
import com.example.jeeHamlaoui.Sattelites_Service.model.enumeration.SensorActivity;
import com.example.jeeHamlaoui.Sattelites_Service.service.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SensorController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SensorControllerTest {
  @Autowired
  private SensorController sensorController;

  @MockitoBean
  private SensorService sensorService;

  /**
   * Test {@link SensorController#createSensor(SensorRequest)}.
   * <p>
   * Method under test: {@link SensorController#createSensor(SensorRequest)}
   */
  @Test
  @DisplayName("Test createSensor(SensorRequest)")
  
  @MethodsUnderTest({"org.springframework.http.ResponseEntity SensorController.createSensor(SensorRequest)"})
  void testCreateSensor() throws Exception {
    // Arrange
    when(sensorService.createSensor(Mockito.<SensorRequest>any())).thenReturn(new SensorResponse());

    SensorRequest sensorRequest = new SensorRequest();
    sensorRequest.setActivity(SensorActivity.ACTIVE);
    sensorRequest.setMaxHeight(10.0d);
    sensorRequest.setMaxRadius(10.0d);
    sensorRequest.setSatelliteId(1L);
    sensorRequest.setType("Type");
    String content = (new ObjectMapper()).writeValueAsString(sensorRequest);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/sensors")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(sensorController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"id\":null,\"type\":null,\"maxHeight\":null,\"maxRadius\":null,\"activity\":null,\"satelliteId\":null}"));
  }

  /**
   * Test {@link SensorController#getAllSensors()}.
   * <p>
   * Method under test: {@link SensorController#getAllSensors()}
   */
  @Test
  @DisplayName("Test getAllSensors()")
  
  @MethodsUnderTest({"org.springframework.http.ResponseEntity SensorController.getAllSensors()"})
  void testGetAllSensors() throws Exception {
    // Arrange
    when(sensorService.findAll()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/sensors");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(sensorController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link SensorController#getSensorById(Long)}.
   * <p>
   * Method under test: {@link SensorController#getSensorById(Long)}
   */
  @Test
  @DisplayName("Test getSensorById(Long)")
  
  @MethodsUnderTest({"org.springframework.http.ResponseEntity SensorController.getSensorById(Long)"})
  void testGetSensorById() throws Exception {
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

    Sensor sensor = new Sensor();
    sensor.setActivity(SensorActivity.ACTIVE);
    sensor.setId(1L);
    sensor.setMaxHeight(10.0d);
    sensor.setMaxRadius(10.0d);
    sensor.setSatellite(satellite);
    sensor.setType("Type");
    Optional<Sensor> ofResult = Optional.of(sensor);
    when(sensorService.findOne(Mockito.<Long>any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/sensors/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(sensorController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"id\":1,\"type\":\"Type\",\"maxHeight\":10.0,\"maxRadius\":10.0,\"activity\":\"ACTIVE\"}"));
  }

  /**
   * Test {@link SensorController#updateSensor(Long, Sensor)}.
   * <p>
   * Method under test: {@link SensorController#updateSensor(Long, Sensor)}
   */
  @Test
  @DisplayName("Test updateSensor(Long, Sensor)")
  
  @MethodsUnderTest({"org.springframework.http.ResponseEntity SensorController.updateSensor(Long, Sensor)"})
  void testUpdateSensor() throws Exception {
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

    Sensor sensor = new Sensor();
    sensor.setActivity(SensorActivity.ACTIVE);
    sensor.setId(1L);
    sensor.setMaxHeight(10.0d);
    sensor.setMaxRadius(10.0d);
    sensor.setSatellite(satellite);
    sensor.setType("Type");
    when(sensorService.updateSensor(Mockito.<Long>any(), Mockito.<Sensor>any())).thenReturn(sensor);

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

    Sensor sensor2 = new Sensor();
    sensor2.setActivity(SensorActivity.ACTIVE);
    sensor2.setId(1L);
    sensor2.setMaxHeight(10.0d);
    sensor2.setMaxRadius(10.0d);
    sensor2.setSatellite(satellite2);
    sensor2.setType("Type");
    String content = (new ObjectMapper()).writeValueAsString(sensor2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/sensors/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(sensorController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string("{\"id\":1,\"type\":\"Type\",\"maxHeight\":10.0,\"maxRadius\":10.0,\"activity\":\"ACTIVE\"}"));
  }

  /**
   * Test {@link SensorController#deleteSensor(Long)}.
   * <p>
   * Method under test: {@link SensorController#deleteSensor(Long)}
   */
  @Test
  @DisplayName("Test deleteSensor(Long)")
  
  @MethodsUnderTest({"org.springframework.http.ResponseEntity SensorController.deleteSensor(Long)"})
  void testDeleteSensor() throws Exception {
    // Arrange
    doNothing().when(sensorService).delete(Mockito.<Long>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/sensors/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(sensorController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
