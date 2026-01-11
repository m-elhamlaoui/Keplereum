package com.example.jeeHamlaoui.Sattelites_Service.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Sattelites_Service.model.SatelliteModel;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteModelRepository;
import java.util.ArrayList;
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

@ContextConfiguration(classes = {SatelliteModelService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SatelliteModelServiceTest {

  @MockitoBean
  private SatelliteModelRepository satelliteModelRepository;

  @Autowired
  private SatelliteModelService satelliteModelService;

  /**
   * Test {@link SatelliteModelService#saveSatelliteModel(SatelliteModel)}.
   * <ul>
   *   <li>Then return {@link SatelliteModel#SatelliteModel()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelService#saveSatelliteModel(SatelliteModel)}
   */
  @Test
  @DisplayName("Test saveSatelliteModel(SatelliteModel); then return SatelliteModel()")
  
  @MethodsUnderTest({"SatelliteModel SatelliteModelService.saveSatelliteModel(SatelliteModel)"})
  void testSaveSatelliteModel_thenReturnSatelliteModel() {
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
    when(satelliteModelRepository.save(Mockito.<SatelliteModel>any())).thenReturn(satelliteModel);

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

    // Act
    SatelliteModel actualSaveSatelliteModelResult = satelliteModelService.saveSatelliteModel(satelliteModel2);

    // Assert
    verify(satelliteModelRepository).save(isA(SatelliteModel.class));
    assertSame(satelliteModel, actualSaveSatelliteModelResult);
  }

  /**
   * Test {@link SatelliteModelService#saveSatelliteModel(SatelliteModel)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelService#saveSatelliteModel(SatelliteModel)}
   */
  @Test
  @DisplayName("Test saveSatelliteModel(SatelliteModel); then throw RuntimeException")
  
  @MethodsUnderTest({"SatelliteModel SatelliteModelService.saveSatelliteModel(SatelliteModel)"})
  void testSaveSatelliteModel_thenThrowRuntimeException() {
    // Arrange
    when(satelliteModelRepository.save(Mockito.<SatelliteModel>any())).thenThrow(new RuntimeException("foo"));

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

    // Act and Assert
    assertThrows(RuntimeException.class, () -> satelliteModelService.saveSatelliteModel(satelliteModel));
    verify(satelliteModelRepository).save(isA(SatelliteModel.class));
  }

  /**
   * Test {@link SatelliteModelService#getAllSatellitesModel()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelService#getAllSatellitesModel()}
   */
  @Test
  @DisplayName("Test getAllSatellitesModel(); then return Empty")
  
  @MethodsUnderTest({"List SatelliteModelService.getAllSatellitesModel()"})
  void testGetAllSatellitesModel_thenReturnEmpty() {
    // Arrange
    when(satelliteModelRepository.findAll()).thenReturn(new ArrayList<>());

    // Act
    List<SatelliteModel> actualAllSatellitesModel = satelliteModelService.getAllSatellitesModel();

    // Assert
    verify(satelliteModelRepository).findAll();
    assertTrue(actualAllSatellitesModel.isEmpty());
  }

  /**
   * Test {@link SatelliteModelService#getAllSatellitesModel()}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelService#getAllSatellitesModel()}
   */
  @Test
  @DisplayName("Test getAllSatellitesModel(); then throw RuntimeException")
  
  @MethodsUnderTest({"List SatelliteModelService.getAllSatellitesModel()"})
  void testGetAllSatellitesModel_thenThrowRuntimeException() {
    // Arrange
    when(satelliteModelRepository.findAll()).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> satelliteModelService.getAllSatellitesModel());
    verify(satelliteModelRepository).findAll();
  }

  /**
   * Test {@link SatelliteModelService#getSatelliteModelById(Long)}.
   * <ul>
   *   <li>Then return {@link Optional} with {@link SatelliteModel#SatelliteModel()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelService#getSatelliteModelById(Long)}
   */
  @Test
  @DisplayName("Test getSatelliteModelById(Long); then return Optional with SatelliteModel()")
  
  @MethodsUnderTest({"Optional SatelliteModelService.getSatelliteModelById(Long)"})
  void testGetSatelliteModelById_thenReturnOptionalWithSatelliteModel() {
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
    when(satelliteModelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    Optional<SatelliteModel> actualSatelliteModelById = satelliteModelService.getSatelliteModelById(1L);

    // Assert
    verify(satelliteModelRepository).findById(eq(1L));
    assertSame(ofResult, actualSatelliteModelById);
  }

  /**
   * Test {@link SatelliteModelService#getSatelliteModelById(Long)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelService#getSatelliteModelById(Long)}
   */
  @Test
  @DisplayName("Test getSatelliteModelById(Long); then throw RuntimeException")
  
  @MethodsUnderTest({"Optional SatelliteModelService.getSatelliteModelById(Long)"})
  void testGetSatelliteModelById_thenThrowRuntimeException() {
    // Arrange
    when(satelliteModelRepository.findById(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

    // Act and Assert
    assertThrows(RuntimeException.class, () -> satelliteModelService.getSatelliteModelById(1L));
    verify(satelliteModelRepository).findById(eq(1L));
  }

  /**
   * Test {@link SatelliteModelService#updateSatelliteModel(Long, SatelliteModel)}.
   * <p>
   * Method under test: {@link SatelliteModelService#updateSatelliteModel(Long, SatelliteModel)}
   */
  @Test
  @DisplayName("Test updateSatelliteModel(Long, SatelliteModel)")
  
  @MethodsUnderTest({"SatelliteModel SatelliteModelService.updateSatelliteModel(Long, SatelliteModel)"})
  void testUpdateSatelliteModel() {
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
    when(satelliteModelRepository.save(Mockito.<SatelliteModel>any())).thenThrow(new RuntimeException("foo"));
    when(satelliteModelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    SatelliteModel updatedSatellite = new SatelliteModel();
    updatedSatellite.setDesignTrajectoryPredictionFactor(10.0d);
    updatedSatellite.setDimensions("Dimensions");
    updatedSatellite.setDryMass(10.0d);
    updatedSatellite.setExpectedLifespan(1);
    updatedSatellite.setLaunchMass(10.0d);
    updatedSatellite.setManufacturer("Manufacturer");
    updatedSatellite.setName("Name");
    updatedSatellite.setPowerCapacity(10.0d);
    updatedSatellite.setWeight(10.0d);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> satelliteModelService.updateSatelliteModel(1L, updatedSatellite));
    verify(satelliteModelRepository).findById(eq(1L));
    verify(satelliteModelRepository).save(isA(SatelliteModel.class));
  }

  /**
   * Test {@link SatelliteModelService#updateSatelliteModel(Long, SatelliteModel)}.
   * <ul>
   *   <li>Given {@link SatelliteModelRepository} {@link CrudRepository#findById(Object)} return empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelService#updateSatelliteModel(Long, SatelliteModel)}
   */
  @Test
  @DisplayName("Test updateSatelliteModel(Long, SatelliteModel); given SatelliteModelRepository findById(Object) return empty")
  
  @MethodsUnderTest({"SatelliteModel SatelliteModelService.updateSatelliteModel(Long, SatelliteModel)"})
  void testUpdateSatelliteModel_givenSatelliteModelRepositoryFindByIdReturnEmpty() {
    // Arrange
    Optional<SatelliteModel> emptyResult = Optional.empty();
    when(satelliteModelRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

    SatelliteModel updatedSatellite = new SatelliteModel();
    updatedSatellite.setDesignTrajectoryPredictionFactor(10.0d);
    updatedSatellite.setDimensions("Dimensions");
    updatedSatellite.setDryMass(10.0d);
    updatedSatellite.setExpectedLifespan(1);
    updatedSatellite.setLaunchMass(10.0d);
    updatedSatellite.setManufacturer("Manufacturer");
    updatedSatellite.setName("Name");
    updatedSatellite.setPowerCapacity(10.0d);
    updatedSatellite.setWeight(10.0d);

    // Act and Assert
    assertThrows(RuntimeException.class, () -> satelliteModelService.updateSatelliteModel(1L, updatedSatellite));
    verify(satelliteModelRepository).findById(eq(1L));
  }

  /**
   * Test {@link SatelliteModelService#updateSatelliteModel(Long, SatelliteModel)}.
   * <ul>
   *   <li>Then return {@link SatelliteModel#SatelliteModel()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelService#updateSatelliteModel(Long, SatelliteModel)}
   */
  @Test
  @DisplayName("Test updateSatelliteModel(Long, SatelliteModel); then return SatelliteModel()")
  
  @MethodsUnderTest({"SatelliteModel SatelliteModelService.updateSatelliteModel(Long, SatelliteModel)"})
  void testUpdateSatelliteModel_thenReturnSatelliteModel() {
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
    when(satelliteModelRepository.save(Mockito.<SatelliteModel>any())).thenReturn(satelliteModel2);
    when(satelliteModelRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    SatelliteModel updatedSatellite = new SatelliteModel();
    updatedSatellite.setDesignTrajectoryPredictionFactor(10.0d);
    updatedSatellite.setDimensions("Dimensions");
    updatedSatellite.setDryMass(10.0d);
    updatedSatellite.setExpectedLifespan(1);
    updatedSatellite.setLaunchMass(10.0d);
    updatedSatellite.setManufacturer("Manufacturer");
    updatedSatellite.setName("Name");
    updatedSatellite.setPowerCapacity(10.0d);
    updatedSatellite.setWeight(10.0d);

    // Act
    SatelliteModel actualUpdateSatelliteModelResult = satelliteModelService.updateSatelliteModel(1L, updatedSatellite);

    // Assert
    verify(satelliteModelRepository).findById(eq(1L));
    verify(satelliteModelRepository).save(isA(SatelliteModel.class));
    assertSame(satelliteModel2, actualUpdateSatelliteModelResult);
  }

  /**
   * Test {@link SatelliteModelService#deleteSatelliteModel(Long)}.
   * <ul>
   *   <li>Given {@link SatelliteModelRepository} {@link CrudRepository#deleteById(Object)} does nothing.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelService#deleteSatelliteModel(Long)}
   */
  @Test
  @DisplayName("Test deleteSatelliteModel(Long); given SatelliteModelRepository deleteById(Object) does nothing")
  
  @MethodsUnderTest({"void SatelliteModelService.deleteSatelliteModel(Long)"})
  void testDeleteSatelliteModel_givenSatelliteModelRepositoryDeleteByIdDoesNothing() {
    // Arrange
    doNothing().when(satelliteModelRepository).deleteById(Mockito.<Long>any());

    // Act
    satelliteModelService.deleteSatelliteModel(1L);

    // Assert
    verify(satelliteModelRepository).deleteById(eq(1L));
  }

  /**
   * Test {@link SatelliteModelService#deleteSatelliteModel(Long)}.
   * <ul>
   *   <li>Then throw {@link RuntimeException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteModelService#deleteSatelliteModel(Long)}
   */
  @Test
  @DisplayName("Test deleteSatelliteModel(Long); then throw RuntimeException")
  
  @MethodsUnderTest({"void SatelliteModelService.deleteSatelliteModel(Long)"})
  void testDeleteSatelliteModel_thenThrowRuntimeException() {
    // Arrange
    doThrow(new RuntimeException("foo")).when(satelliteModelRepository).deleteById(Mockito.<Long>any());

    // Act and Assert
    assertThrows(RuntimeException.class, () -> satelliteModelService.deleteSatelliteModel(1L));
    verify(satelliteModelRepository).deleteById(eq(1L));
  }
}
