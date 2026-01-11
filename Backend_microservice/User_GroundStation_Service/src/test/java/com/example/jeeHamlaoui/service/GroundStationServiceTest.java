package com.example.jeeHamlaoui.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.jeeHamlaoui.client.SatelliteClient;
import com.example.jeeHamlaoui.client.SatelliteDTO;  // Add this import
import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.enums.UserStatus;
import com.example.jeeHamlaoui.repository.GroundStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.jeeHamlaoui.model.SatelliteStatus;
import com.example.jeeHamlaoui.client.SatelliteModelResponse;

@ExtendWith(MockitoExtension.class)
class GroundStationServiceTest {

    @Mock
    private GroundStationRepository groundStationRepository;

    @Mock
    private SatelliteClient satelliteClient;  // ← Make sure this has @Mock annotation

    @InjectMocks
    private GroundStationService groundStationService;

    private GroundStation testGroundStation;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Simple, clean test data setup
        testUser = createTestUser(1L, "test@example.com", "Test User");
        testGroundStation = createTestGroundStation(1L, "Test Station", "station@test.com");
        testGroundStation.setUser(testUser);
        testUser.setGroundStation(testGroundStation);
    }

    // Helper methods for clean object creation
    private User createTestUser(Long id, String email, String name) {
        User user = new User();
        user.setUserId(id);
        user.setEmail(email);
        user.setUser_name(name);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreated_at(Instant.now());
        return user;
    }

    private GroundStation createTestGroundStation(Long id, String name, String email) {
        GroundStation station = new GroundStation();
        station.setGroundStation_id(id);
        station.setGroundStation_Name(name);
        station.setGroundStation_Email(email);
        station.setGroundStation_AccesLevel(5);
        station.setGroundStation_Latitude(40.7128);
        station.setGroundStation_Longitude(-74.0060);
        station.setGroundStation_Description("Test station description");
        return station;
    }

    private SatelliteDTO createTestSatelliteDTO(String name) {
        SatelliteDTO satellite = new SatelliteDTO(
            1L,                                    // satellite_id
            name,                                  // name
            Instant.now(),                         // launchDate
            SatelliteStatus.OPERATIONAL,                // status
            1L,                                    // groundStationId
            "node1",                               // networkNodeId
            new SatelliteModelResponse(),          // model
            Set.of(),                              // sensors
            Set.of()                               // trajectories
        );
        return satellite;
    }

    @Nested
    @DisplayName("Find All Ground Stations")
    class FindAllGroundStationsTests {

        @Test
        @DisplayName("Should return all ground stations when they exist")
        void shouldReturnAllGroundStations() {
            // Arrange
            GroundStation station2 = createTestGroundStation(2L, "Station 2", "station2@test.com");
            List<GroundStation> expectedStations = Arrays.asList(testGroundStation, station2);
            when(groundStationRepository.findAll()).thenReturn(expectedStations);

            // Act
            List<GroundStation> result = groundStationService.findAllGroundStations();

            // Assert
            assertEquals(2, result.size());
            assertEquals(expectedStations, result);
            verify(groundStationRepository).findAll();
        }

        @Test
        @DisplayName("Should return empty list when no ground stations exist")
        void shouldReturnEmptyListWhenNoStationsExist() {
            // Arrange
            when(groundStationRepository.findAll()).thenReturn(Collections.emptyList());

            // Act
            List<GroundStation> result = groundStationService.findAllGroundStations();

            // Assert
            assertTrue(result.isEmpty());
            verify(groundStationRepository).findAll();
        }

        @Test
        @DisplayName("Should propagate repository exceptions")
        void shouldPropagateRepositoryExceptions() {
            // Arrange
            when(groundStationRepository.findAll()).thenThrow(new RuntimeException("Database error"));

            // Act & Assert
            assertThrows(RuntimeException.class, () -> groundStationService.findAllGroundStations());
            verify(groundStationRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Find Ground Station By ID")
    class FindByIdTests {

        @Test
        @DisplayName("Should return ground station when it exists")
        void shouldReturnGroundStationWhenExists() {
            // Arrange
            when(groundStationRepository.findById(1L)).thenReturn(Optional.of(testGroundStation));

            // Act
            Optional<GroundStation> result = groundStationService.findGroundStationById(1L);

            // Assert
            assertTrue(result.isPresent());
            assertEquals(testGroundStation, result.get());
            verify(groundStationRepository).findById(1L);
        }

        @Test
        @DisplayName("Should return empty when ground station doesn't exist")
        void shouldReturnEmptyWhenNotExists() {
            // Arrange
            when(groundStationRepository.findById(999L)).thenReturn(Optional.empty());

            // Act
            Optional<GroundStation> result = groundStationService.findGroundStationById(999L);

            // Assert
            assertFalse(result.isPresent());
            verify(groundStationRepository).findById(999L);
        }
    }

    @Nested
    @DisplayName("Save Ground Station")
    class SaveGroundStationTests {

        @Test
        @DisplayName("Should save ground station successfully")
        void shouldSaveGroundStationSuccessfully() {
            // Arrange
            GroundStation newStation = createTestGroundStation(null, "New Station", "new@test.com");
            GroundStation savedStation = createTestGroundStation(3L, "New Station", "new@test.com");
            when(groundStationRepository.save(newStation)).thenReturn(savedStation);

            // Act
            GroundStation result = groundStationService.saveGroundStation(newStation);

            // Assert
            assertEquals(savedStation, result);
            assertNotNull(result.getGroundStation_id());
            verify(groundStationRepository).save(newStation);
        }
    }

    @Nested
    @DisplayName("Update Ground Station")
    class UpdateGroundStationTests {

        @Test
        @DisplayName("Should update existing ground station successfully")
        void shouldUpdateExistingGroundStation() {
            // Arrange
            GroundStation updatedData = createTestGroundStation(1L, "Updated Station", "updated@test.com");
            when(groundStationRepository.findById(1L)).thenReturn(Optional.of(testGroundStation));
            when(groundStationRepository.save(any(GroundStation.class))).thenReturn(updatedData);

            // Act
            GroundStation result = groundStationService.updateGroundStation(1L, updatedData);

            // Assert
            assertEquals(updatedData, result);
            verify(groundStationRepository).findById(1L);
            verify(groundStationRepository).save(any(GroundStation.class));
        }

        @Test
        @DisplayName("Should throw exception when updating non-existent ground station")
        void shouldThrowExceptionWhenUpdatingNonExistent() {
            // Arrange
            GroundStation updatedData = createTestGroundStation(999L, "Updated", "updated@test.com");
            when(groundStationRepository.findById(999L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(RuntimeException.class, () ->
                    groundStationService.updateGroundStation(999L, updatedData));
            verify(groundStationRepository).findById(999L);
            verify(groundStationRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should maintain bidirectional relationship when updating ground station")
        void shouldMaintainBidirectionalRelationshipWhenUpdating() {
            // Arrange
            User existingUser = createTestUser(1L, "existing@example.com", "Existing User");
            GroundStation existingStation = createTestGroundStation(1L, "Existing Station", "existing@test.com");
            existingStation.setUser(existingUser);
            existingUser.setGroundStation(existingStation);

            GroundStation updatedData = createTestGroundStation(1L, "Updated Station", "updated@test.com");
            updatedData.setUser(existingUser); // Keep the same user

            when(groundStationRepository.findById(1L)).thenReturn(Optional.of(existingStation));
            when(groundStationRepository.save(any(GroundStation.class))).thenAnswer(invocation -> {
                GroundStation savedStation = invocation.getArgument(0);
                // Verify the bidirectional relationship is maintained
                assertNotNull(savedStation.getUser());
                assertEquals(existingUser, savedStation.getUser());
                assertEquals(savedStation, existingUser.getGroundStation());
                return savedStation;
            });

            // Act
            GroundStation result = groundStationService.updateGroundStation(1L, updatedData);

            // Assert
            assertNotNull(result);
            assertEquals(existingUser, result.getUser());
            assertEquals(result, existingUser.getGroundStation());
            verify(groundStationRepository).findById(1L);
            verify(groundStationRepository).save(any(GroundStation.class));
        }
    }

    @Nested
    @DisplayName("Delete Ground Station")
    class DeleteGroundStationTests {

        @Test
        @DisplayName("Should delete ground station by ID")
        void shouldDeleteGroundStationById() {
            // Arrange
            doNothing().when(groundStationRepository).deleteById(1L);

            // Act
            groundStationService.deleteGroundStation(1L);

            // Assert
            verify(groundStationRepository).deleteById(1L);
        }

        @Test
        @DisplayName("Should propagate delete exceptions")
        void shouldPropagateDeleteExceptions() {
            // Arrange
            doThrow(new RuntimeException("Cannot delete")).when(groundStationRepository).deleteById(1L);

            // Act & Assert
            assertThrows(RuntimeException.class, () -> groundStationService.deleteGroundStation(1L));
            verify(groundStationRepository).deleteById(1L);
        }
    }

    @Nested
    @DisplayName("Find Satellites By User ID")
    class FindSatellitesByUserIdTests {

        @Test
        @DisplayName("Should return satellites for existing user")
        void shouldReturnSatellitesForExistingUser() {
            // Arrange - Create proper SatelliteDTO objects
            List<SatelliteDTO> mockSatellites = Arrays.asList(
                    createTestSatelliteDTO("Satellite1"),
                    createTestSatelliteDTO("Satellite2")
            );

            when(groundStationRepository.findByUser_UserId(1L)).thenReturn(Optional.of(testGroundStation));
            when(satelliteClient.findAllSatellitesByGroundStationId(1L)).thenReturn(mockSatellites);

            // Act
            SatelliteByGroundStationResponse result = groundStationService.findSatelliteByUserId(1L);

            // Assert
            assertNotNull(result);
            assertEquals(2, result.getSatellites().size());
            verify(groundStationRepository).findByUser_UserId(1L);
            verify(satelliteClient).findAllSatellitesByGroundStationId(1L);
        }

        @Test
        @DisplayName("Should throw exception when user has no ground station")
        void shouldThrowExceptionWhenUserHasNoGroundStation() {
            // Arrange
            when(groundStationRepository.findByUser_UserId(999L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(RuntimeException.class, () -> groundStationService.findSatelliteByUserId(999L));
            verify(groundStationRepository).findByUser_UserId(999L);
            verify(satelliteClient, never()).findAllSatellitesByGroundStationId(any());
        }

        @Test
        @DisplayName("Should return empty satellites when satellite service returns empty")
        void shouldReturnEmptyWhenSatelliteServiceReturnsEmpty() {
            // Arrange
            when(groundStationRepository.findByUser_UserId(1L)).thenReturn(Optional.of(testGroundStation));
            when(satelliteClient.findAllSatellitesByGroundStationId(1L)).thenReturn(Collections.<SatelliteDTO>emptyList());

            // Act
            SatelliteByGroundStationResponse result = groundStationService.findSatelliteByUserId(1L);

            // Assert
            assertNotNull(result);
            assertTrue(result.getSatellites().isEmpty());
            verify(groundStationRepository).findByUser_UserId(1L);
            verify(satelliteClient).findAllSatellitesByGroundStationId(1L);
        }
    }
}