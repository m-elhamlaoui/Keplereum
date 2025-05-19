package com.keplereum.backend.web.rest;

import com.keplereum.backend.domain.GroundStation;
import com.keplereum.backend.repository.GroundStationRepository;
import com.keplereum.backend.repository.search.GroundStationSearchRepository;
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
 * REST controller for managing {@link com.keplereum.backend.domain.GroundStation}.
 */
@RestController
@RequestMapping("/api/ground-stations")
@Transactional
public class GroundStationResource {

    private static final Logger LOG = LoggerFactory.getLogger(GroundStationResource.class);

    private static final String ENTITY_NAME = "groundStation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroundStationRepository groundStationRepository;

    private final GroundStationSearchRepository groundStationSearchRepository;

    public GroundStationResource(
        GroundStationRepository groundStationRepository,
        GroundStationSearchRepository groundStationSearchRepository
    ) {
        this.groundStationRepository = groundStationRepository;
        this.groundStationSearchRepository = groundStationSearchRepository;
    }

    /**
     * {@code POST  /ground-stations} : Create a new groundStation.
     *
     * @param groundStation the groundStation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groundStation, or with status {@code 400 (Bad Request)} if the groundStation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GroundStation> createGroundStation(@Valid @RequestBody GroundStation groundStation) throws URISyntaxException {
        LOG.debug("REST request to save GroundStation : {}", groundStation);
        if (groundStation.getId() != null) {
            throw new BadRequestAlertException("A new groundStation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        groundStation = groundStationRepository.save(groundStation);
        groundStationSearchRepository.index(groundStation);
        return ResponseEntity.created(new URI("/api/ground-stations/" + groundStation.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, groundStation.getId().toString()))
            .body(groundStation);
    }

    /**
     * {@code PUT  /ground-stations/:id} : Updates an existing groundStation.
     *
     * @param id the id of the groundStation to save.
     * @param groundStation the groundStation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groundStation,
     * or with status {@code 400 (Bad Request)} if the groundStation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groundStation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GroundStation> updateGroundStation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GroundStation groundStation
    ) throws URISyntaxException {
        LOG.debug("REST request to update GroundStation : {}, {}", id, groundStation);
        if (groundStation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groundStation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groundStationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        groundStation = groundStationRepository.save(groundStation);
        groundStationSearchRepository.index(groundStation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groundStation.getId().toString()))
            .body(groundStation);
    }

    /**
     * {@code PATCH  /ground-stations/:id} : Partial updates given fields of an existing groundStation, field will ignore if it is null
     *
     * @param id the id of the groundStation to save.
     * @param groundStation the groundStation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groundStation,
     * or with status {@code 400 (Bad Request)} if the groundStation is not valid,
     * or with status {@code 404 (Not Found)} if the groundStation is not found,
     * or with status {@code 500 (Internal Server Error)} if the groundStation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GroundStation> partialUpdateGroundStation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GroundStation groundStation
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update GroundStation partially : {}, {}", id, groundStation);
        if (groundStation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, groundStation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!groundStationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GroundStation> result = groundStationRepository
            .findById(groundStation.getId())
            .map(existingGroundStation -> {
                if (groundStation.getName() != null) {
                    existingGroundStation.setName(groundStation.getName());
                }
                if (groundStation.getCountry() != null) {
                    existingGroundStation.setCountry(groundStation.getCountry());
                }
                if (groundStation.getContactEmail() != null) {
                    existingGroundStation.setContactEmail(groundStation.getContactEmail());
                }
                if (groundStation.getAccessLevel() != null) {
                    existingGroundStation.setAccessLevel(groundStation.getAccessLevel());
                }
                if (groundStation.getStatus() != null) {
                    existingGroundStation.setStatus(groundStation.getStatus());
                }
                if (groundStation.getLatitude() != null) {
                    existingGroundStation.setLatitude(groundStation.getLatitude());
                }
                if (groundStation.getLongitude() != null) {
                    existingGroundStation.setLongitude(groundStation.getLongitude());
                }

                return existingGroundStation;
            })
            .map(groundStationRepository::save)
            .map(savedGroundStation -> {
                groundStationSearchRepository.index(savedGroundStation);
                return savedGroundStation;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groundStation.getId().toString())
        );
    }

    /**
     * {@code GET  /ground-stations} : get all the groundStations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groundStations in body.
     */
    @GetMapping("")
    public List<GroundStation> getAllGroundStations() {
        LOG.debug("REST request to get all GroundStations");
        return groundStationRepository.findAll();
    }

    /**
     * {@code GET  /ground-stations/:id} : get the "id" groundStation.
     *
     * @param id the id of the groundStation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groundStation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GroundStation> getGroundStation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get GroundStation : {}", id);
        Optional<GroundStation> groundStation = groundStationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groundStation);
    }

    /**
     * {@code DELETE  /ground-stations/:id} : delete the "id" groundStation.
     *
     * @param id the id of the groundStation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroundStation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete GroundStation : {}", id);
        groundStationRepository.deleteById(id);
        groundStationSearchRepository.deleteFromIndexById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /ground-stations/_search?query=:query} : search for the groundStation corresponding
     * to the query.
     *
     * @param query the query of the groundStation search.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public List<GroundStation> searchGroundStations(@RequestParam("query") String query) {
        LOG.debug("REST request to search GroundStations for query {}", query);
        try {
            return StreamSupport.stream(groundStationSearchRepository.search(query).spliterator(), false).toList();
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
