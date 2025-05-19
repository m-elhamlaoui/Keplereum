package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.BlockAsserts.*;
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
import com.keplereum.backend.domain.Block;
import com.keplereum.backend.repository.BlockRepository;
import com.keplereum.backend.repository.search.BlockSearchRepository;
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
 * Integration tests for the {@link BlockResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BlockResourceIT {

    private static final Long DEFAULT_HEIGHT = 1L;
    private static final Long UPDATED_HEIGHT = 2L;

    private static final String DEFAULT_PREVIOUS_BLOCK_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_BLOCK_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_BLOCK_HASH = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_BLOCK_HASH = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/blocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/blocks/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private BlockSearchRepository blockSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlockMockMvc;

    private Block block;

    private Block insertedBlock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Block createEntity() {
        return new Block()
            .height(DEFAULT_HEIGHT)
            .previousBlockHash(DEFAULT_PREVIOUS_BLOCK_HASH)
            .currentBlockHash(DEFAULT_CURRENT_BLOCK_HASH)
            .createdAt(DEFAULT_CREATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Block createUpdatedEntity() {
        return new Block()
            .height(UPDATED_HEIGHT)
            .previousBlockHash(UPDATED_PREVIOUS_BLOCK_HASH)
            .currentBlockHash(UPDATED_CURRENT_BLOCK_HASH)
            .createdAt(UPDATED_CREATED_AT);
    }

    @BeforeEach
    void initTest() {
        block = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedBlock != null) {
            blockRepository.delete(insertedBlock);
            blockSearchRepository.delete(insertedBlock);
            insertedBlock = null;
        }
    }

    @Test
    @Transactional
    void createBlock() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        // Create the Block
        var returnedBlock = om.readValue(
            restBlockMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(block)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Block.class
        );

        // Validate the Block in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertBlockUpdatableFieldsEquals(returnedBlock, getPersistedBlock(returnedBlock));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedBlock = returnedBlock;
    }

    @Test
    @Transactional
    void createBlockWithExistingId() throws Exception {
        // Create the Block with an existing ID
        block.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlockMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(block)))
            .andExpect(status().isBadRequest());

        // Validate the Block in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkHeightIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        // set the field null
        block.setHeight(null);

        // Create the Block, which fails.

        restBlockMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(block)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkPreviousBlockHashIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        // set the field null
        block.setPreviousBlockHash(null);

        // Create the Block, which fails.

        restBlockMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(block)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkCurrentBlockHashIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        // set the field null
        block.setCurrentBlockHash(null);

        // Create the Block, which fails.

        restBlockMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(block)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        // set the field null
        block.setCreatedAt(null);

        // Create the Block, which fails.

        restBlockMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(block)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllBlocks() throws Exception {
        // Initialize the database
        insertedBlock = blockRepository.saveAndFlush(block);

        // Get all the blockList
        restBlockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(block.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].previousBlockHash").value(hasItem(DEFAULT_PREVIOUS_BLOCK_HASH)))
            .andExpect(jsonPath("$.[*].currentBlockHash").value(hasItem(DEFAULT_CURRENT_BLOCK_HASH)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getBlock() throws Exception {
        // Initialize the database
        insertedBlock = blockRepository.saveAndFlush(block);

        // Get the block
        restBlockMockMvc
            .perform(get(ENTITY_API_URL_ID, block.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(block.getId().intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.intValue()))
            .andExpect(jsonPath("$.previousBlockHash").value(DEFAULT_PREVIOUS_BLOCK_HASH))
            .andExpect(jsonPath("$.currentBlockHash").value(DEFAULT_CURRENT_BLOCK_HASH))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBlock() throws Exception {
        // Get the block
        restBlockMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBlock() throws Exception {
        // Initialize the database
        insertedBlock = blockRepository.saveAndFlush(block);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        blockSearchRepository.save(block);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());

        // Update the block
        Block updatedBlock = blockRepository.findById(block.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBlock are not directly saved in db
        em.detach(updatedBlock);
        updatedBlock
            .height(UPDATED_HEIGHT)
            .previousBlockHash(UPDATED_PREVIOUS_BLOCK_HASH)
            .currentBlockHash(UPDATED_CURRENT_BLOCK_HASH)
            .createdAt(UPDATED_CREATED_AT);

        restBlockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBlock.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedBlock))
            )
            .andExpect(status().isOk());

        // Validate the Block in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBlockToMatchAllProperties(updatedBlock);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Block> blockSearchList = Streamable.of(blockSearchRepository.findAll()).toList();
                Block testBlockSearch = blockSearchList.get(searchDatabaseSizeAfter - 1);

                assertBlockAllPropertiesEquals(testBlockSearch, updatedBlock);
            });
    }

    @Test
    @Transactional
    void putNonExistingBlock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        block.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, block.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(block))
            )
            .andExpect(status().isBadRequest());

        // Validate the Block in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchBlock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        block.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(block))
            )
            .andExpect(status().isBadRequest());

        // Validate the Block in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBlock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        block.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlockMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(block)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Block in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateBlockWithPatch() throws Exception {
        // Initialize the database
        insertedBlock = blockRepository.saveAndFlush(block);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the block using partial update
        Block partialUpdatedBlock = new Block();
        partialUpdatedBlock.setId(block.getId());

        partialUpdatedBlock
            .height(UPDATED_HEIGHT)
            .previousBlockHash(UPDATED_PREVIOUS_BLOCK_HASH)
            .currentBlockHash(UPDATED_CURRENT_BLOCK_HASH);

        restBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlock.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBlock))
            )
            .andExpect(status().isOk());

        // Validate the Block in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBlockUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBlock, block), getPersistedBlock(block));
    }

    @Test
    @Transactional
    void fullUpdateBlockWithPatch() throws Exception {
        // Initialize the database
        insertedBlock = blockRepository.saveAndFlush(block);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the block using partial update
        Block partialUpdatedBlock = new Block();
        partialUpdatedBlock.setId(block.getId());

        partialUpdatedBlock
            .height(UPDATED_HEIGHT)
            .previousBlockHash(UPDATED_PREVIOUS_BLOCK_HASH)
            .currentBlockHash(UPDATED_CURRENT_BLOCK_HASH)
            .createdAt(UPDATED_CREATED_AT);

        restBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlock.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBlock))
            )
            .andExpect(status().isOk());

        // Validate the Block in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBlockUpdatableFieldsEquals(partialUpdatedBlock, getPersistedBlock(partialUpdatedBlock));
    }

    @Test
    @Transactional
    void patchNonExistingBlock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        block.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, block.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(block))
            )
            .andExpect(status().isBadRequest());

        // Validate the Block in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBlock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        block.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(block))
            )
            .andExpect(status().isBadRequest());

        // Validate the Block in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBlock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        block.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlockMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(block)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Block in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteBlock() throws Exception {
        // Initialize the database
        insertedBlock = blockRepository.saveAndFlush(block);
        blockRepository.save(block);
        blockSearchRepository.save(block);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the block
        restBlockMockMvc
            .perform(delete(ENTITY_API_URL_ID, block.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(blockSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchBlock() throws Exception {
        // Initialize the database
        insertedBlock = blockRepository.saveAndFlush(block);
        blockSearchRepository.save(block);

        // Search the block
        restBlockMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + block.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(block.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].previousBlockHash").value(hasItem(DEFAULT_PREVIOUS_BLOCK_HASH)))
            .andExpect(jsonPath("$.[*].currentBlockHash").value(hasItem(DEFAULT_CURRENT_BLOCK_HASH)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    protected long getRepositoryCount() {
        return blockRepository.count();
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

    protected Block getPersistedBlock(Block block) {
        return blockRepository.findById(block.getId()).orElseThrow();
    }

    protected void assertPersistedBlockToMatchAllProperties(Block expectedBlock) {
        assertBlockAllPropertiesEquals(expectedBlock, getPersistedBlock(expectedBlock));
    }

    protected void assertPersistedBlockToMatchUpdatableProperties(Block expectedBlock) {
        assertBlockAllUpdatablePropertiesEquals(expectedBlock, getPersistedBlock(expectedBlock));
    }
}
