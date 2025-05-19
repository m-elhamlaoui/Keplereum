package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.ConfirmationAsserts.*;
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
import com.keplereum.backend.domain.Confirmation;
import com.keplereum.backend.repository.ConfirmationRepository;
import com.keplereum.backend.repository.search.ConfirmationSearchRepository;
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
 * Integration tests for the {@link ConfirmationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConfirmationResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/confirmations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/confirmations/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @Autowired
    private ConfirmationSearchRepository confirmationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConfirmationMockMvc;

    private Confirmation confirmation;

    private Confirmation insertedConfirmation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Confirmation createEntity() {
        return new Confirmation().createdAt(DEFAULT_CREATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Confirmation createUpdatedEntity() {
        return new Confirmation().createdAt(UPDATED_CREATED_AT);
    }

    @BeforeEach
    void initTest() {
        confirmation = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedConfirmation != null) {
            confirmationRepository.delete(insertedConfirmation);
            confirmationSearchRepository.delete(insertedConfirmation);
            insertedConfirmation = null;
        }
    }

    @Test
    @Transactional
    void createConfirmation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        // Create the Confirmation
        var returnedConfirmation = om.readValue(
            restConfirmationMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(confirmation))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Confirmation.class
        );

        // Validate the Confirmation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertConfirmationUpdatableFieldsEquals(returnedConfirmation, getPersistedConfirmation(returnedConfirmation));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedConfirmation = returnedConfirmation;
    }

    @Test
    @Transactional
    void createConfirmationWithExistingId() throws Exception {
        // Create the Confirmation with an existing ID
        confirmation.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfirmationMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(confirmation)))
            .andExpect(status().isBadRequest());

        // Validate the Confirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        // set the field null
        confirmation.setCreatedAt(null);

        // Create the Confirmation, which fails.

        restConfirmationMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(confirmation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllConfirmations() throws Exception {
        // Initialize the database
        insertedConfirmation = confirmationRepository.saveAndFlush(confirmation);

        // Get all the confirmationList
        restConfirmationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confirmation.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getConfirmation() throws Exception {
        // Initialize the database
        insertedConfirmation = confirmationRepository.saveAndFlush(confirmation);

        // Get the confirmation
        restConfirmationMockMvc
            .perform(get(ENTITY_API_URL_ID, confirmation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(confirmation.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingConfirmation() throws Exception {
        // Get the confirmation
        restConfirmationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConfirmation() throws Exception {
        // Initialize the database
        insertedConfirmation = confirmationRepository.saveAndFlush(confirmation);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        confirmationSearchRepository.save(confirmation);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());

        // Update the confirmation
        Confirmation updatedConfirmation = confirmationRepository.findById(confirmation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedConfirmation are not directly saved in db
        em.detach(updatedConfirmation);
        updatedConfirmation.createdAt(UPDATED_CREATED_AT);

        restConfirmationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConfirmation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedConfirmation))
            )
            .andExpect(status().isOk());

        // Validate the Confirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedConfirmationToMatchAllProperties(updatedConfirmation);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Confirmation> confirmationSearchList = Streamable.of(confirmationSearchRepository.findAll()).toList();
                Confirmation testConfirmationSearch = confirmationSearchList.get(searchDatabaseSizeAfter - 1);

                assertConfirmationAllPropertiesEquals(testConfirmationSearch, updatedConfirmation);
            });
    }

    @Test
    @Transactional
    void putNonExistingConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        confirmation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfirmationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, confirmation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(confirmation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Confirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        confirmation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(confirmation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Confirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        confirmation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmationMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(confirmation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Confirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateConfirmationWithPatch() throws Exception {
        // Initialize the database
        insertedConfirmation = confirmationRepository.saveAndFlush(confirmation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the confirmation using partial update
        Confirmation partialUpdatedConfirmation = new Confirmation();
        partialUpdatedConfirmation.setId(confirmation.getId());

        partialUpdatedConfirmation.createdAt(UPDATED_CREATED_AT);

        restConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfirmation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConfirmation))
            )
            .andExpect(status().isOk());

        // Validate the Confirmation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConfirmationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedConfirmation, confirmation),
            getPersistedConfirmation(confirmation)
        );
    }

    @Test
    @Transactional
    void fullUpdateConfirmationWithPatch() throws Exception {
        // Initialize the database
        insertedConfirmation = confirmationRepository.saveAndFlush(confirmation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the confirmation using partial update
        Confirmation partialUpdatedConfirmation = new Confirmation();
        partialUpdatedConfirmation.setId(confirmation.getId());

        partialUpdatedConfirmation.createdAt(UPDATED_CREATED_AT);

        restConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConfirmation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedConfirmation))
            )
            .andExpect(status().isOk());

        // Validate the Confirmation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertConfirmationUpdatableFieldsEquals(partialUpdatedConfirmation, getPersistedConfirmation(partialUpdatedConfirmation));
    }

    @Test
    @Transactional
    void patchNonExistingConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        confirmation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, confirmation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(confirmation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Confirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        confirmation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(confirmation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Confirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        confirmation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(confirmation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Confirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteConfirmation() throws Exception {
        // Initialize the database
        insertedConfirmation = confirmationRepository.saveAndFlush(confirmation);
        confirmationRepository.save(confirmation);
        confirmationSearchRepository.save(confirmation);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the confirmation
        restConfirmationMockMvc
            .perform(delete(ENTITY_API_URL_ID, confirmation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(confirmationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchConfirmation() throws Exception {
        // Initialize the database
        insertedConfirmation = confirmationRepository.saveAndFlush(confirmation);
        confirmationSearchRepository.save(confirmation);

        // Search the confirmation
        restConfirmationMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + confirmation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(confirmation.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    protected long getRepositoryCount() {
        return confirmationRepository.count();
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

    protected Confirmation getPersistedConfirmation(Confirmation confirmation) {
        return confirmationRepository.findById(confirmation.getId()).orElseThrow();
    }

    protected void assertPersistedConfirmationToMatchAllProperties(Confirmation expectedConfirmation) {
        assertConfirmationAllPropertiesEquals(expectedConfirmation, getPersistedConfirmation(expectedConfirmation));
    }

    protected void assertPersistedConfirmationToMatchUpdatableProperties(Confirmation expectedConfirmation) {
        assertConfirmationAllUpdatablePropertiesEquals(expectedConfirmation, getPersistedConfirmation(expectedConfirmation));
    }
}
