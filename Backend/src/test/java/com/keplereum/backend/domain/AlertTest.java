package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.AlertTestSamples.*;
import static com.keplereum.backend.domain.ConfirmationTestSamples.*;
import static com.keplereum.backend.domain.EventTestSamples.*;
import static com.keplereum.backend.domain.ValidatorActionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AlertTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alert.class);
        Alert alert1 = getAlertSample1();
        Alert alert2 = new Alert();
        assertThat(alert1).isNotEqualTo(alert2);

        alert2.setId(alert1.getId());
        assertThat(alert1).isEqualTo(alert2);

        alert2 = getAlertSample2();
        assertThat(alert1).isNotEqualTo(alert2);
    }

    @Test
    void confirmationTest() {
        Alert alert = getAlertRandomSampleGenerator();
        Confirmation confirmationBack = getConfirmationRandomSampleGenerator();

        alert.addConfirmation(confirmationBack);
        assertThat(alert.getConfirmations()).containsOnly(confirmationBack);
        assertThat(confirmationBack.getAlert()).isEqualTo(alert);

        alert.removeConfirmation(confirmationBack);
        assertThat(alert.getConfirmations()).doesNotContain(confirmationBack);
        assertThat(confirmationBack.getAlert()).isNull();

        alert.confirmations(new HashSet<>(Set.of(confirmationBack)));
        assertThat(alert.getConfirmations()).containsOnly(confirmationBack);
        assertThat(confirmationBack.getAlert()).isEqualTo(alert);

        alert.setConfirmations(new HashSet<>());
        assertThat(alert.getConfirmations()).doesNotContain(confirmationBack);
        assertThat(confirmationBack.getAlert()).isNull();
    }

    @Test
    void validatorActionTest() {
        Alert alert = getAlertRandomSampleGenerator();
        ValidatorAction validatorActionBack = getValidatorActionRandomSampleGenerator();

        alert.setValidatorAction(validatorActionBack);
        assertThat(alert.getValidatorAction()).isEqualTo(validatorActionBack);
        assertThat(validatorActionBack.getAlert()).isEqualTo(alert);

        alert.validatorAction(null);
        assertThat(alert.getValidatorAction()).isNull();
        assertThat(validatorActionBack.getAlert()).isNull();
    }

    @Test
    void eventTest() {
        Alert alert = getAlertRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        alert.setEvent(eventBack);
        assertThat(alert.getEvent()).isEqualTo(eventBack);
        assertThat(eventBack.getAlert()).isEqualTo(alert);

        alert.event(null);
        assertThat(alert.getEvent()).isNull();
        assertThat(eventBack.getAlert()).isNull();
    }
}
