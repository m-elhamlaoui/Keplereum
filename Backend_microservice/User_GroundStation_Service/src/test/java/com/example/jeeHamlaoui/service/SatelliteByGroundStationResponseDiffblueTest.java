package com.example.jeeHamlaoui.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.client.SatelliteDTO;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SatelliteByGroundStationResponseDiffblueTest {
    /**
     * Test getters and setters.
     * <p>
     * Methods under test:
     * <ul>
     *   <li>{@link SatelliteByGroundStationResponse#SatelliteByGroundStationResponse()}
     *   <li>{@link SatelliteByGroundStationResponse#setSatellites(List)}
     *   <li>{@link SatelliteByGroundStationResponse#getSatellites()}
     * </ul>
     */
    @Test
    @DisplayName("Test getters and setters")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void SatelliteByGroundStationResponse.<init>()",
            "void SatelliteByGroundStationResponse.<init>(List)", "List SatelliteByGroundStationResponse.getSatellites()",
            "void SatelliteByGroundStationResponse.setSatellites(List)"})
    void testGettersAndSetters() {
        // Arrange and Act
        SatelliteByGroundStationResponse actualSatelliteByGroundStationResponse = new SatelliteByGroundStationResponse();
        ArrayList<SatelliteDTO> satelliteDTOS = new ArrayList<>();
        actualSatelliteByGroundStationResponse.setSatellites(satelliteDTOS);
        List<SatelliteDTO> actualSatellites = actualSatelliteByGroundStationResponse.getSatellites();

        // Assert
        assertTrue(actualSatellites.isEmpty());
        assertSame(satelliteDTOS, actualSatellites);
    }

    /**
     * Test getters and setters.
     * <ul>
     *   <li>When {@link ArrayList#ArrayList()}.</li>
     * </ul>
     * <p>
     * Methods under test:
     * <ul>
     *   <li>{@link SatelliteByGroundStationResponse#SatelliteByGroundStationResponse(List)}
     *   <li>{@link SatelliteByGroundStationResponse#setSatellites(List)}
     *   <li>{@link SatelliteByGroundStationResponse#getSatellites()}
     * </ul>
     */
    @Test
    @DisplayName("Test getters and setters; when ArrayList()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void SatelliteByGroundStationResponse.<init>()",
            "void SatelliteByGroundStationResponse.<init>(List)", "List SatelliteByGroundStationResponse.getSatellites()",
            "void SatelliteByGroundStationResponse.setSatellites(List)"})
    void testGettersAndSetters_whenArrayList() {
        // Arrange and Act
        SatelliteByGroundStationResponse actualSatelliteByGroundStationResponse = new SatelliteByGroundStationResponse(
                new ArrayList<>());
        ArrayList<SatelliteDTO> satelliteDTOS = new ArrayList<>();
        actualSatelliteByGroundStationResponse.setSatellites(satelliteDTOS);
        List<SatelliteDTO> actualSatellites = actualSatelliteByGroundStationResponse.getSatellites();

        // Assert
        assertTrue(actualSatellites.isEmpty());
        assertSame(satelliteDTOS, actualSatellites);
    }
}
