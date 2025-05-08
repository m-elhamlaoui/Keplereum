package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

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

class BlockDTODiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BlockDTO}
   *   <li>{@link BlockDTO#setGasLimit(Double)}
   *   <li>{@link BlockDTO#setGasUsed(Double)}
   *   <li>{@link BlockDTO#setHash(String)}
   *   <li>{@link BlockDTO#setNumber(Long)}
   *   <li>{@link BlockDTO#setParentHash(String)}
   *   <li>{@link BlockDTO#setSha3uncles(String)}
   *   <li>{@link BlockDTO#setSize(Integer)}
   *   <li>{@link BlockDTO#setTimestamp(Instant)}
   *   <li>{@link BlockDTO#setTotalFees(Double)}
   *   <li>{@link BlockDTO#setTransactionCount(Integer)}
   *   <li>{@link BlockDTO#setTransactionRoot(String)}
   *   <li>{@link BlockDTO#setTransactions(List)}
   *   <li>{@link BlockDTO#setValidator(String)}
   *   <li>{@link BlockDTO#getGasLimit()}
   *   <li>{@link BlockDTO#getGasUsed()}
   *   <li>{@link BlockDTO#getHash()}
   *   <li>{@link BlockDTO#getNumber()}
   *   <li>{@link BlockDTO#getParentHash()}
   *   <li>{@link BlockDTO#getSha3uncles()}
   *   <li>{@link BlockDTO#getSize()}
   *   <li>{@link BlockDTO#getTimestamp()}
   *   <li>{@link BlockDTO#getTotalFees()}
   *   <li>{@link BlockDTO#getTransactionCount()}
   *   <li>{@link BlockDTO#getTransactionRoot()}
   *   <li>{@link BlockDTO#getTransactions()}
   *   <li>{@link BlockDTO#getValidator()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void BlockDTO.<init>()", "Double BlockDTO.getGasLimit()", "Double BlockDTO.getGasUsed()",
      "String BlockDTO.getHash()", "Long BlockDTO.getNumber()", "String BlockDTO.getParentHash()",
      "String BlockDTO.getSha3uncles()", "Integer BlockDTO.getSize()", "Instant BlockDTO.getTimestamp()",
      "Double BlockDTO.getTotalFees()", "Integer BlockDTO.getTransactionCount()",
      "String BlockDTO.getTransactionRoot()", "List BlockDTO.getTransactions()", "String BlockDTO.getValidator()",
      "void BlockDTO.setGasLimit(Double)", "void BlockDTO.setGasUsed(Double)", "void BlockDTO.setHash(String)",
      "void BlockDTO.setNumber(Long)", "void BlockDTO.setParentHash(String)", "void BlockDTO.setSha3uncles(String)",
      "void BlockDTO.setSize(Integer)", "void BlockDTO.setTimestamp(Instant)", "void BlockDTO.setTotalFees(Double)",
      "void BlockDTO.setTransactionCount(Integer)", "void BlockDTO.setTransactionRoot(String)",
      "void BlockDTO.setTransactions(List)", "void BlockDTO.setValidator(String)"})
  void testGettersAndSetters() {
    // Arrange and Act
    BlockDTO actualBlockDTO = new BlockDTO();
    actualBlockDTO.setGasLimit(10.0d);
    actualBlockDTO.setGasUsed(10.0d);
    actualBlockDTO.setHash("Hash");
    actualBlockDTO.setNumber(1L);
    actualBlockDTO.setParentHash("Parent Hash");
    actualBlockDTO.setSha3uncles("Sha3uncles");
    actualBlockDTO.setSize(3);
    actualBlockDTO.setTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualBlockDTO.setTotalFees(10.0d);
    actualBlockDTO.setTransactionCount(3);
    actualBlockDTO.setTransactionRoot("Transaction Root");
    ArrayList<TransactionDTO> transactions = new ArrayList<>();
    actualBlockDTO.setTransactions(transactions);
    actualBlockDTO.setValidator("Validator");
    Double actualGasLimit = actualBlockDTO.getGasLimit();
    Double actualGasUsed = actualBlockDTO.getGasUsed();
    String actualHash = actualBlockDTO.getHash();
    Long actualNumber = actualBlockDTO.getNumber();
    String actualParentHash = actualBlockDTO.getParentHash();
    String actualSha3uncles = actualBlockDTO.getSha3uncles();
    Integer actualSize = actualBlockDTO.getSize();
    Instant actualTimestamp = actualBlockDTO.getTimestamp();
    Double actualTotalFees = actualBlockDTO.getTotalFees();
    Integer actualTransactionCount = actualBlockDTO.getTransactionCount();
    String actualTransactionRoot = actualBlockDTO.getTransactionRoot();
    List<TransactionDTO> actualTransactions = actualBlockDTO.getTransactions();

    // Assert
    assertEquals("Hash", actualHash);
    assertEquals("Parent Hash", actualParentHash);
    assertEquals("Sha3uncles", actualSha3uncles);
    assertEquals("Transaction Root", actualTransactionRoot);
    assertEquals("Validator", actualBlockDTO.getValidator());
    assertEquals(10.0d, actualGasLimit.doubleValue());
    assertEquals(10.0d, actualGasUsed.doubleValue());
    assertEquals(10.0d, actualTotalFees.doubleValue());
    assertEquals(1L, actualNumber.longValue());
    assertEquals(3, actualSize.intValue());
    assertEquals(3, actualTransactionCount.intValue());
    assertTrue(actualTransactions.isEmpty());
    assertSame(transactions, actualTransactions);
    assertSame(actualTimestamp.EPOCH, actualTimestamp);
  }
}
