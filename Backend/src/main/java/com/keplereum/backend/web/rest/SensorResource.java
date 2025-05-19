package com.keplereum.backend.web.rest;

import com.keplereum.backend.domain.Sensor;
import com.keplereum.backend.repository.SensorRepository;
import com.keplereum.backend.repository.search.SensorSearchRepository;
import com.keplereum.backend.web.rest.errors.BadRequestAlertException;
import com.keplereum.backend.web.rest.errors.ElasticsearchExceptionMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.keplereum.backend.domain.Sensor}.
 */
@RestController
@RequestMapping("/api/sensors")
@Transactional
public class SensorResource {

    private static final Logger LOG = LoggerFactory.getLogger(SensorResource.class);

    private static final String ENTITY_NAME = "sensor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SensorRepository sensorRepository;

    private final SensorSearchRepository sensorSearchRepository;

    public SensorResource(SensorRepository sensorRepository, SensorSearchRepository sensorSearchRepository) {
        this.sensorRepository = sensorRepository;
        this.sensorSearchRepository = sensorSearchRepository;
    }

    /**
     * {@code POST  /sensors} : Create a new sensor.
     *
     * @param sensor the sensor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sensor, or with status {@code 400 (Bad Request)} if the sensor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Sensor> createSensor(@Valid @RequestBody Sensor sensor) throws URISyntaxException {
        LOG.debug("REST request to save Sensor : {}", sensor);
        if (sensor.getId() != null) {
            throw new BadRequestAlertException("A new sensor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sensor = sensorRepository.save(sensor);
        sensorSearchRepository.index(sensor);
        return ResponseEntity.created(new URI("/api/sensors/" + sensor.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sensor.getId().toString()))
            .body(sensor);
    }

    /**
     * {@code PUT  /sensors/:id} : Updates an existing sensor.
     *
     * @param id the id of the sensor to save.
     * @param sensor the sensor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sensor,
     * or with status {@code 400 (Bad Request)} if the sensor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sensor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sensor> updateSensor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Sensor sensor
    ) throws URISyntaxException {
        LOG.debug("REST request to update Sensor : {}, {}", id, sensor);
        if (sensor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sensor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sensorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sensor = sensorRepository.save(sensor);
        sensorSearchRepository.index(sensor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sensor.getId().toString()))
            .body(sensor);
    }

    /**
     * {@code PATCH  /sensors/:id} : Partial updates given fields of an existing sensor, field will ignore if it is null
     *
     * @param id the id of the sensor to save.
     * @param sensor the sensor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sensor,
     * or with status {@code 400 (Bad Request)} if the sensor is not valid,
     * or with status {@code 404 (Not Found)} if the sensor is not found,
     * or with status {@code 500 (Internal Server Error)} if the sensor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sensor> partialUpdateSensor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Sensor sensor
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Sensor partially : {}, {}", id, sensor);
        if (sensor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sensor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sensorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sensor> result = sensorRepository
            .findById(sensor.getId())
            .map(existingSensor -> {
                if (sensor.getType() != null) {
                    existingSensor.setType(sensor.getType());
                }
                if (sensor.getMaxHeight() != null) {
                    existingSensor.setMaxHeight(sensor.getMaxHeight());
                }
                if (sensor.getMaxRadius() != null) {
                    existingSensor.setMaxRadius(sensor.getMaxRadius());
                }
                if (sensor.getActivity() != null) {
                    existingSensor.setActivity(sensor.getActivity());
                }

                return existingSensor;
            })
            .map(sensorRepository::save)
            .map(savedSensor -> {
                sensorSearchRepository.index(savedSensor);
                return savedSensor;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sensor.getId().toString())
        );
    }

    /**
     * {@code GET  /sensors} : get all the sensors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sensors in body.
     */
    @GetMapping("")
    public List<Sensor> getAllSensors() {
        LOG.debug("REST request to get all Sensors");
        return sensorRepository.findAll();
    }

    /**
     * {@code GET  /sensors/:id} : get the "id" sensor.
     *
     * @param id the id of the sensor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sensor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensor(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Sensor : {}", id);
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sensor);
    }

    /**
     * {@code DELETE  /sensors/:id} : delete the "id" sensor.
     *
     * @param id the id of the sensor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Sensor : {}", id);
        sensorRepository.deleteById(id);
        sensorSearchRepository.deleteFromIndexById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /sensors/_search?query=:query} : search for the sensor corresponding
     * to the query.
     *
     * @param query the query of the sensor search.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public List<Sensor> searchSensors(@RequestParam("query") String query) {
        LOG.debug("REST request to search Sensors for query {}", query);
        try {
            return StreamSupport.stream(sensorSearchRepository.search(query).spliterator(), false).toList();
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
