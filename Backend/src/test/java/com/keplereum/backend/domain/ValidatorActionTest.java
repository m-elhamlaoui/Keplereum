package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.AlertTestSamples.*;
import static com.keplereum.backend.domain.EventTestSamples.*;
import static com.keplereum.backend.domain.ValidatorActionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ValidatorActionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidatorAction.class);
        ValidatorAction validatorAction1 = getValidatorActionSample1();
        ValidatorAction validatorAction2 = new ValidatorAction();
        assertThat(validatorAction1).isNotEqualTo(validatorAction2);

        validatorAction2.setId(validatorAction1.getId());
        assertThat(validatorAction1).isEqualTo(validatorAction2);

        validatorAction2 = getValidatorActionSample2();
        assertThat(validatorAction1).isNotEqualTo(validatorAction2);
    }

    @Test
    void eventTest() {
        ValidatorAction validatorAction = getValidatorActionRandomSampleGenerator();
        Event eventBack = getEventRandomSampleGenerator();

        validatorAction.setEvent(eventBack);
        assertThat(validatorAction.getEvent()).isEqualTo(eventBack);

        validatorAction.event(null);
        assertThat(validatorAction.getEvent()).isNull();
    }

    @Test
    void alertTest() {
        ValidatorAction validatorAction = getValidatorActionRandomSampleGenerator();
        Alert alertBack = getAlertRandomSampleGenerator();

        validatorAction.setAlert(alertBack);
        assertThat(validatorAction.getAlert()).isEqualTo(alertBack);

        validatorAction.alert(null);
        assertThat(validatorAction.getAlert()).isNull();
    }
}
