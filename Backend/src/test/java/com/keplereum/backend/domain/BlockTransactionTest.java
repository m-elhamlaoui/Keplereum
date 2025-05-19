package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.BlockTestSamples.*;
import static com.keplereum.backend.domain.BlockTransactionTestSamples.*;
import static com.keplereum.backend.domain.EventTestSamples.*;
import static com.keplereum.backend.domain.NetworkNodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BlockTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlockTransaction.class);
        BlockTransaction blockTransaction1 = getBlockTransactionSample1();
        BlockTransaction blockTransaction2 = new BlockTransaction();
        assertThat(blockTransaction1).isNotEqualTo(blockTransaction2);

        blockTransaction2.setId(blockTransaction1.getId());
        assertThat(blockTransaction1).isEqualTo(blockTransaction2);

        blockTransaction2 = getBlockTransactionSample2();
        assertThat(blockTransaction1).isNotEqualTo(blockTransaction2);
    }

    @Test
    void blockTest() {
        BlockTransaction blockTransaction = getBlockTransactionRandomSampleGenerator();
        Block blockBack = getBlockRandomSampleGenerator();

        blockTransaction.setBlock(blockBack);
        assertThat(blockTransaction.getBlock()).isEqualTo(blockBack);

        blockTransaction.block(null);
        assertThat(blockTransaction.getBlock()).isNull();
    }

    @Test
    void eventTest() {
        BlockTransaction blockTransaction = getBlockTransactionRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        blockTransaction.setEvent(eventBack);
        assertThat(blockTransaction.getEvent()).isEqualTo(eventBack);

        blockTransaction.event(null);
        assertThat(blockTransaction.getEvent()).isNull();
    }

    @Test
    void networkNodeTest() {
        BlockTransaction blockTransaction = getBlockTransactionRandomSampleGenerator();
        NetworkNode networkNodeBack = getNetworkNodeRandomSampleGenerator();

        blockTransaction.addNetworkNode(networkNodeBack);
        assertThat(blockTransaction.getNetworkNodes()).containsOnly(networkNodeBack);
        assertThat(networkNodeBack.getBlockTransactions()).containsOnly(blockTransaction);

        blockTransaction.removeNetworkNode(networkNodeBack);
        assertThat(blockTransaction.getNetworkNodes()).doesNotContain(networkNodeBack);
        assertThat(networkNodeBack.getBlockTransactions()).doesNotContain(blockTransaction);

        blockTransaction.networkNodes(new HashSet<>(Set.of(networkNodeBack)));
        assertThat(blockTransaction.getNetworkNodes()).containsOnly(networkNodeBack);
        assertThat(networkNodeBack.getBlockTransactions()).containsOnly(blockTransaction);

        blockTransaction.setNetworkNodes(new HashSet<>());
        assertThat(blockTransaction.getNetworkNodes()).doesNotContain(networkNodeBack);
        assertThat(networkNodeBack.getBlockTransactions()).doesNotContain(blockTransaction);
    }
}
