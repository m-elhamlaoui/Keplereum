package com.keplereum.backend.web.rest;

import static com.keplereum.backend.domain.NetworkNodeAsserts.*;
import static com.keplereum.backend.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keplereum.backend.IntegrationTest;
import com.keplereum.backend.domain.NetworkNode;
import com.keplereum.backend.repository.NetworkNodeRepository;
import com.keplereum.backend.repository.search.NetworkNodeSearchRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NetworkNodeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NetworkNodeResourceIT {

    private static final String DEFAULT_PUBLIC_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PUBLIC_KEY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTHORITY_STATUS = false;
    private static final Boolean UPDATED_AUTHORITY_STATUS = true;

    private static final String ENTITY_API_URL = "/api/network-nodes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/network-nodes/_search";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NetworkNodeRepository networkNodeRepository;

    @Mock
    private NetworkNodeRepository networkNodeRepositoryMock;

    @Autowired
    private NetworkNodeSearchRepository networkNodeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNetworkNodeMockMvc;

    private NetworkNode networkNode;

    private NetworkNode insertedNetworkNode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetworkNode createEntity() {
        return new NetworkNode().publicKey(DEFAULT_PUBLIC_KEY).authorityStatus(DEFAULT_AUTHORITY_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NetworkNode createUpdatedEntity() {
        return new NetworkNode().publicKey(UPDATED_PUBLIC_KEY).authorityStatus(UPDATED_AUTHORITY_STATUS);
    }

    @BeforeEach
    void initTest() {
        networkNode = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedNetworkNode != null) {
            networkNodeRepository.delete(insertedNetworkNode);
            networkNodeSearchRepository.delete(insertedNetworkNode);
            insertedNetworkNode = null;
        }
    }

    @Test
    @Transactional
    void createNetworkNode() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        // Create the NetworkNode
        var returnedNetworkNode = om.readValue(
            restNetworkNodeMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(networkNode))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NetworkNode.class
        );

        // Validate the NetworkNode in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNetworkNodeUpdatableFieldsEquals(returnedNetworkNode, getPersistedNetworkNode(returnedNetworkNode));

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore + 1);
            });

        insertedNetworkNode = returnedNetworkNode;
    }

    @Test
    @Transactional
    void createNetworkNodeWithExistingId() throws Exception {
        // Create the NetworkNode with an existing ID
        networkNode.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetworkNodeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(networkNode)))
            .andExpect(status().isBadRequest());

        // Validate the NetworkNode in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkPublicKeyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        // set the field null
        networkNode.setPublicKey(null);

        // Create the NetworkNode, which fails.

        restNetworkNodeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(networkNode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void checkAuthorityStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        // set the field null
        networkNode.setAuthorityStatus(null);

        // Create the NetworkNode, which fails.

        restNetworkNodeMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(networkNode)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);

        int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void getAllNetworkNodes() throws Exception {
        // Initialize the database
        insertedNetworkNode = networkNodeRepository.saveAndFlush(networkNode);

        // Get all the networkNodeList
        restNetworkNodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].publicKey").value(hasItem(DEFAULT_PUBLIC_KEY)))
            .andExpect(jsonPath("$.[*].authorityStatus").value(hasItem(DEFAULT_AUTHORITY_STATUS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNetworkNodesWithEagerRelationshipsIsEnabled() throws Exception {
        when(networkNodeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNetworkNodeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(networkNodeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllNetworkNodesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(networkNodeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restNetworkNodeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(networkNodeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getNetworkNode() throws Exception {
        // Initialize the database
        insertedNetworkNode = networkNodeRepository.saveAndFlush(networkNode);

        // Get the networkNode
        restNetworkNodeMockMvc
            .perform(get(ENTITY_API_URL_ID, networkNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(networkNode.getId().intValue()))
            .andExpect(jsonPath("$.publicKey").value(DEFAULT_PUBLIC_KEY))
            .andExpect(jsonPath("$.authorityStatus").value(DEFAULT_AUTHORITY_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingNetworkNode() throws Exception {
        // Get the networkNode
        restNetworkNodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNetworkNode() throws Exception {
        // Initialize the database
        insertedNetworkNode = networkNodeRepository.saveAndFlush(networkNode);

        long databaseSizeBeforeUpdate = getRepositoryCount();
        networkNodeSearchRepository.save(networkNode);
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());

        // Update the networkNode
        NetworkNode updatedNetworkNode = networkNodeRepository.findById(networkNode.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNetworkNode are not directly saved in db
        em.detach(updatedNetworkNode);
        updatedNetworkNode.publicKey(UPDATED_PUBLIC_KEY).authorityStatus(UPDATED_AUTHORITY_STATUS);

        restNetworkNodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNetworkNode.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNetworkNode))
            )
            .andExpect(status().isOk());

        // Validate the NetworkNode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNetworkNodeToMatchAllProperties(updatedNetworkNode);

        await()
            .atMost(5, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
                assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
                List<NetworkNode> networkNodeSearchList = Streamable.of(networkNodeSearchRepository.findAll()).toList();
                NetworkNode testNetworkNodeSearch = networkNodeSearchList.get(searchDatabaseSizeAfter - 1);

                assertNetworkNodeAllPropertiesEquals(testNetworkNodeSearch, updatedNetworkNode);
            });
    }

    @Test
    @Transactional
    void putNonExistingNetworkNode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        networkNode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetworkNodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, networkNode.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(networkNode))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetworkNode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithIdMismatchNetworkNode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        networkNode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworkNodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(networkNode))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetworkNode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNetworkNode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        networkNode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworkNodeMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(networkNode)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NetworkNode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void partialUpdateNetworkNodeWithPatch() throws Exception {
        // Initialize the database
        insertedNetworkNode = networkNodeRepository.saveAndFlush(networkNode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the networkNode using partial update
        NetworkNode partialUpdatedNetworkNode = new NetworkNode();
        partialUpdatedNetworkNode.setId(networkNode.getId());

        partialUpdatedNetworkNode.publicKey(UPDATED_PUBLIC_KEY).authorityStatus(UPDATED_AUTHORITY_STATUS);

        restNetworkNodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNetworkNode.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNetworkNode))
            )
            .andExpect(status().isOk());

        // Validate the NetworkNode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNetworkNodeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNetworkNode, networkNode),
            getPersistedNetworkNode(networkNode)
        );
    }

    @Test
    @Transactional
    void fullUpdateNetworkNodeWithPatch() throws Exception {
        // Initialize the database
        insertedNetworkNode = networkNodeRepository.saveAndFlush(networkNode);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the networkNode using partial update
        NetworkNode partialUpdatedNetworkNode = new NetworkNode();
        partialUpdatedNetworkNode.setId(networkNode.getId());

        partialUpdatedNetworkNode.publicKey(UPDATED_PUBLIC_KEY).authorityStatus(UPDATED_AUTHORITY_STATUS);

        restNetworkNodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNetworkNode.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNetworkNode))
            )
            .andExpect(status().isOk());

        // Validate the NetworkNode in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNetworkNodeUpdatableFieldsEquals(partialUpdatedNetworkNode, getPersistedNetworkNode(partialUpdatedNetworkNode));
    }

    @Test
    @Transactional
    void patchNonExistingNetworkNode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        networkNode.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetworkNodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, networkNode.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(networkNode))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetworkNode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNetworkNode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        networkNode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworkNodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(networkNode))
            )
            .andExpect(status().isBadRequest());

        // Validate the NetworkNode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNetworkNode() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        networkNode.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNetworkNodeMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(networkNode))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NetworkNode in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore);
    }

    @Test
    @Transactional
    void deleteNetworkNode() throws Exception {
        // Initialize the database
        insertedNetworkNode = networkNodeRepository.saveAndFlush(networkNode);
        networkNodeRepository.save(networkNode);
        networkNodeSearchRepository.save(networkNode);

        long databaseSizeBeforeDelete = getRepositoryCount();
        int searchDatabaseSizeBefore = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeBefore).isEqualTo(databaseSizeBeforeDelete);

        // Delete the networkNode
        restNetworkNodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, networkNode.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
        int searchDatabaseSizeAfter = IterableUtil.sizeOf(networkNodeSearchRepository.findAll());
        assertThat(searchDatabaseSizeAfter).isEqualTo(searchDatabaseSizeBefore - 1);
    }

    @Test
    @Transactional
    void searchNetworkNode() throws Exception {
        // Initialize the database
        insertedNetworkNode = networkNodeRepository.saveAndFlush(networkNode);
        networkNodeSearchRepository.save(networkNode);

        // Search the networkNode
        restNetworkNodeMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + networkNode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkNode.getId().intValue())))
            .andExpect(jsonPath("$.[*].publicKey").value(hasItem(DEFAULT_PUBLIC_KEY)))
            .andExpect(jsonPath("$.[*].authorityStatus").value(hasItem(DEFAULT_AUTHORITY_STATUS)));
    }

    protected long getRepositoryCount() {
        return networkNodeRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected NetworkNode getPersistedNetworkNode(NetworkNode networkNode) {
        return networkNodeRepository.findById(networkNode.getId()).orElseThrow();
    }

    protected void assertPersistedNetworkNodeToMatchAllProperties(NetworkNode expectedNetworkNode) {
        assertNetworkNodeAllPropertiesEquals(expectedNetworkNode, getPersistedNetworkNode(expectedNetworkNode));
    }

    protected void assertPersistedNetworkNodeToMatchUpdatableProperties(NetworkNode expectedNetworkNode) {
        assertNetworkNodeAllUpdatablePropertiesEquals(expectedNetworkNode, getPersistedNetworkNode(expectedNetworkNode));
    }
}
