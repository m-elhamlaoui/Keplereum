package com.keplereum.backend.web.rest;

import com.keplereum.backend.domain.Block;
import com.keplereum.backend.repository.BlockRepository;
import com.keplereum.backend.repository.search.BlockSearchRepository;
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
 * REST controller for managing {@link com.keplereum.backend.domain.Block}.
 */
@RestController
@RequestMapping("/api/blocks")
@Transactional
public class BlockResource {

    private static final Logger LOG = LoggerFactory.getLogger(BlockResource.class);

    private static final String ENTITY_NAME = "block";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlockRepository blockRepository;

    private final BlockSearchRepository blockSearchRepository;

    public BlockResource(BlockRepository blockRepository, BlockSearchRepository blockSearchRepository) {
        this.blockRepository = blockRepository;
        this.blockSearchRepository = blockSearchRepository;
    }

    /**
     * {@code POST  /blocks} : Create a new block.
     *
     * @param block the block to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new block, or with status {@code 400 (Bad Request)} if the block has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Block> createBlock(@Valid @RequestBody Block block) throws URISyntaxException {
        LOG.debug("REST request to save Block : {}", block);
        if (block.getId() != null) {
            throw new BadRequestAlertException("A new block cannot already have an ID", ENTITY_NAME, "idexists");
        }
        block = blockRepository.save(block);
        blockSearchRepository.index(block);
        return ResponseEntity.created(new URI("/api/blocks/" + block.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, block.getId().toString()))
            .body(block);
    }

    /**
     * {@code PUT  /blocks/:id} : Updates an existing block.
     *
     * @param id the id of the block to save.
     * @param block the block to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated block,
     * or with status {@code 400 (Bad Request)} if the block is not valid,
     * or with status {@code 500 (Internal Server Error)} if the block couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Block> updateBlock(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Block block)
        throws URISyntaxException {
        LOG.debug("REST request to update Block : {}, {}", id, block);
        if (block.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, block.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        block = blockRepository.save(block);
        blockSearchRepository.index(block);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, block.getId().toString()))
            .body(block);
    }

    /**
     * {@code PATCH  /blocks/:id} : Partial updates given fields of an existing block, field will ignore if it is null
     *
     * @param id the id of the block to save.
     * @param block the block to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated block,
     * or with status {@code 400 (Bad Request)} if the block is not valid,
     * or with status {@code 404 (Not Found)} if the block is not found,
     * or with status {@code 500 (Internal Server Error)} if the block couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Block> partialUpdateBlock(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Block block
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Block partially : {}, {}", id, block);
        if (block.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, block.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Block> result = blockRepository
            .findById(block.getId())
            .map(existingBlock -> {
                if (block.getHeight() != null) {
                    existingBlock.setHeight(block.getHeight());
                }
                if (block.getPreviousBlockHash() != null) {
                    existingBlock.setPreviousBlockHash(block.getPreviousBlockHash());
                }
                if (block.getCurrentBlockHash() != null) {
                    existingBlock.setCurrentBlockHash(block.getCurrentBlockHash());
                }
                if (block.getCreatedAt() != null) {
                    existingBlock.setCreatedAt(block.getCreatedAt());
                }

                return existingBlock;
            })
            .map(blockRepository::save)
            .map(savedBlock -> {
                blockSearchRepository.index(savedBlock);
                return savedBlock;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, block.getId().toString())
        );
    }

    /**
     * {@code GET  /blocks} : get all the blocks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blocks in body.
     */
    @GetMapping("")
    public List<Block> getAllBlocks() {
        LOG.debug("REST request to get all Blocks");
        return blockRepository.findAll();
    }

    /**
     * {@code GET  /blocks/:id} : get the "id" block.
     *
     * @param id the id of the block to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the block, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Block> getBlock(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Block : {}", id);
        Optional<Block> block = blockRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(block);
    }

    /**
     * {@code DELETE  /blocks/:id} : delete the "id" block.
     *
     * @param id the id of the block to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Block : {}", id);
        blockRepository.deleteById(id);
        blockSearchRepository.deleteFromIndexById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /blocks/_search?query=:query} : search for the block corresponding
     * to the query.
     *
     * @param query the query of the block search.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public List<Block> searchBlocks(@RequestParam("query") String query) {
        LOG.debug("REST request to search Blocks for query {}", query);
        try {
            return StreamSupport.stream(blockSearchRepository.search(query).spliterator(), false).toList();
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
