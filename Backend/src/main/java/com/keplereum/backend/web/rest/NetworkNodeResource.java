package com.keplereum.backend.web.rest;

import com.keplereum.backend.domain.NetworkNode;
import com.keplereum.backend.repository.NetworkNodeRepository;
import com.keplereum.backend.repository.search.NetworkNodeSearchRepository;
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
 * REST controller for managing {@link com.keplereum.backend.domain.NetworkNode}.
 */
@RestController
@RequestMapping("/api/network-nodes")
@Transactional
public class NetworkNodeResource {

    private static final Logger LOG = LoggerFactory.getLogger(NetworkNodeResource.class);

    private static final String ENTITY_NAME = "networkNode";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NetworkNodeRepository networkNodeRepository;

    private final NetworkNodeSearchRepository networkNodeSearchRepository;

    public NetworkNodeResource(NetworkNodeRepository networkNodeRepository, NetworkNodeSearchRepository networkNodeSearchRepository) {
        this.networkNodeRepository = networkNodeRepository;
        this.networkNodeSearchRepository = networkNodeSearchRepository;
    }

    /**
     * {@code POST  /network-nodes} : Create a new networkNode.
     *
     * @param networkNode the networkNode to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new networkNode, or with status {@code 400 (Bad Request)} if the networkNode has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NetworkNode> createNetworkNode(@Valid @RequestBody NetworkNode networkNode) throws URISyntaxException {
        LOG.debug("REST request to save NetworkNode : {}", networkNode);
        if (networkNode.getId() != null) {
            throw new BadRequestAlertException("A new networkNode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        networkNode = networkNodeRepository.save(networkNode);
        networkNodeSearchRepository.index(networkNode);
        return ResponseEntity.created(new URI("/api/network-nodes/" + networkNode.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, networkNode.getId().toString()))
            .body(networkNode);
    }

    /**
     * {@code PUT  /network-nodes/:id} : Updates an existing networkNode.
     *
     * @param id the id of the networkNode to save.
     * @param networkNode the networkNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated networkNode,
     * or with status {@code 400 (Bad Request)} if the networkNode is not valid,
     * or with status {@code 500 (Internal Server Error)} if the networkNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NetworkNode> updateNetworkNode(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NetworkNode networkNode
    ) throws URISyntaxException {
        LOG.debug("REST request to update NetworkNode : {}, {}", id, networkNode);
        if (networkNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, networkNode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!networkNodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        networkNode = networkNodeRepository.save(networkNode);
        networkNodeSearchRepository.index(networkNode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, networkNode.getId().toString()))
            .body(networkNode);
    }

    /**
     * {@code PATCH  /network-nodes/:id} : Partial updates given fields of an existing networkNode, field will ignore if it is null
     *
     * @param id the id of the networkNode to save.
     * @param networkNode the networkNode to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated networkNode,
     * or with status {@code 400 (Bad Request)} if the networkNode is not valid,
     * or with status {@code 404 (Not Found)} if the networkNode is not found,
     * or with status {@code 500 (Internal Server Error)} if the networkNode couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NetworkNode> partialUpdateNetworkNode(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NetworkNode networkNode
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update NetworkNode partially : {}, {}", id, networkNode);
        if (networkNode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, networkNode.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!networkNodeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NetworkNode> result = networkNodeRepository
            .findById(networkNode.getId())
            .map(existingNetworkNode -> {
                if (networkNode.getPublicKey() != null) {
                    existingNetworkNode.setPublicKey(networkNode.getPublicKey());
                }
                if (networkNode.getAuthorityStatus() != null) {
                    existingNetworkNode.setAuthorityStatus(networkNode.getAuthorityStatus());
                }

                return existingNetworkNode;
            })
            .map(networkNodeRepository::save)
            .map(savedNetworkNode -> {
                networkNodeSearchRepository.index(savedNetworkNode);
                return savedNetworkNode;
            });

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, networkNode.getId().toString())
        );
    }

    /**
     * {@code GET  /network-nodes} : get all the networkNodes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of networkNodes in body.
     */
    @GetMapping("")
    public List<NetworkNode> getAllNetworkNodes(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get all NetworkNodes");
        if (eagerload) {
            return networkNodeRepository.findAllWithEagerRelationships();
        } else {
            return networkNodeRepository.findAll();
        }
    }

    /**
     * {@code GET  /network-nodes/:id} : get the "id" networkNode.
     *
     * @param id the id of the networkNode to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the networkNode, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NetworkNode> getNetworkNode(@PathVariable("id") Long id) {
        LOG.debug("REST request to get NetworkNode : {}", id);
        Optional<NetworkNode> networkNode = networkNodeRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(networkNode);
    }

    /**
     * {@code DELETE  /network-nodes/:id} : delete the "id" networkNode.
     *
     * @param id the id of the networkNode to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNetworkNode(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete NetworkNode : {}", id);
        networkNodeRepository.deleteById(id);
        networkNodeSearchRepository.deleteFromIndexById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code SEARCH  /network-nodes/_search?query=:query} : search for the networkNode corresponding
     * to the query.
     *
     * @param query the query of the networkNode search.
     * @return the result of the search.
     */
    @GetMapping("/_search")
    public List<NetworkNode> searchNetworkNodes(@RequestParam("query") String query) {
        LOG.debug("REST request to search NetworkNodes for query {}", query);
        try {
            return StreamSupport.stream(networkNodeSearchRepository.search(query).spliterator(), false).toList();
        } catch (RuntimeException e) {
            throw ElasticsearchExceptionMapper.mapException(e);
        }
    }
}
