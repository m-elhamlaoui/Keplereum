package com.example.jeeHamlaoui.Blockchain_Service.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NodeNotFoundException.class, String.class})
@ExtendWith(SpringExtension.class)
class NodeNotFoundExceptionDiffblueTest {
  @Autowired
  private NodeNotFoundException nodeNotFoundException;

  /**
   * Test {@link NodeNotFoundException#NodeNotFoundException(String)}.
   * <p>
   * Method under test: {@link NodeNotFoundException#NodeNotFoundException(String)}
   */
  @Test
  @DisplayName("Test new NodeNotFoundException(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NodeNotFoundException.<init>(String)"})
  void testNewNodeNotFoundException() {
    // Arrange and Act
    NodeNotFoundException actualNodeNotFoundException = new NodeNotFoundException("Sender Public Key");

    // Assert
    assertEquals("Node Not found with public keySender Public Key", actualNodeNotFoundException.getLocalizedMessage());
    assertEquals("Node Not found with public keySender Public Key", actualNodeNotFoundException.getMessage());
    assertNull(actualNodeNotFoundException.getCause());
    assertEquals(0, actualNodeNotFoundException.getSuppressed().length);
  }
}
