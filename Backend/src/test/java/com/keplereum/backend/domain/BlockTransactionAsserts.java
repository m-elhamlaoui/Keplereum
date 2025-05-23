package com.keplereum.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockTransactionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBlockTransactionAllPropertiesEquals(BlockTransaction expected, BlockTransaction actual) {
        assertBlockTransactionAutoGeneratedPropertiesEquals(expected, actual);
        assertBlockTransactionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBlockTransactionAllUpdatablePropertiesEquals(BlockTransaction expected, BlockTransaction actual) {
        assertBlockTransactionUpdatableFieldsEquals(expected, actual);
        assertBlockTransactionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBlockTransactionAutoGeneratedPropertiesEquals(BlockTransaction expected, BlockTransaction actual) {
        assertThat(actual)
            .as("Verify BlockTransaction auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBlockTransactionUpdatableFieldsEquals(BlockTransaction expected, BlockTransaction actual) {
        assertThat(actual)
            .as("Verify BlockTransaction relevant properties")
            .satisfies(a -> assertThat(a.getHash()).as("check hash").isEqualTo(expected.getHash()))
            .satisfies(a -> assertThat(a.getFrom()).as("check from").isEqualTo(expected.getFrom()))
            .satisfies(a -> assertThat(a.getTo()).as("check to").isEqualTo(expected.getTo()))
            .satisfies(a -> assertThat(a.getAmount()).as("check amount").isEqualTo(expected.getAmount()))
            .satisfies(a -> assertThat(a.getFee()).as("check fee").isEqualTo(expected.getFee()))
            .satisfies(a -> assertThat(a.getStatus()).as("check status").isEqualTo(expected.getStatus()))
            .satisfies(a -> assertThat(a.getGasPrice()).as("check gasPrice").isEqualTo(expected.getGasPrice()))
            .satisfies(a -> assertThat(a.getGasLimit()).as("check gasLimit").isEqualTo(expected.getGasLimit()))
            .satisfies(a -> assertThat(a.getGasUsed()).as("check gasUsed").isEqualTo(expected.getGasUsed()))
            .satisfies(a -> assertThat(a.getBlockNumber()).as("check blockNumber").isEqualTo(expected.getBlockNumber()))
            .satisfies(a -> assertThat(a.getCreatedAt()).as("check createdAt").isEqualTo(expected.getCreatedAt()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBlockTransactionUpdatableRelationshipsEquals(BlockTransaction expected, BlockTransaction actual) {
        assertThat(actual)
            .as("Verify BlockTransaction relationships")
            .satisfies(a -> assertThat(a.getBlock()).as("check block").isEqualTo(expected.getBlock()))
            .satisfies(a -> assertThat(a.getEvent()).as("check event").isEqualTo(expected.getEvent()))
            .satisfies(a -> assertThat(a.getNetworkNodes()).as("check networkNodes").isEqualTo(expected.getNetworkNodes()));
    }
}
