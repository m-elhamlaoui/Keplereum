package com.example.jeeHamlaoui.Sattelites_Service.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteModel;
import com.example.jeeHamlaoui.Sattelites_Service.service.SatelliteModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
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
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SatelliteModelController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SatelliteModelControllerTest {
  @Autowired
  private SatelliteModelController satelliteModelController;

  @MockitoBean
  private SatelliteModelService satelliteModelService;

  /**
   * Test {@link SatelliteModelController#createModel(SatelliteModel)}.
   * <p>
   * Method under test: {@link SatelliteModelController#createModel(SatelliteModel)}
   */
  @Test
  @DisplayName("Test createModel(SatelliteModel)")
  
  @MethodsUnderTest({"org.springframework.http.ResponseEntity SatelliteModelController.createModel(SatelliteModel)"})
  void testCreateModel() throws Exception {
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
    when(satelliteModelService.saveSatelliteModel(Mockito.<SatelliteModel>any())).thenReturn(satelliteModel);

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
    String content = (new ObjectMapper()).writeValueAsString(satelliteModel2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/models")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteModelController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"name\":\"Name\",\"manufacturer\":\"Manufacturer\",\"weight\":10.0,\"dimensions\":\"Dimensions\",\"powerCapacity\""
                    + ":10.0,\"expectedLifespan\":1,\"designTrajectoryPredictionFactor\":10.0,\"launchMass\":10.0,\"dryMass\":10.0,"
                    + "\"satelliteModel_id\":null}"));
  }

  /**
   * Test {@link SatelliteModelController#getAllModels()}.
   * <p>
   * Method under test: {@link SatelliteModelController#getAllModels()}
   */
  @Test
  @DisplayName("Test getAllModels()")
  
  @MethodsUnderTest({"org.springframework.http.ResponseEntity SatelliteModelController.getAllModels()"})
  void testGetAllModels() throws Exception {
    // Arrange
    when(satelliteModelService.getAllSatellitesModel()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/models");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteModelController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link SatelliteModelController#getModelById(Long)}.
   * <p>
   * Method under test: {@link SatelliteModelController#getModelById(Long)}
   */
  @Test
  @DisplayName("Test getModelById(Long)")
  
  @MethodsUnderTest({"org.springframework.http.ResponseEntity SatelliteModelController.getModelById(Long)"})
  void testGetModelById() throws Exception {
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
    Optional<SatelliteModel> ofResult = Optional.of(satelliteModel);
    when(satelliteModelService.getSatelliteModelById(Mockito.<Long>any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/models/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteModelController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"name\":\"Name\",\"manufacturer\":\"Manufacturer\",\"weight\":10.0,\"dimensions\":\"Dimensions\",\"powerCapacity\""
                    + ":10.0,\"expectedLifespan\":1,\"designTrajectoryPredictionFactor\":10.0,\"launchMass\":10.0,\"dryMass\":10.0,"
                    + "\"satelliteModel_id\":null}"));
  }

  /**
   * Test {@link SatelliteModelController#updateModel(Long, SatelliteModel)}.
   * <ul>
   *   <li>Then status {@link StatusResultMatchers#isNotFound()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelController#updateModel(Long, SatelliteModel)}
   */
  @Test
  @DisplayName("Test updateModel(Long, SatelliteModel); then status isNotFound()")
  
  @MethodsUnderTest({
      "org.springframework.http.ResponseEntity SatelliteModelController.updateModel(Long, SatelliteModel)"})
  void testUpdateModel_thenStatusIsNotFound() throws Exception {
    // Arrange
    when(satelliteModelService.updateSatelliteModel(Mockito.<Long>any(), Mockito.<SatelliteModel>any()))
        .thenThrow(new RuntimeException("foo"));

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
    String content = (new ObjectMapper()).writeValueAsString(satelliteModel);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/models/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteModelController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  /**
   * Test {@link SatelliteModelController#updateModel(Long, SatelliteModel)}.
   * <ul>
   *   <li>Then status {@link StatusResultMatchers#isOk()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelController#updateModel(Long, SatelliteModel)}
   */
  @Test
  @DisplayName("Test updateModel(Long, SatelliteModel); then status isOk()")
  
  @MethodsUnderTest({
      "org.springframework.http.ResponseEntity SatelliteModelController.updateModel(Long, SatelliteModel)"})
  void testUpdateModel_thenStatusIsOk() throws Exception {
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
    when(satelliteModelService.updateSatelliteModel(Mockito.<Long>any(), Mockito.<SatelliteModel>any()))
        .thenReturn(satelliteModel);

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
    String content = (new ObjectMapper()).writeValueAsString(satelliteModel2);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/models/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(content);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteModelController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"name\":\"Name\",\"manufacturer\":\"Manufacturer\",\"weight\":10.0,\"dimensions\":\"Dimensions\",\"powerCapacity\""
                    + ":10.0,\"expectedLifespan\":1,\"designTrajectoryPredictionFactor\":10.0,\"launchMass\":10.0,\"dryMass\":10.0,"
                    + "\"satelliteModel_id\":null}"));
  }

  /**
   * Test {@link SatelliteModelController#deleteModel(Long)}.
   * <p>
   * Method under test: {@link SatelliteModelController#deleteModel(Long)}
   */
  @Test
  @DisplayName("Test deleteModel(Long)")
  
  @MethodsUnderTest({"org.springframework.http.ResponseEntity SatelliteModelController.deleteModel(Long)"})
  void testDeleteModel() throws Exception {
    // Arrange
    doNothing().when(satelliteModelService).deleteSatelliteModel(Mockito.<Long>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/models/{id}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(satelliteModelController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
