package com.keplereum.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SatelliteModelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SatelliteModel getSatelliteModelSample1() {
        return new SatelliteModel().id(1L).name("name1").manufacturer("manufacturer1").dimensions("dimensions1").expectedLifespan(1);
    }

    public static SatelliteModel getSatelliteModelSample2() {
        return new SatelliteModel().id(2L).name("name2").manufacturer("manufacturer2").dimensions("dimensions2").expectedLifespan(2);
    }

    public static SatelliteModel getSatelliteModelRandomSampleGenerator() {
        return new SatelliteModel()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .manufacturer(UUID.randomUUID().toString())
            .dimensions(UUID.randomUUID().toString())
            .expectedLifespan(intCount.incrementAndGet());
    }
}
