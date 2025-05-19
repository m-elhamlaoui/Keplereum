package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.SatelliteTestSamples.*;
import static com.keplereum.backend.domain.SensorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SensorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sensor.class);
        Sensor sensor1 = getSensorSample1();
        Sensor sensor2 = new Sensor();
        assertThat(sensor1).isNotEqualTo(sensor2);

        sensor2.setId(sensor1.getId());
        assertThat(sensor1).isEqualTo(sensor2);

        sensor2 = getSensorSample2();
        assertThat(sensor1).isNotEqualTo(sensor2);
    }

    @Test
    void satelliteTest() {
        Sensor sensor = getSensorRandomSampleGenerator();
        Satellite satelliteBack = getSatelliteRandomSampleGenerator();

        sensor.setSatellite(satelliteBack);
        assertThat(sensor.getSatellite()).isEqualTo(satelliteBack);

        sensor.satellite(null);
        assertThat(sensor.getSatellite()).isNull();
    }
}
