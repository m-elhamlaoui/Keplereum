package com.example.jeeHamlaoui.Blockchain_Service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class NetworkNodeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NetworkNode#NetworkNode()}
   *   <li>{@link NetworkNode#setAuthorityStatus(boolean)}
   *   <li>{@link NetworkNode#setBlocksValidated(Integer)}
   *   <li>{@link NetworkNode#setLastActive(Instant)}
   *   <li>{@link NetworkNode#setNodeName(String)}
   *   <li>{@link NetworkNode#setPublicKey(String)}
   *   <li>{@link NetworkNode#setReceivedTransactions(List)}
   *   <li>{@link NetworkNode#setSentTransactions(List)}
   *   <li>{@link NetworkNode#getBlocksValidated()}
   *   <li>{@link NetworkNode#getLastActive()}
   *   <li>{@link NetworkNode#getNodeName()}
   *   <li>{@link NetworkNode#getPublicKey()}
   *   <li>{@link NetworkNode#getReceivedTransactions()}
   *   <li>{@link NetworkNode#getSentTransactions()}
   *   <li>{@link NetworkNode#isAuthorityStatus()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NetworkNode.<init>()",
      "void NetworkNode.<init>(String, List, List, Instant, Integer, boolean, String)",
      "Integer NetworkNode.getBlocksValidated()", "Instant NetworkNode.getLastActive()",
      "String NetworkNode.getNodeName()", "String NetworkNode.getPublicKey()",
      "List NetworkNode.getReceivedTransactions()", "List NetworkNode.getSentTransactions()",
      "boolean NetworkNode.isAuthorityStatus()", "void NetworkNode.setAuthorityStatus(boolean)",
      "void NetworkNode.setBlocksValidated(Integer)", "void NetworkNode.setLastActive(Instant)",
      "void NetworkNode.setNodeName(String)", "void NetworkNode.setPublicKey(String)",
      "void NetworkNode.setReceivedTransactions(List)", "void NetworkNode.setSentTransactions(List)"})
  void testGettersAndSetters() {
    // Arrange and Act
    NetworkNode actualNetworkNode = new NetworkNode();
    actualNetworkNode.setAuthorityStatus(true);
    actualNetworkNode.setBlocksValidated(1);
    actualNetworkNode.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualNetworkNode.setNodeName("Node Name");
    actualNetworkNode.setPublicKey("Public Key");
    ArrayList<BlockTransaction> receivedTransactions = new ArrayList<>();
    actualNetworkNode.setReceivedTransactions(receivedTransactions);
    ArrayList<BlockTransaction> sentTransactions = new ArrayList<>();
    actualNetworkNode.setSentTransactions(sentTransactions);
    Integer actualBlocksValidated = actualNetworkNode.getBlocksValidated();
    Instant actualLastActive = actualNetworkNode.getLastActive();
    String actualNodeName = actualNetworkNode.getNodeName();
    String actualPublicKey = actualNetworkNode.getPublicKey();
    List<BlockTransaction> actualReceivedTransactions = actualNetworkNode.getReceivedTransactions();
    List<BlockTransaction> actualSentTransactions = actualNetworkNode.getSentTransactions();
    boolean actualIsAuthorityStatusResult = actualNetworkNode.isAuthorityStatus();

    // Assert
    assertEquals("Node Name", actualNodeName);
    assertEquals("Public Key", actualPublicKey);
    assertEquals(1, actualBlocksValidated.intValue());
    assertTrue(actualIsAuthorityStatusResult);
    assertTrue(actualReceivedTransactions.isEmpty());
    assertTrue(actualSentTransactions.isEmpty());
    assertSame(receivedTransactions, actualReceivedTransactions);
    assertSame(sentTransactions, actualSentTransactions);
    assertSame(actualLastActive.EPOCH, actualLastActive);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Public Key}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link NetworkNode#NetworkNode(String, List, List, Instant, Integer, boolean, String)}
   *   <li>{@link NetworkNode#setAuthorityStatus(boolean)}
   *   <li>{@link NetworkNode#setBlocksValidated(Integer)}
   *   <li>{@link NetworkNode#setLastActive(Instant)}
   *   <li>{@link NetworkNode#setNodeName(String)}
   *   <li>{@link NetworkNode#setPublicKey(String)}
   *   <li>{@link NetworkNode#setReceivedTransactions(List)}
   *   <li>{@link NetworkNode#setSentTransactions(List)}
   *   <li>{@link NetworkNode#getBlocksValidated()}
   *   <li>{@link NetworkNode#getLastActive()}
   *   <li>{@link NetworkNode#getNodeName()}
   *   <li>{@link NetworkNode#getPublicKey()}
   *   <li>{@link NetworkNode#getReceivedTransactions()}
   *   <li>{@link NetworkNode#getSentTransactions()}
   *   <li>{@link NetworkNode#isAuthorityStatus()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when 'Public Key'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void NetworkNode.<init>()",
      "void NetworkNode.<init>(String, List, List, Instant, Integer, boolean, String)",
      "Integer NetworkNode.getBlocksValidated()", "Instant NetworkNode.getLastActive()",
      "String NetworkNode.getNodeName()", "String NetworkNode.getPublicKey()",
      "List NetworkNode.getReceivedTransactions()", "List NetworkNode.getSentTransactions()",
      "boolean NetworkNode.isAuthorityStatus()", "void NetworkNode.setAuthorityStatus(boolean)",
      "void NetworkNode.setBlocksValidated(Integer)", "void NetworkNode.setLastActive(Instant)",
      "void NetworkNode.setNodeName(String)", "void NetworkNode.setPublicKey(String)",
      "void NetworkNode.setReceivedTransactions(List)", "void NetworkNode.setSentTransactions(List)"})
  void testGettersAndSetters_whenPublicKey() {
    // Arrange
    ArrayList<BlockTransaction> receivedTransactions = new ArrayList<>();
    ArrayList<BlockTransaction> sentTransactions = new ArrayList<>();

    // Act
    NetworkNode actualNetworkNode = new NetworkNode("Public Key", receivedTransactions, sentTransactions,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(), 1, true, "Node Name");
    actualNetworkNode.setAuthorityStatus(true);
    actualNetworkNode.setBlocksValidated(1);
    actualNetworkNode.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualNetworkNode.setNodeName("Node Name");
    actualNetworkNode.setPublicKey("Public Key");
    ArrayList<BlockTransaction> receivedTransactions2 = new ArrayList<>();
    actualNetworkNode.setReceivedTransactions(receivedTransactions2);
    ArrayList<BlockTransaction> sentTransactions2 = new ArrayList<>();
    actualNetworkNode.setSentTransactions(sentTransactions2);
    Integer actualBlocksValidated = actualNetworkNode.getBlocksValidated();
    Instant actualLastActive = actualNetworkNode.getLastActive();
    String actualNodeName = actualNetworkNode.getNodeName();
    String actualPublicKey = actualNetworkNode.getPublicKey();
    List<BlockTransaction> actualReceivedTransactions = actualNetworkNode.getReceivedTransactions();
    List<BlockTransaction> actualSentTransactions = actualNetworkNode.getSentTransactions();
    boolean actualIsAuthorityStatusResult = actualNetworkNode.isAuthorityStatus();

    // Assert
    assertEquals("Node Name", actualNodeName);
    assertEquals("Public Key", actualPublicKey);
    assertEquals(1, actualBlocksValidated.intValue());
    assertTrue(actualIsAuthorityStatusResult);
    assertTrue(actualReceivedTransactions.isEmpty());
    assertTrue(actualSentTransactions.isEmpty());
    assertSame(receivedTransactions2, actualReceivedTransactions);
    assertSame(sentTransactions2, actualSentTransactions);
    assertSame(actualLastActive.EPOCH, actualLastActive);
  }
}
