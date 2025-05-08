package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConfirmationDTODiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ConfirmationDTO}
   *   <li>{@link ConfirmationDTO#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConfirmationDTO.<init>()", "TransactionType ConfirmationDTO.getType()"})
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals(TransactionType.CONFIRMATION, (new ConfirmationDTO()).getType());
  }
}
