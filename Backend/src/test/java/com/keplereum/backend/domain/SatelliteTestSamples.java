package com.keplereum.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SatelliteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Satellite getSatelliteSample1() {
        return new Satellite().id(1L).name("name1");
    }

    public static Satellite getSatelliteSample2() {
        return new Satellite().id(2L).name("name2");
    }

    public static Satellite getSatelliteRandomSampleGenerator() {
        return new Satellite().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
