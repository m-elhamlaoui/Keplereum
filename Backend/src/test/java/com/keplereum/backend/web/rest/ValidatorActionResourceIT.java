package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.ValidatorActionAsserts.*;
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
import com.keplereum.backend.domain.ValidatorAction;
import com.keplereum.backend.domain.enumeration.ActionType;
import com.keplereum.backend.repository.ValidatorActionRepository;
import com.keplereum.backend.repository.search.ValidatorActionSearchRepository;
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
 * Integration tests for the {@link ValidatorActionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ValidatorActionResourceIT {

    private static final ActionType DEFAULT_ACTION_TYPE = ActionType.SWITCH_ORBIT;
    private static final ActionType UPDATED_ACTION_TYPE = ActionType.SWITCH_SENSOR;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/validator-actions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/validator-actions/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ValidatorActionRepository validatorActionRepository;

    @Autowired
    private ValidatorActionSearchRepository validatorActionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restValidatorActionMockMvc;

    private ValidatorAction validatorAction;

    private ValidatorAction insertedValidatorAction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValidatorAction createEntity() {
        return new ValidatorAction().actionType(DEFAULT_ACTION_TYPE).createdAt(DEFAULT_CREATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValidatorAction createUpdatedEntity() {
        return new ValidatorAction().actionType(UPDATED_ACTION_TYPE).createdAt(UPDATED_CREATED_AT);
    }

    @BeforeEach
    void initTest() {
        validatorAction = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedValidatorAction != null) {
            validatorActionRepository.delete(insertedValidatorAction);
            validatorActionSearchRepository.delete(insertedValidatorAction);
            insertedValidatorAction = null;
        }
    }

    @Test
    @Transactional
    void createValidatorAction() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        // Create the ValidatorAction
        var returnedValidatorAction = om.readValue(
            restValidatorActionMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(validatorAction))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ValidatorAction.class
        );

        // Validate the ValidatorAction in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertValidatorActionUpdatableFieldsEquals(returnedValidatorAction, getPersistedValidatorAction(returnedValidatorAction));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedValidatorAction = returnedValidatorAction;
    }

    @Test
    @Transactional
    void createValidatorActionWithExistingId() throws Exception {
        // Create the ValidatorAction with an existing ID
        validatorAction.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restValidatorActionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(validatorAction))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidatorAction in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkActionTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        // set the field null
        validatorAction.setActionType(null);

        // Create the ValidatorAction, which fails.

        restValidatorActionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(validatorAction))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        // set the field null
        validatorAction.setCreatedAt(null);

        // Create the ValidatorAction, which fails.

        restValidatorActionMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(validatorAction))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllValidatorActions() throws Exception {
        // Initialize the database
        insertedValidatorAction = validatorActionRepository.saveAndFlush(validatorAction);

        // Get all the validatorActionList
        restValidatorActionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validatorAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getValidatorAction() throws Exception {
        // Initialize the database
        insertedValidatorAction = validatorActionRepository.saveAndFlush(validatorAction);

        // Get the validatorAction
        restValidatorActionMockMvc
            .perform(get(ENTITY_API_URL_ID, validatorAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(validatorAction.getId().intValue()))
            .andExpect(jsonPath("$.actionType").value(DEFAULT_ACTION_TYPE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingValidatorAction() throws Exception {
        // Get the validatorAction
        restValidatorActionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingValidatorAction() throws Exception {
        // Initialize the database
        insertedValidatorAction = validatorActionRepository.saveAndFlush(validatorAction);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        validatorActionSearchRepository.save(validatorAction);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());

        // Update the validatorAction
        ValidatorAction updatedValidatorAction = validatorActionRepository.findById(validatorAction.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedValidatorAction are not directly saved in db
        em.detach(updatedValidatorAction);
        updatedValidatorAction.actionType(UPDATED_ACTION_TYPE).createdAt(UPDATED_CREATED_AT);

        restValidatorActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedValidatorAction.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedValidatorAction))
            )
            .andExpect(status().isOk());

        // Validate the ValidatorAction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedValidatorActionToMatchAllProperties(updatedValidatorAction);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<ValidatorAction> validatorActionSearchList = Streamable.of(validatorActionSearchRepository.findAll()).toList();
                ValidatorAction testValidatorActionSearch = validatorActionSearchList.get(searchDatabaseSizeAfter - 1);

                assertValidatorActionAllPropertiesEquals(testValidatorActionSearch, updatedValidatorAction);
            });
    }

    @Test
    @Transactional
    void putNonExistingValidatorAction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        validatorAction.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValidatorActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, validatorAction.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(validatorAction))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidatorAction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchValidatorAction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        validatorAction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidatorActionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(validatorAction))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidatorAction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamValidatorAction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        validatorAction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidatorActionMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(validatorAction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ValidatorAction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateValidatorActionWithPatch() throws Exception {
        // Initialize the database
        insertedValidatorAction = validatorActionRepository.saveAndFlush(validatorAction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the validatorAction using partial update
        ValidatorAction partialUpdatedValidatorAction = new ValidatorAction();
        partialUpdatedValidatorAction.setId(validatorAction.getId());

        restValidatorActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValidatorAction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedValidatorAction))
            )
            .andExpect(status().isOk());

        // Validate the ValidatorAction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertValidatorActionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedValidatorAction, validatorAction),
            getPersistedValidatorAction(validatorAction)
        );
    }

    @Test
    @Transactional
    void fullUpdateValidatorActionWithPatch() throws Exception {
        // Initialize the database
        insertedValidatorAction = validatorActionRepository.saveAndFlush(validatorAction);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the validatorAction using partial update
        ValidatorAction partialUpdatedValidatorAction = new ValidatorAction();
        partialUpdatedValidatorAction.setId(validatorAction.getId());

        partialUpdatedValidatorAction.actionType(UPDATED_ACTION_TYPE).createdAt(UPDATED_CREATED_AT);

        restValidatorActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValidatorAction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedValidatorAction))
            )
            .andExpect(status().isOk());

        // Validate the ValidatorAction in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertValidatorActionUpdatableFieldsEquals(
            partialUpdatedValidatorAction,
            getPersistedValidatorAction(partialUpdatedValidatorAction)
        );
    }

    @Test
    @Transactional
    void patchNonExistingValidatorAction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        validatorAction.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValidatorActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, validatorAction.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(validatorAction))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidatorAction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchValidatorAction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        validatorAction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidatorActionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(validatorAction))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidatorAction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamValidatorAction() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        validatorAction.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidatorActionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(validatorAction))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ValidatorAction in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteValidatorAction() throws Exception {
        // Initialize the database
        insertedValidatorAction = validatorActionRepository.saveAndFlush(validatorAction);
        validatorActionRepository.save(validatorAction);
        validatorActionSearchRepository.save(validatorAction);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the validatorAction
        restValidatorActionMockMvc
            .perform(delete(ENTITY_API_URL_ID, validatorAction.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(validatorActionSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchValidatorAction() throws Exception {
        // Initialize the database
        insertedValidatorAction = validatorActionRepository.saveAndFlush(validatorAction);
        validatorActionSearchRepository.save(validatorAction);

        // Search the validatorAction
        restValidatorActionMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + validatorAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validatorAction.getId().intValue())))
            .andExpect(jsonPath("$.[*].actionType").value(hasItem(DEFAULT_ACTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    protected long getRepositoryCount() {
        return validatorActionRepository.count();
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

    protected ValidatorAction getPersistedValidatorAction(ValidatorAction validatorAction) {
        return validatorActionRepository.findById(validatorAction.getId()).orElseThrow();
    }

    protected void assertPersistedValidatorActionToMatchAllProperties(ValidatorAction expectedValidatorAction) {
        assertValidatorActionAllPropertiesEquals(expectedValidatorAction, getPersistedValidatorAction(expectedValidatorAction));
    }

    protected void assertPersistedValidatorActionToMatchUpdatableProperties(ValidatorAction expectedValidatorAction) {
        assertValidatorActionAllUpdatablePropertiesEquals(expectedValidatorAction, getPersistedValidatorAction(expectedValidatorAction));
    }
}
