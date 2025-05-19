package com.keplereum.backend.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ConfirmationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Confirmation getConfirmationSample1() {
        return new Confirmation().id(1L);
    }

    public static Confirmation getConfirmationSample2() {
        return new Confirmation().id(2L);
    }

    public static Confirmation getConfirmationRandomSampleGenerator() {
        return new Confirmation().id(longCount.incrementAndGet());
    }
}
