package com.example.jeeHamlaoui.Blockchain_Service.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.Block;
import com.example.jeeHamlaoui.Blockchain_Service.model.BlockTransaction;
import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionStatus;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockTransactionRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BlockTransactionService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BlockTransactionServiceDiffblueTest {
    @MockitoBean
    private BlockRepository blockRepository;

    @MockitoBean
    private BlockTransactionRepository blockTransactionRepository;

    @Autowired
    private BlockTransactionService blockTransactionService;

    @MockitoBean
    private NetworkNodeRepository networkNodeRepository;

    /**
     * Test {@link BlockTransactionService#saveTransaction(BlockTransaction)}.
     * <p>
     * Method under test: {@link BlockTransactionService#saveTransaction(BlockTransaction)}
     */
    @Test
    @DisplayName("Test saveTransaction(BlockTransaction)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"BlockTransaction BlockTransactionService.saveTransaction(BlockTransaction)"})
    void testSaveTransaction() {
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
        when(blockTransactionRepository.save(Mockito.<BlockTransaction>any())).thenReturn(blockTransaction);

        NetworkNode validator2 = new NetworkNode();
        validator2.setAuthorityStatus(true);
        validator2.setBlocksValidated(1);
        validator2.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        validator2.setNodeName("Node Name");
        validator2.setPublicKey("Public Key");
        validator2.setReceivedTransactions(new ArrayList<>());
        validator2.setSentTransactions(new ArrayList<>());

        Block block2 = new Block();
        block2.setBlockSize("Block Size");
        block2.setBlockWeight(10.0d);
        block2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        block2.setCurrentBlockHash("Current Block Hash");
        block2.setHeight(1L);
        block2.setPreviousBlockHash("Previous Block Hash");
        block2.setSha3Uncles("Sha3 Uncles");
        block2.setTransactionRoot("Transaction Root");
        block2.setTransactions(new ArrayList<>());
        block2.setValidator(validator2);

        NetworkNode receiver2 = new NetworkNode();
        receiver2.setAuthorityStatus(true);
        receiver2.setBlocksValidated(1);
        receiver2.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        receiver2.setNodeName("Node Name");
        receiver2.setPublicKey("Public Key");
        receiver2.setReceivedTransactions(new ArrayList<>());
        receiver2.setSentTransactions(new ArrayList<>());

        NetworkNode sender2 = new NetworkNode();
        sender2.setAuthorityStatus(true);
        sender2.setBlocksValidated(1);
        sender2.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        sender2.setNodeName("Node Name");
        sender2.setPublicKey("Public Key");
        sender2.setReceivedTransactions(new ArrayList<>());
        sender2.setSentTransactions(new ArrayList<>());

        BlockTransaction transaction = new BlockTransaction();
        transaction.setAmount(10.0d);
        transaction.setBlock(block2);
        transaction.setBlockNumber(1L);
        transaction.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        transaction.setFee(10.0d);
        transaction.setGasLimit(10.0d);
        transaction.setGasPrice(10.0d);
        transaction.setGasUsed(10.0d);
        transaction.setHash("Hash");
        transaction.setReceiver(receiver2);
        transaction.setSender(sender2);
        transaction.setStatus(TransactionStatus.CONFIRMED);

        // Act
        BlockTransaction actualSaveTransactionResult = blockTransactionService.saveTransaction(transaction);

        // Assert
        verify(blockTransactionRepository).save(isA(BlockTransaction.class));
        assertSame(blockTransaction, actualSaveTransactionResult);
    }

    /**
     * Test {@link BlockTransactionService#saveAllTransactions(List)}.
     * <ul>
     *   <li>Given {@link NetworkNode#NetworkNode()} AuthorityStatus is {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link BlockTransactionService#saveAllTransactions(List)}
     */
    @Test
    @DisplayName("Test saveAllTransactions(List); given NetworkNode() AuthorityStatus is 'false'")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List BlockTransactionService.saveAllTransactions(List)"})
    void testSaveAllTransactions_givenNetworkNodeAuthorityStatusIsFalse() {
        // Arrange
        when(blockTransactionRepository.saveAll(Mockito.<Iterable<BlockTransaction>>any())).thenReturn(new ArrayList<>());

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

        NetworkNode validator2 = new NetworkNode();
        validator2.setAuthorityStatus(false);
        validator2.setBlocksValidated(2);
        validator2.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        validator2.setNodeName("42");
        validator2.setPublicKey("42");
        validator2.setReceivedTransactions(new ArrayList<>());
        validator2.setSentTransactions(new ArrayList<>());

        Block block2 = new Block();
        block2.setBlockSize("42");
        block2.setBlockWeight(0.5d);
        block2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        block2.setCurrentBlockHash("42");
        block2.setHeight(0L);
        block2.setPreviousBlockHash("42");
        block2.setSha3Uncles("42");
        block2.setTransactionRoot("42");
        block2.setTransactions(new ArrayList<>());
        block2.setValidator(validator2);

        NetworkNode receiver2 = new NetworkNode();
        receiver2.setAuthorityStatus(false);
        receiver2.setBlocksValidated(2);
        receiver2.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        receiver2.setNodeName("42");
        receiver2.setPublicKey("42");
        receiver2.setReceivedTransactions(new ArrayList<>());
        receiver2.setSentTransactions(new ArrayList<>());

        NetworkNode sender2 = new NetworkNode();
        sender2.setAuthorityStatus(false);
        sender2.setBlocksValidated(2);
        sender2.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        sender2.setNodeName("42");
        sender2.setPublicKey("42");
        sender2.setReceivedTransactions(new ArrayList<>());
        sender2.setSentTransactions(new ArrayList<>());

        BlockTransaction blockTransaction2 = new BlockTransaction();
        blockTransaction2.setAmount(0.5d);
        blockTransaction2.setBlock(block2);
        blockTransaction2.setBlockNumber(0L);
        blockTransaction2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        blockTransaction2.setFee(0.5d);
        blockTransaction2.setGasLimit(0.5d);
        blockTransaction2.setGasPrice(0.5d);
        blockTransaction2.setGasUsed(0.5d);
        blockTransaction2.setHash("42");
        blockTransaction2.setReceiver(receiver2);
        blockTransaction2.setSender(sender2);
        blockTransaction2.setStatus(TransactionStatus.PENDING);

        ArrayList<BlockTransaction> transactions = new ArrayList<>();
        transactions.add(blockTransaction2);
        transactions.add(blockTransaction);

        // Act
        List<BlockTransaction> actualSaveAllTransactionsResult = blockTransactionService.saveAllTransactions(transactions);

        // Assert
        verify(blockTransactionRepository).saveAll(isA(Iterable.class));
        assertTrue(actualSaveAllTransactionsResult.isEmpty());
    }

    /**
     * Test {@link BlockTransactionService#saveAllTransactions(List)}.
     * <ul>
     *   <li>Given {@link NetworkNode#NetworkNode()} AuthorityStatus is {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link BlockTransactionService#saveAllTransactions(List)}
     */
    @Test
    @DisplayName("Test saveAllTransactions(List); given NetworkNode() AuthorityStatus is 'true'")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List BlockTransactionService.saveAllTransactions(List)"})
    void testSaveAllTransactions_givenNetworkNodeAuthorityStatusIsTrue() {
        // Arrange
        when(blockTransactionRepository.saveAll(Mockito.<Iterable<BlockTransaction>>any())).thenReturn(new ArrayList<>());

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

        ArrayList<BlockTransaction> transactions = new ArrayList<>();
        transactions.add(blockTransaction);

        // Act
        List<BlockTransaction> actualSaveAllTransactionsResult = blockTransactionService.saveAllTransactions(transactions);

        // Assert
        verify(blockTransactionRepository).saveAll(isA(Iterable.class));
        assertTrue(actualSaveAllTransactionsResult.isEmpty());
    }

    /**
     * Test {@link BlockTransactionService#saveAllTransactions(List)}.
     * <ul>
     *   <li>When {@link ArrayList#ArrayList()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link BlockTransactionService#saveAllTransactions(List)}
     */
    @Test
    @DisplayName("Test saveAllTransactions(List); when ArrayList()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List BlockTransactionService.saveAllTransactions(List)"})
    void testSaveAllTransactions_whenArrayList() {
        // Arrange
        when(blockTransactionRepository.saveAll(Mockito.<Iterable<BlockTransaction>>any())).thenReturn(new ArrayList<>());

        // Act
        List<BlockTransaction> actualSaveAllTransactionsResult = blockTransactionService
                .saveAllTransactions(new ArrayList<>());

        // Assert
        verify(blockTransactionRepository).saveAll(isA(Iterable.class));
        assertTrue(actualSaveAllTransactionsResult.isEmpty());
    }

    /**
     * Test {@link BlockTransactionService#getTransactionsByBlock(Long)}.
     * <p>
     * Method under test: {@link BlockTransactionService#getTransactionsByBlock(Long)}
     */
    @Test
    @DisplayName("Test getTransactionsByBlock(Long)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List BlockTransactionService.getTransactionsByBlock(Long)"})
    void testGetTransactionsByBlock() {
        // Arrange
        when(blockTransactionRepository.findByBlockNumber(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        List<BlockTransaction> actualTransactionsByBlock = blockTransactionService.getTransactionsByBlock(1L);

        // Assert
        verify(blockTransactionRepository).findByBlockNumber(eq(1L));
        assertTrue(actualTransactionsByBlock.isEmpty());
    }

    /**
     * Test {@link BlockTransactionService#getTransactionsBySender(String)}.
     * <p>
     * Method under test: {@link BlockTransactionService#getTransactionsBySender(String)}
     */
    @Test
    @DisplayName("Test getTransactionsBySender(String)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List BlockTransactionService.getTransactionsBySender(String)"})
    void testGetTransactionsBySender() {
        // Arrange
        when(blockTransactionRepository.findBySenderPublicKey(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<BlockTransaction> actualTransactionsBySender = blockTransactionService.getTransactionsBySender("Public Key");

        // Assert
        verify(blockTransactionRepository).findBySenderPublicKey(eq("Public Key"));
        assertTrue(actualTransactionsBySender.isEmpty());
    }

    /**
     * Test {@link BlockTransactionService#getTransactionsByReceiver(String)}.
     * <p>
     * Method under test: {@link BlockTransactionService#getTransactionsByReceiver(String)}
     */
    @Test
    @DisplayName("Test getTransactionsByReceiver(String)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List BlockTransactionService.getTransactionsByReceiver(String)"})
    void testGetTransactionsByReceiver() {
        // Arrange
        when(blockTransactionRepository.findByReceiverPublicKey(Mockito.<String>any())).thenReturn(new ArrayList<>());

        // Act
        List<BlockTransaction> actualTransactionsByReceiver = blockTransactionService
                .getTransactionsByReceiver("Public Key");

        // Assert
        verify(blockTransactionRepository).findByReceiverPublicKey(eq("Public Key"));
        assertTrue(actualTransactionsByReceiver.isEmpty());
    }

    /**
     * Test {@link BlockTransactionService#getTransactionByHash(String)}.
     * <p>
     * Method under test: {@link BlockTransactionService#getTransactionByHash(String)}
     */
    @Test
    @DisplayName("Test getTransactionByHash(String)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Optional BlockTransactionService.getTransactionByHash(String)"})
    void testGetTransactionByHash() {
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
        when(blockTransactionRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Optional<BlockTransaction> actualTransactionByHash = blockTransactionService.getTransactionByHash("Hash");

        // Assert
        verify(blockTransactionRepository).findById(eq("Hash"));
        assertSame(ofResult, actualTransactionByHash);
    }
}
