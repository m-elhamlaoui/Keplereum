package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.SatelliteAsserts.*;
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
import com.keplereum.backend.domain.Satellite;
import com.keplereum.backend.domain.enumeration.SatelliteStatus;
import com.keplereum.backend.repository.SatelliteRepository;
import com.keplereum.backend.repository.search.SatelliteSearchRepository;
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
 * Integration tests for the {@link SatelliteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SatelliteResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAUNCH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAUNCH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SatelliteStatus DEFAULT_STATUS = SatelliteStatus.LAUNCHED;
    private static final SatelliteStatus UPDATED_STATUS = SatelliteStatus.OPERATIONAL;

    private static final String ENTITY_API_URL = "/api/satellites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/satellites/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SatelliteRepository satelliteRepository;

    @Autowired
    private SatelliteSearchRepository satelliteSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSatelliteMockMvc;

    private Satellite satellite;

    private Satellite insertedSatellite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Satellite createEntity() {
        return new Satellite().name(DEFAULT_NAME).launchDate(DEFAULT_LAUNCH_DATE).status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Satellite createUpdatedEntity() {
        return new Satellite().name(UPDATED_NAME).launchDate(UPDATED_LAUNCH_DATE).status(UPDATED_STATUS);
    }

    @BeforeEach
    void initTest() {
        satellite = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSatellite != null) {
            satelliteRepository.delete(insertedSatellite);
            satelliteSearchRepository.delete(insertedSatellite);
            insertedSatellite = null;
        }
    }

    @Test
    @Transactional
    void createSatellite() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        // Create the Satellite
        var returnedSatellite = om.readValue(
            restSatelliteMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satellite)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Satellite.class
        );

        // Validate the Satellite in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSatelliteUpdatableFieldsEquals(returnedSatellite, getPersistedSatellite(returnedSatellite));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedSatellite = returnedSatellite;
    }

    @Test
    @Transactional
    void createSatelliteWithExistingId() throws Exception {
        // Create the Satellite with an existing ID
        satellite.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restSatelliteMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satellite)))
            .andExpect(status().isBadRequest());

        // Validate the Satellite in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        // set the field null
        satellite.setName(null);

        // Create the Satellite, which fails.

        restSatelliteMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satellite)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        // set the field null
        satellite.setStatus(null);

        // Create the Satellite, which fails.

        restSatelliteMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satellite)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllSatellites() throws Exception {
        // Initialize the database
        insertedSatellite = satelliteRepository.saveAndFlush(satellite);

        // Get all the satelliteList
        restSatelliteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(satellite.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].launchDate").value(hasItem(DEFAULT_LAUNCH_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getSatellite() throws Exception {
        // Initialize the database
        insertedSatellite = satelliteRepository.saveAndFlush(satellite);

        // Get the satellite
        restSatelliteMockMvc
            .perform(get(ENTITY_API_URL_ID, satellite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(satellite.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.launchDate").value(DEFAULT_LAUNCH_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSatellite() throws Exception {
        // Get the satellite
        restSatelliteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSatellite() throws Exception {
        // Initialize the database
        insertedSatellite = satelliteRepository.saveAndFlush(satellite);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        satelliteSearchRepository.save(satellite);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());

        // Update the satellite
        Satellite updatedSatellite = satelliteRepository.findById(satellite.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSatellite are not directly saved in db
        em.detach(updatedSatellite);
        updatedSatellite.name(UPDATED_NAME).launchDate(UPDATED_LAUNCH_DATE).status(UPDATED_STATUS);

        restSatelliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSatellite.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSatellite))
            )
            .andExpect(status().isOk());

        // Validate the Satellite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSatelliteToMatchAllProperties(updatedSatellite);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Satellite> satelliteSearchList = Streamable.of(satelliteSearchRepository.findAll()).toList();
                Satellite testSatelliteSearch = satelliteSearchList.get(searchDatabaseSizeAfter - 1);

                assertSatelliteAllPropertiesEquals(testSatelliteSearch, updatedSatellite);
            });
    }

    @Test
    @Transactional
    void putNonExistingSatellite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        satellite.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSatelliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, satellite.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(satellite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Satellite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchSatellite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        satellite.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(satellite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Satellite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSatellite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        satellite.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(satellite)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Satellite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateSatelliteWithPatch() throws Exception {
        // Initialize the database
        insertedSatellite = satelliteRepository.saveAndFlush(satellite);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the satellite using partial update
        Satellite partialUpdatedSatellite = new Satellite();
        partialUpdatedSatellite.setId(satellite.getId());

        partialUpdatedSatellite.status(UPDATED_STATUS);

        restSatelliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSatellite.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSatellite))
            )
            .andExpect(status().isOk());

        // Validate the Satellite in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSatelliteUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSatellite, satellite),
            getPersistedSatellite(satellite)
        );
    }

    @Test
    @Transactional
    void fullUpdateSatelliteWithPatch() throws Exception {
        // Initialize the database
        insertedSatellite = satelliteRepository.saveAndFlush(satellite);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the satellite using partial update
        Satellite partialUpdatedSatellite = new Satellite();
        partialUpdatedSatellite.setId(satellite.getId());

        partialUpdatedSatellite.name(UPDATED_NAME).launchDate(UPDATED_LAUNCH_DATE).status(UPDATED_STATUS);

        restSatelliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSatellite.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSatellite))
            )
            .andExpect(status().isOk());

        // Validate the Satellite in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSatelliteUpdatableFieldsEquals(partialUpdatedSatellite, getPersistedSatellite(partialUpdatedSatellite));
    }

    @Test
    @Transactional
    void patchNonExistingSatellite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        satellite.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSatelliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, satellite.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(satellite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Satellite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSatellite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        satellite.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(satellite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Satellite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSatellite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        satellite.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSatelliteMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(satellite))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Satellite in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteSatellite() throws Exception {
        // Initialize the database
        insertedSatellite = satelliteRepository.saveAndFlush(satellite);
        satelliteRepository.save(satellite);
        satelliteSearchRepository.save(satellite);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the satellite
        restSatelliteMockMvc
            .perform(delete(ENTITY_API_URL_ID, satellite.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(satelliteSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchSatellite() throws Exception {
        // Initialize the database
        insertedSatellite = satelliteRepository.saveAndFlush(satellite);
        satelliteSearchRepository.save(satellite);

        // Search the satellite
        restSatelliteMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + satellite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(satellite.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].launchDate").value(hasItem(DEFAULT_LAUNCH_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    protected long getRepositoryCount() {
        return satelliteRepository.count();
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

    protected Satellite getPersistedSatellite(Satellite satellite) {
        return satelliteRepository.findById(satellite.getId()).orElseThrow();
    }

    protected void assertPersistedSatelliteToMatchAllProperties(Satellite expectedSatellite) {
        assertSatelliteAllPropertiesEquals(expectedSatellite, getPersistedSatellite(expectedSatellite));
    }

    protected void assertPersistedSatelliteToMatchUpdatableProperties(Satellite expectedSatellite) {
        assertSatelliteAllUpdatablePropertiesEquals(expectedSatellite, getPersistedSatellite(expectedSatellite));
    }
}
