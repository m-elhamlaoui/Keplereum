package com.keplereum.backend.web.rest;

import com.keplereum.backend.domain.Satellite;
import com.keplereum.backend.repository.SatelliteRepository;
import com.keplereum.backend.repository.search.SatelliteSearchRepository;
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
 * REST controller for managing {@link com.keplereum.backend.domain.Satellite}.
 */
@RestController
@RequestMapping("/api/satellites")
@Transactional
public class SatelliteResource {

    private static final Logger LOG = LoggerFactory.getLogger(SatelliteResource.class);

    private static final String ENTITY_NAME = "satellite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SatelliteRepository satelliteRepository;

    private final SatelliteSearchRepository satelliteSearchRepository;

    public SatelliteResource(SatelliteRepository satelliteRepository, SatelliteSearchRepository satelliteSearchRepository) {
        this.satelliteRepository = satelliteRepository;
        this.satelliteSearchRepository = satelliteSearchRepository;
    }

    /**
     * {@code POST  /satellites} : Create a new satellite.
     *
     * @param satellite the satellite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new satellite, or with status {@code 400 (Bad Request)} if the satellite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Satellite> createSatellite(@Valid @RequestBody Satellite satellite) throws URISyntaxException {
        LOG.debug("REST request to save Satellite : {}", satellite);
        if (satellite.getId() != null) {
            throw new BadRequestAlertException("A new satellite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        satellite = satelliteRepository.save(satellite);
        satelliteSearchRepository.index(satellite);
        return ResponseEntity.created(new URI("/api/satellites/" + satellite.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, satellite.getId().toString()))
            .body(satellite);
    }

    /**
     * {@code PUT  /satellites/:id} : Updates an existing satellite.
     *
     * @param id the id of the satellite to save.
     * @param satellite the satellite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated satellite,
     * or with status {@code 400 (Bad Request)} if the satellite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the satellite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Satellite> updateSatellite(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Satellite satellite
    ) throws URISyntaxException {
        LOG.debug("REST request to update Satellite : {}, {}", id, satellite);
        if (satellite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, satellite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!satelliteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        satellite = satelliteRepository.save(satellite);
        satelliteSearchRepository.index(satellite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, satellite.getId().toString()))
            .body(satellite);
    }

    /**
     * {@code PATCH  /satellites/:id} : Partial updates given fields of an existing satellite, field will ignore if it is null
     *
     * @param id the id of the satellite to save.
     * @param satellite the satellite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated satellite,
     * or with status {@code 400 (Bad Request)} if the satellite is not valid,
     * or with status {@code 404 (Not Found)} if the satellite is not found,
     * or with status {@code 500 (Internal Server Error)} if the satellite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Satellite> partialUpdateSatellite(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Satellite satellite
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Satellite partially : {}, {}", id, satellite);
        if (satellite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, satellite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!satelliteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Satellite> result = satelliteRepository
            .findById(satellite.getId())
            .map(existingSatellite -> {
                if (satellite.getName() != null) {
                    existingSatellite.setName(satellite.getName());
                }
                if (satellite.getLaunchDate() != null) {
                    existingSatellite.setLaunchDate(satellite.getLaunchDate());
                }
                if (satellite.getStatus() != null) {
                    existingSatellite.setStatus(satellite.getStatus());
                }

                return existingSatellite;
            })
            .map(satelliteRepository::save)
            .map(savedSatellite -> {
                satelliteSearchRepository.index(savedSatellite);
                return savedSatellite;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, satellite.getId().toString())
        );
    }

    /**
     * {@code GET  /satellites} : get all the satellites.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of satellites in body.
     */
    @GetMapping("")
    public List<Satellite> getAllSatellites(@RequestParam(name = "filter", required = false) String filter) {
        if ("networknode-is-null".equals(filter)) {
            LOG.debug("REST request to get all Satellites where networkNode is null");
            return StreamSupport.stream(satelliteRepository.findAll().spliterator(), false)
                .filter(satellite -> satellite.getNetworkNode() == null)
                .toList();
        }
        LOG.debug("REST request to get all Satellites");
        return satelliteRepository.findAll();
    }

    /**
     * {@code GET  /satellites/:id} : get the "id" satellite.
     *
     * @param id the id of the satellite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the satellite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Satellite> getSatellite(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Satellite : {}", id);
        Optional<Satellite> satellite = satelliteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(satellite);
    }

    /**
     * {@code DELETE  /satellites/:id} : delete the "id" satellite.
     *
     * @param id the id of the satellite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSatellite(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Satellite : {}", id);
        satelliteRepository.deleteById(id);
        satelliteSearchRepository.deleteFromIndexById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /satellites/_search?query=:query} : search for the satellite corresponding
     * to the query.
     *
     * @param query the query of the satellite search.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public List<Satellite> searchSatellites(@RequestParam("query") String query) {
        LOG.debug("REST request to search Satellites for query {}", query);
        try {
            return StreamSupport.stream(satelliteSearchRepository.search(query).spliterator(), false).toList();
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
