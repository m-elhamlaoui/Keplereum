package com.keplereum.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NetworkNodeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static NetworkNode getNetworkNodeSample1() {
        return new NetworkNode().id(1L).publicKey("publicKey1");
    }

    public static NetworkNode getNetworkNodeSample2() {
        return new NetworkNode().id(2L).publicKey("publicKey2");
    }

    public static NetworkNode getNetworkNodeRandomSampleGenerator() {
        return new NetworkNode().id(longCount.incrementAndGet()).publicKey(UUID.randomUUID().toString());
    }
}
