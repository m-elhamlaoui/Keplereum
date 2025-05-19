package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.GroundStationTestSamples.*;
import static com.keplereum.backend.domain.SatelliteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GroundStationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroundStation.class);
        GroundStation groundStation1 = getGroundStationSample1();
        GroundStation groundStation2 = new GroundStation();
        assertThat(groundStation1).isNotEqualTo(groundStation2);

        groundStation2.setId(groundStation1.getId());
        assertThat(groundStation1).isEqualTo(groundStation2);

        groundStation2 = getGroundStationSample2();
        assertThat(groundStation1).isNotEqualTo(groundStation2);
    }

    @Test
    void satelliteTest() {
        GroundStation groundStation = getGroundStationRandomSampleGenerator();
        Satellite satelliteBack = getSatelliteRandomSampleGenerator();

        groundStation.addSatellite(satelliteBack);
        assertThat(groundStation.getSatellites()).containsOnly(satelliteBack);
        assertThat(satelliteBack.getGroundStation()).isEqualTo(groundStation);

        groundStation.removeSatellite(satelliteBack);
        assertThat(groundStation.getSatellites()).doesNotContain(satelliteBack);
        assertThat(satelliteBack.getGroundStation()).isNull();

        groundStation.satellites(new HashSet<>(Set.of(satelliteBack)));
        assertThat(groundStation.getSatellites()).containsOnly(satelliteBack);
        assertThat(satelliteBack.getGroundStation()).isEqualTo(groundStation);

        groundStation.setSatellites(new HashSet<>());
        assertThat(groundStation.getSatellites()).doesNotContain(satelliteBack);
        assertThat(satelliteBack.getGroundStation()).isNull();
    }
}
