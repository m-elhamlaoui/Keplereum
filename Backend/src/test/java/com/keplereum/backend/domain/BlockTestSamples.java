package com.keplereum.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BlockTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Block getBlockSample1() {
        return new Block().id(1L).height(1L).previousBlockHash("previousBlockHash1").currentBlockHash("currentBlockHash1");
    }

    public static Block getBlockSample2() {
        return new Block().id(2L).height(2L).previousBlockHash("previousBlockHash2").currentBlockHash("currentBlockHash2");
    }

    public static Block getBlockRandomSampleGenerator() {
        return new Block()
            .id(longCount.incrementAndGet())
            .height(longCount.incrementAndGet())
            .previousBlockHash(UUID.randomUUID().toString())
            .currentBlockHash(UUID.randomUUID().toString());
    }
}
