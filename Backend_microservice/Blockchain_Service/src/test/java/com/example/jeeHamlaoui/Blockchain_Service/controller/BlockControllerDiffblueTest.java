package com.example.jeeHamlaoui.Blockchain_Service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.Block;
import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.BlockDTO;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockTransactionRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;
import com.example.jeeHamlaoui.Blockchain_Service.service.BlockService;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BlockController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BlockControllerDiffblueTest {
  @Autowired
  private BlockController blockController;

  @MockitoBean
  private BlockService blockService;

  /**
   * Test {@link BlockController#getLatestBlock()}.
   * <p>
   * Method under test: {@link BlockController#getLatestBlock()}
   */
  @Test
  @DisplayName("Test getLatestBlock()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional BlockController.getLatestBlock()"})
  void testGetLatestBlock() throws Exception {
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
    Optional<Block> ofResult = Optional.of(block);
    when(blockService.getLatestBlock()).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/blocks/latest");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(blockController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"height\":1,\"previousBlockHash\":\"Previous Block Hash\",\"currentBlockHash\":\"Current Block Hash\",\"createdAt"
                    + "\":0.0,\"blockWeight\":10.0,\"transactionRoot\":\"Transaction Root\",\"sha3Uncles\":\"Sha3 Uncles\",\"blockSize\":\"Block"
                    + " Size\",\"transactions\":[],\"validator\":{\"publicKey\":\"Public Key\",\"authorityStatus\":true,\"blocksValidated"
                    + "\":1,\"lastActive\":0.0,\"nodeName\":\"Node Name\"}}"));
  }

  /**
   * Test {@link BlockController#getBlockByHeight(Long)}.
   * <p>
   * Method under test: {@link BlockController#getBlockByHeight(Long)}
   */
  @Test
  @DisplayName("Test getBlockByHeight(Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional BlockController.getBlockByHeight(Long)"})
  void testGetBlockByHeight() throws Exception {
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
    Optional<Block> ofResult = Optional.of(block);
    when(blockService.getBlockByHeight(Mockito.<Long>any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/blocks/{height}", 1L);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(blockController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"height\":1,\"previousBlockHash\":\"Previous Block Hash\",\"currentBlockHash\":\"Current Block Hash\",\"createdAt"
                    + "\":0.0,\"blockWeight\":10.0,\"transactionRoot\":\"Transaction Root\",\"sha3Uncles\":\"Sha3 Uncles\",\"blockSize\":\"Block"
                    + " Size\",\"transactions\":[],\"validator\":{\"publicKey\":\"Public Key\",\"authorityStatus\":true,\"blocksValidated"
                    + "\":1,\"lastActive\":0.0,\"nodeName\":\"Node Name\"}}"));
  }

  /**
   * Test {@link BlockController#getBlockByHash(String)}.
   * <p>
   * Method under test: {@link BlockController#getBlockByHash(String)}
   */
  @Test
  @DisplayName("Test getBlockByHash(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional BlockController.getBlockByHash(String)"})
  void testGetBlockByHash() throws Exception {
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
    Optional<Block> ofResult = Optional.of(block);
    when(blockService.getBlockByHash(Mockito.<String>any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/blocks/hash/{hash}", "Hash");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(blockController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"height\":1,\"previousBlockHash\":\"Previous Block Hash\",\"currentBlockHash\":\"Current Block Hash\",\"createdAt"
                    + "\":0.0,\"blockWeight\":10.0,\"transactionRoot\":\"Transaction Root\",\"sha3Uncles\":\"Sha3 Uncles\",\"blockSize\":\"Block"
                    + " Size\",\"transactions\":[],\"validator\":{\"publicKey\":\"Public Key\",\"authorityStatus\":true,\"blocksValidated"
                    + "\":1,\"lastActive\":0.0,\"nodeName\":\"Node Name\"}}"));
  }

  /**
   * Test {@link BlockController#saveBlock(BlockDTO)}.
   * <ul>
   *   <li>Given {@link BlockRepository} {@link BlockRepository#existsByHeight(Long)} return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockController#saveBlock(BlockDTO)}
   */
  @Test
  @DisplayName("Test saveBlock(BlockDTO); given BlockRepository existsByHeight(Long) return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity BlockController.saveBlock(BlockDTO)"})
  void testSaveBlock_givenBlockRepositoryExistsByHeightReturnTrue() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BlockRepository blockRepository = mock(BlockRepository.class);
    when(blockRepository.existsByHeight(Mockito.<Long>any())).thenReturn(true);
    BlockController blockController = new BlockController(
        new BlockService(blockRepository, mock(BlockTransactionRepository.class), mock(NetworkNodeRepository.class)));

    BlockDTO block = new BlockDTO();
    block.setGasLimit(10.0d);
    block.setGasUsed(10.0d);
    block.setHash("Hash");
    block.setNumber(1L);
    block.setParentHash("Parent Hash");
    block.setSha3uncles("Sha3uncles");
    block.setSize(3);
    block.setTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    block.setTotalFees(10.0d);
    block.setTransactionCount(3);
    block.setTransactionRoot("Transaction Root");
    block.setTransactions(new ArrayList<>());
    block.setValidator("Validator");

    // Act
    ResponseEntity<Void> actualSaveBlockResult = blockController.saveBlock(block);

    // Assert
    verify(blockRepository).existsByHeight(eq(1L));
    HttpStatusCode statusCode = actualSaveBlockResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualSaveBlockResult.getBody());
    assertEquals(409, actualSaveBlockResult.getStatusCodeValue());
    assertEquals(HttpStatus.CONFLICT, statusCode);
    assertFalse(actualSaveBlockResult.hasBody());
    assertTrue(actualSaveBlockResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link BlockController#saveBlock(BlockDTO)}.
   * <ul>
   *   <li>Then calls {@link BlockRepository#existsByCurrentBlockHash(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockController#saveBlock(BlockDTO)}
   */
  @Test
  @DisplayName("Test saveBlock(BlockDTO); then calls existsByCurrentBlockHash(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity BlockController.saveBlock(BlockDTO)"})
  void testSaveBlock_thenCallsExistsByCurrentBlockHash() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BlockRepository blockRepository = mock(BlockRepository.class);
    when(blockRepository.existsByCurrentBlockHash(Mockito.<String>any())).thenReturn(true);
    when(blockRepository.existsByHeight(Mockito.<Long>any())).thenReturn(false);
    BlockController blockController = new BlockController(
        new BlockService(blockRepository, mock(BlockTransactionRepository.class), mock(NetworkNodeRepository.class)));

    BlockDTO block = new BlockDTO();
    block.setGasLimit(10.0d);
    block.setGasUsed(10.0d);
    block.setHash("Hash");
    block.setNumber(1L);
    block.setParentHash("Parent Hash");
    block.setSha3uncles("Sha3uncles");
    block.setSize(3);
    block.setTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    block.setTotalFees(10.0d);
    block.setTransactionCount(3);
    block.setTransactionRoot("Transaction Root");
    block.setTransactions(new ArrayList<>());
    block.setValidator("Validator");

    // Act
    ResponseEntity<Void> actualSaveBlockResult = blockController.saveBlock(block);

    // Assert
    verify(blockRepository).existsByCurrentBlockHash(eq("Hash"));
    verify(blockRepository).existsByHeight(eq(1L));
    HttpStatusCode statusCode = actualSaveBlockResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualSaveBlockResult.getBody());
    assertEquals(409, actualSaveBlockResult.getStatusCodeValue());
    assertEquals(HttpStatus.CONFLICT, statusCode);
    assertFalse(actualSaveBlockResult.hasBody());
    assertTrue(actualSaveBlockResult.getHeaders().isEmpty());
  }
}
