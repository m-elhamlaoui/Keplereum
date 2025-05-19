package com.keplereum.backend.web.rest;

import com.keplereum.backend.domain.SatelliteModel;
import com.keplereum.backend.repository.SatelliteModelRepository;
import com.keplereum.backend.repository.search.SatelliteModelSearchRepository;
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
 * REST controller for managing {@link com.keplereum.backend.domain.SatelliteModel}.
 */
@RestController
@RequestMapping("/api/satellite-models")
@Transactional
public class SatelliteModelResource {

    private static final Logger LOG = LoggerFactory.getLogger(SatelliteModelResource.class);

    private static final String ENTITY_NAME = "satelliteModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SatelliteModelRepository satelliteModelRepository;

    private final SatelliteModelSearchRepository satelliteModelSearchRepository;

    public SatelliteModelResource(
        SatelliteModelRepository satelliteModelRepository,
        SatelliteModelSearchRepository satelliteModelSearchRepository
    ) {
        this.satelliteModelRepository = satelliteModelRepository;
        this.satelliteModelSearchRepository = satelliteModelSearchRepository;
    }

    /**
     * {@code POST  /satellite-models} : Create a new satelliteModel.
     *
     * @param satelliteModel the satelliteModel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new satelliteModel, or with status {@code 400 (Bad Request)} if the satelliteModel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SatelliteModel> createSatelliteModel(@Valid @RequestBody SatelliteModel satelliteModel)
        throws URISyntaxException {
        LOG.debug("REST request to save SatelliteModel : {}", satelliteModel);
        if (satelliteModel.getId() != null) {
            throw new BadRequestAlertException("A new satelliteModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        satelliteModel = satelliteModelRepository.save(satelliteModel);
        satelliteModelSearchRepository.index(satelliteModel);
        return ResponseEntity.created(new URI("/api/satellite-models/" + satelliteModel.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, satelliteModel.getId().toString()))
            .body(satelliteModel);
    }

    /**
     * {@code PUT  /satellite-models/:id} : Updates an existing satelliteModel.
     *
     * @param id the id of the satelliteModel to save.
     * @param satelliteModel the satelliteModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated satelliteModel,
     * or with status {@code 400 (Bad Request)} if the satelliteModel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the satelliteModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SatelliteModel> updateSatelliteModel(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SatelliteModel satelliteModel
    ) throws URISyntaxException {
        LOG.debug("REST request to update SatelliteModel : {}, {}", id, satelliteModel);
        if (satelliteModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, satelliteModel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!satelliteModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        satelliteModel = satelliteModelRepository.save(satelliteModel);
        satelliteModelSearchRepository.index(satelliteModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, satelliteModel.getId().toString()))
            .body(satelliteModel);
    }

    /**
     * {@code PATCH  /satellite-models/:id} : Partial updates given fields of an existing satelliteModel, field will ignore if it is null
     *
     * @param id the id of the satelliteModel to save.
     * @param satelliteModel the satelliteModel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated satelliteModel,
     * or with status {@code 400 (Bad Request)} if the satelliteModel is not valid,
     * or with status {@code 404 (Not Found)} if the satelliteModel is not found,
     * or with status {@code 500 (Internal Server Error)} if the satelliteModel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SatelliteModel> partialUpdateSatelliteModel(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SatelliteModel satelliteModel
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SatelliteModel partially : {}, {}", id, satelliteModel);
        if (satelliteModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, satelliteModel.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!satelliteModelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SatelliteModel> result = satelliteModelRepository
            .findById(satelliteModel.getId())
            .map(existingSatelliteModel -> {
                if (satelliteModel.getName() != null) {
                    existingSatelliteModel.setName(satelliteModel.getName());
                }
                if (satelliteModel.getManufacturer() != null) {
                    existingSatelliteModel.setManufacturer(satelliteModel.getManufacturer());
                }
                if (satelliteModel.getWeight() != null) {
                    existingSatelliteModel.setWeight(satelliteModel.getWeight());
                }
                if (satelliteModel.getDimensions() != null) {
                    existingSatelliteModel.setDimensions(satelliteModel.getDimensions());
                }
                if (satelliteModel.getPowerCapacity() != null) {
                    existingSatelliteModel.setPowerCapacity(satelliteModel.getPowerCapacity());
                }
                if (satelliteModel.getExpectedLifespan() != null) {
                    existingSatelliteModel.setExpectedLifespan(satelliteModel.getExpectedLifespan());
                }
                if (satelliteModel.getDesignTrajectoryPredictionFactor() != null) {
                    existingSatelliteModel.setDesignTrajectoryPredictionFactor(satelliteModel.getDesignTrajectoryPredictionFactor());
                }
                if (satelliteModel.getLaunchMass() != null) {
                    existingSatelliteModel.setLaunchMass(satelliteModel.getLaunchMass());
                }
                if (satelliteModel.getDryMass() != null) {
                    existingSatelliteModel.setDryMass(satelliteModel.getDryMass());
                }

                return existingSatelliteModel;
            })
            .map(satelliteModelRepository::save)
            .map(savedSatelliteModel -> {
                satelliteModelSearchRepository.index(savedSatelliteModel);
                return savedSatelliteModel;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, satelliteModel.getId().toString())
        );
    }

    /**
     * {@code GET  /satellite-models} : get all the satelliteModels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of satelliteModels in body.
     */
    @GetMapping("")
    public List<SatelliteModel> getAllSatelliteModels() {
        LOG.debug("REST request to get all SatelliteModels");
        return satelliteModelRepository.findAll();
    }

    /**
     * {@code GET  /satellite-models/:id} : get the "id" satelliteModel.
     *
     * @param id the id of the satelliteModel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the satelliteModel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SatelliteModel> getSatelliteModel(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SatelliteModel : {}", id);
        Optional<SatelliteModel> satelliteModel = satelliteModelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(satelliteModel);
    }

    /**
     * {@code DELETE  /satellite-models/:id} : delete the "id" satelliteModel.
     *
     * @param id the id of the satelliteModel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSatelliteModel(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SatelliteModel : {}", id);
        satelliteModelRepository.deleteById(id);
        satelliteModelSearchRepository.deleteFromIndexById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /satellite-models/_search?query=:query} : search for the satelliteModel corresponding
     * to the query.
     *
     * @param query the query of the satelliteModel search.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public List<SatelliteModel> searchSatelliteModels(@RequestParam("query") String query) {
        LOG.debug("REST request to search SatelliteModels for query {}", query);
        try {
            return StreamSupport.stream(satelliteModelSearchRepository.search(query).spliterator(), false).toList();
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
