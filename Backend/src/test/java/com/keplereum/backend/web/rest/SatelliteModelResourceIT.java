package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.SatelliteModelAsserts.*;
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
import com.keplereum.backend.domain.SatelliteModel;
import com.keplereum.backend.repository.SatelliteModelRepository;
import com.keplereum.backend.repository.search.SatelliteModelSearchRepository;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link SatelliteModelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SatelliteModelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;

    private static final String DEFAULT_DIMENSIONS = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSIONS = "BBBBBBBBBB";

    private static final Double DEFAULT_POWER_CAPACITY = 1D;
    private static final Double UPDATED_POWER_CAPACITY = 2D;

    private static final Integer DEFAULT_EXPECTED_LIFESPAN = 1;
    private static final Integer UPDATED_EXPECTED_LIFESPAN = 2;

    private static final Double DEFAULT_DESIGN_TRAJECTORY_PREDICTION_FACTOR = 1D;
    private static final Double UPDATED_DESIGN_TRAJECTORY_PREDICTION_FACTOR = 2D;

    private static final Double DEFAULT_LAUNCH_MASS = 1D;
    private static final Double UPDATED_LAUNCH_MASS = 2D;

    private static final Double DEFAULT_DRY_MASS = 1D;
    private static final Double UPDATED_DRY_MASS = 2D;

    private static final String ENTITY_API_URL = "/api/satellite-models";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/satellite-models/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SatelliteModelRepository satelliteModelRepository;

    @Autowired
    private SatelliteModelSearchRepository satelliteModelSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSatelliteModelMockMvc;

    private SatelliteModel satelliteModel;

    private SatelliteModel insertedSatelliteModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SatelliteModel createEntity() {
        return new SatelliteModel()
            .name(DEFAULT_NAME)
            .manufacturer(DEFAULT_MANUFACTURER)
            .weight(DEFAULT_WEIGHT)
            .dimensions(DEFAULT_DIMENSIONS)
            .powerCapacity(DEFAULT_POWER_CAPACITY)
            .expectedLifespan(DEFAULT_EXPECTED_LIFESPAN)
            .designTrajectoryPredictionFactor(DEFAULT_DESIGN_TRAJECTORY_PREDICTION_FACTOR)
            .launchMass(DEFAULT_LAUNCH_MASS)
            .dryMass(DEFAULT_DRY_MASS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SatelliteModel createUpdatedEntity() {
        return new SatelliteModel()
            .name(UPDATED_NAME)
            .manufacturer(UPDATED_MANUFACTURER)
            .weight(UPDATED_WEIGHT)
            .dimensions(UPDATED_DIMENSIONS)
            .powerCapacity(UPDATED_POWER_CAPACITY)
            .expectedLifespan(UPDATED_EXPECTED_LIFESPAN)
            .designTrajectoryPredictionFactor(UPDATED_DESIGN_TRAJECTORY_PREDICTION_FACTOR)
            .launchMass(UPDATED_LAUNCH_MASS)
            .dryMass(UPDATED_DRY_MASS);
    }

    @BeforeEach
    void initTest() {
        satelliteModel = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSatelliteModel != null) {
            satelliteModelRepository.delete(insertedSatelliteModel);
            satelliteModelSearchRepository.delete(insertedSatelliteModel);
            insertedSatelliteModel = null;
        }
    }

    @Test
    @Transactional
    void createSatelliteModel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        // Create the SatelliteModel
        var returnedSatelliteModel = om.readValue(
            restSatelliteModelMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satelliteModel))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SatelliteModel.class
        );

        // Validate the SatelliteModel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSatelliteModelUpdatableFieldsEquals(returnedSatelliteModel, getPersistedSatelliteModel(returnedSatelliteModel));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedSatelliteModel = returnedSatelliteModel;
    }

    @Test
    @Transactional
    void createSatelliteModelWithExistingId() throws Exception {
        // Create the SatelliteModel with an existing ID
        satelliteModel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restSatelliteModelMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satelliteModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the SatelliteModel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        // set the field null
        satelliteModel.setName(null);

        // Create the SatelliteModel, which fails.

        restSatelliteModelMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satelliteModel))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkManufacturerIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        // set the field null
        satelliteModel.setManufacturer(null);

        // Create the SatelliteModel, which fails.

        restSatelliteModelMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satelliteModel))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllSatelliteModels() throws Exception {
        // Initialize the database
        insertedSatelliteModel = satelliteModelRepository.saveAndFlush(satelliteModel);

        // Get all the satelliteModelList
        restSatelliteModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(satelliteModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].dimensions").value(hasItem(DEFAULT_DIMENSIONS)))
            .andExpect(jsonPath("$.[*].powerCapacity").value(hasItem(DEFAULT_POWER_CAPACITY)))
            .andExpect(jsonPath("$.[*].expectedLifespan").value(hasItem(DEFAULT_EXPECTED_LIFESPAN)))
            .andExpect(jsonPath("$.[*].designTrajectoryPredictionFactor").value(hasItem(DEFAULT_DESIGN_TRAJECTORY_PREDICTION_FACTOR)))
            .andExpect(jsonPath("$.[*].launchMass").value(hasItem(DEFAULT_LAUNCH_MASS)))
            .andExpect(jsonPath("$.[*].dryMass").value(hasItem(DEFAULT_DRY_MASS)));
    }

    @Test
    @Transactional
    void getSatelliteModel() throws Exception {
        // Initialize the database
        insertedSatelliteModel = satelliteModelRepository.saveAndFlush(satelliteModel);

        // Get the satelliteModel
        restSatelliteModelMockMvc
            .perform(get(ENTITY_API_URL_ID, satelliteModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(satelliteModel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT))
            .andExpect(jsonPath("$.dimensions").value(DEFAULT_DIMENSIONS))
            .andExpect(jsonPath("$.powerCapacity").value(DEFAULT_POWER_CAPACITY))
            .andExpect(jsonPath("$.expectedLifespan").value(DEFAULT_EXPECTED_LIFESPAN))
            .andExpect(jsonPath("$.designTrajectoryPredictionFactor").value(DEFAULT_DESIGN_TRAJECTORY_PREDICTION_FACTOR))
            .andExpect(jsonPath("$.launchMass").value(DEFAULT_LAUNCH_MASS))
            .andExpect(jsonPath("$.dryMass").value(DEFAULT_DRY_MASS));
    }

    @Test
    @Transactional
    void getNonExistingSatelliteModel() throws Exception {
        // Get the satelliteModel
        restSatelliteModelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSatelliteModel() throws Exception {
        // Initialize the database
        insertedSatelliteModel = satelliteModelRepository.saveAndFlush(satelliteModel);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        satelliteModelSearchRepository.save(satelliteModel);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());

        // Update the satelliteModel
        SatelliteModel updatedSatelliteModel = satelliteModelRepository.findById(satelliteModel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSatelliteModel are not directly saved in db
        em.detach(updatedSatelliteModel);
        updatedSatelliteModel
            .name(UPDATED_NAME)
            .manufacturer(UPDATED_MANUFACTURER)
            .weight(UPDATED_WEIGHT)
            .dimensions(UPDATED_DIMENSIONS)
            .powerCapacity(UPDATED_POWER_CAPACITY)
            .expectedLifespan(UPDATED_EXPECTED_LIFESPAN)
            .designTrajectoryPredictionFactor(UPDATED_DESIGN_TRAJECTORY_PREDICTION_FACTOR)
            .launchMass(UPDATED_LAUNCH_MASS)
            .dryMass(UPDATED_DRY_MASS);

        restSatelliteModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSatelliteModel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSatelliteModel))
            )
            .andExpect(status().isOk());

        // Validate the SatelliteModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSatelliteModelToMatchAllProperties(updatedSatelliteModel);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<SatelliteModel> satelliteModelSearchList = Streamable.of(satelliteModelSearchRepository.findAll()).toList();
                SatelliteModel testSatelliteModelSearch = satelliteModelSearchList.get(searchDatabaseSizeAfter - 1);

                assertSatelliteModelAllPropertiesEquals(testSatelliteModelSearch, updatedSatelliteModel);
            });
    }

    @Test
    @Transactional
    void putNonExistingSatelliteModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        satelliteModel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSatelliteModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, satelliteModel.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(satelliteModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the SatelliteModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchSatelliteModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        satelliteModel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(satelliteModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the SatelliteModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSatelliteModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        satelliteModel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteModelMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satelliteModel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SatelliteModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateSatelliteModelWithPatch() throws Exception {
        // Initialize the database
        insertedSatelliteModel = satelliteModelRepository.saveAndFlush(satelliteModel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the satelliteModel using partial update
        SatelliteModel partialUpdatedSatelliteModel = new SatelliteModel();
        partialUpdatedSatelliteModel.setId(satelliteModel.getId());

        partialUpdatedSatelliteModel
            .manufacturer(UPDATED_MANUFACTURER)
            .powerCapacity(UPDATED_POWER_CAPACITY)
            .launchMass(UPDATED_LAUNCH_MASS);

        restSatelliteModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSatelliteModel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSatelliteModel))
            )
            .andExpect(status().isOk());

        // Validate the SatelliteModel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSatelliteModelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSatelliteModel, satelliteModel),
            getPersistedSatelliteModel(satelliteModel)
        );
    }

    @Test
    @Transactional
    void fullUpdateSatelliteModelWithPatch() throws Exception {
        // Initialize the database
        insertedSatelliteModel = satelliteModelRepository.saveAndFlush(satelliteModel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the satelliteModel using partial update
        SatelliteModel partialUpdatedSatelliteModel = new SatelliteModel();
        partialUpdatedSatelliteModel.setId(satelliteModel.getId());

        partialUpdatedSatelliteModel
            .name(UPDATED_NAME)
            .manufacturer(UPDATED_MANUFACTURER)
            .weight(UPDATED_WEIGHT)
            .dimensions(UPDATED_DIMENSIONS)
            .powerCapacity(UPDATED_POWER_CAPACITY)
            .expectedLifespan(UPDATED_EXPECTED_LIFESPAN)
            .designTrajectoryPredictionFactor(UPDATED_DESIGN_TRAJECTORY_PREDICTION_FACTOR)
            .launchMass(UPDATED_LAUNCH_MASS)
            .dryMass(UPDATED_DRY_MASS);

        restSatelliteModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSatelliteModel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSatelliteModel))
            )
            .andExpect(status().isOk());

        // Validate the SatelliteModel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSatelliteModelUpdatableFieldsEquals(partialUpdatedSatelliteModel, getPersistedSatelliteModel(partialUpdatedSatelliteModel));
    }

    @Test
    @Transactional
    void patchNonExistingSatelliteModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        satelliteModel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSatelliteModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, satelliteModel.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(satelliteModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the SatelliteModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSatelliteModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        satelliteModel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(satelliteModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the SatelliteModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSatelliteModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        satelliteModel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteModelMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(satelliteModel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SatelliteModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteSatelliteModel() throws Exception {
        // Initialize the database
        insertedSatelliteModel = satelliteModelRepository.saveAndFlush(satelliteModel);
        satelliteModelRepository.save(satelliteModel);
        satelliteModelSearchRepository.save(satelliteModel);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the satelliteModel
        restSatelliteModelMockMvc
            .perform(delete(ENTITY_API_URL_ID, satelliteModel.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteModelSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchSatelliteModel() throws Exception {
        // Initialize the database
        insertedSatelliteModel = satelliteModelRepository.saveAndFlush(satelliteModel);
        satelliteModelSearchRepository.save(satelliteModel);

        // Search the satelliteModel
        restSatelliteModelMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + satelliteModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(satelliteModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT)))
            .andExpect(jsonPath("$.[*].dimensions").value(hasItem(DEFAULT_DIMENSIONS)))
            .andExpect(jsonPath("$.[*].powerCapacity").value(hasItem(DEFAULT_POWER_CAPACITY)))
            .andExpect(jsonPath("$.[*].expectedLifespan").value(hasItem(DEFAULT_EXPECTED_LIFESPAN)))
            .andExpect(jsonPath("$.[*].designTrajectoryPredictionFactor").value(hasItem(DEFAULT_DESIGN_TRAJECTORY_PREDICTION_FACTOR)))
            .andExpect(jsonPath("$.[*].launchMass").value(hasItem(DEFAULT_LAUNCH_MASS)))
            .andExpect(jsonPath("$.[*].dryMass").value(hasItem(DEFAULT_DRY_MASS)));
    }

    protected long getRepositoryCount() {
        return satelliteModelRepository.count();
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

    protected SatelliteModel getPersistedSatelliteModel(SatelliteModel satelliteModel) {
        return satelliteModelRepository.findById(satelliteModel.getId()).orElseThrow();
    }

    protected void assertPersistedSatelliteModelToMatchAllProperties(SatelliteModel expectedSatelliteModel) {
        assertSatelliteModelAllPropertiesEquals(expectedSatelliteModel, getPersistedSatelliteModel(expectedSatelliteModel));
    }

    protected void assertPersistedSatelliteModelToMatchUpdatableProperties(SatelliteModel expectedSatelliteModel) {
        assertSatelliteModelAllUpdatablePropertiesEquals(expectedSatelliteModel, getPersistedSatelliteModel(expectedSatelliteModel));
    }
}
