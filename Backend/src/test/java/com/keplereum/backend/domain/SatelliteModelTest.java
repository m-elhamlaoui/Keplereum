package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.SatelliteModelTestSamples.*;
import static com.keplereum.backend.domain.SatelliteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SatelliteModelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SatelliteModel.class);
        SatelliteModel satelliteModel1 = getSatelliteModelSample1();
        SatelliteModel satelliteModel2 = new SatelliteModel();
        assertThat(satelliteModel1).isNotEqualTo(satelliteModel2);

        satelliteModel2.setId(satelliteModel1.getId());
        assertThat(satelliteModel1).isEqualTo(satelliteModel2);

        satelliteModel2 = getSatelliteModelSample2();
        assertThat(satelliteModel1).isNotEqualTo(satelliteModel2);
    }

    @Test
    void satelliteTest() {
        SatelliteModel satelliteModel = getSatelliteModelRandomSampleGenerator();
        Satellite satelliteBack = getSatelliteRandomSampleGenerator();

        satelliteModel.addSatellite(satelliteBack);
        assertThat(satelliteModel.getSatellites()).containsOnly(satelliteBack);
        assertThat(satelliteBack.getModel()).isEqualTo(satelliteModel);

        satelliteModel.removeSatellite(satelliteBack);
        assertThat(satelliteModel.getSatellites()).doesNotContain(satelliteBack);
        assertThat(satelliteBack.getModel()).isNull();

        satelliteModel.satellites(new HashSet<>(Set.of(satelliteBack)));
        assertThat(satelliteModel.getSatellites()).containsOnly(satelliteBack);
        assertThat(satelliteBack.getModel()).isEqualTo(satelliteModel);

        satelliteModel.setSatellites(new HashSet<>());
        assertThat(satelliteModel.getSatellites()).doesNotContain(satelliteBack);
        assertThat(satelliteBack.getModel()).isNull();
    }
}
