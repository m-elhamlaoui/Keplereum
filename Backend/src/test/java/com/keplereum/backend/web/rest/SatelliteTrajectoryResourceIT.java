package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.SatelliteTrajectoryAsserts.*;
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
import com.keplereum.backend.domain.SatelliteTrajectory;
import com.keplereum.backend.repository.SatelliteTrajectoryRepository;
import com.keplereum.backend.repository.search.SatelliteTrajectorySearchRepository;
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
 * Integration tests for the {@link SatelliteTrajectoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SatelliteTrajectoryResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_ORBIT_ECCENTRICITY = 1D;
    private static final Double UPDATED_ORBIT_ECCENTRICITY = 2D;

    private static final Double DEFAULT_ORBIT_INCLINATION = 1D;
    private static final Double UPDATED_ORBIT_INCLINATION = 2D;

    private static final Double DEFAULT_ORBIT_RIGHT_ASCENSION = 1D;
    private static final Double UPDATED_ORBIT_RIGHT_ASCENSION = 2D;

    private static final Double DEFAULT_ORBIT_ARGUMENT_OF_PERIGEE = 1D;
    private static final Double UPDATED_ORBIT_ARGUMENT_OF_PERIGEE = 2D;

    private static final Double DEFAULT_ORBIT_MEAN_ANOMALY = 1D;
    private static final Double UPDATED_ORBIT_MEAN_ANOMALY = 2D;

    private static final Double DEFAULT_ORBIT_PERIAPSIS = 1D;
    private static final Double UPDATED_ORBIT_PERIAPSIS = 2D;

    private static final String DEFAULT_CHANGE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_REASON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/satellite-trajectories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/satellite-trajectories/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SatelliteTrajectoryRepository satelliteTrajectoryRepository;

    @Autowired
    private SatelliteTrajectorySearchRepository satelliteTrajectorySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSatelliteTrajectoryMockMvc;

    private SatelliteTrajectory satelliteTrajectory;

    private SatelliteTrajectory insertedSatelliteTrajectory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SatelliteTrajectory createEntity() {
        return new SatelliteTrajectory()
            .status(DEFAULT_STATUS)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .orbitEccentricity(DEFAULT_ORBIT_ECCENTRICITY)
            .orbitInclination(DEFAULT_ORBIT_INCLINATION)
            .orbitRightAscension(DEFAULT_ORBIT_RIGHT_ASCENSION)
            .orbitArgumentOfPerigee(DEFAULT_ORBIT_ARGUMENT_OF_PERIGEE)
            .orbitMeanAnomaly(DEFAULT_ORBIT_MEAN_ANOMALY)
            .orbitPeriapsis(DEFAULT_ORBIT_PERIAPSIS)
            .changeReason(DEFAULT_CHANGE_REASON);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SatelliteTrajectory createUpdatedEntity() {
        return new SatelliteTrajectory()
            .status(UPDATED_STATUS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .orbitEccentricity(UPDATED_ORBIT_ECCENTRICITY)
            .orbitInclination(UPDATED_ORBIT_INCLINATION)
            .orbitRightAscension(UPDATED_ORBIT_RIGHT_ASCENSION)
            .orbitArgumentOfPerigee(UPDATED_ORBIT_ARGUMENT_OF_PERIGEE)
            .orbitMeanAnomaly(UPDATED_ORBIT_MEAN_ANOMALY)
            .orbitPeriapsis(UPDATED_ORBIT_PERIAPSIS)
            .changeReason(UPDATED_CHANGE_REASON);
    }

    @BeforeEach
    void initTest() {
        satelliteTrajectory = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSatelliteTrajectory != null) {
            satelliteTrajectoryRepository.delete(insertedSatelliteTrajectory);
            satelliteTrajectorySearchRepository.delete(insertedSatelliteTrajectory);
            insertedSatelliteTrajectory = null;
        }
    }

    @Test
    @Transactional
    void createSatelliteTrajectory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        // Create the SatelliteTrajectory
        var returnedSatelliteTrajectory = om.readValue(
            restSatelliteTrajectoryMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(satelliteTrajectory))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SatelliteTrajectory.class
        );

        // Validate the SatelliteTrajectory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSatelliteTrajectoryUpdatableFieldsEquals(
            returnedSatelliteTrajectory,
            getPersistedSatelliteTrajectory(returnedSatelliteTrajectory)
        );

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedSatelliteTrajectory = returnedSatelliteTrajectory;
    }

    @Test
    @Transactional
    void createSatelliteTrajectoryWithExistingId() throws Exception {
        // Create the SatelliteTrajectory with an existing ID
        satelliteTrajectory.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restSatelliteTrajectoryMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satelliteTrajectory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SatelliteTrajectory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        // set the field null
        satelliteTrajectory.setStatus(null);

        // Create the SatelliteTrajectory, which fails.

        restSatelliteTrajectoryMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satelliteTrajectory))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkStartTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        // set the field null
        satelliteTrajectory.setStartTime(null);

        // Create the SatelliteTrajectory, which fails.

        restSatelliteTrajectoryMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satelliteTrajectory))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllSatelliteTrajectories() throws Exception {
        // Initialize the database
        insertedSatelliteTrajectory = satelliteTrajectoryRepository.saveAndFlush(satelliteTrajectory);

        // Get all the satelliteTrajectoryList
        restSatelliteTrajectoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(satelliteTrajectory.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].orbitEccentricity").value(hasItem(DEFAULT_ORBIT_ECCENTRICITY)))
            .andExpect(jsonPath("$.[*].orbitInclination").value(hasItem(DEFAULT_ORBIT_INCLINATION)))
            .andExpect(jsonPath("$.[*].orbitRightAscension").value(hasItem(DEFAULT_ORBIT_RIGHT_ASCENSION)))
            .andExpect(jsonPath("$.[*].orbitArgumentOfPerigee").value(hasItem(DEFAULT_ORBIT_ARGUMENT_OF_PERIGEE)))
            .andExpect(jsonPath("$.[*].orbitMeanAnomaly").value(hasItem(DEFAULT_ORBIT_MEAN_ANOMALY)))
            .andExpect(jsonPath("$.[*].orbitPeriapsis").value(hasItem(DEFAULT_ORBIT_PERIAPSIS)))
            .andExpect(jsonPath("$.[*].changeReason").value(hasItem(DEFAULT_CHANGE_REASON)));
    }

    @Test
    @Transactional
    void getSatelliteTrajectory() throws Exception {
        // Initialize the database
        insertedSatelliteTrajectory = satelliteTrajectoryRepository.saveAndFlush(satelliteTrajectory);

        // Get the satelliteTrajectory
        restSatelliteTrajectoryMockMvc
            .perform(get(ENTITY_API_URL_ID, satelliteTrajectory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(satelliteTrajectory.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.orbitEccentricity").value(DEFAULT_ORBIT_ECCENTRICITY))
            .andExpect(jsonPath("$.orbitInclination").value(DEFAULT_ORBIT_INCLINATION))
            .andExpect(jsonPath("$.orbitRightAscension").value(DEFAULT_ORBIT_RIGHT_ASCENSION))
            .andExpect(jsonPath("$.orbitArgumentOfPerigee").value(DEFAULT_ORBIT_ARGUMENT_OF_PERIGEE))
            .andExpect(jsonPath("$.orbitMeanAnomaly").value(DEFAULT_ORBIT_MEAN_ANOMALY))
            .andExpect(jsonPath("$.orbitPeriapsis").value(DEFAULT_ORBIT_PERIAPSIS))
            .andExpect(jsonPath("$.changeReason").value(DEFAULT_CHANGE_REASON));
    }

    @Test
    @Transactional
    void getNonExistingSatelliteTrajectory() throws Exception {
        // Get the satelliteTrajectory
        restSatelliteTrajectoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSatelliteTrajectory() throws Exception {
        // Initialize the database
        insertedSatelliteTrajectory = satelliteTrajectoryRepository.saveAndFlush(satelliteTrajectory);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        satelliteTrajectorySearchRepository.save(satelliteTrajectory);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());

        // Update the satelliteTrajectory
        SatelliteTrajectory updatedSatelliteTrajectory = satelliteTrajectoryRepository.findById(satelliteTrajectory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSatelliteTrajectory are not directly saved in db
        em.detach(updatedSatelliteTrajectory);
        updatedSatelliteTrajectory
            .status(UPDATED_STATUS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .orbitEccentricity(UPDATED_ORBIT_ECCENTRICITY)
            .orbitInclination(UPDATED_ORBIT_INCLINATION)
            .orbitRightAscension(UPDATED_ORBIT_RIGHT_ASCENSION)
            .orbitArgumentOfPerigee(UPDATED_ORBIT_ARGUMENT_OF_PERIGEE)
            .orbitMeanAnomaly(UPDATED_ORBIT_MEAN_ANOMALY)
            .orbitPeriapsis(UPDATED_ORBIT_PERIAPSIS)
            .changeReason(UPDATED_CHANGE_REASON);

        restSatelliteTrajectoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSatelliteTrajectory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSatelliteTrajectory))
            )
            .andExpect(status().isOk());

        // Validate the SatelliteTrajectory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSatelliteTrajectoryToMatchAllProperties(updatedSatelliteTrajectory);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<SatelliteTrajectory> satelliteTrajectorySearchList = Streamable.of(
                    satelliteTrajectorySearchRepository.findAll()
                ).toList();
                SatelliteTrajectory testSatelliteTrajectorySearch = satelliteTrajectorySearchList.get(searchDatabaseSizeAfter - 1);

                assertSatelliteTrajectoryAllPropertiesEquals(testSatelliteTrajectorySearch, updatedSatelliteTrajectory);
            });
    }

    @Test
    @Transactional
    void putNonExistingSatelliteTrajectory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        satelliteTrajectory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSatelliteTrajectoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, satelliteTrajectory.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(satelliteTrajectory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SatelliteTrajectory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchSatelliteTrajectory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        satelliteTrajectory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteTrajectoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(satelliteTrajectory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SatelliteTrajectory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSatelliteTrajectory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        satelliteTrajectory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteTrajectoryMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satelliteTrajectory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SatelliteTrajectory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateSatelliteTrajectoryWithPatch() throws Exception {
        // Initialize the database
        insertedSatelliteTrajectory = satelliteTrajectoryRepository.saveAndFlush(satelliteTrajectory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the satelliteTrajectory using partial update
        SatelliteTrajectory partialUpdatedSatelliteTrajectory = new SatelliteTrajectory();
        partialUpdatedSatelliteTrajectory.setId(satelliteTrajectory.getId());

        partialUpdatedSatelliteTrajectory
            .status(UPDATED_STATUS)
            .orbitEccentricity(UPDATED_ORBIT_ECCENTRICITY)
            .orbitArgumentOfPerigee(UPDATED_ORBIT_ARGUMENT_OF_PERIGEE);

        restSatelliteTrajectoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSatelliteTrajectory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSatelliteTrajectory))
            )
            .andExpect(status().isOk());

        // Validate the SatelliteTrajectory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSatelliteTrajectoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSatelliteTrajectory, satelliteTrajectory),
            getPersistedSatelliteTrajectory(satelliteTrajectory)
        );
    }

    @Test
    @Transactional
    void fullUpdateSatelliteTrajectoryWithPatch() throws Exception {
        // Initialize the database
        insertedSatelliteTrajectory = satelliteTrajectoryRepository.saveAndFlush(satelliteTrajectory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the satelliteTrajectory using partial update
        SatelliteTrajectory partialUpdatedSatelliteTrajectory = new SatelliteTrajectory();
        partialUpdatedSatelliteTrajectory.setId(satelliteTrajectory.getId());

        partialUpdatedSatelliteTrajectory
            .status(UPDATED_STATUS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .orbitEccentricity(UPDATED_ORBIT_ECCENTRICITY)
            .orbitInclination(UPDATED_ORBIT_INCLINATION)
            .orbitRightAscension(UPDATED_ORBIT_RIGHT_ASCENSION)
            .orbitArgumentOfPerigee(UPDATED_ORBIT_ARGUMENT_OF_PERIGEE)
            .orbitMeanAnomaly(UPDATED_ORBIT_MEAN_ANOMALY)
            .orbitPeriapsis(UPDATED_ORBIT_PERIAPSIS)
            .changeReason(UPDATED_CHANGE_REASON);

        restSatelliteTrajectoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSatelliteTrajectory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSatelliteTrajectory))
            )
            .andExpect(status().isOk());

        // Validate the SatelliteTrajectory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSatelliteTrajectoryUpdatableFieldsEquals(
            partialUpdatedSatelliteTrajectory,
            getPersistedSatelliteTrajectory(partialUpdatedSatelliteTrajectory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSatelliteTrajectory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        satelliteTrajectory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSatelliteTrajectoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, satelliteTrajectory.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(satelliteTrajectory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SatelliteTrajectory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSatelliteTrajectory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        satelliteTrajectory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteTrajectoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(satelliteTrajectory))
            )
            .andExpect(status().isBadRequest());

        // Validate the SatelliteTrajectory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSatelliteTrajectory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        satelliteTrajectory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteTrajectoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(satelliteTrajectory))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SatelliteTrajectory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteSatelliteTrajectory() throws Exception {
        // Initialize the database
        insertedSatelliteTrajectory = satelliteTrajectoryRepository.saveAndFlush(satelliteTrajectory);
        satelliteTrajectoryRepository.save(satelliteTrajectory);
        satelliteTrajectorySearchRepository.save(satelliteTrajectory);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the satelliteTrajectory
        restSatelliteTrajectoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, satelliteTrajectory.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteTrajectorySearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchSatelliteTrajectory() throws Exception {
        // Initialize the database
        insertedSatelliteTrajectory = satelliteTrajectoryRepository.saveAndFlush(satelliteTrajectory);
        satelliteTrajectorySearchRepository.save(satelliteTrajectory);

        // Search the satelliteTrajectory
        restSatelliteTrajectoryMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + satelliteTrajectory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(satelliteTrajectory.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].orbitEccentricity").value(hasItem(DEFAULT_ORBIT_ECCENTRICITY)))
            .andExpect(jsonPath("$.[*].orbitInclination").value(hasItem(DEFAULT_ORBIT_INCLINATION)))
            .andExpect(jsonPath("$.[*].orbitRightAscension").value(hasItem(DEFAULT_ORBIT_RIGHT_ASCENSION)))
            .andExpect(jsonPath("$.[*].orbitArgumentOfPerigee").value(hasItem(DEFAULT_ORBIT_ARGUMENT_OF_PERIGEE)))
            .andExpect(jsonPath("$.[*].orbitMeanAnomaly").value(hasItem(DEFAULT_ORBIT_MEAN_ANOMALY)))
            .andExpect(jsonPath("$.[*].orbitPeriapsis").value(hasItem(DEFAULT_ORBIT_PERIAPSIS)))
            .andExpect(jsonPath("$.[*].changeReason").value(hasItem(DEFAULT_CHANGE_REASON)));
    }

    protected long getRepositoryCount() {
        return satelliteTrajectoryRepository.count();
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

    protected SatelliteTrajectory getPersistedSatelliteTrajectory(SatelliteTrajectory satelliteTrajectory) {
        return satelliteTrajectoryRepository.findById(satelliteTrajectory.getId()).orElseThrow();
    }

    protected void assertPersistedSatelliteTrajectoryToMatchAllProperties(SatelliteTrajectory expectedSatelliteTrajectory) {
        assertSatelliteTrajectoryAllPropertiesEquals(
            expectedSatelliteTrajectory,
            getPersistedSatelliteTrajectory(expectedSatelliteTrajectory)
        );
    }

    protected void assertPersistedSatelliteTrajectoryToMatchUpdatableProperties(SatelliteTrajectory expectedSatelliteTrajectory) {
        assertSatelliteTrajectoryAllUpdatablePropertiesEquals(
            expectedSatelliteTrajectory,
            getPersistedSatelliteTrajectory(expectedSatelliteTrajectory)
        );
    }
}
