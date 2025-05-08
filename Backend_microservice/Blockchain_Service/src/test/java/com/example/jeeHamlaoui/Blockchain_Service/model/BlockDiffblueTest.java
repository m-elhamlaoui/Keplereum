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

class BlockDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Block#Block()}
   *   <li>{@link Block#setBlockSize(String)}
   *   <li>{@link Block#setBlockWeight(Double)}
   *   <li>{@link Block#setCreatedAt(Instant)}
   *   <li>{@link Block#setCurrentBlockHash(String)}
   *   <li>{@link Block#setHeight(Long)}
   *   <li>{@link Block#setPreviousBlockHash(String)}
   *   <li>{@link Block#setSha3Uncles(String)}
   *   <li>{@link Block#setTransactionRoot(String)}
   *   <li>{@link Block#setTransactions(List)}
   *   <li>{@link Block#setValidator(NetworkNode)}
   *   <li>{@link Block#getBlockSize()}
   *   <li>{@link Block#getBlockWeight()}
   *   <li>{@link Block#getCreatedAt()}
   *   <li>{@link Block#getCurrentBlockHash()}
   *   <li>{@link Block#getHeight()}
   *   <li>{@link Block#getPreviousBlockHash()}
   *   <li>{@link Block#getSha3Uncles()}
   *   <li>{@link Block#getTransactionRoot()}
   *   <li>{@link Block#getTransactions()}
   *   <li>{@link Block#getValidator()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Block.<init>()",
      "void Block.<init>(Long, NetworkNode, List, String, String, String, Double, Instant, String, String)",
      "String Block.getBlockSize()", "Double Block.getBlockWeight()", "Instant Block.getCreatedAt()",
      "String Block.getCurrentBlockHash()", "Long Block.getHeight()", "String Block.getPreviousBlockHash()",
      "String Block.getSha3Uncles()", "String Block.getTransactionRoot()", "List Block.getTransactions()",
      "NetworkNode Block.getValidator()", "void Block.setBlockSize(String)", "void Block.setBlockWeight(Double)",
      "void Block.setCreatedAt(Instant)", "void Block.setCurrentBlockHash(String)", "void Block.setHeight(Long)",
      "void Block.setPreviousBlockHash(String)", "void Block.setSha3Uncles(String)",
      "void Block.setTransactionRoot(String)", "void Block.setTransactions(List)",
      "void Block.setValidator(NetworkNode)"})
  void testGettersAndSetters() {
    // Arrange and Act
    Block actualBlock = new Block();
    actualBlock.setBlockSize("Block Size");
    actualBlock.setBlockWeight(10.0d);
    actualBlock.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualBlock.setCurrentBlockHash("Current Block Hash");
    actualBlock.setHeight(1L);
    actualBlock.setPreviousBlockHash("Previous Block Hash");
    actualBlock.setSha3Uncles("Sha3 Uncles");
    actualBlock.setTransactionRoot("Transaction Root");
    ArrayList<BlockTransaction> transactions = new ArrayList<>();
    actualBlock.setTransactions(transactions);
    NetworkNode validator = new NetworkNode();
    validator.setAuthorityStatus(true);
    validator.setBlocksValidated(1);
    validator.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    validator.setNodeName("Node Name");
    validator.setPublicKey("Public Key");
    validator.setReceivedTransactions(new ArrayList<>());
    validator.setSentTransactions(new ArrayList<>());
    actualBlock.setValidator(validator);
    String actualBlockSize = actualBlock.getBlockSize();
    Double actualBlockWeight = actualBlock.getBlockWeight();
    Instant actualCreatedAt = actualBlock.getCreatedAt();
    String actualCurrentBlockHash = actualBlock.getCurrentBlockHash();
    Long actualHeight = actualBlock.getHeight();
    String actualPreviousBlockHash = actualBlock.getPreviousBlockHash();
    String actualSha3Uncles = actualBlock.getSha3Uncles();
    String actualTransactionRoot = actualBlock.getTransactionRoot();
    List<BlockTransaction> actualTransactions = actualBlock.getTransactions();
    NetworkNode actualValidator = actualBlock.getValidator();

    // Assert
    assertEquals("Block Size", actualBlockSize);
    assertEquals("Current Block Hash", actualCurrentBlockHash);
    assertEquals("Previous Block Hash", actualPreviousBlockHash);
    assertEquals("Sha3 Uncles", actualSha3Uncles);
    assertEquals("Transaction Root", actualTransactionRoot);
    assertEquals(10.0d, actualBlockWeight.doubleValue());
    assertEquals(1L, actualHeight.longValue());
    assertTrue(actualTransactions.isEmpty());
    assertSame(validator, actualValidator);
    assertSame(transactions, actualTransactions);
    assertSame(actualCreatedAt.EPOCH, actualCreatedAt);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>Given {@code true}.</li>
   *   <li>When one.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Block#Block(Long, NetworkNode, List, String, String, String, Double, Instant, String, String)}
   *   <li>{@link Block#setBlockSize(String)}
   *   <li>{@link Block#setBlockWeight(Double)}
   *   <li>{@link Block#setCreatedAt(Instant)}
   *   <li>{@link Block#setCurrentBlockHash(String)}
   *   <li>{@link Block#setHeight(Long)}
   *   <li>{@link Block#setPreviousBlockHash(String)}
   *   <li>{@link Block#setSha3Uncles(String)}
   *   <li>{@link Block#setTransactionRoot(String)}
   *   <li>{@link Block#setTransactions(List)}
   *   <li>{@link Block#setValidator(NetworkNode)}
   *   <li>{@link Block#getBlockSize()}
   *   <li>{@link Block#getBlockWeight()}
   *   <li>{@link Block#getCreatedAt()}
   *   <li>{@link Block#getCurrentBlockHash()}
   *   <li>{@link Block#getHeight()}
   *   <li>{@link Block#getPreviousBlockHash()}
   *   <li>{@link Block#getSha3Uncles()}
   *   <li>{@link Block#getTransactionRoot()}
   *   <li>{@link Block#getTransactions()}
   *   <li>{@link Block#getValidator()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; given 'true'; when one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Block.<init>()",
      "void Block.<init>(Long, NetworkNode, List, String, String, String, Double, Instant, String, String)",
      "String Block.getBlockSize()", "Double Block.getBlockWeight()", "Instant Block.getCreatedAt()",
      "String Block.getCurrentBlockHash()", "Long Block.getHeight()", "String Block.getPreviousBlockHash()",
      "String Block.getSha3Uncles()", "String Block.getTransactionRoot()", "List Block.getTransactions()",
      "NetworkNode Block.getValidator()", "void Block.setBlockSize(String)", "void Block.setBlockWeight(Double)",
      "void Block.setCreatedAt(Instant)", "void Block.setCurrentBlockHash(String)", "void Block.setHeight(Long)",
      "void Block.setPreviousBlockHash(String)", "void Block.setSha3Uncles(String)",
      "void Block.setTransactionRoot(String)", "void Block.setTransactions(List)",
      "void Block.setValidator(NetworkNode)"})
  void testGettersAndSetters_givenTrue_whenOne() {
    // Arrange
    NetworkNode validator = new NetworkNode();
    validator.setAuthorityStatus(true);
    validator.setBlocksValidated(1);
    validator.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    validator.setNodeName("Node Name");
    validator.setPublicKey("Public Key");
    validator.setReceivedTransactions(new ArrayList<>());
    validator.setSentTransactions(new ArrayList<>());
    ArrayList<BlockTransaction> transactions = new ArrayList<>();

    // Act
    Block actualBlock = new Block(1L, validator, transactions, "Block Size", "Sha3 Uncles", "Transaction Root", 10.0d,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(), "Current Block Hash",
        "Previous Block Hash");
    actualBlock.setBlockSize("Block Size");
    actualBlock.setBlockWeight(10.0d);
    actualBlock.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualBlock.setCurrentBlockHash("Current Block Hash");
    actualBlock.setHeight(1L);
    actualBlock.setPreviousBlockHash("Previous Block Hash");
    actualBlock.setSha3Uncles("Sha3 Uncles");
    actualBlock.setTransactionRoot("Transaction Root");
    ArrayList<BlockTransaction> transactions2 = new ArrayList<>();
    actualBlock.setTransactions(transactions2);
    NetworkNode validator2 = new NetworkNode();
    validator2.setAuthorityStatus(true);
    validator2.setBlocksValidated(1);
    validator2.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    validator2.setNodeName("Node Name");
    validator2.setPublicKey("Public Key");
    validator2.setReceivedTransactions(new ArrayList<>());
    validator2.setSentTransactions(new ArrayList<>());
    actualBlock.setValidator(validator2);
    String actualBlockSize = actualBlock.getBlockSize();
    Double actualBlockWeight = actualBlock.getBlockWeight();
    Instant actualCreatedAt = actualBlock.getCreatedAt();
    String actualCurrentBlockHash = actualBlock.getCurrentBlockHash();
    Long actualHeight = actualBlock.getHeight();
    String actualPreviousBlockHash = actualBlock.getPreviousBlockHash();
    String actualSha3Uncles = actualBlock.getSha3Uncles();
    String actualTransactionRoot = actualBlock.getTransactionRoot();
    List<BlockTransaction> actualTransactions = actualBlock.getTransactions();
    NetworkNode actualValidator = actualBlock.getValidator();

    // Assert
    assertEquals("Block Size", actualBlockSize);
    assertEquals("Current Block Hash", actualCurrentBlockHash);
    assertEquals("Previous Block Hash", actualPreviousBlockHash);
    assertEquals("Sha3 Uncles", actualSha3Uncles);
    assertEquals("Transaction Root", actualTransactionRoot);
    assertEquals(10.0d, actualBlockWeight.doubleValue());
    assertEquals(1L, actualHeight.longValue());
    assertTrue(actualTransactions.isEmpty());
    assertSame(validator2, actualValidator);
    assertSame(transactions2, actualTransactions);
    assertSame(actualCreatedAt.EPOCH, actualCreatedAt);
  }
}
