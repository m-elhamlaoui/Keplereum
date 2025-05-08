package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class NetworkNodeDTODiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link NetworkNodeDTO}
   *   <li>{@link NetworkNodeDTO#setAuthorityStatus(boolean)}
   *   <li>{@link NetworkNodeDTO#setBlocksValidated(Integer)}
   *   <li>{@link NetworkNodeDTO#setLastActive(Instant)}
   *   <li>{@link NetworkNodeDTO#setNodeName(String)}
   *   <li>{@link NetworkNodeDTO#setPublicKey(String)}
   *   <li>{@link NetworkNodeDTO#getBlocksValidated()}
   *   <li>{@link NetworkNodeDTO#getLastActive()}
   *   <li>{@link NetworkNodeDTO#getNodeName()}
   *   <li>{@link NetworkNodeDTO#getPublicKey()}
   *   <li>{@link NetworkNodeDTO#isAuthorityStatus()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NetworkNodeDTO.<init>()", "Integer NetworkNodeDTO.getBlocksValidated()",
      "Instant NetworkNodeDTO.getLastActive()", "String NetworkNodeDTO.getNodeName()",
      "String NetworkNodeDTO.getPublicKey()", "boolean NetworkNodeDTO.isAuthorityStatus()",
      "void NetworkNodeDTO.setAuthorityStatus(boolean)", "void NetworkNodeDTO.setBlocksValidated(Integer)",
      "void NetworkNodeDTO.setLastActive(Instant)", "void NetworkNodeDTO.setNodeName(String)",
      "void NetworkNodeDTO.setPublicKey(String)"})
  void testGettersAndSetters() {
    // Arrange and Act
    NetworkNodeDTO actualNetworkNodeDTO = new NetworkNodeDTO();
    actualNetworkNodeDTO.setAuthorityStatus(true);
    actualNetworkNodeDTO.setBlocksValidated(1);
    actualNetworkNodeDTO.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualNetworkNodeDTO.setNodeName("Node Name");
    actualNetworkNodeDTO.setPublicKey("Public Key");
    Integer actualBlocksValidated = actualNetworkNodeDTO.getBlocksValidated();
    Instant actualLastActive = actualNetworkNodeDTO.getLastActive();
    String actualNodeName = actualNetworkNodeDTO.getNodeName();
    String actualPublicKey = actualNetworkNodeDTO.getPublicKey();
    boolean actualIsAuthorityStatusResult = actualNetworkNodeDTO.isAuthorityStatus();

    // Assert
    assertEquals("Node Name", actualNodeName);
    assertEquals("Public Key", actualPublicKey);
    assertEquals(1, actualBlocksValidated.intValue());
    assertTrue(actualIsAuthorityStatusResult);
    assertSame(actualLastActive.EPOCH, actualLastActive);
  }
}
