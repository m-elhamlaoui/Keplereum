package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.ActionType;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionStatus;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TransactionDTODiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TransactionDTO}
   *   <li>{@link TransactionDTO#setAction(ActionType)}
   *   <li>{@link TransactionDTO#setAlertType(String)}
   *   <li>{@link TransactionDTO#setAmount(Double)}
   *   <li>{@link TransactionDTO#setBlockNumber(Long)}
   *   <li>{@link TransactionDTO#setConfirmsAlertId(String)}
   *   <li>{@link TransactionDTO#setFee(Double)}
   *   <li>{@link TransactionDTO#setFrom(String)}
   *   <li>{@link TransactionDTO#setGasLimit(Double)}
   *   <li>{@link TransactionDTO#setGasPrice(Double)}
   *   <li>{@link TransactionDTO#setGasUsed(Double)}
   *   <li>{@link TransactionDTO#setHash(String)}
   *   <li>{@link TransactionDTO#setId(String)}
   *   <li>{@link TransactionDTO#setLatitude(Double)}
   *   <li>{@link TransactionDTO#setLongitude(Double)}
   *   <li>{@link TransactionDTO#setStatus(TransactionStatus)}
   *   <li>{@link TransactionDTO#setTimestamp(Instant)}
   *   <li>{@link TransactionDTO#setTo(String)}
   *   <li>{@link TransactionDTO#getAction()}
   *   <li>{@link TransactionDTO#getAlertType()}
   *   <li>{@link TransactionDTO#getAmount()}
   *   <li>{@link TransactionDTO#getBlockNumber()}
   *   <li>{@link TransactionDTO#getConfirmsAlertId()}
   *   <li>{@link TransactionDTO#getFee()}
   *   <li>{@link TransactionDTO#getFrom()}
   *   <li>{@link TransactionDTO#getGasLimit()}
   *   <li>{@link TransactionDTO#getGasPrice()}
   *   <li>{@link TransactionDTO#getGasUsed()}
   *   <li>{@link TransactionDTO#getHash()}
   *   <li>{@link TransactionDTO#getId()}
   *   <li>{@link TransactionDTO#getLatitude()}
   *   <li>{@link TransactionDTO#getLongitude()}
   *   <li>{@link TransactionDTO#getStatus()}
   *   <li>{@link TransactionDTO#getTimestamp()}
   *   <li>{@link TransactionDTO#getTo()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TransactionDTO.<init>()", "ActionType TransactionDTO.getAction()",
      "String TransactionDTO.getAlertType()", "Double TransactionDTO.getAmount()",
      "Long TransactionDTO.getBlockNumber()", "String TransactionDTO.getConfirmsAlertId()",
      "Double TransactionDTO.getFee()", "String TransactionDTO.getFrom()", "Double TransactionDTO.getGasLimit()",
      "Double TransactionDTO.getGasPrice()", "Double TransactionDTO.getGasUsed()", "String TransactionDTO.getHash()",
      "String TransactionDTO.getId()", "Double TransactionDTO.getLatitude()", "Double TransactionDTO.getLongitude()",
      "TransactionStatus TransactionDTO.getStatus()", "Instant TransactionDTO.getTimestamp()",
      "String TransactionDTO.getTo()", "void TransactionDTO.setAction(ActionType)",
      "void TransactionDTO.setAlertType(String)", "void TransactionDTO.setAmount(Double)",
      "void TransactionDTO.setBlockNumber(Long)", "void TransactionDTO.setConfirmsAlertId(String)",
      "void TransactionDTO.setFee(Double)", "void TransactionDTO.setFrom(String)",
      "void TransactionDTO.setGasLimit(Double)", "void TransactionDTO.setGasPrice(Double)",
      "void TransactionDTO.setGasUsed(Double)", "void TransactionDTO.setHash(String)",
      "void TransactionDTO.setId(String)", "void TransactionDTO.setLatitude(Double)",
      "void TransactionDTO.setLongitude(Double)", "void TransactionDTO.setStatus(TransactionStatus)",
      "void TransactionDTO.setTimestamp(Instant)", "void TransactionDTO.setTo(String)"})
  void testGettersAndSetters() {
    // Arrange and Act
    TransactionDTO actualTransactionDTO = new TransactionDTO();
    actualTransactionDTO.setAction(ActionType.SWITCH_ORBIT);
    actualTransactionDTO.setAlertType("Alert Type");
    actualTransactionDTO.setAmount(10.0d);
    actualTransactionDTO.setBlockNumber(1L);
    actualTransactionDTO.setConfirmsAlertId("42");
    actualTransactionDTO.setFee(10.0d);
    actualTransactionDTO.setFrom("jane.doe@example.org");
    actualTransactionDTO.setGasLimit(10.0d);
    actualTransactionDTO.setGasPrice(10.0d);
    actualTransactionDTO.setGasUsed(10.0d);
    actualTransactionDTO.setHash("Hash");
    actualTransactionDTO.setId("42");
    actualTransactionDTO.setLatitude(10.0d);
    actualTransactionDTO.setLongitude(10.0d);
    actualTransactionDTO.setStatus(TransactionStatus.CONFIRMED);
    actualTransactionDTO.setTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualTransactionDTO.setTo("alice.liddell@example.org");
    ActionType actualAction = actualTransactionDTO.getAction();
    String actualAlertType = actualTransactionDTO.getAlertType();
    Double actualAmount = actualTransactionDTO.getAmount();
    Long actualBlockNumber = actualTransactionDTO.getBlockNumber();
    String actualConfirmsAlertId = actualTransactionDTO.getConfirmsAlertId();
    Double actualFee = actualTransactionDTO.getFee();
    String actualFrom = actualTransactionDTO.getFrom();
    Double actualGasLimit = actualTransactionDTO.getGasLimit();
    Double actualGasPrice = actualTransactionDTO.getGasPrice();
    Double actualGasUsed = actualTransactionDTO.getGasUsed();
    String actualHash = actualTransactionDTO.getHash();
    String actualId = actualTransactionDTO.getId();
    Double actualLatitude = actualTransactionDTO.getLatitude();
    Double actualLongitude = actualTransactionDTO.getLongitude();
    TransactionStatus actualStatus = actualTransactionDTO.getStatus();
    Instant actualTimestamp = actualTransactionDTO.getTimestamp();

    // Assert
    assertEquals("42", actualConfirmsAlertId);
    assertEquals("42", actualId);
    assertEquals("Alert Type", actualAlertType);
    assertEquals("Hash", actualHash);
    assertEquals("alice.liddell@example.org", actualTransactionDTO.getTo());
    assertEquals("jane.doe@example.org", actualFrom);
    assertEquals(10.0d, actualAmount.doubleValue());
    assertEquals(10.0d, actualFee.doubleValue());
    assertEquals(10.0d, actualGasLimit.doubleValue());
    assertEquals(10.0d, actualGasPrice.doubleValue());
    assertEquals(10.0d, actualGasUsed.doubleValue());
    assertEquals(10.0d, actualLatitude.doubleValue());
    assertEquals(10.0d, actualLongitude.doubleValue());
    assertEquals(1L, actualBlockNumber.longValue());
    assertEquals(ActionType.SWITCH_ORBIT, actualAction);
    assertEquals(TransactionStatus.CONFIRMED, actualStatus);
    assertSame(actualTimestamp.EPOCH, actualTimestamp);
  }
}
