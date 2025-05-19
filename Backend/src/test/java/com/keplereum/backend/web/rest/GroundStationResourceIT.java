package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.GroundStationAsserts.*;
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
import com.keplereum.backend.domain.GroundStation;
import com.keplereum.backend.repository.GroundStationRepository;
import com.keplereum.backend.repository.search.GroundStationSearchRepository;
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
 * Integration tests for the {@link GroundStationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GroundStationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACCESS_LEVEL = 1;
    private static final Integer UPDATED_ACCESS_LEVEL = 2;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final String ENTITY_API_URL = "/api/ground-stations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/ground-stations/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GroundStationRepository groundStationRepository;

    @Autowired
    private GroundStationSearchRepository groundStationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroundStationMockMvc;

    private GroundStation groundStation;

    private GroundStation insertedGroundStation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroundStation createEntity() {
        return new GroundStation()
            .name(DEFAULT_NAME)
            .country(DEFAULT_COUNTRY)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .accessLevel(DEFAULT_ACCESS_LEVEL)
            .status(DEFAULT_STATUS)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroundStation createUpdatedEntity() {
        return new GroundStation()
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .accessLevel(UPDATED_ACCESS_LEVEL)
            .status(UPDATED_STATUS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
    }

    @BeforeEach
    void initTest() {
        groundStation = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedGroundStation != null) {
            groundStationRepository.delete(insertedGroundStation);
            groundStationSearchRepository.delete(insertedGroundStation);
            insertedGroundStation = null;
        }
    }

    @Test
    @Transactional
    void createGroundStation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        // Create the GroundStation
        var returnedGroundStation = om.readValue(
            restGroundStationMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groundStation))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GroundStation.class
        );

        // Validate the GroundStation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertGroundStationUpdatableFieldsEquals(returnedGroundStation, getPersistedGroundStation(returnedGroundStation));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedGroundStation = returnedGroundStation;
    }

    @Test
    @Transactional
    void createGroundStationWithExistingId() throws Exception {
        // Create the GroundStation with an existing ID
        groundStation.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroundStationMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groundStation)))
            .andExpect(status().isBadRequest());

        // Validate the GroundStation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        // set the field null
        groundStation.setName(null);

        // Create the GroundStation, which fails.

        restGroundStationMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groundStation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        // set the field null
        groundStation.setCountry(null);

        // Create the GroundStation, which fails.

        restGroundStationMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groundStation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkContactEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        // set the field null
        groundStation.setContactEmail(null);

        // Create the GroundStation, which fails.

        restGroundStationMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groundStation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkLatitudeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        // set the field null
        groundStation.setLatitude(null);

        // Create the GroundStation, which fails.

        restGroundStationMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groundStation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkLongitudeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        // set the field null
        groundStation.setLongitude(null);

        // Create the GroundStation, which fails.

        restGroundStationMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groundStation)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllGroundStations() throws Exception {
        // Initialize the database
        insertedGroundStation = groundStationRepository.saveAndFlush(groundStation);

        // Get all the groundStationList
        restGroundStationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groundStation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].accessLevel").value(hasItem(DEFAULT_ACCESS_LEVEL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)));
    }

    @Test
    @Transactional
    void getGroundStation() throws Exception {
        // Initialize the database
        insertedGroundStation = groundStationRepository.saveAndFlush(groundStation);

        // Get the groundStation
        restGroundStationMockMvc
            .perform(get(ENTITY_API_URL_ID, groundStation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groundStation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL))
            .andExpect(jsonPath("$.accessLevel").value(DEFAULT_ACCESS_LEVEL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE));
    }

    @Test
    @Transactional
    void getNonExistingGroundStation() throws Exception {
        // Get the groundStation
        restGroundStationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGroundStation() throws Exception {
        // Initialize the database
        insertedGroundStation = groundStationRepository.saveAndFlush(groundStation);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        groundStationSearchRepository.save(groundStation);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());

        // Update the groundStation
        GroundStation updatedGroundStation = groundStationRepository.findById(groundStation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGroundStation are not directly saved in db
        em.detach(updatedGroundStation);
        updatedGroundStation
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .accessLevel(UPDATED_ACCESS_LEVEL)
            .status(UPDATED_STATUS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restGroundStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGroundStation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedGroundStation))
            )
            .andExpect(status().isOk());

        // Validate the GroundStation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGroundStationToMatchAllProperties(updatedGroundStation);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<GroundStation> groundStationSearchList = Streamable.of(groundStationSearchRepository.findAll()).toList();
                GroundStation testGroundStationSearch = groundStationSearchList.get(searchDatabaseSizeAfter - 1);

                assertGroundStationAllPropertiesEquals(testGroundStationSearch, updatedGroundStation);
            });
    }

    @Test
    @Transactional
    void putNonExistingGroundStation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        groundStation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroundStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groundStation.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(groundStation))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroundStation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchGroundStation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        groundStation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroundStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(groundStation))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroundStation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGroundStation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        groundStation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroundStationMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(groundStation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroundStation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateGroundStationWithPatch() throws Exception {
        // Initialize the database
        insertedGroundStation = groundStationRepository.saveAndFlush(groundStation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the groundStation using partial update
        GroundStation partialUpdatedGroundStation = new GroundStation();
        partialUpdatedGroundStation.setId(groundStation.getId());

        partialUpdatedGroundStation.contactEmail(UPDATED_CONTACT_EMAIL).accessLevel(UPDATED_ACCESS_LEVEL);

        restGroundStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroundStation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGroundStation))
            )
            .andExpect(status().isOk());

        // Validate the GroundStation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGroundStationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGroundStation, groundStation),
            getPersistedGroundStation(groundStation)
        );
    }

    @Test
    @Transactional
    void fullUpdateGroundStationWithPatch() throws Exception {
        // Initialize the database
        insertedGroundStation = groundStationRepository.saveAndFlush(groundStation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the groundStation using partial update
        GroundStation partialUpdatedGroundStation = new GroundStation();
        partialUpdatedGroundStation.setId(groundStation.getId());

        partialUpdatedGroundStation
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .accessLevel(UPDATED_ACCESS_LEVEL)
            .status(UPDATED_STATUS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restGroundStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroundStation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGroundStation))
            )
            .andExpect(status().isOk());

        // Validate the GroundStation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGroundStationUpdatableFieldsEquals(partialUpdatedGroundStation, getPersistedGroundStation(partialUpdatedGroundStation));
    }

    @Test
    @Transactional
    void patchNonExistingGroundStation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        groundStation.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroundStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groundStation.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(groundStation))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroundStation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGroundStation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        groundStation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroundStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(groundStation))
            )
            .andExpect(status().isBadRequest());

        // Validate the GroundStation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGroundStation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        groundStation.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroundStationMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(groundStation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GroundStation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteGroundStation() throws Exception {
        // Initialize the database
        insertedGroundStation = groundStationRepository.saveAndFlush(groundStation);
        groundStationRepository.save(groundStation);
        groundStationSearchRepository.save(groundStation);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the groundStation
        restGroundStationMockMvc
            .perform(delete(ENTITY_API_URL_ID, groundStation.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(groundStationSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchGroundStation() throws Exception {
        // Initialize the database
        insertedGroundStation = groundStationRepository.saveAndFlush(groundStation);
        groundStationSearchRepository.save(groundStation);

        // Search the groundStation
        restGroundStationMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + groundStation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groundStation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].accessLevel").value(hasItem(DEFAULT_ACCESS_LEVEL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)));
    }

    protected long getRepositoryCount() {
        return groundStationRepository.count();
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

    protected GroundStation getPersistedGroundStation(GroundStation groundStation) {
        return groundStationRepository.findById(groundStation.getId()).orElseThrow();
    }

    protected void assertPersistedGroundStationToMatchAllProperties(GroundStation expectedGroundStation) {
        assertGroundStationAllPropertiesEquals(expectedGroundStation, getPersistedGroundStation(expectedGroundStation));
    }

    protected void assertPersistedGroundStationToMatchUpdatableProperties(GroundStation expectedGroundStation) {
        assertGroundStationAllUpdatablePropertiesEquals(expectedGroundStation, getPersistedGroundStation(expectedGroundStation));
    }
}
