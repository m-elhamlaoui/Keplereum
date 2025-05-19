package com.keplereum.backend.web.rest;

import com.keplereum.backend.domain.SatelliteTrajectory;
import com.keplereum.backend.repository.SatelliteTrajectoryRepository;
import com.keplereum.backend.repository.search.SatelliteTrajectorySearchRepository;
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
 * REST controller for managing {@link com.keplereum.backend.domain.SatelliteTrajectory}.
 */
@RestController
@RequestMapping("/api/satellite-trajectories")
@Transactional
public class SatelliteTrajectoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(SatelliteTrajectoryResource.class);

    private static final String ENTITY_NAME = "satelliteTrajectory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SatelliteTrajectoryRepository satelliteTrajectoryRepository;

    private final SatelliteTrajectorySearchRepository satelliteTrajectorySearchRepository;

    public SatelliteTrajectoryResource(
        SatelliteTrajectoryRepository satelliteTrajectoryRepository,
        SatelliteTrajectorySearchRepository satelliteTrajectorySearchRepository
    ) {
        this.satelliteTrajectoryRepository = satelliteTrajectoryRepository;
        this.satelliteTrajectorySearchRepository = satelliteTrajectorySearchRepository;
    }

    /**
     * {@code POST  /satellite-trajectories} : Create a new satelliteTrajectory.
     *
     * @param satelliteTrajectory the satelliteTrajectory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new satelliteTrajectory, or with status {@code 400 (Bad Request)} if the satelliteTrajectory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SatelliteTrajectory> createSatelliteTrajectory(@Valid @RequestBody SatelliteTrajectory satelliteTrajectory)
        throws URISyntaxException {
        LOG.debug("REST request to save SatelliteTrajectory : {}", satelliteTrajectory);
        if (satelliteTrajectory.getId() != null) {
            throw new BadRequestAlertException("A new satelliteTrajectory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        satelliteTrajectory = satelliteTrajectoryRepository.save(satelliteTrajectory);
        satelliteTrajectorySearchRepository.index(satelliteTrajectory);
        return ResponseEntity.created(new URI("/api/satellite-trajectories/" + satelliteTrajectory.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, satelliteTrajectory.getId().toString()))
            .body(satelliteTrajectory);
    }

    /**
     * {@code PUT  /satellite-trajectories/:id} : Updates an existing satelliteTrajectory.
     *
     * @param id the id of the satelliteTrajectory to save.
     * @param satelliteTrajectory the satelliteTrajectory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated satelliteTrajectory,
     * or with status {@code 400 (Bad Request)} if the satelliteTrajectory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the satelliteTrajectory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SatelliteTrajectory> updateSatelliteTrajectory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SatelliteTrajectory satelliteTrajectory
    ) throws URISyntaxException {
        LOG.debug("REST request to update SatelliteTrajectory : {}, {}", id, satelliteTrajectory);
        if (satelliteTrajectory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, satelliteTrajectory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!satelliteTrajectoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        satelliteTrajectory = satelliteTrajectoryRepository.save(satelliteTrajectory);
        satelliteTrajectorySearchRepository.index(satelliteTrajectory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, satelliteTrajectory.getId().toString()))
            .body(satelliteTrajectory);
    }

    /**
     * {@code PATCH  /satellite-trajectories/:id} : Partial updates given fields of an existing satelliteTrajectory, field will ignore if it is null
     *
     * @param id the id of the satelliteTrajectory to save.
     * @param satelliteTrajectory the satelliteTrajectory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated satelliteTrajectory,
     * or with status {@code 400 (Bad Request)} if the satelliteTrajectory is not valid,
     * or with status {@code 404 (Not Found)} if the satelliteTrajectory is not found,
     * or with status {@code 500 (Internal Server Error)} if the satelliteTrajectory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SatelliteTrajectory> partialUpdateSatelliteTrajectory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SatelliteTrajectory satelliteTrajectory
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SatelliteTrajectory partially : {}, {}", id, satelliteTrajectory);
        if (satelliteTrajectory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, satelliteTrajectory.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!satelliteTrajectoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SatelliteTrajectory> result = satelliteTrajectoryRepository
            .findById(satelliteTrajectory.getId())
            .map(existingSatelliteTrajectory -> {
                if (satelliteTrajectory.getStatus() != null) {
                    existingSatelliteTrajectory.setStatus(satelliteTrajectory.getStatus());
                }
                if (satelliteTrajectory.getStartTime() != null) {
                    existingSatelliteTrajectory.setStartTime(satelliteTrajectory.getStartTime());
                }
                if (satelliteTrajectory.getEndTime() != null) {
                    existingSatelliteTrajectory.setEndTime(satelliteTrajectory.getEndTime());
                }
                if (satelliteTrajectory.getOrbitEccentricity() != null) {
                    existingSatelliteTrajectory.setOrbitEccentricity(satelliteTrajectory.getOrbitEccentricity());
                }
                if (satelliteTrajectory.getOrbitInclination() != null) {
                    existingSatelliteTrajectory.setOrbitInclination(satelliteTrajectory.getOrbitInclination());
                }
                if (satelliteTrajectory.getOrbitRightAscension() != null) {
                    existingSatelliteTrajectory.setOrbitRightAscension(satelliteTrajectory.getOrbitRightAscension());
                }
                if (satelliteTrajectory.getOrbitArgumentOfPerigee() != null) {
                    existingSatelliteTrajectory.setOrbitArgumentOfPerigee(satelliteTrajectory.getOrbitArgumentOfPerigee());
                }
                if (satelliteTrajectory.getOrbitMeanAnomaly() != null) {
                    existingSatelliteTrajectory.setOrbitMeanAnomaly(satelliteTrajectory.getOrbitMeanAnomaly());
                }
                if (satelliteTrajectory.getOrbitPeriapsis() != null) {
                    existingSatelliteTrajectory.setOrbitPeriapsis(satelliteTrajectory.getOrbitPeriapsis());
                }
                if (satelliteTrajectory.getChangeReason() != null) {
                    existingSatelliteTrajectory.setChangeReason(satelliteTrajectory.getChangeReason());
                }

                return existingSatelliteTrajectory;
            })
            .map(satelliteTrajectoryRepository::save)
            .map(savedSatelliteTrajectory -> {
                satelliteTrajectorySearchRepository.index(savedSatelliteTrajectory);
                return savedSatelliteTrajectory;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, satelliteTrajectory.getId().toString())
        );
    }

    /**
     * {@code GET  /satellite-trajectories} : get all the satelliteTrajectories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of satelliteTrajectories in body.
     */
    @GetMapping("")
    public List<SatelliteTrajectory> getAllSatelliteTrajectories() {
        LOG.debug("REST request to get all SatelliteTrajectories");
        return satelliteTrajectoryRepository.findAll();
    }

    /**
     * {@code GET  /satellite-trajectories/:id} : get the "id" satelliteTrajectory.
     *
     * @param id the id of the satelliteTrajectory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the satelliteTrajectory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SatelliteTrajectory> getSatelliteTrajectory(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SatelliteTrajectory : {}", id);
        Optional<SatelliteTrajectory> satelliteTrajectory = satelliteTrajectoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(satelliteTrajectory);
    }

    /**
     * {@code DELETE  /satellite-trajectories/:id} : delete the "id" satelliteTrajectory.
     *
     * @param id the id of the satelliteTrajectory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSatelliteTrajectory(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SatelliteTrajectory : {}", id);
        satelliteTrajectoryRepository.deleteById(id);
        satelliteTrajectorySearchRepository.deleteFromIndexById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /satellite-trajectories/_search?query=:query} : search for the satelliteTrajectory corresponding
     * to the query.
     *
     * @param query the query of the satelliteTrajectory search.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public List<SatelliteTrajectory> searchSatelliteTrajectories(@RequestParam("query") String query) {
        LOG.debug("REST request to search SatelliteTrajectories for query {}", query);
        try {
            return StreamSupport.stream(satelliteTrajectorySearchRepository.search(query).spliterator(), false).toList();
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
