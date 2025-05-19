package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.AlertTestSamples.*;
import static com.keplereum.backend.domain.ConfirmationTestSamples.*;
import static com.keplereum.backend.domain.EventTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConfirmationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Confirmation.class);
        Confirmation confirmation1 = getConfirmationSample1();
        Confirmation confirmation2 = new Confirmation();
        assertThat(confirmation1).isNotEqualTo(confirmation2);

        confirmation2.setId(confirmation1.getId());
        assertThat(confirmation1).isEqualTo(confirmation2);

        confirmation2 = getConfirmationSample2();
        assertThat(confirmation1).isNotEqualTo(confirmation2);
    }

    @Test
    void eventTest() {
        Confirmation confirmation = getConfirmationRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        confirmation.setEvent(eventBack);
        assertThat(confirmation.getEvent()).isEqualTo(eventBack);

        confirmation.event(null);
        assertThat(confirmation.getEvent()).isNull();
    }

    @Test
    void alertTest() {
        Confirmation confirmation = getConfirmationRandomSampleGenerator();
        Alert alertBack = getAlertRandomSampleGenerator();

        confirmation.setAlert(alertBack);
        assertThat(confirmation.getAlert()).isEqualTo(alertBack);

        confirmation.alert(null);
        assertThat(confirmation.getAlert()).isNull();
    }
}
