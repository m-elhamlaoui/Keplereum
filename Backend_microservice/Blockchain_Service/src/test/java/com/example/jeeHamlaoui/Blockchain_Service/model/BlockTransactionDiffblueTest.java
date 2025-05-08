package com.example.jeeHamlaoui.Blockchain_Service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionStatus;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BlockTransactionDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BlockTransaction}
   *   <li>{@link BlockTransaction#setAmount(Double)}
   *   <li>{@link BlockTransaction#setBlock(Block)}
   *   <li>{@link BlockTransaction#setBlockNumber(Long)}
   *   <li>{@link BlockTransaction#setCreatedAt(Instant)}
   *   <li>{@link BlockTransaction#setFee(Double)}
   *   <li>{@link BlockTransaction#setGasLimit(Double)}
   *   <li>{@link BlockTransaction#setGasPrice(Double)}
   *   <li>{@link BlockTransaction#setGasUsed(Double)}
   *   <li>{@link BlockTransaction#setHash(String)}
   *   <li>{@link BlockTransaction#setReceiver(NetworkNode)}
   *   <li>{@link BlockTransaction#setSender(NetworkNode)}
   *   <li>{@link BlockTransaction#setStatus(TransactionStatus)}
   *   <li>{@link BlockTransaction#getAmount()}
   *   <li>{@link BlockTransaction#getBlock()}
   *   <li>{@link BlockTransaction#getBlockNumber()}
   *   <li>{@link BlockTransaction#getCreatedAt()}
   *   <li>{@link BlockTransaction#getFee()}
   *   <li>{@link BlockTransaction#getGasLimit()}
   *   <li>{@link BlockTransaction#getGasPrice()}
   *   <li>{@link BlockTransaction#getGasUsed()}
   *   <li>{@link BlockTransaction#getHash()}
   *   <li>{@link BlockTransaction#getReceiver()}
   *   <li>{@link BlockTransaction#getSender()}
   *   <li>{@link BlockTransaction#getStatus()}
   *   <li>{@link BlockTransaction#getTransactionType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BlockTransaction.<init>()", "Double BlockTransaction.getAmount()",
      "Block BlockTransaction.getBlock()", "Long BlockTransaction.getBlockNumber()",
      "Instant BlockTransaction.getCreatedAt()", "Double BlockTransaction.getFee()",
      "Double BlockTransaction.getGasLimit()", "Double BlockTransaction.getGasPrice()",
      "Double BlockTransaction.getGasUsed()", "String BlockTransaction.getHash()",
      "NetworkNode BlockTransaction.getReceiver()", "NetworkNode BlockTransaction.getSender()",
      "TransactionStatus BlockTransaction.getStatus()",
      "com.example.jeeHamlaoui.Blockchain_Service.model.AbstractTransactionType BlockTransaction.getTransactionType()",
      "void BlockTransaction.setAmount(Double)", "void BlockTransaction.setBlock(Block)",
      "void BlockTransaction.setBlockNumber(Long)", "void BlockTransaction.setCreatedAt(Instant)",
      "void BlockTransaction.setFee(Double)", "void BlockTransaction.setGasLimit(Double)",
      "void BlockTransaction.setGasPrice(Double)", "void BlockTransaction.setGasUsed(Double)",
      "void BlockTransaction.setHash(String)", "void BlockTransaction.setReceiver(NetworkNode)",
      "void BlockTransaction.setSender(NetworkNode)", "void BlockTransaction.setStatus(TransactionStatus)"})
  void testGettersAndSetters() {
    // Arrange and Act
    BlockTransaction actualBlockTransaction = new BlockTransaction();
    actualBlockTransaction.setAmount(10.0d);
    NetworkNode validator = new NetworkNode();
    validator.setAuthorityStatus(true);
    validator.setBlocksValidated(1);
    validator.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    validator.setNodeName("Node Name");
    validator.setPublicKey("Public Key");
    validator.setReceivedTransactions(new ArrayList<>());
    validator.setSentTransactions(new ArrayList<>());
    Block block = new Block();
    block.setBlockSize("Block Size");
    block.setBlockWeight(10.0d);
    block.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    block.setCurrentBlockHash("Current Block Hash");
    block.setHeight(1L);
    block.setPreviousBlockHash("Previous Block Hash");
    block.setSha3Uncles("Sha3 Uncles");
    block.setTransactionRoot("Transaction Root");
    block.setTransactions(new ArrayList<>());
    block.setValidator(validator);
    actualBlockTransaction.setBlock(block);
    actualBlockTransaction.setBlockNumber(1L);
    actualBlockTransaction.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualBlockTransaction.setFee(10.0d);
    actualBlockTransaction.setGasLimit(10.0d);
    actualBlockTransaction.setGasPrice(10.0d);
    actualBlockTransaction.setGasUsed(10.0d);
    actualBlockTransaction.setHash("Hash");
    NetworkNode receiver = new NetworkNode();
    receiver.setAuthorityStatus(true);
    receiver.setBlocksValidated(1);
    receiver.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    receiver.setNodeName("Node Name");
    receiver.setPublicKey("Public Key");
    receiver.setReceivedTransactions(new ArrayList<>());
    receiver.setSentTransactions(new ArrayList<>());
    actualBlockTransaction.setReceiver(receiver);
    NetworkNode sender = new NetworkNode();
    sender.setAuthorityStatus(true);
    sender.setBlocksValidated(1);
    sender.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    sender.setNodeName("Node Name");
    sender.setPublicKey("Public Key");
    sender.setReceivedTransactions(new ArrayList<>());
    sender.setSentTransactions(new ArrayList<>());
    actualBlockTransaction.setSender(sender);
    actualBlockTransaction.setStatus(TransactionStatus.CONFIRMED);
    Double actualAmount = actualBlockTransaction.getAmount();
    Block actualBlock = actualBlockTransaction.getBlock();
    Long actualBlockNumber = actualBlockTransaction.getBlockNumber();
    Instant actualCreatedAt = actualBlockTransaction.getCreatedAt();
    Double actualFee = actualBlockTransaction.getFee();
    Double actualGasLimit = actualBlockTransaction.getGasLimit();
    Double actualGasPrice = actualBlockTransaction.getGasPrice();
    Double actualGasUsed = actualBlockTransaction.getGasUsed();
    String actualHash = actualBlockTransaction.getHash();
    NetworkNode actualReceiver = actualBlockTransaction.getReceiver();
    NetworkNode actualSender = actualBlockTransaction.getSender();
    TransactionStatus actualStatus = actualBlockTransaction.getStatus();

    // Assert
    assertEquals("Hash", actualHash);
    assertNull(actualBlockTransaction.getTransactionType());
    assertEquals(10.0d, actualAmount.doubleValue());
    assertEquals(10.0d, actualFee.doubleValue());
    assertEquals(10.0d, actualGasLimit.doubleValue());
    assertEquals(10.0d, actualGasPrice.doubleValue());
    assertEquals(10.0d, actualGasUsed.doubleValue());
    assertEquals(1L, actualBlockNumber.longValue());
    assertEquals(TransactionStatus.CONFIRMED, actualStatus);
    assertSame(block, actualBlock);
    assertSame(receiver, actualReceiver);
    assertSame(sender, actualSender);
    assertSame(actualCreatedAt.EPOCH, actualCreatedAt);
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BlockTransaction}
   *   <li>{@link BlockTransaction#setAmount(Double)}
   *   <li>{@link BlockTransaction#setBlock(Block)}
   *   <li>{@link BlockTransaction#setBlockNumber(Long)}
   *   <li>{@link BlockTransaction#setCreatedAt(Instant)}
   *   <li>{@link BlockTransaction#setFee(Double)}
   *   <li>{@link BlockTransaction#setGasLimit(Double)}
   *   <li>{@link BlockTransaction#setGasPrice(Double)}
   *   <li>{@link BlockTransaction#setGasUsed(Double)}
   *   <li>{@link BlockTransaction#setHash(String)}
   *   <li>{@link BlockTransaction#setReceiver(NetworkNode)}
   *   <li>{@link BlockTransaction#setSender(NetworkNode)}
   *   <li>{@link BlockTransaction#setStatus(TransactionStatus)}
   *   <li>{@link BlockTransaction#getAmount()}
   *   <li>{@link BlockTransaction#getBlock()}
   *   <li>{@link BlockTransaction#getBlockNumber()}
   *   <li>{@link BlockTransaction#getCreatedAt()}
   *   <li>{@link BlockTransaction#getFee()}
   *   <li>{@link BlockTransaction#getGasLimit()}
   *   <li>{@link BlockTransaction#getGasPrice()}
   *   <li>{@link BlockTransaction#getGasUsed()}
   *   <li>{@link BlockTransaction#getHash()}
   *   <li>{@link BlockTransaction#getReceiver()}
   *   <li>{@link BlockTransaction#getSender()}
   *   <li>{@link BlockTransaction#getStatus()}
   *   <li>{@link BlockTransaction#getTransactionType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BlockTransaction.<init>()", "Double BlockTransaction.getAmount()",
      "Block BlockTransaction.getBlock()", "Long BlockTransaction.getBlockNumber()",
      "Instant BlockTransaction.getCreatedAt()", "Double BlockTransaction.getFee()",
      "Double BlockTransaction.getGasLimit()", "Double BlockTransaction.getGasPrice()",
      "Double BlockTransaction.getGasUsed()", "String BlockTransaction.getHash()",
      "NetworkNode BlockTransaction.getReceiver()", "NetworkNode BlockTransaction.getSender()",
      "TransactionStatus BlockTransaction.getStatus()",
      "com.example.jeeHamlaoui.Blockchain_Service.model.AbstractTransactionType BlockTransaction.getTransactionType()",
      "void BlockTransaction.setAmount(Double)", "void BlockTransaction.setBlock(Block)",
      "void BlockTransaction.setBlockNumber(Long)", "void BlockTransaction.setCreatedAt(Instant)",
      "void BlockTransaction.setFee(Double)", "void BlockTransaction.setGasLimit(Double)",
      "void BlockTransaction.setGasPrice(Double)", "void BlockTransaction.setGasUsed(Double)",
      "void BlockTransaction.setHash(String)", "void BlockTransaction.setReceiver(NetworkNode)",
      "void BlockTransaction.setSender(NetworkNode)", "void BlockTransaction.setStatus(TransactionStatus)"})
  void testGettersAndSetters2() {
    // Arrange and Act
    BlockTransaction actualBlockTransaction = new BlockTransaction();
    actualBlockTransaction.setAmount(10.0d);
    NetworkNode validator = new NetworkNode();
    validator.setAuthorityStatus(true);
    validator.setBlocksValidated(1);
    validator.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    validator.setNodeName("Node Name");
    validator.setPublicKey("Public Key");
    validator.setReceivedTransactions(new ArrayList<>());
    validator.setSentTransactions(new ArrayList<>());
    Block block = new Block();
    block.setBlockSize("Block Size");
    block.setBlockWeight(10.0d);
    block.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    block.setCurrentBlockHash("Current Block Hash");
    block.setHeight(1L);
    block.setPreviousBlockHash("Previous Block Hash");
    block.setSha3Uncles("Sha3 Uncles");
    block.setTransactionRoot("Transaction Root");
    block.setTransactions(new ArrayList<>());
    block.setValidator(validator);
    actualBlockTransaction.setBlock(block);
    actualBlockTransaction.setBlockNumber(1L);
    actualBlockTransaction.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualBlockTransaction.setFee(10.0d);
    actualBlockTransaction.setGasLimit(10.0d);
    actualBlockTransaction.setGasPrice(10.0d);
    actualBlockTransaction.setGasUsed(10.0d);
    actualBlockTransaction.setHash("Hash");
    NetworkNode receiver = new NetworkNode();
    receiver.setAuthorityStatus(true);
    receiver.setBlocksValidated(1);
    receiver.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    receiver.setNodeName("Node Name");
    receiver.setPublicKey("Public Key");
    receiver.setReceivedTransactions(new ArrayList<>());
    receiver.setSentTransactions(new ArrayList<>());
    actualBlockTransaction.setReceiver(receiver);
    NetworkNode sender = new NetworkNode();
    sender.setAuthorityStatus(true);
    sender.setBlocksValidated(1);
    sender.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    sender.setNodeName("Node Name");
    sender.setPublicKey("Public Key");
    sender.setReceivedTransactions(new ArrayList<>());
    sender.setSentTransactions(new ArrayList<>());
    actualBlockTransaction.setSender(sender);
    actualBlockTransaction.setStatus(TransactionStatus.CONFIRMED);
    Double actualAmount = actualBlockTransaction.getAmount();
    Block actualBlock = actualBlockTransaction.getBlock();
    Long actualBlockNumber = actualBlockTransaction.getBlockNumber();
    Instant actualCreatedAt = actualBlockTransaction.getCreatedAt();
    Double actualFee = actualBlockTransaction.getFee();
    Double actualGasLimit = actualBlockTransaction.getGasLimit();
    Double actualGasPrice = actualBlockTransaction.getGasPrice();
    Double actualGasUsed = actualBlockTransaction.getGasUsed();
    String actualHash = actualBlockTransaction.getHash();
    NetworkNode actualReceiver = actualBlockTransaction.getReceiver();
    NetworkNode actualSender = actualBlockTransaction.getSender();
    TransactionStatus actualStatus = actualBlockTransaction.getStatus();

    // Assert
    assertEquals("Hash", actualHash);
    assertNull(actualBlockTransaction.getTransactionType());
    assertEquals(10.0d, actualAmount.doubleValue());
    assertEquals(10.0d, actualFee.doubleValue());
    assertEquals(10.0d, actualGasLimit.doubleValue());
    assertEquals(10.0d, actualGasPrice.doubleValue());
    assertEquals(10.0d, actualGasUsed.doubleValue());
    assertEquals(1L, actualBlockNumber.longValue());
    assertEquals(TransactionStatus.CONFIRMED, actualStatus);
    assertSame(block, actualBlock);
    assertSame(receiver, actualReceiver);
    assertSame(sender, actualSender);
    assertSame(actualCreatedAt.EPOCH, actualCreatedAt);
  }
}
