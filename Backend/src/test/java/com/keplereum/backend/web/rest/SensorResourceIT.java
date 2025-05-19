package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.SensorAsserts.*;
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
import com.keplereum.backend.domain.Sensor;
import com.keplereum.backend.domain.enumeration.SensorActivity;
import com.keplereum.backend.repository.SensorRepository;
import com.keplereum.backend.repository.search.SensorSearchRepository;
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
 * Integration tests for the {@link SensorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SensorResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_MAX_HEIGHT = 1D;
    private static final Double UPDATED_MAX_HEIGHT = 2D;

    private static final Double DEFAULT_MAX_RADIUS = 1D;
    private static final Double UPDATED_MAX_RADIUS = 2D;

    private static final SensorActivity DEFAULT_ACTIVITY = SensorActivity.ACTIVE;
    private static final SensorActivity UPDATED_ACTIVITY = SensorActivity.INACTIVE;

    private static final String ENTITY_API_URL = "/api/sensors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/sensors/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorSearchRepository sensorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSensorMockMvc;

    private Sensor sensor;

    private Sensor insertedSensor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sensor createEntity() {
        return new Sensor().type(DEFAULT_TYPE).maxHeight(DEFAULT_MAX_HEIGHT).maxRadius(DEFAULT_MAX_RADIUS).activity(DEFAULT_ACTIVITY);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sensor createUpdatedEntity() {
        return new Sensor().type(UPDATED_TYPE).maxHeight(UPDATED_MAX_HEIGHT).maxRadius(UPDATED_MAX_RADIUS).activity(UPDATED_ACTIVITY);
    }

    @BeforeEach
    void initTest() {
        sensor = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSensor != null) {
            sensorRepository.delete(insertedSensor);
            sensorSearchRepository.delete(insertedSensor);
            insertedSensor = null;
        }
    }

    @Test
    @Transactional
    void createSensor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        // Create the Sensor
        var returnedSensor = om.readValue(
            restSensorMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensor)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Sensor.class
        );

        // Validate the Sensor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSensorUpdatableFieldsEquals(returnedSensor, getPersistedSensor(returnedSensor));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedSensor = returnedSensor;
    }

    @Test
    @Transactional
    void createSensorWithExistingId() throws Exception {
        // Create the Sensor with an existing ID
        sensor.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensor)))
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        // set the field null
        sensor.setType(null);

        // Create the Sensor, which fails.

        restSensorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensor)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkActivityIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        // set the field null
        sensor.setActivity(null);

        // Create the Sensor, which fails.

        restSensorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensor)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllSensors() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get all the sensorList
        restSensorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensor.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].maxHeight").value(hasItem(DEFAULT_MAX_HEIGHT)))
            .andExpect(jsonPath("$.[*].maxRadius").value(hasItem(DEFAULT_MAX_RADIUS)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY.toString())));
    }

    @Test
    @Transactional
    void getSensor() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        // Get the sensor
        restSensorMockMvc
            .perform(get(ENTITY_API_URL_ID, sensor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sensor.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.maxHeight").value(DEFAULT_MAX_HEIGHT))
            .andExpect(jsonPath("$.maxRadius").value(DEFAULT_MAX_RADIUS))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSensor() throws Exception {
        // Get the sensor
        restSensorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSensor() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        sensorSearchRepository.save(sensor);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());

        // Update the sensor
        Sensor updatedSensor = sensorRepository.findById(sensor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSensor are not directly saved in db
        em.detach(updatedSensor);
        updatedSensor.type(UPDATED_TYPE).maxHeight(UPDATED_MAX_HEIGHT).maxRadius(UPDATED_MAX_RADIUS).activity(UPDATED_ACTIVITY);

        restSensorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSensor.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSensor))
            )
            .andExpect(status().isOk());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSensorToMatchAllProperties(updatedSensor);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<Sensor> sensorSearchList = Streamable.of(sensorSearchRepository.findAll()).toList();
                Sensor testSensorSearch = sensorSearchList.get(searchDatabaseSizeAfter - 1);

                assertSensorAllPropertiesEquals(testSensorSearch, updatedSensor);
            });
    }

    @Test
    @Transactional
    void putNonExistingSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        sensor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sensor.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sensor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        sensor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sensor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        sensor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sensor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateSensorWithPatch() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensor using partial update
        Sensor partialUpdatedSensor = new Sensor();
        partialUpdatedSensor.setId(sensor.getId());

        partialUpdatedSensor.type(UPDATED_TYPE).maxHeight(UPDATED_MAX_HEIGHT);

        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSensor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSensor))
            )
            .andExpect(status().isOk());

        // Validate the Sensor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSensorUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSensor, sensor), getPersistedSensor(sensor));
    }

    @Test
    @Transactional
    void fullUpdateSensorWithPatch() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sensor using partial update
        Sensor partialUpdatedSensor = new Sensor();
        partialUpdatedSensor.setId(sensor.getId());

        partialUpdatedSensor.type(UPDATED_TYPE).maxHeight(UPDATED_MAX_HEIGHT).maxRadius(UPDATED_MAX_RADIUS).activity(UPDATED_ACTIVITY);

        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSensor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSensor))
            )
            .andExpect(status().isOk());

        // Validate the Sensor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSensorUpdatableFieldsEquals(partialUpdatedSensor, getPersistedSensor(partialUpdatedSensor));
    }

    @Test
    @Transactional
    void patchNonExistingSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        sensor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sensor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sensor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        sensor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sensor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSensor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        sensor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensorMockMvc
            .perform(patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sensor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sensor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteSensor() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);
        sensorRepository.save(sensor);
        sensorSearchRepository.save(sensor);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the sensor
        restSensorMockMvc
            .perform(delete(ENTITY_API_URL_ID, sensor.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(sensorSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchSensor() throws Exception {
        // Initialize the database
        insertedSensor = sensorRepository.saveAndFlush(sensor);
        sensorSearchRepository.save(sensor);

        // Search the sensor
        restSensorMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + sensor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensor.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].maxHeight").value(hasItem(DEFAULT_MAX_HEIGHT)))
            .andExpect(jsonPath("$.[*].maxRadius").value(hasItem(DEFAULT_MAX_RADIUS)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY.toString())));
    }

    protected long getRepositoryCount() {
        return sensorRepository.count();
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

    protected Sensor getPersistedSensor(Sensor sensor) {
        return sensorRepository.findById(sensor.getId()).orElseThrow();
    }

    protected void assertPersistedSensorToMatchAllProperties(Sensor expectedSensor) {
        assertSensorAllPropertiesEquals(expectedSensor, getPersistedSensor(expectedSensor));
    }

    protected void assertPersistedSensorToMatchUpdatableProperties(Sensor expectedSensor) {
        assertSensorAllUpdatablePropertiesEquals(expectedSensor, getPersistedSensor(expectedSensor));
    }
}
