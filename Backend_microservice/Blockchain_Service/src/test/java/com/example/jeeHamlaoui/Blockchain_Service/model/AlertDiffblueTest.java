package com.example.jeeHamlaoui.Blockchain_Service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.AlertType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Alert.class})
@ExtendWith(SpringExtension.class)
class AlertDiffblueTest {
  @Autowired
  private Alert alert;

  /**
   * Test {@link Alert#Alert()}.
   * <p>
   * Method under test: {@link Alert#Alert()}
   */
  @Test
  @DisplayName("Test new Alert()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Alert.<init>()"})
  void testNewAlert() {
    // Arrange and Act
    Alert actualAlert = new Alert();

    // Assert
    assertNull(actualAlert.getBlockTransaction());
    assertNull(actualAlert.getAlertType());
    assertNull(actualAlert.getLatitude());
    assertNull(actualAlert.getLongitude());
    assertNull(actualAlert.getId());
  }

  /**
   * Test {@link Alert#Alert(AlertType, Double, Double)}.
   * <p>
   * Method under test: {@link Alert#Alert(AlertType, Double, Double)}
   */
  @Test
  @DisplayName("Test new Alert(AlertType, Double, Double)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Alert.<init>(AlertType, Double, Double)"})
  void testNewAlert2() {
    // Arrange and Act
    Alert actualAlert = new Alert(AlertType.TSUNAMI, 10.0d, 10.0d);

    // Assert
    assertNull(actualAlert.getBlockTransaction());
    assertNull(actualAlert.getId());
    assertEquals(10.0d, actualAlert.getLatitude().doubleValue());
    assertEquals(10.0d, actualAlert.getLongitude().doubleValue());
    assertEquals(AlertType.TSUNAMI, actualAlert.getAlertType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Alert#setAlertType(AlertType)}
   *   <li>{@link Alert#setLatitude(Double)}
   *   <li>{@link Alert#setLongitude(Double)}
   *   <li>{@link Alert#getAlertType()}
   *   <li>{@link Alert#getLatitude()}
   *   <li>{@link Alert#getLongitude()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"AlertType Alert.getAlertType()", "Double Alert.getLatitude()", "Double Alert.getLongitude()",
      "void Alert.setAlertType(AlertType)", "void Alert.setLatitude(Double)", "void Alert.setLongitude(Double)"})
  void testGettersAndSetters() {
    // Arrange
    Alert alert = new Alert();

    // Act
    alert.setAlertType(AlertType.TSUNAMI);
    alert.setLatitude(10.0d);
    alert.setLongitude(10.0d);
    AlertType actualAlertType = alert.getAlertType();
    Double actualLatitude = alert.getLatitude();
    Double actualLongitude = alert.getLongitude();

    // Assert
    assertEquals(10.0d, actualLatitude.doubleValue());
    assertEquals(10.0d, actualLongitude.doubleValue());
    assertEquals(AlertType.TSUNAMI, actualAlertType);
  }
}
