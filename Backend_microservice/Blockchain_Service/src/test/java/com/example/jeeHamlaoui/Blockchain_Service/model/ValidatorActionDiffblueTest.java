package com.example.jeeHamlaoui.Blockchain_Service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.ActionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ValidatorAction.class})
@ExtendWith(SpringExtension.class)
class ValidatorActionDiffblueTest {
  @Autowired
  private ValidatorAction validatorAction;

  /**
   * Test {@link ValidatorAction#ValidatorAction()}.
   * <p>
   * Method under test: {@link ValidatorAction#ValidatorAction()}
   */
  @Test
  @DisplayName("Test new ValidatorAction()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ValidatorAction.<init>()"})
  void testNewValidatorAction() {
    // Arrange and Act
    ValidatorAction actualValidatorAction = new ValidatorAction();

    // Assert
    assertNull(actualValidatorAction.getBlockTransaction());
    assertNull(actualValidatorAction.getActionType());
    assertNull(actualValidatorAction.getId());
  }

  /**
   * Test {@link ValidatorAction#ValidatorAction(ActionType)}.
   * <p>
   * Method under test: {@link ValidatorAction#ValidatorAction(ActionType)}
   */
  @Test
  @DisplayName("Test new ValidatorAction(ActionType)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ValidatorAction.<init>(ActionType)"})
  void testNewValidatorAction2() {
    // Arrange and Act
    ValidatorAction actualValidatorAction = new ValidatorAction(ActionType.SWITCH_ORBIT);

    // Assert
    assertNull(actualValidatorAction.getBlockTransaction());
    assertNull(actualValidatorAction.getId());
    assertEquals(ActionType.SWITCH_ORBIT, actualValidatorAction.getActionType());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ValidatorAction#setActionType(ActionType)}
   *   <li>{@link ValidatorAction#getActionType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ActionType ValidatorAction.getActionType()", "void ValidatorAction.setActionType(ActionType)"})
  void testGettersAndSetters() {
    // Arrange
    ValidatorAction validatorAction = new ValidatorAction();

    // Act
    validatorAction.setActionType(ActionType.SWITCH_ORBIT);

    // Assert
    assertEquals(ActionType.SWITCH_ORBIT, validatorAction.getActionType());
  }
}
