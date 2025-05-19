package com.keplereum.backend.web.rest;

import com.keplereum.backend.domain.Confirmation;
import com.keplereum.backend.repository.ConfirmationRepository;
import com.keplereum.backend.repository.search.ConfirmationSearchRepository;
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
 * REST controller for managing {@link com.keplereum.backend.domain.Confirmation}.
 */
@RestController
@RequestMapping("/api/confirmations")
@Transactional
public class ConfirmationResource {

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmationResource.class);

    private static final String ENTITY_NAME = "confirmation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConfirmationRepository confirmationRepository;

    private final ConfirmationSearchRepository confirmationSearchRepository;

    public ConfirmationResource(ConfirmationRepository confirmationRepository, ConfirmationSearchRepository confirmationSearchRepository) {
        this.confirmationRepository = confirmationRepository;
        this.confirmationSearchRepository = confirmationSearchRepository;
    }

    /**
     * {@code POST  /confirmations} : Create a new confirmation.
     *
     * @param confirmation the confirmation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new confirmation, or with status {@code 400 (Bad Request)} if the confirmation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Confirmation> createConfirmation(@Valid @RequestBody Confirmation confirmation) throws URISyntaxException {
        LOG.debug("REST request to save Confirmation : {}", confirmation);
        if (confirmation.getId() != null) {
            throw new BadRequestAlertException("A new confirmation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        confirmation = confirmationRepository.save(confirmation);
        confirmationSearchRepository.index(confirmation);
        return ResponseEntity.created(new URI("/api/confirmations/" + confirmation.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, confirmation.getId().toString()))
            .body(confirmation);
    }

    /**
     * {@code PUT  /confirmations/:id} : Updates an existing confirmation.
     *
     * @param id the id of the confirmation to save.
     * @param confirmation the confirmation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated confirmation,
     * or with status {@code 400 (Bad Request)} if the confirmation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the confirmation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Confirmation> updateConfirmation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Confirmation confirmation
    ) throws URISyntaxException {
        LOG.debug("REST request to update Confirmation : {}, {}", id, confirmation);
        if (confirmation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, confirmation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!confirmationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        confirmation = confirmationRepository.save(confirmation);
        confirmationSearchRepository.index(confirmation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, confirmation.getId().toString()))
            .body(confirmation);
    }

    /**
     * {@code PATCH  /confirmations/:id} : Partial updates given fields of an existing confirmation, field will ignore if it is null
     *
     * @param id the id of the confirmation to save.
     * @param confirmation the confirmation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated confirmation,
     * or with status {@code 400 (Bad Request)} if the confirmation is not valid,
     * or with status {@code 404 (Not Found)} if the confirmation is not found,
     * or with status {@code 500 (Internal Server Error)} if the confirmation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Confirmation> partialUpdateConfirmation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Confirmation confirmation
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Confirmation partially : {}, {}", id, confirmation);
        if (confirmation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, confirmation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!confirmationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Confirmation> result = confirmationRepository
            .findById(confirmation.getId())
            .map(existingConfirmation -> {
                if (confirmation.getCreatedAt() != null) {
                    existingConfirmation.setCreatedAt(confirmation.getCreatedAt());
                }

                return existingConfirmation;
            })
            .map(confirmationRepository::save)
            .map(savedConfirmation -> {
                confirmationSearchRepository.index(savedConfirmation);
                return savedConfirmation;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, confirmation.getId().toString())
        );
    }

    /**
     * {@code GET  /confirmations} : get all the confirmations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of confirmations in body.
     */
    @GetMapping("")
    public List<Confirmation> getAllConfirmations() {
        LOG.debug("REST request to get all Confirmations");
        return confirmationRepository.findAll();
    }

    /**
     * {@code GET  /confirmations/:id} : get the "id" confirmation.
     *
     * @param id the id of the confirmation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the confirmation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Confirmation> getConfirmation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Confirmation : {}", id);
        Optional<Confirmation> confirmation = confirmationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(confirmation);
    }

    /**
     * {@code DELETE  /confirmations/:id} : delete the "id" confirmation.
     *
     * @param id the id of the confirmation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfirmation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Confirmation : {}", id);
        confirmationRepository.deleteById(id);
        confirmationSearchRepository.deleteFromIndexById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /confirmations/_search?query=:query} : search for the confirmation corresponding
     * to the query.
     *
     * @param query the query of the confirmation search.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public List<Confirmation> searchConfirmations(@RequestParam("query") String query) {
        LOG.debug("REST request to search Confirmations for query {}", query);
        try {
            return StreamSupport.stream(confirmationSearchRepository.search(query).spliterator(), false).toList();
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
