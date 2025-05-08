package com.example.jeeHamlaoui.Blockchain_Service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionStatus;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Alert.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class AbstractTransactionTypeDiffblueTest {
  @Autowired
  private AbstractTransactionType abstractTransactionType;

  /**
   * Test {@link AbstractTransactionType#setBlockTransaction(BlockTransaction)}.
   * <ul>
   *   <li>Given {@link Alert#Alert()} BlockTransaction is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractTransactionType#setBlockTransaction(BlockTransaction)}
   */
  @Test
  @DisplayName("Test setBlockTransaction(BlockTransaction); given Alert() BlockTransaction is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AbstractTransactionType.setBlockTransaction(BlockTransaction)"})
  void testSetBlockTransaction_givenAlertBlockTransactionIsNull() {
    // Arrange
    Alert alert = new Alert();
    alert.setBlockTransaction(null);

    // Act
    alert.setBlockTransaction(null);

    // Assert that nothing has changed
    assertNull(alert.getBlockTransaction());
  }

  /**
   * Test {@link AbstractTransactionType#setBlockTransaction(BlockTransaction)}.
   * <ul>
   *   <li>Then {@link AbstractTransactionType} {@link Alert}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractTransactionType#setBlockTransaction(BlockTransaction)}
   */
  @Test
  @DisplayName("Test setBlockTransaction(BlockTransaction); then AbstractTransactionType Alert")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AbstractTransactionType.setBlockTransaction(BlockTransaction)"})
  void testSetBlockTransaction_thenAbstractTransactionTypeAlert() {
    // Arrange
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

    NetworkNode receiver = new NetworkNode();
    receiver.setAuthorityStatus(true);
    receiver.setBlocksValidated(1);
    receiver.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    receiver.setNodeName("Node Name");
    receiver.setPublicKey("Public Key");
    receiver.setReceivedTransactions(new ArrayList<>());
    receiver.setSentTransactions(new ArrayList<>());

    NetworkNode sender = new NetworkNode();
    sender.setAuthorityStatus(true);
    sender.setBlocksValidated(1);
    sender.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    sender.setNodeName("Node Name");
    sender.setPublicKey("Public Key");
    sender.setReceivedTransactions(new ArrayList<>());
    sender.setSentTransactions(new ArrayList<>());

    BlockTransaction blockTransaction = new BlockTransaction();
    blockTransaction.setAmount(10.0d);
    blockTransaction.setBlock(block);
    blockTransaction.setBlockNumber(1L);
    blockTransaction.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    blockTransaction.setFee(10.0d);
    blockTransaction.setGasLimit(10.0d);
    blockTransaction.setGasPrice(10.0d);
    blockTransaction.setGasUsed(10.0d);
    blockTransaction.setHash("Hash");
    blockTransaction.setReceiver(receiver);
    blockTransaction.setSender(sender);
    blockTransaction.setStatus(TransactionStatus.CONFIRMED);

    // Act
    abstractTransactionType.setBlockTransaction(blockTransaction);

    // Assert
    assertTrue(abstractTransactionType instanceof Alert);
    assertSame(blockTransaction, abstractTransactionType.getBlockTransaction());
    assertSame(abstractTransactionType, blockTransaction.getTransactionType());
  }

  /**
   * Test {@link AbstractTransactionType#setBlockTransaction(BlockTransaction)}.
   * <ul>
   *   <li>Then calls {@link BlockTransaction#getTransactionType()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractTransactionType#setBlockTransaction(BlockTransaction)}
   */
  @Test
  @DisplayName("Test setBlockTransaction(BlockTransaction); then calls getTransactionType()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AbstractTransactionType.setBlockTransaction(BlockTransaction)"})
  void testSetBlockTransaction_thenCallsGetTransactionType() {
    // Arrange
    BlockTransaction blockTransaction = mock(BlockTransaction.class);
    when(blockTransaction.getTransactionType()).thenReturn(new Alert());
    doNothing().when(blockTransaction).setTransactionType(Mockito.<AbstractTransactionType>any());

    Alert alert = new Alert();
    alert.setBlockTransaction(blockTransaction);

    // Act
    alert.setBlockTransaction(null);

    // Assert
    verify(blockTransaction).getTransactionType();
    verify(blockTransaction, atLeast(1)).setTransactionType(Mockito.<AbstractTransactionType>any());
    assertNull(alert.getBlockTransaction());
  }

  /**
   * Test {@link AbstractTransactionType#getId()}.
   * <p>
   * Method under test: {@link AbstractTransactionType#getId()}
   */
  @Test
  @DisplayName("Test getId()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Long AbstractTransactionType.getId()"})
  void testGetId() {
    // Arrange, Act and Assert
    assertNull(abstractTransactionType.getId());
  }

  /**
   * Test {@link AbstractTransactionType#setId(Long)}.
   * <p>
   * Method under test: {@link AbstractTransactionType#setId(Long)}
   */
  @Test
  @DisplayName("Test setId(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AbstractTransactionType.setId(Long)"})
  void testSetId() {
    // Arrange and Act
    abstractTransactionType.setId(1L);

    // Assert
    assertTrue(abstractTransactionType instanceof Alert);
    assertEquals(1L, abstractTransactionType.getId().longValue());
  }

  /**
   * Test {@link AbstractTransactionType#getBlockTransaction()}.
   * <p>
   * Method under test: {@link AbstractTransactionType#getBlockTransaction()}
   */
  @Test
  @DisplayName("Test getBlockTransaction()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"BlockTransaction AbstractTransactionType.getBlockTransaction()"})
  void testGetBlockTransaction() {
    // Arrange, Act and Assert
    assertNull(abstractTransactionType.getBlockTransaction());
  }
}
