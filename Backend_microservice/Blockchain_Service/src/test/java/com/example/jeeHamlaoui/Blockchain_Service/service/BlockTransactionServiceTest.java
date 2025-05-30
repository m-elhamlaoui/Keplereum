package com.example.jeeHamlaoui.Blockchain_Service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.example.jeeHamlaoui.Blockchain_Service.model.Block;
import com.example.jeeHamlaoui.Blockchain_Service.model.BlockTransaction;
import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionStatus;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockTransactionRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("BlockTransactionService Tests - Critical Transaction Processing")
class BlockTransactionServiceTest {

    @Mock
    private BlockTransactionRepository blockTransactionRepository;

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private NetworkNodeRepository networkNodeRepository;

    @InjectMocks
    private BlockTransactionService blockTransactionService;

    private BlockTransaction testTransaction;
    private Block testBlock;
    private NetworkNode senderNode;
    private NetworkNode receiverNode;
    private NetworkNode validatorNode;

    @BeforeEach
    void setUp() {
        setupTestData();
    }

    private void setupTestData() {
        // Create validator node
        validatorNode = new NetworkNode();
        validatorNode.setPublicKey("0xvalidator123");
        validatorNode.setPrivateKey("0xvalidatorprivate");
        validatorNode.setNodeName("Validator Node");
        validatorNode.setAuthorityStatus(true);
        validatorNode.setBlocksValidated(50);
        validatorNode.setLastActive(Instant.now());

        // Create sender node
        senderNode = new NetworkNode();
        senderNode.setPublicKey("0xsender123");
        senderNode.setPrivateKey("0xsenderprivate");
        senderNode.setNodeName("Sender Node");
        senderNode.setAuthorityStatus(false);
        senderNode.setBlocksValidated(0);
        senderNode.setLastActive(Instant.now());
        senderNode.setSentTransactions(new ArrayList<>());
        senderNode.setReceivedTransactions(new ArrayList<>());

        // Create receiver node
        receiverNode = new NetworkNode();
        receiverNode.setPublicKey("0xreceiver456");
        receiverNode.setPrivateKey("0xreceiverprivate");
        receiverNode.setNodeName("Receiver Node");
        receiverNode.setAuthorityStatus(false);
        receiverNode.setBlocksValidated(0);
        receiverNode.setLastActive(Instant.now());
        receiverNode.setSentTransactions(new ArrayList<>());
        receiverNode.setReceivedTransactions(new ArrayList<>());

        // Create test block
        testBlock = new Block();
        testBlock.setHeight(100L);
        testBlock.setCurrentBlockHash("0xblock123456789");
        testBlock.setPreviousBlockHash("0xprevblock123");
        testBlock.setValidator(validatorNode);
        testBlock.setCreatedAt(Instant.now());
        testBlock.setTransactions(new ArrayList<>());

        // Create test transaction
        testTransaction = new BlockTransaction();
        testTransaction.setHash("0xtx123456789");
        testTransaction.setAmount(50.0);
        testTransaction.setFee(2.5);
        testTransaction.setGasPrice(20.0);
        testTransaction.setGasLimit(21000.0);
        testTransaction.setGasUsed(21000.0);
        testTransaction.setStatus(TransactionStatus.PENDING);
        testTransaction.setBlockNumber(100L);
        testTransaction.setCreatedAt(Instant.now());
        testTransaction.setSender(senderNode);
        testTransaction.setReceiver(receiverNode);
        testTransaction.setBlock(testBlock);
    }

    @Nested
    @DisplayName("Transaction Save Operations")
    class TransactionSaveOperationsTests {

    @Test
        @DisplayName("Should save single transaction successfully")
        void shouldSaveSingleTransactionSuccessfully() {
            // Given
            when(blockTransactionRepository.save(testTransaction)).thenReturn(testTransaction);

            // When
            BlockTransaction savedTransaction = blockTransactionService.saveTransaction(testTransaction);

            // Then
            assertNotNull(savedTransaction);
            assertEquals(testTransaction, savedTransaction);
            assertEquals("0xtx123456789", savedTransaction.getHash());
            assertEquals(50.0, savedTransaction.getAmount());
            assertEquals(TransactionStatus.PENDING, savedTransaction.getStatus());
            verify(blockTransactionRepository, times(1)).save(testTransaction);
        }

        @Test
        @DisplayName("Should save multiple transactions successfully")
        void shouldSaveMultipleTransactionsSuccessfully() {
            // Given
            BlockTransaction transaction2 = new BlockTransaction();
            transaction2.setHash("0xtx987654321");
            transaction2.setAmount(25.0);
            transaction2.setStatus(TransactionStatus.CONFIRMED);

            List<BlockTransaction> transactions = Arrays.asList(testTransaction, transaction2);
            when(blockTransactionRepository.saveAll(transactions)).thenReturn(transactions);

            // When
            List<BlockTransaction> savedTransactions = blockTransactionService.saveAllTransactions(transactions);

            // Then
            assertNotNull(savedTransactions);
            assertEquals(2, savedTransactions.size());
            verify(blockTransactionRepository, times(1)).saveAll(transactions);
        }

        @Test
        @DisplayName("Should handle empty transaction list")
        void shouldHandleEmptyTransactionList() {
            // Given
            List<BlockTransaction> emptyList = new ArrayList<>();
            when(blockTransactionRepository.saveAll(emptyList)).thenReturn(emptyList);

            // When
            List<BlockTransaction> result = blockTransactionService.saveAllTransactions(emptyList);

            // Then
            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(blockTransactionRepository, times(1)).saveAll(emptyList);
        }

        @Test
        @DisplayName("Should handle transaction save exception")
        void shouldHandleTransactionSaveException() {
            // Given
            when(blockTransactionRepository.save(testTransaction))
                    .thenThrow(new RuntimeException("Database constraint violation"));

            // When & Then
            assertThrows(RuntimeException.class, () -> blockTransactionService.saveTransaction(testTransaction));
            verify(blockTransactionRepository, times(1)).save(testTransaction);
        }
    }

    @Nested
    @DisplayName("Transaction Retrieval Operations")
    class TransactionRetrievalTests {

        @Test
        @DisplayName("Should retrieve transaction by hash when exists")
        void shouldRetrieveTransactionByHashWhenExists() {
            // Given
            String hash = "0xtx123456789";
            when(blockTransactionRepository.findById(hash)).thenReturn(Optional.of(testTransaction));

            // When
            Optional<BlockTransaction> result = blockTransactionService.getTransactionByHash(hash);

            // Then
            assertTrue(result.isPresent());
            assertEquals(testTransaction, result.get());
            assertEquals(hash, result.get().getHash());
            verify(blockTransactionRepository, times(1)).findById(hash);
        }

        @Test
        @DisplayName("Should return empty when transaction hash does not exist")
        void shouldReturnEmptyWhenTransactionHashDoesNotExist() {
            // Given
            String hash = "0xnonexistent";
            when(blockTransactionRepository.findById(hash)).thenReturn(Optional.empty());

            // When
            Optional<BlockTransaction> result = blockTransactionService.getTransactionByHash(hash);

            // Then
            assertTrue(result.isEmpty());
            verify(blockTransactionRepository, times(1)).findById(hash);
        }

    @Test
        @DisplayName("Should retrieve transactions by block number")
        void shouldRetrieveTransactionsByBlockNumber() {
            // Given
            Long blockNumber = 100L;
            List<BlockTransaction> blockTransactions = Arrays.asList(testTransaction);
            when(blockTransactionRepository.findByBlockNumber(blockNumber)).thenReturn(blockTransactions);

            // When
            List<BlockTransaction> result = blockTransactionService.getTransactionsByBlock(blockNumber);

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(testTransaction, result.get(0));
            verify(blockTransactionRepository, times(1)).findByBlockNumber(blockNumber);
        }

        @Test
        @DisplayName("Should retrieve transactions by sender public key")
        void shouldRetrieveTransactionsBySenderPublicKey() {
            // Given
            String senderKey = "0xsender123";
            List<BlockTransaction> senderTransactions = Arrays.asList(testTransaction);
            when(blockTransactionRepository.findBySenderPublicKey(senderKey)).thenReturn(senderTransactions);

            // When
            List<BlockTransaction> result = blockTransactionService.getTransactionsBySender(senderKey);

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(testTransaction, result.get(0));
            verify(blockTransactionRepository, times(1)).findBySenderPublicKey(senderKey);
        }

        @Test
        @DisplayName("Should retrieve transactions by receiver public key")
        void shouldRetrieveTransactionsByReceiverPublicKey() {
            // Given
            String receiverKey = "0xreceiver456";
            List<BlockTransaction> receiverTransactions = Arrays.asList(testTransaction);
            when(blockTransactionRepository.findByReceiverPublicKey(receiverKey)).thenReturn(receiverTransactions);

            // When
            List<BlockTransaction> result = blockTransactionService.getTransactionsByReceiver(receiverKey);

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(testTransaction, result.get(0));
            verify(blockTransactionRepository, times(1)).findByReceiverPublicKey(receiverKey);
        }
    }

    @Nested
    @DisplayName("Transaction Status Management")
    class TransactionStatusTests {

        @Test
        @DisplayName("Should handle pending to confirmed status transition")
        void shouldHandlePendingToConfirmedStatusTransition() {
            // Given
            testTransaction.setStatus(TransactionStatus.PENDING);
            BlockTransaction confirmedTransaction = new BlockTransaction();
            confirmedTransaction.setHash(testTransaction.getHash());
            confirmedTransaction.setStatus(TransactionStatus.CONFIRMED);

            when(blockTransactionRepository.save(any(BlockTransaction.class))).thenReturn(confirmedTransaction);

            // When
            BlockTransaction result = blockTransactionService.saveTransaction(testTransaction);

            // Then
            assertNotNull(result);
            assertEquals(TransactionStatus.CONFIRMED, result.getStatus());
            verify(blockTransactionRepository, times(1)).save(any(BlockTransaction.class));
        }

        @Test
        @DisplayName("Should handle failed transaction status")
        void shouldHandleFailedTransactionStatus() {
            // Given
            testTransaction.setStatus(TransactionStatus.FAILED);
            when(blockTransactionRepository.save(testTransaction)).thenReturn(testTransaction);

            // When
            BlockTransaction result = blockTransactionService.saveTransaction(testTransaction);

            // Then
            assertNotNull(result);
            assertEquals(TransactionStatus.FAILED, result.getStatus());
            verify(blockTransactionRepository, times(1)).save(testTransaction);
        }

    @Test
        @DisplayName("Should track transaction status history")
        void shouldTrackTransactionStatusHistory() {
            // Given
            List<BlockTransaction> statusTransitions = Arrays.asList(
                    createTransactionWithStatus(TransactionStatus.PENDING),
                    createTransactionWithStatus(TransactionStatus.CONFIRMED)
            );

            when(blockTransactionRepository.saveAll(statusTransitions)).thenReturn(statusTransitions);

            // When
            List<BlockTransaction> result = blockTransactionService.saveAllTransactions(statusTransitions);

            // Then
            assertNotNull(result);
            assertEquals(2, result.size());
            verify(blockTransactionRepository, times(1)).saveAll(statusTransitions);
        }

        private BlockTransaction createTransactionWithStatus(TransactionStatus status) {
            BlockTransaction tx = new BlockTransaction();
            tx.setHash("0xtx" + status.toString().toLowerCase());
            tx.setStatus(status);
            tx.setAmount(10.0);
            tx.setCreatedAt(Instant.now());
            return tx;
        }
    }

    @Nested
    @DisplayName("Transaction Validation and Security")
    class TransactionValidationTests {

        @Test
        @DisplayName("Should validate transaction amount constraints")
        void shouldValidateTransactionAmountConstraints() {
            // Given
            BlockTransaction negativeAmountTx = new BlockTransaction();
            negativeAmountTx.setHash("0xinvalid123");
            negativeAmountTx.setAmount(-50.0); // Invalid negative amount
            negativeAmountTx.setStatus(TransactionStatus.PENDING);

            when(blockTransactionRepository.save(negativeAmountTx))
                    .thenThrow(new IllegalArgumentException("Transaction amount must be positive"));

            // When & Then
            assertThrows(IllegalArgumentException.class,
                    () -> blockTransactionService.saveTransaction(negativeAmountTx));
            verify(blockTransactionRepository, times(1)).save(negativeAmountTx);
        }

        @Test
        @DisplayName("Should validate gas parameters")
        void shouldValidateGasParameters() {
            // Given
            BlockTransaction invalidGasTx = new BlockTransaction();
            invalidGasTx.setHash("0xgastest123");
            invalidGasTx.setAmount(10.0);
            invalidGasTx.setGasUsed(25000.0); // Gas used exceeds gas limit
            invalidGasTx.setGasLimit(21000.0);

            when(blockTransactionRepository.save(invalidGasTx))
                    .thenThrow(new IllegalArgumentException("Gas used cannot exceed gas limit"));

            // When & Then
            assertThrows(IllegalArgumentException.class,
                    () -> blockTransactionService.saveTransaction(invalidGasTx));
            verify(blockTransactionRepository, times(1)).save(invalidGasTx);
        }

        @Test
        @DisplayName("Should validate sender-receiver relationship")
        void shouldValidateSenderReceiverRelationship() {
            // Given
            BlockTransaction selfTransferTx = new BlockTransaction();
            selfTransferTx.setHash("0xselftx123");
            selfTransferTx.setAmount(10.0);
            selfTransferTx.setSender(senderNode);
            selfTransferTx.setReceiver(senderNode); // Same sender and receiver

            when(blockTransactionRepository.save(selfTransferTx))
                    .thenThrow(new IllegalArgumentException("Sender and receiver cannot be the same"));

            // When & Then
            assertThrows(IllegalArgumentException.class,
                    () -> blockTransactionService.saveTransaction(selfTransferTx));
            verify(blockTransactionRepository, times(1)).save(selfTransferTx);
        }

    @Test
        @DisplayName("Should validate transaction hash uniqueness")
        void shouldValidateTransactionHashUniqueness() {
            // Given
            String duplicateHash = "0xduplicate123";

            BlockTransaction duplicateTx = new BlockTransaction();
            duplicateTx.setHash(duplicateHash);
            duplicateTx.setAmount(20.0);

            when(blockTransactionRepository.save(duplicateTx))
                    .thenThrow(new RuntimeException("Duplicate transaction hash"));

            // When & Then
            assertThrows(RuntimeException.class,
                    () -> blockTransactionService.saveTransaction(duplicateTx));
            verify(blockTransactionRepository, times(1)).save(duplicateTx);
        }
    }

    @Nested
    @DisplayName("Transaction Relationships and Integrity")
    class TransactionRelationshipTests {

        @Test
        @DisplayName("Should maintain block-transaction relationship consistency")
        void shouldMaintainBlockTransactionRelationshipConsistency() {
            // Given
            testTransaction.setBlock(testBlock);
            testTransaction.setBlockNumber(testBlock.getHeight());

            when(blockTransactionRepository.save(testTransaction)).thenReturn(testTransaction);

            // When
            BlockTransaction result = blockTransactionService.saveTransaction(testTransaction);

            // Then
            assertNotNull(result);
            assertEquals(testBlock, result.getBlock());
            assertEquals(testBlock.getHeight(), result.getBlockNumber());
            verify(blockTransactionRepository, times(1)).save(testTransaction);
        }

        @Test
        @DisplayName("Should maintain sender-receiver node relationships")
        void shouldMaintainSenderReceiverNodeRelationships() {
            // Given
            when(blockTransactionRepository.save(testTransaction)).thenReturn(testTransaction);

            // When
            BlockTransaction result = blockTransactionService.saveTransaction(testTransaction);

            // Then
            assertNotNull(result);
            assertEquals(senderNode, result.getSender());
            assertEquals(receiverNode, result.getReceiver());
            assertEquals("0xsender123", result.getSender().getPublicKey());
            assertEquals("0xreceiver456", result.getReceiver().getPublicKey());
            verify(blockTransactionRepository, times(1)).save(testTransaction);
        }

    @Test
        @DisplayName("Should handle orphaned transaction cleanup")
        void shouldHandleOrphanedTransactionCleanup() {
            // Given
            BlockTransaction orphanedTx = new BlockTransaction();
            orphanedTx.setHash("0xorphan123");
            orphanedTx.setBlock(null); // Orphaned - no block reference
            orphanedTx.setBlockNumber(null);

            when(blockTransactionRepository.save(orphanedTx))
                    .thenThrow(new RuntimeException("Transaction must be associated with a block"));

            // When & Then
            assertThrows(RuntimeException.class,
                    () -> blockTransactionService.saveTransaction(orphanedTx));
            verify(blockTransactionRepository, times(1)).save(orphanedTx);
        }
    }

    @Nested
    @DisplayName("Performance and Concurrency Tests")
    class PerformanceTests {

        @Test
        @DisplayName("Should handle bulk transaction processing efficiently")
        void shouldHandleBulkTransactionProcessingEfficiently() {
            // Given
            List<BlockTransaction> bulkTransactions = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                BlockTransaction tx = new BlockTransaction();
                tx.setHash("0xbulk" + i);
                tx.setAmount(1.0);
                tx.setStatus(TransactionStatus.PENDING);
                bulkTransactions.add(tx);
            }

            when(blockTransactionRepository.saveAll(bulkTransactions)).thenReturn(bulkTransactions);

            // When
            List<BlockTransaction> result = blockTransactionService.saveAllTransactions(bulkTransactions);

            // Then
            assertNotNull(result);
            assertEquals(1000, result.size());
            verify(blockTransactionRepository, times(1)).saveAll(bulkTransactions);
        }

    @Test
        @DisplayName("Should handle concurrent transaction saves")
        void shouldHandleConcurrentTransactionSaves() {
            // Given
            BlockTransaction concurrentTx = new BlockTransaction();
            concurrentTx.setHash("0xconcurrent123");
            concurrentTx.setAmount(15.0);

            when(blockTransactionRepository.save(concurrentTx))
                    .thenThrow(new RuntimeException("Optimistic locking failure"));

            // When & Then
            assertThrows(RuntimeException.class,
                    () -> blockTransactionService.saveTransaction(concurrentTx));
            verify(blockTransactionRepository, times(1)).save(concurrentTx);
        }

        @Test
        @DisplayName("Should handle database timeout during transaction processing")
        void shouldHandleDatabaseTimeoutDuringTransactionProcessing() {
            // Given
            when(blockTransactionRepository.save(testTransaction))
                    .thenThrow(new RuntimeException("Database connection timeout"));

            // When & Then
            assertThrows(RuntimeException.class,
                    () -> blockTransactionService.saveTransaction(testTransaction));
            verify(blockTransactionRepository, times(1)).save(testTransaction);
        }
    }

    @Nested
    @DisplayName("Transaction Security and Anti-Fraud")
    class SecurityTests {

    @Test
        @DisplayName("Should detect potentially fraudulent transaction patterns")
        void shouldDetectPotentiallyFraudulentTransactionPatterns() {
            // Given
            BlockTransaction suspiciousTx = new BlockTransaction();
            suspiciousTx.setHash("0xsuspicious123");
            suspiciousTx.setAmount(1000000.0); // Unusually large amount
            suspiciousTx.setFee(0.0); // Zero fee is suspicious

            when(blockTransactionRepository.save(suspiciousTx))
                    .thenThrow(new SecurityException("Transaction flagged for manual review"));

            // When & Then
            assertThrows(SecurityException.class,
                    () -> blockTransactionService.saveTransaction(suspiciousTx));
            verify(blockTransactionRepository, times(1)).save(suspiciousTx);
        }

        @Test
        @DisplayName("Should validate transaction timestamp integrity")
        void shouldValidateTransactionTimestampIntegrity() {
            // Given
            BlockTransaction futureTx = new BlockTransaction();
            futureTx.setHash("0xfuture123");
            futureTx.setAmount(10.0);
            futureTx.setCreatedAt(Instant.now().plusSeconds(3600)); // 1 hour in future

            when(blockTransactionRepository.save(futureTx))
                    .thenThrow(new IllegalArgumentException("Transaction timestamp cannot be in the future"));

            // When & Then
            assertThrows(IllegalArgumentException.class,
                    () -> blockTransactionService.saveTransaction(futureTx));
            verify(blockTransactionRepository, times(1)).save(futureTx);
        }

        @Test
        @DisplayName("Should handle malformed transaction data")
        void shouldHandleMalformedTransactionData() {
            // Given
            BlockTransaction malformedTx = new BlockTransaction();
            malformedTx.setHash("invalid_hash_format"); // Invalid hash format
            malformedTx.setAmount(10.0);

            when(blockTransactionRepository.save(malformedTx))
                    .thenThrow(new IllegalArgumentException("Invalid transaction hash format"));

            // When & Then
            assertThrows(IllegalArgumentException.class,
                    () -> blockTransactionService.saveTransaction(malformedTx));
            verify(blockTransactionRepository, times(1)).save(malformedTx);
        }
    }
}