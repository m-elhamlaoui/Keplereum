package com.keplereum.backend.domain;

import static com.keplereum.backend.domain.GroundStationTestSamples.*;
import static com.keplereum.backend.domain.NetworkNodeTestSamples.*;
import static com.keplereum.backend.domain.SatelliteModelTestSamples.*;
import static com.keplereum.backend.domain.SatelliteTestSamples.*;
import static com.keplereum.backend.domain.SatelliteTrajectoryTestSamples.*;
import static com.keplereum.backend.domain.SensorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.keplereum.backend.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SatelliteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Satellite.class);
        Satellite satellite1 = getSatelliteSample1();
        Satellite satellite2 = new Satellite();
        assertThat(satellite1).isNotEqualTo(satellite2);

        satellite2.setId(satellite1.getId());
        assertThat(satellite1).isEqualTo(satellite2);

        satellite2 = getSatelliteSample2();
        assertThat(satellite1).isNotEqualTo(satellite2);
    }

    @Test
    void sensorTest() {
        Satellite satellite = getSatelliteRandomSampleGenerator();
        Sensor sensorBack = getSensorRandomSampleGenerator();

        satellite.addSensor(sensorBack);
        assertThat(satellite.getSensors()).containsOnly(sensorBack);
        assertThat(sensorBack.getSatellite()).isEqualTo(satellite);

        satellite.removeSensor(sensorBack);
        assertThat(satellite.getSensors()).doesNotContain(sensorBack);
        assertThat(sensorBack.getSatellite()).isNull();

        satellite.sensors(new HashSet<>(Set.of(sensorBack)));
        assertThat(satellite.getSensors()).containsOnly(sensorBack);
        assertThat(sensorBack.getSatellite()).isEqualTo(satellite);

        satellite.setSensors(new HashSet<>());
        assertThat(satellite.getSensors()).doesNotContain(sensorBack);
        assertThat(sensorBack.getSatellite()).isNull();
    }

    @Test
    void trajectoryTest() {
        Satellite satellite = getSatelliteRandomSampleGenerator();
        SatelliteTrajectory satelliteTrajectoryBack = getSatelliteTrajectoryRandomSampleGenerator();

        satellite.addTrajectory(satelliteTrajectoryBack);
        assertThat(satellite.getTrajectories()).containsOnly(satelliteTrajectoryBack);
        assertThat(satelliteTrajectoryBack.getSatellite()).isEqualTo(satellite);

        satellite.removeTrajectory(satelliteTrajectoryBack);
        assertThat(satellite.getTrajectories()).doesNotContain(satelliteTrajectoryBack);
        assertThat(satelliteTrajectoryBack.getSatellite()).isNull();

        satellite.trajectories(new HashSet<>(Set.of(satelliteTrajectoryBack)));
        assertThat(satellite.getTrajectories()).containsOnly(satelliteTrajectoryBack);
        assertThat(satelliteTrajectoryBack.getSatellite()).isEqualTo(satellite);

        satellite.setTrajectories(new HashSet<>());
        assertThat(satellite.getTrajectories()).doesNotContain(satelliteTrajectoryBack);
        assertThat(satelliteTrajectoryBack.getSatellite()).isNull();
    }

    @Test
    void modelTest() {
        Satellite satellite = getSatelliteRandomSampleGenerator();
        SatelliteModel satelliteModelBack = getSatelliteModelRandomSampleGenerator();

        satellite.setModel(satelliteModelBack);
        assertThat(satellite.getModel()).isEqualTo(satelliteModelBack);

        satellite.model(null);
        assertThat(satellite.getModel()).isNull();
    }

    @Test
    void networkNodeTest() {
        Satellite satellite = getSatelliteRandomSampleGenerator();
        NetworkNode networkNodeBack = getNetworkNodeRandomSampleGenerator();

        satellite.setNetworkNode(networkNodeBack);
        assertThat(satellite.getNetworkNode()).isEqualTo(networkNodeBack);
        assertThat(networkNodeBack.getSatellite()).isEqualTo(satellite);

        satellite.networkNode(null);
        assertThat(satellite.getNetworkNode()).isNull();
        assertThat(networkNodeBack.getSatellite()).isNull();
    }

    @Test
    void groundStationTest() {
        Satellite satellite = getSatelliteRandomSampleGenerator();
        GroundStation groundStationBack = getGroundStationRandomSampleGenerator();

        satellite.setGroundStation(groundStationBack);
        assertThat(satellite.getGroundStation()).isEqualTo(groundStationBack);

        satellite.groundStation(null);
        assertThat(satellite.getGroundStation()).isNull();
    }
}
