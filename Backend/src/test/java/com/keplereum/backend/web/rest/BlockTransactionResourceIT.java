package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.BlockTransactionAsserts.*;
import static com.keplereum.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keplereum.backend.IntegrationTest;
import com.keplereum.backend.domain.BlockTransaction;
import com.keplereum.backend.domain.enumeration.TransactionStatus;
import com.keplereum.backend.repository.BlockTransactionRepository;
import com.keplereum.backend.repository.search.BlockTransactionSearchRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.util.Streamable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BlockTransactionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BlockTransactionResourceIT {

    private static final String DEFAULT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_TO = "AAAAAAAAAA";
    private static final String UPDATED_TO = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_FEE = 1D;
    private static final Double UPDATED_FEE = 2D;

    private static final TransactionStatus DEFAULT_STATUS = TransactionStatus.CONFIRMED;
    private static final TransactionStatus UPDATED_STATUS = TransactionStatus.PENDING;

    private static final Double DEFAULT_GAS_PRICE = 1D;
    private static final Double UPDATED_GAS_PRICE = 2D;

    private static final Double DEFAULT_GAS_LIMIT = 1D;
    private static final Double UPDATED_GAS_LIMIT = 2D;

    private static final Double DEFAULT_GAS_USED = 1D;
    private static final Double UPDATED_GAS_USED = 2D;

    private static final Long DEFAULT_BLOCK_NUMBER = 1L;
    private static final Long UPDATED_BLOCK_NUMBER = 2L;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/block-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/block-transactions/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BlockTransactionRepository blockTransactionRepository;

    @Autowired
    private BlockTransactionSearchRepository blockTransactionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlockTransactionMockMvc;

    private BlockTransaction blockTransaction;

    private BlockTransaction insertedBlockTransaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlockTransaction createEntity() {
        return new BlockTransaction()
            .hash(DEFAULT_HASH)
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .amount(DEFAULT_AMOUNT)
            .fee(DEFAULT_FEE)
            .status(DEFAULT_STATUS)
            .gasPrice(DEFAULT_GAS_PRICE)
            .gasLimit(DEFAULT_GAS_LIMIT)
            .gasUsed(DEFAULT_GAS_USED)
            .blockNumber(DEFAULT_BLOCK_NUMBER)
            .createdAt(DEFAULT_CREATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlockTransaction createUpdatedEntity() {
        return new BlockTransaction()
            .hash(UPDATED_HASH)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .amount(UPDATED_AMOUNT)
            .fee(UPDATED_FEE)
            .status(UPDATED_STATUS)
            .gasPrice(UPDATED_GAS_PRICE)
            .gasLimit(UPDATED_GAS_LIMIT)
            .gasUsed(UPDATED_GAS_USED)
            .blockNumber(UPDATED_BLOCK_NUMBER)
            .createdAt(UPDATED_CREATED_AT);
    }

    @BeforeEach
    void initTest() {
        blockTransaction = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedBlockTransaction != null) {
            blockTransactionRepository.delete(insertedBlockTransaction);
            blockTransactionSearchRepository.delete(insertedBlockTransaction);
            insertedBlockTransaction = null;
        }
    }

    @Test
    @Transactional
    void createBlockTransaction() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        // Create the BlockTransaction
        var returnedBlockTransaction = om.readValue(
            restBlockTransactionMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(blockTransaction))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BlockTransaction.class
        );

        // Validate the BlockTransaction in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBlockTransactionUpdatableFieldsEquals(returnedBlockTransaction, getPersistedBlockTransaction(returnedBlockTransaction));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedBlockTransaction = returnedBlockTransaction;
    }

    @Test
    @Transactional
    void createBlockTransactionWithExistingId() throws Exception {
        // Create the BlockTransaction with an existing ID
        blockTransaction.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlockTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlockTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkHashIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        // set the field null
        blockTransaction.setHash(null);

        // Create the BlockTransaction, which fails.

        restBlockTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkFromIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        // set the field null
        blockTransaction.setFrom(null);

        // Create the BlockTransaction, which fails.

        restBlockTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkToIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        // set the field null
        blockTransaction.setTo(null);

        // Create the BlockTransaction, which fails.

        restBlockTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        // set the field null
        blockTransaction.setStatus(null);

        // Create the BlockTransaction, which fails.

        restBlockTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        // set the field null
        blockTransaction.setCreatedAt(null);

        // Create the BlockTransaction, which fails.

        restBlockTransactionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllBlockTransactions() throws Exception {
        // Initialize the database
        insertedBlockTransaction = blockTransactionRepository.saveAndFlush(blockTransaction);

        // Get all the blockTransactionList
        restBlockTransactionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blockTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gasPrice").value(hasItem(DEFAULT_GAS_PRICE)))
            .andExpect(jsonPath("$.[*].gasLimit").value(hasItem(DEFAULT_GAS_LIMIT)))
            .andExpect(jsonPath("$.[*].gasUsed").value(hasItem(DEFAULT_GAS_USED)))
            .andExpect(jsonPath("$.[*].blockNumber").value(hasItem(DEFAULT_BLOCK_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getBlockTransaction() throws Exception {
        // Initialize the database
        insertedBlockTransaction = blockTransactionRepository.saveAndFlush(blockTransaction);

        // Get the blockTransaction
        restBlockTransactionMockMvc
            .perform(get(ENTITY_API_URL_ID, blockTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blockTransaction.getId().intValue()))
            .andExpect(jsonPath("$.hash").value(DEFAULT_HASH))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.gasPrice").value(DEFAULT_GAS_PRICE))
            .andExpect(jsonPath("$.gasLimit").value(DEFAULT_GAS_LIMIT))
            .andExpect(jsonPath("$.gasUsed").value(DEFAULT_GAS_USED))
            .andExpect(jsonPath("$.blockNumber").value(DEFAULT_BLOCK_NUMBER.intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBlockTransaction() throws Exception {
        // Get the blockTransaction
        restBlockTransactionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBlockTransaction() throws Exception {
        // Initialize the database
        insertedBlockTransaction = blockTransactionRepository.saveAndFlush(blockTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        blockTransactionSearchRepository.save(blockTransaction);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());

        // Update the blockTransaction
        BlockTransaction updatedBlockTransaction = blockTransactionRepository.findById(blockTransaction.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBlockTransaction are not directly saved in db
        em.detach(updatedBlockTransaction);
        updatedBlockTransaction
            .hash(UPDATED_HASH)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .amount(UPDATED_AMOUNT)
            .fee(UPDATED_FEE)
            .status(UPDATED_STATUS)
            .gasPrice(UPDATED_GAS_PRICE)
            .gasLimit(UPDATED_GAS_LIMIT)
            .gasUsed(UPDATED_GAS_USED)
            .blockNumber(UPDATED_BLOCK_NUMBER)
            .createdAt(UPDATED_CREATED_AT);

        restBlockTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBlockTransaction.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBlockTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BlockTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBlockTransactionToMatchAllProperties(updatedBlockTransaction);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<BlockTransaction> blockTransactionSearchList = Streamable.of(blockTransactionSearchRepository.findAll()).toList();
                BlockTransaction testBlockTransactionSearch = blockTransactionSearchList.get(searchDatabaseSizeAfter - 1);

                assertBlockTransactionAllPropertiesEquals(testBlockTransactionSearch, updatedBlockTransaction);
            });
    }

    @Test
    @Transactional
    void putNonExistingBlockTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        blockTransaction.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlockTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, blockTransaction.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlockTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchBlockTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        blockTransaction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlockTransactionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlockTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBlockTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        blockTransaction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlockTransactionMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlockTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateBlockTransactionWithPatch() throws Exception {
        // Initialize the database
        insertedBlockTransaction = blockTransactionRepository.saveAndFlush(blockTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blockTransaction using partial update
        BlockTransaction partialUpdatedBlockTransaction = new BlockTransaction();
        partialUpdatedBlockTransaction.setId(blockTransaction.getId());

        partialUpdatedBlockTransaction
            .hash(UPDATED_HASH)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .amount(UPDATED_AMOUNT)
            .gasPrice(UPDATED_GAS_PRICE)
            .gasUsed(UPDATED_GAS_USED)
            .blockNumber(UPDATED_BLOCK_NUMBER)
            .createdAt(UPDATED_CREATED_AT);

        restBlockTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlockTransaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBlockTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BlockTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBlockTransactionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBlockTransaction, blockTransaction),
            getPersistedBlockTransaction(blockTransaction)
        );
    }

    @Test
    @Transactional
    void fullUpdateBlockTransactionWithPatch() throws Exception {
        // Initialize the database
        insertedBlockTransaction = blockTransactionRepository.saveAndFlush(blockTransaction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the blockTransaction using partial update
        BlockTransaction partialUpdatedBlockTransaction = new BlockTransaction();
        partialUpdatedBlockTransaction.setId(blockTransaction.getId());

        partialUpdatedBlockTransaction
            .hash(UPDATED_HASH)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .amount(UPDATED_AMOUNT)
            .fee(UPDATED_FEE)
            .status(UPDATED_STATUS)
            .gasPrice(UPDATED_GAS_PRICE)
            .gasLimit(UPDATED_GAS_LIMIT)
            .gasUsed(UPDATED_GAS_USED)
            .blockNumber(UPDATED_BLOCK_NUMBER)
            .createdAt(UPDATED_CREATED_AT);

        restBlockTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlockTransaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBlockTransaction))
            )
            .andExpect(status().isOk());

        // Validate the BlockTransaction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBlockTransactionUpdatableFieldsEquals(
            partialUpdatedBlockTransaction,
            getPersistedBlockTransaction(partialUpdatedBlockTransaction)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBlockTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        blockTransaction.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlockTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, blockTransaction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlockTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBlockTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        blockTransaction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlockTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlockTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBlockTransaction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        blockTransaction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlockTransactionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(blockTransaction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlockTransaction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteBlockTransaction() throws Exception {
        // Initialize the database
        insertedBlockTransaction = blockTransactionRepository.saveAndFlush(blockTransaction);
        blockTransactionRepository.save(blockTransaction);
        blockTransactionSearchRepository.save(blockTransaction);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the blockTransaction
        restBlockTransactionMockMvc
            .perform(delete(ENTITY_API_URL_ID, blockTransaction.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockTransactionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchBlockTransaction() throws Exception {
        // Initialize the database
        insertedBlockTransaction = blockTransactionRepository.saveAndFlush(blockTransaction);
        blockTransactionSearchRepository.save(blockTransaction);

        // Search the blockTransaction
        restBlockTransactionMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + blockTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blockTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].hash").value(hasItem(DEFAULT_HASH)))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gasPrice").value(hasItem(DEFAULT_GAS_PRICE)))
            .andExpect(jsonPath("$.[*].gasLimit").value(hasItem(DEFAULT_GAS_LIMIT)))
            .andExpect(jsonPath("$.[*].gasUsed").value(hasItem(DEFAULT_GAS_USED)))
            .andExpect(jsonPath("$.[*].blockNumber").value(hasItem(DEFAULT_BLOCK_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    protected long getRepositoryCount() {
        return blockTransactionRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected BlockTransaction getPersistedBlockTransaction(BlockTransaction blockTransaction) {
        return blockTransactionRepository.findById(blockTransaction.getId()).orElseThrow();
    }

    protected void assertPersistedBlockTransactionToMatchAllProperties(BlockTransaction expectedBlockTransaction) {
        assertBlockTransactionAllPropertiesEquals(expectedBlockTransaction, getPersistedBlockTransaction(expectedBlockTransaction));
    }

    protected void assertPersistedBlockTransactionToMatchUpdatableProperties(BlockTransaction expectedBlockTransaction) {
        assertBlockTransactionAllUpdatablePropertiesEquals(
            expectedBlockTransaction,
            getPersistedBlockTransaction(expectedBlockTransaction)
        );
    }
}
