package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.SatelliteTestSamples.*;
import static com.keplereum.backend.domain.SatelliteTrajectoryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SatelliteTrajectoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SatelliteTrajectory.class);
        SatelliteTrajectory satelliteTrajectory1 = getSatelliteTrajectorySample1();
        SatelliteTrajectory satelliteTrajectory2 = new SatelliteTrajectory();
        assertThat(satelliteTrajectory1).isNotEqualTo(satelliteTrajectory2);

        satelliteTrajectory2.setId(satelliteTrajectory1.getId());
        assertThat(satelliteTrajectory1).isEqualTo(satelliteTrajectory2);

        satelliteTrajectory2 = getSatelliteTrajectorySample2();
        assertThat(satelliteTrajectory1).isNotEqualTo(satelliteTrajectory2);
    }

    @Test
    void satelliteTest() {
        SatelliteTrajectory satelliteTrajectory = getSatelliteTrajectoryRandomSampleGenerator();
        Satellite satelliteBack = getSatelliteRandomSampleGenerator();

        satelliteTrajectory.setSatellite(satelliteBack);
        assertThat(satelliteTrajectory.getSatellite()).isEqualTo(satelliteBack);

        satelliteTrajectory.satellite(null);
        assertThat(satelliteTrajectory.getSatellite()).isNull();
    }
}
