package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.BlockTestSamples.*;
import static com.keplereum.backend.domain.BlockTransactionTestSamples.*;
import static com.keplereum.backend.domain.NetworkNodeTestSamples.*;
import static com.keplereum.backend.domain.SatelliteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class NetworkNodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetworkNode.class);
        NetworkNode networkNode1 = getNetworkNodeSample1();
        NetworkNode networkNode2 = new NetworkNode();
        assertThat(networkNode1).isNotEqualTo(networkNode2);

        networkNode2.setId(networkNode1.getId());
        assertThat(networkNode1).isEqualTo(networkNode2);

        networkNode2 = getNetworkNodeSample2();
        assertThat(networkNode1).isNotEqualTo(networkNode2);
    }

    @Test
    void satelliteTest() {
        NetworkNode networkNode = getNetworkNodeRandomSampleGenerator();
        Satellite satelliteBack = getSatelliteRandomSampleGenerator();

        networkNode.setSatellite(satelliteBack);
        assertThat(networkNode.getSatellite()).isEqualTo(satelliteBack);

        networkNode.satellite(null);
        assertThat(networkNode.getSatellite()).isNull();
    }

    @Test
    void blockTest() {
        NetworkNode networkNode = getNetworkNodeRandomSampleGenerator();
        Block blockBack = getBlockRandomSampleGenerator();

        networkNode.addBlock(blockBack);
        assertThat(networkNode.getBlocks()).containsOnly(blockBack);
        assertThat(blockBack.getNetworkNode()).isEqualTo(networkNode);

        networkNode.removeBlock(blockBack);
        assertThat(networkNode.getBlocks()).doesNotContain(blockBack);
        assertThat(blockBack.getNetworkNode()).isNull();

        networkNode.blocks(new HashSet<>(Set.of(blockBack)));
        assertThat(networkNode.getBlocks()).containsOnly(blockBack);
        assertThat(blockBack.getNetworkNode()).isEqualTo(networkNode);

        networkNode.setBlocks(new HashSet<>());
        assertThat(networkNode.getBlocks()).doesNotContain(blockBack);
        assertThat(blockBack.getNetworkNode()).isNull();
    }

    @Test
    void blockTransactionTest() {
        NetworkNode networkNode = getNetworkNodeRandomSampleGenerator();
        BlockTransaction blockTransactionBack = getBlockTransactionRandomSampleGenerator();

        networkNode.addBlockTransaction(blockTransactionBack);
        assertThat(networkNode.getBlockTransactions()).containsOnly(blockTransactionBack);

        networkNode.removeBlockTransaction(blockTransactionBack);
        assertThat(networkNode.getBlockTransactions()).doesNotContain(blockTransactionBack);

        networkNode.blockTransactions(new HashSet<>(Set.of(blockTransactionBack)));
        assertThat(networkNode.getBlockTransactions()).containsOnly(blockTransactionBack);

        networkNode.setBlockTransactions(new HashSet<>());
        assertThat(networkNode.getBlockTransactions()).doesNotContain(blockTransactionBack);
    }
}
