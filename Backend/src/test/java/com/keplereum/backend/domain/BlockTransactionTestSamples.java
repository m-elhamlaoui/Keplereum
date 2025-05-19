package com.keplereum.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BlockTransactionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BlockTransaction getBlockTransactionSample1() {
        return new BlockTransaction().id(1L).hash("hash1").from("from1").to("to1").blockNumber(1L);
    }

    public static BlockTransaction getBlockTransactionSample2() {
        return new BlockTransaction().id(2L).hash("hash2").from("from2").to("to2").blockNumber(2L);
    }

    public static BlockTransaction getBlockTransactionRandomSampleGenerator() {
        return new BlockTransaction()
            .id(longCount.incrementAndGet())
            .hash(UUID.randomUUID().toString())
            .from(UUID.randomUUID().toString())
            .to(UUID.randomUUID().toString())
            .blockNumber(longCount.incrementAndGet());
    }
}
