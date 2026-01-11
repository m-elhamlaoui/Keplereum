package com.example.jeeHamlaoui.Sattelites_Service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Sattelites_Service.model.util.OrbitalTrajectoryResponse;
import com.example.jeeHamlaoui.Sattelites_Service.repository.SatelliteTrajectoryRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SatelliteTrajectoryService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SatelliteTrajectoryServiceTest {
  @MockitoBean
  private SatelliteService satelliteService;

  @MockitoBean
  private SatelliteTrajectoryRepository satelliteTrajectoryRepository;

  @Autowired
  private SatelliteTrajectoryService satelliteTrajectoryService;

  /**
   * Test {@link SatelliteTrajectoryService#calculateOrbitalTrajectory(double, double, double, double, double, double, double)}.
   * <ul>
   *   <li>When {@code 1.0E-6}.</li>
   *   <li>Then return CartesianValues size is {@code 2004}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SatelliteTrajectoryService#calculateOrbitalTrajectory(double, double, double, double, double, double, double)}
   */
  @Test
  @DisplayName("Test calculateOrbitalTrajectory(double, double, double, double, double, double, double); when '1.0E-6'; then return CartesianValues size is '2004'")
  
  @MethodsUnderTest({
      "OrbitalTrajectoryResponse SatelliteTrajectoryService.calculateOrbitalTrajectory(double, double, double, double, double, double, double)"})
  void testCalculateOrbitalTrajectory_when10e6_thenReturnCartesianValuesSizeIs2004() {
    // Arrange and Act
    OrbitalTrajectoryResponse actualCalculateOrbitalTrajectoryResult = satelliteTrajectoryService
        .calculateOrbitalTrajectory(10.0d, 1.0E-6d, 10.0d, 10.0d, 10.0d, 10.0d, 10.0d);

    // Assert
    List<Double> cartesianValues = actualCalculateOrbitalTrajectoryResult.getCartesianValues();
    assertEquals(2004, cartesianValues.size());
    assertEquals(0.0d, cartesianValues.get(0).doubleValue());
    assertEquals(0.8901365324017179d, actualCalculateOrbitalTrajectoryResult.getPeriodSeconds());
    assertEquals(188.03014461720235d, cartesianValues.get(1).doubleValue());
    assertEquals(188.0301446172024d, cartesianValues.get(2001).doubleValue());
    assertEquals(501, actualCalculateOrbitalTrajectoryResult.getPoints3D().size());
    assertEquals(6.030737921409153d, cartesianValues.get(2003).doubleValue());
    assertEquals(67.88442321591333d, cartesianValues.get(2002).doubleValue());
    assertEquals(67.88442321591339d, cartesianValues.get(2).doubleValue());
  }
}
