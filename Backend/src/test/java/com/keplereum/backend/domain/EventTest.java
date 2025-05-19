package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.AlertTestSamples.*;
import static com.keplereum.backend.domain.BlockTransactionTestSamples.*;
import static com.keplereum.backend.domain.ConfirmationTestSamples.*;
import static com.keplereum.backend.domain.EventTestSamples.*;
import static com.keplereum.backend.domain.ValidatorActionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EventTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Event.class);
        Event event1 = getEventSample1();
        Event event2 = new Event();
        assertThat(event1).isNotEqualTo(event2);

        event2.setId(event1.getId());
        assertThat(event1).isEqualTo(event2);

        event2 = getEventSample2();
        assertThat(event1).isNotEqualTo(event2);
    }

    @Test
    void alertTest() {
        Event event = getEventRandomSampleGenerator();
        Alert alertBack = getAlertRandomSampleGenerator();

        event.setAlert(alertBack);
        assertThat(event.getAlert()).isEqualTo(alertBack);

        event.alert(null);
        assertThat(event.getAlert()).isNull();
    }

    @Test
    void confirmationTest() {
        Event event = getEventRandomSampleGenerator();
        Confirmation confirmationBack = getConfirmationRandomSampleGenerator();

        event.addConfirmation(confirmationBack);
        assertThat(event.getConfirmations()).containsOnly(confirmationBack);
        assertThat(confirmationBack.getEvent()).isEqualTo(event);

        event.removeConfirmation(confirmationBack);
        assertThat(event.getConfirmations()).doesNotContain(confirmationBack);
        assertThat(confirmationBack.getEvent()).isNull();

        event.confirmations(new HashSet<>(Set.of(confirmationBack)));
        assertThat(event.getConfirmations()).containsOnly(confirmationBack);
        assertThat(confirmationBack.getEvent()).isEqualTo(event);

        event.setConfirmations(new HashSet<>());
        assertThat(event.getConfirmations()).doesNotContain(confirmationBack);
        assertThat(confirmationBack.getEvent()).isNull();
    }

    @Test
    void blockTransactionTest() {
        Event event = getEventRandomSampleGenerator();
        BlockTransaction blockTransactionBack = getBlockTransactionRandomSampleGenerator();

        event.addBlockTransaction(blockTransactionBack);
        assertThat(event.getBlockTransactions()).containsOnly(blockTransactionBack);
        assertThat(blockTransactionBack.getEvent()).isEqualTo(event);

        event.removeBlockTransaction(blockTransactionBack);
        assertThat(event.getBlockTransactions()).doesNotContain(blockTransactionBack);
        assertThat(blockTransactionBack.getEvent()).isNull();

        event.blockTransactions(new HashSet<>(Set.of(blockTransactionBack)));
        assertThat(event.getBlockTransactions()).containsOnly(blockTransactionBack);
        assertThat(blockTransactionBack.getEvent()).isEqualTo(event);

        event.setBlockTransactions(new HashSet<>());
        assertThat(event.getBlockTransactions()).doesNotContain(blockTransactionBack);
        assertThat(blockTransactionBack.getEvent()).isNull();
    }

    @Test
    void validatorActionTest() {
        Event event = getEventRandomSampleGenerator();
        ValidatorAction validatorActionBack = getValidatorActionRandomSampleGenerator();

        event.setValidatorAction(validatorActionBack);
        assertThat(event.getValidatorAction()).isEqualTo(validatorActionBack);
        assertThat(validatorActionBack.getEvent()).isEqualTo(event);

        event.validatorAction(null);
        assertThat(event.getValidatorAction()).isNull();
        assertThat(validatorActionBack.getEvent()).isNull();
    }
}
