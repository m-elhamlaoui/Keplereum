package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.BlockTestSamples.*;
import static com.keplereum.backend.domain.BlockTransactionTestSamples.*;
import static com.keplereum.backend.domain.NetworkNodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BlockTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Block.class);
        Block block1 = getBlockSample1();
        Block block2 = new Block();
        assertThat(block1).isNotEqualTo(block2);

        block2.setId(block1.getId());
        assertThat(block1).isEqualTo(block2);

        block2 = getBlockSample2();
        assertThat(block1).isNotEqualTo(block2);
    }

    @Test
    void transactionTest() {
        Block block = getBlockRandomSampleGenerator();
        BlockTransaction blockTransactionBack = getBlockTransactionRandomSampleGenerator();

        block.addTransaction(blockTransactionBack);
        assertThat(block.getTransactions()).containsOnly(blockTransactionBack);
        assertThat(blockTransactionBack.getBlock()).isEqualTo(block);

        block.removeTransaction(blockTransactionBack);
        assertThat(block.getTransactions()).doesNotContain(blockTransactionBack);
        assertThat(blockTransactionBack.getBlock()).isNull();

        block.transactions(new HashSet<>(Set.of(blockTransactionBack)));
        assertThat(block.getTransactions()).containsOnly(blockTransactionBack);
        assertThat(blockTransactionBack.getBlock()).isEqualTo(block);

        block.setTransactions(new HashSet<>());
        assertThat(block.getTransactions()).doesNotContain(blockTransactionBack);
        assertThat(blockTransactionBack.getBlock()).isNull();
    }

    @Test
    void networkNodeTest() {
        Block block = getBlockRandomSampleGenerator();
        NetworkNode networkNodeBack = getNetworkNodeRandomSampleGenerator();

        block.setNetworkNode(networkNodeBack);
        assertThat(block.getNetworkNode()).isEqualTo(networkNodeBack);

        block.networkNode(null);
        assertThat(block.getNetworkNode()).isNull();
    }
}
