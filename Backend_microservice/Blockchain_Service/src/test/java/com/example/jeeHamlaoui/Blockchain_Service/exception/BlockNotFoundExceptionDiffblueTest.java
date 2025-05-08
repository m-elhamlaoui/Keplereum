package com.example.jeeHamlaoui.Blockchain_Service.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BlockNotFoundExceptionDiffblueTest {
  /**
   * Test {@link BlockNotFoundException#BlockNotFoundException(Long)}.
   * <p>
   * Method under test: {@link BlockNotFoundException#BlockNotFoundException(Long)}
   */
  @Test
  @DisplayName("Test new BlockNotFoundException(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BlockNotFoundException.<init>(Long)"})
  void testNewBlockNotFoundException() {
    // Arrange and Act
    BlockNotFoundException actualBlockNotFoundException = new BlockNotFoundException(1L);

    // Assert
    assertEquals("Block Not Found: 1", actualBlockNotFoundException.getLocalizedMessage());
    assertEquals("Block Not Found: 1", actualBlockNotFoundException.getMessage());
    assertNull(actualBlockNotFoundException.getCause());
    assertEquals(0, actualBlockNotFoundException.getSuppressed().length);
  }
}
