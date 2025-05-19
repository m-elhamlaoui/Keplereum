package com.keplereum.backend.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SatelliteTrajectoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SatelliteTrajectory getSatelliteTrajectorySample1() {
        return new SatelliteTrajectory().id(1L).status("status1").changeReason("changeReason1");
    }

    public static SatelliteTrajectory getSatelliteTrajectorySample2() {
        return new SatelliteTrajectory().id(2L).status("status2").changeReason("changeReason2");
    }

    public static SatelliteTrajectory getSatelliteTrajectoryRandomSampleGenerator() {
        return new SatelliteTrajectory()
            .id(longCount.incrementAndGet())
            .status(UUID.randomUUID().toString())
            .changeReason(UUID.randomUUID().toString());
    }
}
