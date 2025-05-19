package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.AlertAsserts.*;
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
import com.keplereum.backend.domain.Alert;
import com.keplereum.backend.domain.enumeration.AlertType;
import com.keplereum.backend.repository.AlertRepository;
import com.keplereum.backend.repository.search.AlertSearchRepository;
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
 * Integration tests for the {@link AlertResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlertResourceIT {

    private static final AlertType DEFAULT_ALERT_TYPE = AlertType.TSUNAMI;
    private static final AlertType UPDATED_ALERT_TYPE = AlertType.TORNADO;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/alerts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/alerts/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private AlertSearchRepository alertSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlertMockMvc;

    private Alert alert;

    private Alert insertedAlert;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alert createEntity() {
        return new Alert()
            .alertType(DEFAULT_ALERT_TYPE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .createdAt(DEFAULT_CREATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alert createUpdatedEntity() {
        return new Alert()
            .alertType(UPDATED_ALERT_TYPE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .createdAt(UPDATED_CREATED_AT);
    }

    @BeforeEach
    void initTest() {
        alert = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAlert != null) {
            alertRepository.delete(insertedAlert);
            alertSearchRepository.delete(insertedAlert);
            insertedAlert = null;
        }
    }

    @Test
    @Transactional
    void createAlert() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        // Create the Alert
        var returnedAlert = om.readValue(
            restAlertMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alert)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Alert.class
        );

        // Validate the Alert in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAlertUpdatableFieldsEquals(returnedAlert, getPersistedAlert(returnedAlert));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedAlert = returnedAlert;
    }

    @Test
    @Transactional
    void createAlertWithExistingId() throws Exception {
        // Create the Alert with an existing ID
        alert.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlertMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alert)))
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkAlertTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        // set the field null
        alert.setAlertType(null);

        // Create the Alert, which fails.

        restAlertMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alert)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkLatitudeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        // set the field null
        alert.setLatitude(null);

        // Create the Alert, which fails.

        restAlertMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alert)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkLongitudeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        // set the field null
        alert.setLongitude(null);

        // Create the Alert, which fails.

        restAlertMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alert)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        // set the field null
        alert.setCreatedAt(null);

        // Create the Alert, which fails.

        restAlertMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alert)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllAlerts() throws Exception {
        // Initialize the database
        insertedAlert = alertRepository.saveAndFlush(alert);

        // Get all the alertList
        restAlertMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alert.getId().intValue())))
            .andExpect(jsonPath("$.[*].alertType").value(hasItem(DEFAULT_ALERT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getAlert() throws Exception {
        // Initialize the database
        insertedAlert = alertRepository.saveAndFlush(alert);

        // Get the alert
        restAlertMockMvc
            .perform(get(ENTITY_API_URL_ID, alert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alert.getId().intValue()))
            .andExpect(jsonPath("$.alertType").value(DEFAULT_ALERT_TYPE.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAlert() throws Exception {
        // Get the alert
        restAlertMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAlert() throws Exception {
        // Initialize the database
        insertedAlert = alertRepository.saveAndFlush(alert);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        alertSearchRepository.save(alert);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());

        // Update the alert
        Alert updatedAlert = alertRepository.findById(alert.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAlert are not directly saved in db
        em.detach(updatedAlert);
        updatedAlert.alertType(UPDATED_ALERT_TYPE).latitude(UPDATED_LATITUDE).longitude(UPDATED_LONGITUDE).createdAt(UPDATED_CREATED_AT);

        restAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAlert.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAlert))
            )
            .andExpect(status().isOk());

        // Validate the Alert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAlertToMatchAllProperties(updatedAlert);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Alert> alertSearchList = Streamable.of(alertSearchRepository.findAll()).toList();
                Alert testAlertSearch = alertSearchList.get(searchDatabaseSizeAfter - 1);

                assertAlertAllPropertiesEquals(testAlertSearch, updatedAlert);
            });
    }

    @Test
    @Transactional
    void putNonExistingAlert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        alert.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alert.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        alert.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        alert.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(alert)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateAlertWithPatch() throws Exception {
        // Initialize the database
        insertedAlert = alertRepository.saveAndFlush(alert);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the alert using partial update
        Alert partialUpdatedAlert = new Alert();
        partialUpdatedAlert.setId(alert.getId());

        partialUpdatedAlert.longitude(UPDATED_LONGITUDE);

        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlert.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAlert))
            )
            .andExpect(status().isOk());

        // Validate the Alert in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAlertUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAlert, alert), getPersistedAlert(alert));
    }

    @Test
    @Transactional
    void fullUpdateAlertWithPatch() throws Exception {
        // Initialize the database
        insertedAlert = alertRepository.saveAndFlush(alert);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the alert using partial update
        Alert partialUpdatedAlert = new Alert();
        partialUpdatedAlert.setId(alert.getId());

        partialUpdatedAlert
            .alertType(UPDATED_ALERT_TYPE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .createdAt(UPDATED_CREATED_AT);

        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlert.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAlert))
            )
            .andExpect(status().isOk());

        // Validate the Alert in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAlertUpdatableFieldsEquals(partialUpdatedAlert, getPersistedAlert(partialUpdatedAlert));
    }

    @Test
    @Transactional
    void patchNonExistingAlert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        alert.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alert.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        alert.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(alert))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        alert.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlertMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(alert)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteAlert() throws Exception {
        // Initialize the database
        insertedAlert = alertRepository.saveAndFlush(alert);
        alertRepository.save(alert);
        alertSearchRepository.save(alert);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the alert
        restAlertMockMvc
            .perform(delete(ENTITY_API_URL_ID, alert.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(alertSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchAlert() throws Exception {
        // Initialize the database
        insertedAlert = alertRepository.saveAndFlush(alert);
        alertSearchRepository.save(alert);

        // Search the alert
        restAlertMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + alert.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alert.getId().intValue())))
            .andExpect(jsonPath("$.[*].alertType").value(hasItem(DEFAULT_ALERT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    protected long getRepositoryCount() {
        return alertRepository.count();
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

    protected Alert getPersistedAlert(Alert alert) {
        return alertRepository.findById(alert.getId()).orElseThrow();
    }

    protected void assertPersistedAlertToMatchAllProperties(Alert expectedAlert) {
        assertAlertAllPropertiesEquals(expectedAlert, getPersistedAlert(expectedAlert));
    }

    protected void assertPersistedAlertToMatchUpdatableProperties(Alert expectedAlert) {
        assertAlertAllUpdatablePropertiesEquals(expectedAlert, getPersistedAlert(expectedAlert));
    }
}
