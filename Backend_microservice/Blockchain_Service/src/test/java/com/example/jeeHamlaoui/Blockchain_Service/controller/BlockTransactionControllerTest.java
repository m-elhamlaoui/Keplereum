package com.example.jeeHamlaoui.Blockchain_Service.controller;

import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.Block;
import com.example.jeeHamlaoui.Blockchain_Service.model.BlockTransaction;
import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionStatus;
import com.example.jeeHamlaoui.Blockchain_Service.service.BlockTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BlockTransactionController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BlockTransactionControllerTest {
  @Autowired
  private BlockTransactionController blockTransactionController;

  @MockitoBean
  private BlockTransactionService blockTransactionService;

  /**
   * Test {@link BlockTransactionController#createTransactions(List)}.
   * <p>
   * Method under test: {@link BlockTransactionController#createTransactions(List)}
   */
  @Test
  @DisplayName("Test createTransactions(List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"org.springframework.http.ResponseEntity BlockTransactionController.createTransactions(List)"})
  void testCreateTransactions() throws Exception {
    // Arrange
    when(blockTransactionService.saveAllTransactions(Mockito.<List<BlockTransaction>>any()))
        .thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/v1/transactions/batch")
        .contentType(MediaType.APPLICATION_JSON);

    ObjectMapper objectMapper = new ObjectMapper();
    MockHttpServletRequestBuilder requestBuilder = contentTypeResult
        .content(objectMapper.writeValueAsString(new ArrayList<>()));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(blockTransactionController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link BlockTransactionController#getTransactionsByBlock(Long)}.
   * <p>
   * Method under test: {@link BlockTransactionController#getTransactionsByBlock(Long)}
   */
  @Test
  @DisplayName("Test getTransactionsByBlock(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"org.springframework.http.ResponseEntity BlockTransactionController.getTransactionsByBlock(Long)"})
  void testGetTransactionsByBlock() throws Exception {
    // Arrange
    when(blockTransactionService.getTransactionsByBlock(Mockito.<Long>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/api/v1/transactions/block/{blockNumber}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(blockTransactionController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link BlockTransactionController#getTransactionsBySender(String)}.
   * <p>
   * Method under test: {@link BlockTransactionController#getTransactionsBySender(String)}
   */
  @Test
  @DisplayName("Test getTransactionsBySender(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.http.ResponseEntity BlockTransactionController.getTransactionsBySender(String)"})
  void testGetTransactionsBySender() throws Exception {
    // Arrange
    when(blockTransactionService.getTransactionsBySender(Mockito.<String>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/transactions/sender/{publicKey}",
        "Public Key");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(blockTransactionController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link BlockTransactionController#getTransactionsByReceiver(String)}.
   * <p>
   * Method under test: {@link BlockTransactionController#getTransactionsByReceiver(String)}
   */
  @Test
  @DisplayName("Test getTransactionsByReceiver(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.http.ResponseEntity BlockTransactionController.getTransactionsByReceiver(String)"})
  void testGetTransactionsByReceiver() throws Exception {
    // Arrange
    when(blockTransactionService.getTransactionsByReceiver(Mockito.<String>any())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/api/v1/transactions/receiver/{publicKey}", "Public Key");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(blockTransactionController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link BlockTransactionController#getTransaction(String)}.
   * <p>
   * Method under test: {@link BlockTransactionController#getTransaction(String)}
   */
  @Test
  @DisplayName("Test getTransaction(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"org.springframework.http.ResponseEntity BlockTransactionController.getTransaction(String)"})
  void testGetTransaction() throws Exception {
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
    Optional<BlockTransaction> ofResult = Optional.of(blockTransaction);
    when(blockTransactionService.getTransactionByHash(Mockito.<String>any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/transactions/{hash}", "Hash");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(blockTransactionController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"hash\":\"Hash\",\"amount\":10.0,\"fee\":10.0,\"status\":\"CONFIRMED\",\"gasPrice\":10.0,\"gasLimit\":10.0,\"gasUsed"
                    + "\":10.0,\"blockNumber\":1,\"createdAt\":0.0,\"sender\":\"Public Key\",\"receiver\":\"Public Key\",\"transactionType"
                    + "\":null}"));
  }
}
