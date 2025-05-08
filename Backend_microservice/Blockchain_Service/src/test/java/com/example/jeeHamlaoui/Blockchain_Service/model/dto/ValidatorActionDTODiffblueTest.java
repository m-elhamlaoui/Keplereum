package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.ActionType;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ValidatorActionDTODiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ValidatorActionDTO}
   *   <li>{@link ValidatorActionDTO#setActionType(ActionType)}
   *   <li>{@link ValidatorActionDTO#getActionType()}
   *   <li>{@link ValidatorActionDTO#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ValidatorActionDTO.<init>()", "ActionType ValidatorActionDTO.getActionType()",
      "TransactionType ValidatorActionDTO.getType()", "void ValidatorActionDTO.setActionType(ActionType)"})
  void testGettersAndSetters() {
    // Arrange and Act
    ValidatorActionDTO actualValidatorActionDTO = new ValidatorActionDTO();
    actualValidatorActionDTO.setActionType(ActionType.SWITCH_ORBIT);
    ActionType actualActionType = actualValidatorActionDTO.getActionType();

    // Assert
    assertEquals(ActionType.SWITCH_ORBIT, actualActionType);
    assertEquals(TransactionType.VALIDATOR_ACTION, actualValidatorActionDTO.getType());
  }
}
