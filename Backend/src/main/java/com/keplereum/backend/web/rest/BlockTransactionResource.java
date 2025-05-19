package com.keplereum.backend.web.rest;

import com.keplereum.backend.domain.BlockTransaction;
import com.keplereum.backend.repository.BlockTransactionRepository;
import com.keplereum.backend.repository.search.BlockTransactionSearchRepository;
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
 * REST controller for managing {@link com.keplereum.backend.domain.BlockTransaction}.
 */
@RestController
@RequestMapping("/api/block-transactions")
@Transactional
public class BlockTransactionResource {

    private static final Logger LOG = LoggerFactory.getLogger(BlockTransactionResource.class);

    private static final String ENTITY_NAME = "blockTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlockTransactionRepository blockTransactionRepository;

    private final BlockTransactionSearchRepository blockTransactionSearchRepository;

    public BlockTransactionResource(
        BlockTransactionRepository blockTransactionRepository,
        BlockTransactionSearchRepository blockTransactionSearchRepository
    ) {
        this.blockTransactionRepository = blockTransactionRepository;
        this.blockTransactionSearchRepository = blockTransactionSearchRepository;
    }

    /**
     * {@code POST  /block-transactions} : Create a new blockTransaction.
     *
     * @param blockTransaction the blockTransaction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blockTransaction, or with status {@code 400 (Bad Request)} if the blockTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BlockTransaction> createBlockTransaction(@Valid @RequestBody BlockTransaction blockTransaction)
        throws URISyntaxException {
        LOG.debug("REST request to save BlockTransaction : {}", blockTransaction);
        if (blockTransaction.getId() != null) {
            throw new BadRequestAlertException("A new blockTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        blockTransaction = blockTransactionRepository.save(blockTransaction);
        blockTransactionSearchRepository.index(blockTransaction);
        return ResponseEntity.created(new URI("/api/block-transactions/" + blockTransaction.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, blockTransaction.getId().toString()))
            .body(blockTransaction);
    }

    /**
     * {@code PUT  /block-transactions/:id} : Updates an existing blockTransaction.
     *
     * @param id the id of the blockTransaction to save.
     * @param blockTransaction the blockTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blockTransaction,
     * or with status {@code 400 (Bad Request)} if the blockTransaction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blockTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BlockTransaction> updateBlockTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BlockTransaction blockTransaction
    ) throws URISyntaxException {
        LOG.debug("REST request to update BlockTransaction : {}, {}", id, blockTransaction);
        if (blockTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blockTransaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blockTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        blockTransaction = blockTransactionRepository.save(blockTransaction);
        blockTransactionSearchRepository.index(blockTransaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, blockTransaction.getId().toString()))
            .body(blockTransaction);
    }

    /**
     * {@code PATCH  /block-transactions/:id} : Partial updates given fields of an existing blockTransaction, field will ignore if it is null
     *
     * @param id the id of the blockTransaction to save.
     * @param blockTransaction the blockTransaction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blockTransaction,
     * or with status {@code 400 (Bad Request)} if the blockTransaction is not valid,
     * or with status {@code 404 (Not Found)} if the blockTransaction is not found,
     * or with status {@code 500 (Internal Server Error)} if the blockTransaction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BlockTransaction> partialUpdateBlockTransaction(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BlockTransaction blockTransaction
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BlockTransaction partially : {}, {}", id, blockTransaction);
        if (blockTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blockTransaction.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blockTransactionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BlockTransaction> result = blockTransactionRepository
            .findById(blockTransaction.getId())
            .map(existingBlockTransaction -> {
                if (blockTransaction.getHash() != null) {
                    existingBlockTransaction.setHash(blockTransaction.getHash());
                }
                if (blockTransaction.getFrom() != null) {
                    existingBlockTransaction.setFrom(blockTransaction.getFrom());
                }
                if (blockTransaction.getTo() != null) {
                    existingBlockTransaction.setTo(blockTransaction.getTo());
                }
                if (blockTransaction.getAmount() != null) {
                    existingBlockTransaction.setAmount(blockTransaction.getAmount());
                }
                if (blockTransaction.getFee() != null) {
                    existingBlockTransaction.setFee(blockTransaction.getFee());
                }
                if (blockTransaction.getStatus() != null) {
                    existingBlockTransaction.setStatus(blockTransaction.getStatus());
                }
                if (blockTransaction.getGasPrice() != null) {
                    existingBlockTransaction.setGasPrice(blockTransaction.getGasPrice());
                }
                if (blockTransaction.getGasLimit() != null) {
                    existingBlockTransaction.setGasLimit(blockTransaction.getGasLimit());
                }
                if (blockTransaction.getGasUsed() != null) {
                    existingBlockTransaction.setGasUsed(blockTransaction.getGasUsed());
                }
                if (blockTransaction.getBlockNumber() != null) {
                    existingBlockTransaction.setBlockNumber(blockTransaction.getBlockNumber());
                }
                if (blockTransaction.getCreatedAt() != null) {
                    existingBlockTransaction.setCreatedAt(blockTransaction.getCreatedAt());
                }

                return existingBlockTransaction;
            })
            .map(blockTransactionRepository::save)
            .map(savedBlockTransaction -> {
                blockTransactionSearchRepository.index(savedBlockTransaction);
                return savedBlockTransaction;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, blockTransaction.getId().toString())
        );
    }

    /**
     * {@code GET  /block-transactions} : get all the blockTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blockTransactions in body.
     */
    @GetMapping("")
    public List<BlockTransaction> getAllBlockTransactions() {
        LOG.debug("REST request to get all BlockTransactions");
        return blockTransactionRepository.findAll();
    }

    /**
     * {@code GET  /block-transactions/:id} : get the "id" blockTransaction.
     *
     * @param id the id of the blockTransaction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blockTransaction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BlockTransaction> getBlockTransaction(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BlockTransaction : {}", id);
        Optional<BlockTransaction> blockTransaction = blockTransactionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(blockTransaction);
    }

    /**
     * {@code DELETE  /block-transactions/:id} : delete the "id" blockTransaction.
     *
     * @param id the id of the blockTransaction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlockTransaction(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BlockTransaction : {}", id);
        blockTransactionRepository.deleteById(id);
        blockTransactionSearchRepository.deleteFromIndexById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /block-transactions/_search?query=:query} : search for the blockTransaction corresponding
     * to the query.
     *
     * @param query the query of the blockTransaction search.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public List<BlockTransaction> searchBlockTransactions(@RequestParam("query") String query) {
        LOG.debug("REST request to search BlockTransactions for query {}", query);
        try {
            return StreamSupport.stream(blockTransactionSearchRepository.search(query).spliterator(), false).toList();
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
