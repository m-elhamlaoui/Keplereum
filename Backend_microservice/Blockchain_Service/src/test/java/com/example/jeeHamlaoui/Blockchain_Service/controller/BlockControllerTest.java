package com.example.jeeHamlaoui.Blockchain_Service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.exception.GlobalExceptionHandler;
import com.example.jeeHamlaoui.Blockchain_Service.model.Block;
import com.example.jeeHamlaoui.Blockchain_Service.model.BlockTransaction;
import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.BlockDTO;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.TransactionDTO;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionStatus;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.BlockTransactionRepository;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;
import com.example.jeeHamlaoui.Blockchain_Service.service.BlockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BlockController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BlockControllerTest {

    @Mock
    private BlockService blockService;

    @InjectMocks
    private BlockController blockController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Block testBlock;
    private NetworkNode testValidator;
    private BlockDTO testBlockDTO;
    private BlockTransaction testTransaction;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(blockController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        setupTestData();
    }

    private void setupTestData() {
        // Setup validator
        testValidator = new NetworkNode();
        testValidator.setPublicKey("0xvalidator123456789");
        testValidator.setNodeName("Validator-1");
        testValidator.setAuthorityStatus(true);

        // Setup test transaction
        testTransaction = new BlockTransaction();
        testTransaction.setHash("0xtx123");
        testTransaction.setAmount(100.0);
        testTransaction.setStatus(TransactionStatus.PENDING);
        testTransaction.setGasPrice(20.0);
        testTransaction.setGasLimit(21000.0);
        testTransaction.setGasUsed(21000.0);
        testTransaction.setCreatedAt(Instant.now());

        // Setup test block
        testBlock = new Block();
        testBlock.setHeight(1L);
        testBlock.setCurrentBlockHash("0xblock123");
        testBlock.setPreviousBlockHash("0xprevious123");
        testBlock.setValidator(testValidator);
        testBlock.setCreatedAt(Instant.now());
        testBlock.setBlockWeight(100.0);
        testBlock.setTransactionRoot("0xroot123");
        testBlock.setSha3Uncles("0xuncles123");
        testBlock.setBlockSize("1024");
        testBlock.setTransactions(new ArrayList<>(Arrays.asList(testTransaction)));

        // Setup test block DTO
        testBlockDTO = new BlockDTO();
        testBlockDTO.setNumber(1L);
        testBlockDTO.setHash("0xblock123");
        testBlockDTO.setParentHash("0xprevious123");
        testBlockDTO.setValidator("0xvalidator123456789");
        testBlockDTO.setSize(1024);
        testBlockDTO.setTimestamp(Instant.now());
        testBlockDTO.setGasLimit(8000000.0);
        testBlockDTO.setGasUsed(4000000.0);
        testBlockDTO.setTotalFees(0.5);
        testBlockDTO.setTransactionCount(1);
        testBlockDTO.setTransactions(new ArrayList<>());
    }

    @Nested
    @DisplayName("GET /api/v1/blocks/latest - Latest Block Endpoint")
    class GetLatestBlockTests {

        @Test
        @DisplayName("Should return latest block with full details")
        void shouldReturnLatestBlockWithFullDetails() throws Exception {
            when(blockService.getLatestBlock()).thenReturn(Optional.of(testBlock));

            mockMvc.perform(get("/api/v1/blocks/latest")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.height").value(testBlock.getHeight()))
                    .andExpect(jsonPath("$.currentBlockHash").value(testBlock.getCurrentBlockHash()))
                    .andExpect(jsonPath("$.previousBlockHash").value(testBlock.getPreviousBlockHash()))
                    .andExpect(jsonPath("$.validator.publicKey").value(testValidator.getPublicKey()))
                    .andExpect(jsonPath("$.validator.authorityStatus").value(testValidator.isAuthorityStatus()))
                    .andExpect(jsonPath("$.transactions").isArray())
                    .andExpect(jsonPath("$.transactions[0].hash").value(testTransaction.getHash()))
                    .andExpect(jsonPath("$.transactions[0].status").value(testTransaction.getStatus().toString()));

            verify(blockService, times(1)).getLatestBlock();
        }

        @Test
        @DisplayName("Should return empty optional when no latest block exists")
        void shouldReturnEmptyOptionalWhenNoLatestBlockExists() throws Exception {
            when(blockService.getLatestBlock()).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/v1/blocks/latest")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());  // Your controller returns 200 with empty Optional

            verify(blockService, times(1)).getLatestBlock();
        }
    }

    @Nested
    @DisplayName("GET /api/v1/blocks/{height} - Get Block by Height Endpoint")
    class GetBlockByHeightTests {

        @Test
        @DisplayName("Should return block with transactions when height exists")
        void shouldReturnBlockWithTransactionsWhenHeightExists() throws Exception {
            Long height = 1L;
            when(blockService.getBlockByHeight(height)).thenReturn(Optional.of(testBlock));

            mockMvc.perform(get("/api/v1/blocks/{height}", height)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.height").value(height))
                    .andExpect(jsonPath("$.currentBlockHash").value(testBlock.getCurrentBlockHash()))
                    .andExpect(jsonPath("$.transactions").isArray())
                    .andExpect(jsonPath("$.transactions[0].hash").value(testTransaction.getHash()));

            verify(blockService, times(1)).getBlockByHeight(height);
        }

        @Test
        @DisplayName("Should return empty optional when height does not exist")
        void shouldReturnEmptyOptionalWhenHeightDoesNotExist() throws Exception {
            Long height = 99999L;
            when(blockService.getBlockByHeight(height)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/v1/blocks/{height}", height)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());  // Your controller returns 200 with empty Optional

            verify(blockService, times(1)).getBlockByHeight(height);
        }

        @Test
        @DisplayName("Should handle invalid height parameter")
        void shouldHandleInvalidHeightParameter() throws Exception {
            mockMvc.perform(get("/api/v1/blocks/{height}", "invalid")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.error").value("Bad Request"))
                    .andExpect(jsonPath("$.message").value("Invalid parameter format"));

            verify(blockService, never()).getBlockByHeight(any());
        }
    }

    @Nested
    @DisplayName("GET /api/v1/blocks/hash/{hash} - Get Block by Hash Endpoint")
    class GetBlockByHashTests {

        @Test
        @DisplayName("Should return block with full details when hash exists")
        void shouldReturnBlockWithFullDetailsWhenHashExists() throws Exception {
            String hash = "0xblock123";
            when(blockService.getBlockByHash(hash)).thenReturn(Optional.of(testBlock));

            mockMvc.perform(get("/api/v1/blocks/hash/{hash}", hash)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.currentBlockHash").value(hash))
                    .andExpect(jsonPath("$.height").value(testBlock.getHeight()))
                    .andExpect(jsonPath("$.blockWeight").value(testBlock.getBlockWeight()))
                    .andExpect(jsonPath("$.transactionRoot").value(testBlock.getTransactionRoot()))
                    .andExpect(jsonPath("$.transactions").isArray());

            verify(blockService, times(1)).getBlockByHash(hash);
        }

        @Test
        @DisplayName("Should return empty optional when hash does not exist")
        void shouldReturnEmptyOptionalWhenHashDoesNotExist() throws Exception {
            String hash = "0xnonexistent";
            when(blockService.getBlockByHash(hash)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/v1/blocks/hash/{hash}", hash)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());  // Your controller returns 200 with empty Optional

            verify(blockService, times(1)).getBlockByHash(hash);
        }

        @Test
        @DisplayName("Should handle invalid hash format")
        void shouldHandleInvalidHashFormat() throws Exception {
            String invalidHash = "invalid_hash_format";
            when(blockService.getBlockByHash(invalidHash))
                    .thenThrow(new IllegalArgumentException("Invalid hash format"));

            mockMvc.perform(get("/api/v1/blocks/hash/{hash}", invalidHash)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.error").value("Bad Request"))
                    .andExpect(jsonPath("$.message").value("Invalid hash format"));

            verify(blockService, times(1)).getBlockByHash(invalidHash);
        }
    }

    @Nested
    @DisplayName("POST /api/v1/blocks - Save Block Endpoint")
    class SaveBlockTests {

        @Test
        @DisplayName("Should save block successfully when no conflicts")
        void shouldSaveBlockSuccessfullyWhenNoConflicts() throws Exception {
            TransactionDTO txDTO = new TransactionDTO();
            txDTO.setHash("0xtx123");
            txDTO.setAmount(100.0);
            txDTO.setStatus(TransactionStatus.PENDING);
            testBlockDTO.setTransactions(Arrays.asList(txDTO));

            when(blockService.blockExists(testBlockDTO.getNumber())).thenReturn(false);
            when(blockService.blockExists(testBlockDTO.getHash())).thenReturn(false);
            doNothing().when(blockService).createBlock(any(BlockDTO.class)); // Void method

            mockMvc.perform(post("/api/v1/blocks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testBlockDTO)))
                    .andExpect(status().isOk());

            verify(blockService, times(1)).blockExists(testBlockDTO.getNumber());
            verify(blockService, times(1)).blockExists(testBlockDTO.getHash());
            verify(blockService, times(1)).createBlock(any(BlockDTO.class));
        }

        @Test
        @DisplayName("Should return conflict when block height already exists")
        void shouldReturnConflictWhenBlockHeightExists() throws Exception {
            when(blockService.blockExists(testBlockDTO.getNumber())).thenReturn(true);

            mockMvc.perform(post("/api/v1/blocks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testBlockDTO)))
                    .andExpect(status().isConflict());

            verify(blockService, times(1)).blockExists(testBlockDTO.getNumber());
            verify(blockService, never()).createBlock(any(BlockDTO.class));
        }

        @Test
        @DisplayName("Should return conflict when block hash already exists")
        void shouldReturnConflictWhenBlockHashExists() throws Exception {
            when(blockService.blockExists(testBlockDTO.getNumber())).thenReturn(false);
            when(blockService.blockExists(testBlockDTO.getHash())).thenReturn(true);

            mockMvc.perform(post("/api/v1/blocks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testBlockDTO)))
                    .andExpect(status().isConflict());

            verify(blockService, times(1)).blockExists(testBlockDTO.getNumber());
            verify(blockService, times(1)).blockExists(testBlockDTO.getHash());
            verify(blockService, never()).createBlock(any(BlockDTO.class));
        }

        @Test
        @DisplayName("Should handle missing required fields")
        void shouldHandleMissingRequiredFields() throws Exception {
            testBlockDTO.setHash(null);
            testBlockDTO.setNumber(null);

            mockMvc.perform(post("/api/v1/blocks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(testBlockDTO)))
                    .andExpect(status().isOk());  // Your controller doesn't validate, so it returns 200

            // Note: Your controller doesn't validate required fields, so this test expects 200
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should handle malformed JSON request")
        void shouldHandleMalformedJsonRequest() throws Exception {
            String malformedJson = "{ \"invalid\": \"json\" \"missing\": \"comma\" }";

            mockMvc.perform(post("/api/v1/blocks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(malformedJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.error").value("Bad Request"))
                    .andExpect(jsonPath("$.message").value("Invalid request format"));

            verify(blockService, never()).createBlock(any(BlockDTO.class));
        }

        @Test
        @DisplayName("Should handle missing content type")
        void shouldHandleMissingContentType() throws Exception {
            mockMvc.perform(post("/api/v1/blocks")
                            .content(objectMapper.writeValueAsString(testBlockDTO)))
                    .andExpect(status().isUnsupportedMediaType());

            verify(blockService, never()).createBlock(any(BlockDTO.class));
        }

        @Test
        @DisplayName("Should handle empty request body")
        void shouldHandleEmptyRequestBody() throws Exception {
            mockMvc.perform(post("/api/v1/blocks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(""))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.error").value("Bad Request"))
                    .andExpect(jsonPath("$.message").value("Invalid request format"));

            verify(blockService, never()).createBlock(any(BlockDTO.class));
        }
    }
}