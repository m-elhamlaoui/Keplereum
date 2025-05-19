package com.keplereum.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class GroundStationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static GroundStation getGroundStationSample1() {
        return new GroundStation().id(1L).name("name1").country("country1").contactEmail("contactEmail1").accessLevel(1).status("status1");
    }

    public static GroundStation getGroundStationSample2() {
        return new GroundStation().id(2L).name("name2").country("country2").contactEmail("contactEmail2").accessLevel(2).status("status2");
    }

    public static GroundStation getGroundStationRandomSampleGenerator() {
        return new GroundStation()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .contactEmail(UUID.randomUUID().toString())
            .accessLevel(intCount.incrementAndGet())
            .status(UUID.randomUUID().toString());
    }
}
