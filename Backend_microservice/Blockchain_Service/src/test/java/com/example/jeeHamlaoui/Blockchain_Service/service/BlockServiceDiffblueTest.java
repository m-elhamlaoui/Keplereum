package com.example.jeeHamlaoui.Blockchain_Service.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.Block;
import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.BlockDTO;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockTransactionRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;
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
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BlockService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BlockServiceDiffblueTest {
  @MockitoBean
  private BlockRepository blockRepository;

  @Autowired
  private BlockService blockService;

  @MockitoBean
  private BlockTransactionRepository blockTransactionRepository;

  @MockitoBean
  private NetworkNodeRepository networkNodeRepository;

  /**
   * Test {@link BlockService#getBlockByHeight(Long)}.
   * <ul>
   *   <li>Given {@link NetworkNode#NetworkNode()} AuthorityStatus is {@code true}.</li>
   *   <li>Then return Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#getBlockByHeight(Long)}
   */
  @Test
  @DisplayName("Test getBlockByHeight(Long); given NetworkNode() AuthorityStatus is 'true'; then return Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional BlockService.getBlockByHeight(Long)"})
  void testGetBlockByHeight_givenNetworkNodeAuthorityStatusIsTrue_thenReturnPresent() {
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
    when(blockRepository.findByHeight(Mockito.<Long>any())).thenReturn(block);

    // Act
    Optional<Block> actualBlockByHeight = blockService.getBlockByHeight(1L);

    // Assert
    verify(blockRepository).findByHeight(eq(1L));
    assertTrue(actualBlockByHeight.isPresent());
    assertSame(block, actualBlockByHeight.get());
  }

  /**
   * Test {@link BlockService#getBlockByHeight(Long)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#getBlockByHeight(Long)}
   */
  @Test
  @DisplayName("Test getBlockByHeight(Long); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional BlockService.getBlockByHeight(Long)"})
  void testGetBlockByHeight_thenThrowIllegalStateException() {
    // Arrange
    when(blockRepository.findByHeight(Mockito.<Long>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> blockService.getBlockByHeight(1L));
    verify(blockRepository).findByHeight(eq(1L));
  }

  /**
   * Test {@link BlockService#getBlockByHash(String)}.
   * <ul>
   *   <li>Given {@link NetworkNode#NetworkNode()} AuthorityStatus is {@code true}.</li>
   *   <li>Then return Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#getBlockByHash(String)}
   */
  @Test
  @DisplayName("Test getBlockByHash(String); given NetworkNode() AuthorityStatus is 'true'; then return Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional BlockService.getBlockByHash(String)"})
  void testGetBlockByHash_givenNetworkNodeAuthorityStatusIsTrue_thenReturnPresent() {
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
    when(blockRepository.findByCurrentBlockHash(Mockito.<String>any())).thenReturn(block);

    // Act
    Optional<Block> actualBlockByHash = blockService.getBlockByHash("Hash");

    // Assert
    verify(blockRepository).findByCurrentBlockHash(eq("Hash"));
    assertTrue(actualBlockByHash.isPresent());
    assertSame(block, actualBlockByHash.get());
  }

  /**
   * Test {@link BlockService#getBlockByHash(String)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#getBlockByHash(String)}
   */
  @Test
  @DisplayName("Test getBlockByHash(String); then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Optional BlockService.getBlockByHash(String)"})
  void testGetBlockByHash_thenThrowIllegalStateException() {
    // Arrange
    when(blockRepository.findByCurrentBlockHash(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> blockService.getBlockByHash("Hash"));
    verify(blockRepository).findByCurrentBlockHash(eq("Hash"));
  }

  /**
   * Test {@link BlockService#blockExists(String)} with {@code hash}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#blockExists(String)}
   */
  @Test
  @DisplayName("Test blockExists(String) with 'hash'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BlockService.blockExists(String)"})
  void testBlockExistsWithHash_thenReturnFalse() {
    // Arrange
    when(blockRepository.existsByCurrentBlockHash(Mockito.<String>any())).thenReturn(false);

    // Act
    boolean actualBlockExistsResult = blockService.blockExists("Hash");

    // Assert
    verify(blockRepository).existsByCurrentBlockHash(eq("Hash"));
    assertFalse(actualBlockExistsResult);
  }

  /**
   * Test {@link BlockService#blockExists(String)} with {@code hash}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#blockExists(String)}
   */
  @Test
  @DisplayName("Test blockExists(String) with 'hash'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BlockService.blockExists(String)"})
  void testBlockExistsWithHash_thenReturnTrue() {
    // Arrange
    when(blockRepository.existsByCurrentBlockHash(Mockito.<String>any())).thenReturn(true);

    // Act
    boolean actualBlockExistsResult = blockService.blockExists("Hash");

    // Assert
    verify(blockRepository).existsByCurrentBlockHash(eq("Hash"));
    assertTrue(actualBlockExistsResult);
  }

  /**
   * Test {@link BlockService#blockExists(String)} with {@code hash}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#blockExists(String)}
   */
  @Test
  @DisplayName("Test blockExists(String) with 'hash'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BlockService.blockExists(String)"})
  void testBlockExistsWithHash_thenThrowIllegalStateException() {
    // Arrange
    when(blockRepository.existsByCurrentBlockHash(Mockito.<String>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> blockService.blockExists("Hash"));
    verify(blockRepository).existsByCurrentBlockHash(eq("Hash"));
  }

  /**
   * Test {@link BlockService#blockExists(Long)} with {@code height}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#blockExists(Long)}
   */
  @Test
  @DisplayName("Test blockExists(Long) with 'height'; then return 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BlockService.blockExists(Long)"})
  void testBlockExistsWithHeight_thenReturnFalse() {
    // Arrange
    when(blockRepository.existsByHeight(Mockito.<Long>any())).thenReturn(false);

    // Act
    boolean actualBlockExistsResult = blockService.blockExists(1L);

    // Assert
    verify(blockRepository).existsByHeight(eq(1L));
    assertFalse(actualBlockExistsResult);
  }

  /**
   * Test {@link BlockService#blockExists(Long)} with {@code height}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#blockExists(Long)}
   */
  @Test
  @DisplayName("Test blockExists(Long) with 'height'; then return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BlockService.blockExists(Long)"})
  void testBlockExistsWithHeight_thenReturnTrue() {
    // Arrange
    when(blockRepository.existsByHeight(Mockito.<Long>any())).thenReturn(true);

    // Act
    boolean actualBlockExistsResult = blockService.blockExists(1L);

    // Assert
    verify(blockRepository).existsByHeight(eq(1L));
    assertTrue(actualBlockExistsResult);
  }

  /**
   * Test {@link BlockService#blockExists(Long)} with {@code height}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#blockExists(Long)}
   */
  @Test
  @DisplayName("Test blockExists(Long) with 'height'; then throw IllegalStateException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean BlockService.blockExists(Long)"})
  void testBlockExistsWithHeight_thenThrowIllegalStateException() {
    // Arrange
    when(blockRepository.existsByHeight(Mockito.<Long>any())).thenThrow(new IllegalStateException("foo"));

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> blockService.blockExists(1L));
    verify(blockRepository).existsByHeight(eq(1L));
  }

  /**
   * Test {@link BlockService#createBlock(BlockDTO)}.
   * <ul>
   *   <li>Given {@link BlockRepository} {@link CrudRepository#existsById(Object)} return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#createBlock(BlockDTO)}
   */
  @Test
  @DisplayName("Test createBlock(BlockDTO); given BlockRepository existsById(Object) return 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.Void BlockService.createBlock(BlockDTO)"})
  void testCreateBlock_givenBlockRepositoryExistsByIdReturnTrue() {
    // Arrange
    when(blockRepository.existsById(Mockito.<Long>any())).thenReturn(true);

    BlockDTO blockDTO = new BlockDTO();
    blockDTO.setGasLimit(10.0d);
    blockDTO.setGasUsed(10.0d);
    blockDTO.setHash("Hash");
    blockDTO.setNumber(1L);
    blockDTO.setParentHash("Parent Hash");
    blockDTO.setSha3uncles("Sha3uncles");
    blockDTO.setSize(3);
    blockDTO.setTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    blockDTO.setTotalFees(10.0d);
    blockDTO.setTransactionCount(3);
    blockDTO.setTransactionRoot("Transaction Root");
    blockDTO.setTransactions(new ArrayList<>());
    blockDTO.setValidator("Validator");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> blockService.createBlock(blockDTO));
    verify(blockRepository).existsById(eq(1L));
  }

  /**
   * Test {@link BlockService#createBlock(BlockDTO)}.
   * <ul>
   *   <li>Then calls {@link BlockRepository#existsByCurrentBlockHash(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BlockService#createBlock(BlockDTO)}
   */
  @Test
  @DisplayName("Test createBlock(BlockDTO); then calls existsByCurrentBlockHash(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.Void BlockService.createBlock(BlockDTO)"})
  void testCreateBlock_thenCallsExistsByCurrentBlockHash() {
    // Arrange
    when(blockRepository.existsByCurrentBlockHash(Mockito.<String>any())).thenReturn(true);
    when(blockRepository.existsById(Mockito.<Long>any())).thenReturn(false);

    BlockDTO blockDTO = new BlockDTO();
    blockDTO.setGasLimit(10.0d);
    blockDTO.setGasUsed(10.0d);
    blockDTO.setHash("Hash");
    blockDTO.setNumber(1L);
    blockDTO.setParentHash("Parent Hash");
    blockDTO.setSha3uncles("Sha3uncles");
    blockDTO.setSize(3);
    blockDTO.setTimestamp(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    blockDTO.setTotalFees(10.0d);
    blockDTO.setTransactionCount(3);
    blockDTO.setTransactionRoot("Transaction Root");
    blockDTO.setTransactions(new ArrayList<>());
    blockDTO.setValidator("Validator");

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> blockService.createBlock(blockDTO));
    verify(blockRepository).existsByCurrentBlockHash(eq("Hash"));
    verify(blockRepository).existsById(eq(1L));
  }
}
