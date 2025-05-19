package com.keplereum.backend.web.rest;

import com.keplereum.backend.domain.ValidatorAction;
import com.keplereum.backend.repository.ValidatorActionRepository;
import com.keplereum.backend.repository.search.ValidatorActionSearchRepository;
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
 * REST controller for managing {@link com.keplereum.backend.domain.ValidatorAction}.
 */
@RestController
@RequestMapping("/api/validator-actions")
@Transactional
public class ValidatorActionResource {

    private static final Logger LOG = LoggerFactory.getLogger(ValidatorActionResource.class);

    private static final String ENTITY_NAME = "validatorAction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ValidatorActionRepository validatorActionRepository;

    private final ValidatorActionSearchRepository validatorActionSearchRepository;

    public ValidatorActionResource(
        ValidatorActionRepository validatorActionRepository,
        ValidatorActionSearchRepository validatorActionSearchRepository
    ) {
        this.validatorActionRepository = validatorActionRepository;
        this.validatorActionSearchRepository = validatorActionSearchRepository;
    }

    /**
     * {@code POST  /validator-actions} : Create a new validatorAction.
     *
     * @param validatorAction the validatorAction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new validatorAction, or with status {@code 400 (Bad Request)} if the validatorAction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ValidatorAction> createValidatorAction(@Valid @RequestBody ValidatorAction validatorAction)
        throws URISyntaxException {
        LOG.debug("REST request to save ValidatorAction : {}", validatorAction);
        if (validatorAction.getId() != null) {
            throw new BadRequestAlertException("A new validatorAction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        validatorAction = validatorActionRepository.save(validatorAction);
        validatorActionSearchRepository.index(validatorAction);
        return ResponseEntity.created(new URI("/api/validator-actions/" + validatorAction.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, validatorAction.getId().toString()))
            .body(validatorAction);
    }

    /**
     * {@code PUT  /validator-actions/:id} : Updates an existing validatorAction.
     *
     * @param id the id of the validatorAction to save.
     * @param validatorAction the validatorAction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated validatorAction,
     * or with status {@code 400 (Bad Request)} if the validatorAction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the validatorAction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ValidatorAction> updateValidatorAction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ValidatorAction validatorAction
    ) throws URISyntaxException {
        LOG.debug("REST request to update ValidatorAction : {}, {}", id, validatorAction);
        if (validatorAction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, validatorAction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!validatorActionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        validatorAction = validatorActionRepository.save(validatorAction);
        validatorActionSearchRepository.index(validatorAction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, validatorAction.getId().toString()))
            .body(validatorAction);
    }

    /**
     * {@code PATCH  /validator-actions/:id} : Partial updates given fields of an existing validatorAction, field will ignore if it is null
     *
     * @param id the id of the validatorAction to save.
     * @param validatorAction the validatorAction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated validatorAction,
     * or with status {@code 400 (Bad Request)} if the validatorAction is not valid,
     * or with status {@code 404 (Not Found)} if the validatorAction is not found,
     * or with status {@code 500 (Internal Server Error)} if the validatorAction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ValidatorAction> partialUpdateValidatorAction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ValidatorAction validatorAction
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ValidatorAction partially : {}, {}", id, validatorAction);
        if (validatorAction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, validatorAction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!validatorActionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ValidatorAction> result = validatorActionRepository
            .findById(validatorAction.getId())
            .map(existingValidatorAction -> {
                if (validatorAction.getActionType() != null) {
                    existingValidatorAction.setActionType(validatorAction.getActionType());
                }
                if (validatorAction.getCreatedAt() != null) {
                    existingValidatorAction.setCreatedAt(validatorAction.getCreatedAt());
                }

                return existingValidatorAction;
            })
            .map(validatorActionRepository::save)
            .map(savedValidatorAction -> {
                validatorActionSearchRepository.index(savedValidatorAction);
                return savedValidatorAction;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, validatorAction.getId().toString())
        );
    }

    /**
     * {@code GET  /validator-actions} : get all the validatorActions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of validatorActions in body.
     */
    @GetMapping("")
    public List<ValidatorAction> getAllValidatorActions() {
        LOG.debug("REST request to get all ValidatorActions");
        return validatorActionRepository.findAll();
    }

    /**
     * {@code GET  /validator-actions/:id} : get the "id" validatorAction.
     *
     * @param id the id of the validatorAction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the validatorAction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ValidatorAction> getValidatorAction(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ValidatorAction : {}", id);
        Optional<ValidatorAction> validatorAction = validatorActionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(validatorAction);
    }

    /**
     * {@code DELETE  /validator-actions/:id} : delete the "id" validatorAction.
     *
     * @param id the id of the validatorAction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValidatorAction(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ValidatorAction : {}", id);
        validatorActionRepository.deleteById(id);
        validatorActionSearchRepository.deleteFromIndexById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /validator-actions/_search?query=:query} : search for the validatorAction corresponding
     * to the query.
     *
     * @param query the query of the validatorAction search.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public List<ValidatorAction> searchValidatorActions(@RequestParam("query") String query) {
        LOG.debug("REST request to search ValidatorActions for query {}", query);
        try {
            return StreamSupport.stream(validatorActionSearchRepository.search(query).spliterator(), false).toList();
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
