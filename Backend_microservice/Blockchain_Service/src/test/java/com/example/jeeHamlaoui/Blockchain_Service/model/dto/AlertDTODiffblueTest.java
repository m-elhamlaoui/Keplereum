package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.AlertType;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AlertDTODiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AlertDTO#AlertDTO()}
   *   <li>{@link AlertDTO#setAlertType(AlertType)}
   *   <li>{@link AlertDTO#setLatitude(Double)}
   *   <li>{@link AlertDTO#setLongitude(Double)}
   *   <li>{@link AlertDTO#getAlertType()}
   *   <li>{@link AlertDTO#getLatitude()}
   *   <li>{@link AlertDTO#getLongitude()}
   *   <li>{@link AlertDTO#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AlertDTO.<init>()", "void AlertDTO.<init>(AlertType, Double, Double)",
      "AlertType AlertDTO.getAlertType()", "Double AlertDTO.getLatitude()", "Double AlertDTO.getLongitude()",
      "TransactionType AlertDTO.getType()", "void AlertDTO.setAlertType(AlertType)",
      "void AlertDTO.setLatitude(Double)", "void AlertDTO.setLongitude(Double)"})
  void testGettersAndSetters() {
    // Arrange and Act
    AlertDTO actualAlertDTO = new AlertDTO();
    actualAlertDTO.setAlertType(AlertType.TSUNAMI);
    actualAlertDTO.setLatitude(10.0d);
    actualAlertDTO.setLongitude(10.0d);
    AlertType actualAlertType = actualAlertDTO.getAlertType();
    Double actualLatitude = actualAlertDTO.getLatitude();
    Double actualLongitude = actualAlertDTO.getLongitude();
    TransactionType actualType = actualAlertDTO.getType();

    // Assert
    assertEquals(10.0d, actualLatitude.doubleValue());
    assertEquals(10.0d, actualLongitude.doubleValue());
    assertEquals(AlertType.TSUNAMI, actualAlertType);
    assertEquals(TransactionType.ALERT, actualType);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code TSUNAMI}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AlertDTO#AlertDTO(AlertType, Double, Double)}
   *   <li>{@link AlertDTO#setAlertType(AlertType)}
   *   <li>{@link AlertDTO#setLatitude(Double)}
   *   <li>{@link AlertDTO#setLongitude(Double)}
   *   <li>{@link AlertDTO#getAlertType()}
   *   <li>{@link AlertDTO#getLatitude()}
   *   <li>{@link AlertDTO#getLongitude()}
   *   <li>{@link AlertDTO#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'TSUNAMI'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AlertDTO.<init>()", "void AlertDTO.<init>(AlertType, Double, Double)",
      "AlertType AlertDTO.getAlertType()", "Double AlertDTO.getLatitude()", "Double AlertDTO.getLongitude()",
      "TransactionType AlertDTO.getType()", "void AlertDTO.setAlertType(AlertType)",
      "void AlertDTO.setLatitude(Double)", "void AlertDTO.setLongitude(Double)"})
  void testGettersAndSetters_whenTsunami() {
    // Arrange and Act
    AlertDTO actualAlertDTO = new AlertDTO(AlertType.TSUNAMI, 10.0d, 10.0d);
    actualAlertDTO.setAlertType(AlertType.TSUNAMI);
    actualAlertDTO.setLatitude(10.0d);
    actualAlertDTO.setLongitude(10.0d);
    AlertType actualAlertType = actualAlertDTO.getAlertType();
    Double actualLatitude = actualAlertDTO.getLatitude();
    Double actualLongitude = actualAlertDTO.getLongitude();
    TransactionType actualType = actualAlertDTO.getType();

    // Assert
    assertEquals(10.0d, actualLatitude.doubleValue());
    assertEquals(10.0d, actualLongitude.doubleValue());
    assertEquals(AlertType.TSUNAMI, actualAlertType);
    assertEquals(TransactionType.ALERT, actualType);
  }
}
