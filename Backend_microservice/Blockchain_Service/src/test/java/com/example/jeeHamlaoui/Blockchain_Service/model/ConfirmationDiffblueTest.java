package com.example.jeeHamlaoui.Blockchain_Service.model;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Confirmation.class})
@ExtendWith(SpringExtension.class)
class ConfirmationDiffblueTest {
  @Autowired
  private Confirmation confirmation;

  /**
   * Test new {@link Confirmation} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link Confirmation}
   */
  @Test
  @DisplayName("Test new Confirmation (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Confirmation.<init>()"})
  void testNewConfirmation() {
    // Arrange and Act
    Confirmation actualConfirmation = new Confirmation();

    // Assert
    assertNull(actualConfirmation.getBlockTransaction());
    assertNull(actualConfirmation.getId());
  }
}
