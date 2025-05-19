package com.keplereum.backend.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ValidatorActionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ValidatorAction getValidatorActionSample1() {
        return new ValidatorAction().id(1L);
    }

    public static ValidatorAction getValidatorActionSample2() {
        return new ValidatorAction().id(2L);
    }

    public static ValidatorAction getValidatorActionRandomSampleGenerator() {
        return new ValidatorAction().id(longCount.incrementAndGet());
    }
}
