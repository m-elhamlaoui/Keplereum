package com.example.jeeHamlaoui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.enums.UserStatus;
import com.example.jeeHamlaoui.service.GroundStationService;
import com.example.jeeHamlaoui.service.SatelliteByGroundStationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("GroundStationController Tests")
class GroundStationControllerTest {

  @Mock
  private GroundStationService groundStationService;

  @InjectMocks
  private GroundStationController groundStationController;

  private GroundStation testGroundStation;
  private User testUser;
  private SatelliteByGroundStationResponse testSatelliteResponse;

  @BeforeEach
  void setUp() {
    // Create test user
    testUser = new User();
    testUser.setUserId(1L);
    testUser.setUser_name("Test User");
    testUser.setEmail("test@example.com");
    testUser.setPassword("testPassword");
    testUser.setStatus(UserStatus.ACTIVE);
    testUser.setCreated_at(Instant.now());

    // Create test ground station
    testGroundStation = new GroundStation();
    testGroundStation.setGroundStation_id(1L);
    testGroundStation.setGroundStation_Name("Test Ground Station");
    testGroundStation.setGroundStation_Description("Test Description for Ground Station");
    testGroundStation.setGroundStation_Email("groundstation@test.com");
    testGroundStation.setGroundStation_Latitude(40.7128);
    testGroundStation.setGroundStation_Longitude(-74.0060);
    testGroundStation.setGroundStation_AccesLevel(1);
    testGroundStation.setUser(testUser);

    // Create test satellite response
    testSatelliteResponse = new SatelliteByGroundStationResponse();
    // Assuming this response has some satellites data
  }

  @Nested
  @DisplayName("Get All Ground Stations Tests")
  class GetAllGroundStationsTests {

    @Test
    @DisplayName("Should return all ground stations when stations exist")
    void shouldReturnAllGroundStationsWhenStationsExist() {
      // Given
      List<GroundStation> expectedStations = Arrays.asList(testGroundStation);
      when(groundStationService.findAllGroundStations()).thenReturn(expectedStations);

      // When
      ResponseEntity<List<GroundStation>> response = groundStationController.getAllGroundStations();

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(expectedStations, response.getBody());
      assertEquals(1, response.getBody().size());
      assertEquals("Test Ground Station", response.getBody().get(0).getGroundStation_Name());
      verify(groundStationService, times(1)).findAllGroundStations();
    }

    @Test
    @DisplayName("Should return empty list when no ground stations exist")
    void shouldReturnEmptyListWhenNoGroundStationsExist() {
      // Given
      when(groundStationService.findAllGroundStations()).thenReturn(Arrays.asList());

      // When
      ResponseEntity<List<GroundStation>> response = groundStationController.getAllGroundStations();

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertTrue(response.getBody().isEmpty());
      verify(groundStationService, times(1)).findAllGroundStations();
    }
  }

  @Nested
  @DisplayName("Get Ground Station By ID Tests")
  class GetGroundStationByIdTests {

    @Test
    @DisplayName("Should return ground station when station exists")
    void shouldReturnGroundStationWhenStationExists() {
      // Given
      Long stationId = 1L;
      when(groundStationService.findGroundStationById(stationId)).thenReturn(Optional.of(testGroundStation));

      // When
      ResponseEntity<GroundStation> response = groundStationController.getGroundStationById(stationId);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(testGroundStation, response.getBody());
      assertEquals("Test Ground Station", response.getBody().getGroundStation_Name());
      assertEquals("groundstation@test.com", response.getBody().getGroundStation_Email());
      verify(groundStationService, times(1)).findGroundStationById(stationId);
    }

    @Test
    @DisplayName("Should return 404 when ground station does not exist")
    void shouldReturn404WhenGroundStationDoesNotExist() {
      // Given
      Long stationId = 999L;
      when(groundStationService.findGroundStationById(stationId)).thenReturn(Optional.empty());

      // When
      ResponseEntity<GroundStation> response = groundStationController.getGroundStationById(stationId);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
      assertNull(response.getBody());
      verify(groundStationService, times(1)).findGroundStationById(stationId);
    }
  }

  @Nested
  @DisplayName("Create Ground Station Tests")
  class CreateGroundStationTests {

    @Test
    @DisplayName("Should create ground station successfully")
    void shouldCreateGroundStationSuccessfully() {
      // Given
      when(groundStationService.saveGroundStation(any(GroundStation.class))).thenReturn(testGroundStation);

      // When
      ResponseEntity<GroundStation> response = groundStationController.createGroundStation(testGroundStation);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(testGroundStation, response.getBody());
      assertEquals("Test Ground Station", response.getBody().getGroundStation_Name());
      assertEquals("groundstation@test.com", response.getBody().getGroundStation_Email());
      assertEquals(40.7128, response.getBody().getGroundStation_Latitude());
      assertEquals(-74.0060, response.getBody().getGroundStation_Longitude());
      verify(groundStationService, times(1)).saveGroundStation(any(GroundStation.class));
    }

    @Test
    @DisplayName("Should handle service exception during ground station creation")
    void shouldHandleServiceExceptionDuringGroundStationCreation() {
      // Given
      when(groundStationService.saveGroundStation(any(GroundStation.class)))
              .thenThrow(new RuntimeException("Database error"));

      // When & Then
      assertThrows(RuntimeException.class, () -> groundStationController.createGroundStation(testGroundStation));
      verify(groundStationService, times(1)).saveGroundStation(any(GroundStation.class));
    }
  }

  @Nested
  @DisplayName("Update Ground Station Tests")
  class UpdateGroundStationTests {

    @Test
    @DisplayName("Should update ground station successfully")
    void shouldUpdateGroundStationSuccessfully() {
      // Given
      Long stationId = 1L;
      GroundStation updatedStation = new GroundStation();
      updatedStation.setGroundStation_Name("Updated Ground Station");
      updatedStation.setGroundStation_Email("updated@groundstation.com");

      when(groundStationService.updateGroundStation(eq(stationId), any(GroundStation.class)))
              .thenReturn(testGroundStation);

      // When
      ResponseEntity<GroundStation> response = groundStationController.updateGroundStation(stationId, updatedStation);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(testGroundStation, response.getBody());
      verify(groundStationService, times(1)).updateGroundStation(eq(stationId), any(GroundStation.class));
    }

    @Test
    @DisplayName("Should return 404 when ground station to update does not exist")
    void shouldReturn404WhenGroundStationToUpdateDoesNotExist() {
      // Given
      Long stationId = 999L;
      when(groundStationService.updateGroundStation(eq(stationId), any(GroundStation.class)))
              .thenThrow(new RuntimeException("Ground station not found"));

      // When
      ResponseEntity<GroundStation> response = groundStationController.updateGroundStation(stationId, testGroundStation);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
      assertNull(response.getBody());
      verify(groundStationService, times(1)).updateGroundStation(eq(stationId), any(GroundStation.class));
    }
  }

  @Nested
  @DisplayName("Delete Ground Station Tests")
  class DeleteGroundStationTests {

    @Test
    @DisplayName("Should delete ground station successfully")
    void shouldDeleteGroundStationSuccessfully() {
      // Given
      Long stationId = 1L;
      doNothing().when(groundStationService).deleteGroundStation(stationId);

      // When
      ResponseEntity<Void> response = groundStationController.deleteGroundStation(stationId);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
      assertNull(response.getBody());
      verify(groundStationService, times(1)).deleteGroundStation(stationId);
    }

    @Test
    @DisplayName("Should handle service exception during ground station deletion")
    void shouldHandleServiceExceptionDuringGroundStationDeletion() {
      // Given
      Long stationId = 1L;
      doThrow(new RuntimeException("Database error")).when(groundStationService).deleteGroundStation(stationId);

      // When & Then
      assertThrows(RuntimeException.class, () -> groundStationController.deleteGroundStation(stationId));
      verify(groundStationService, times(1)).deleteGroundStation(stationId);
    }
  }

  @Nested
  @DisplayName("Find All Satellites Tests")
  class FindAllSatellitesTests {

    @Test
    @DisplayName("Should return satellites for given user ID")
    void shouldReturnSatellitesForGivenUserId() {
      // Given
      Long userId = 1L;
      when(groundStationService.findSatelliteByUserId(userId)).thenReturn(testSatelliteResponse);

      // When
      ResponseEntity<SatelliteByGroundStationResponse> response = groundStationController.findAllSatellites(userId);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(testSatelliteResponse, response.getBody());
      verify(groundStationService, times(1)).findSatelliteByUserId(userId);
    }

    @Test
    @DisplayName("Should handle service exception when finding satellites")
    void shouldHandleServiceExceptionWhenFindingSatellites() {
      // Given
      Long userId = 1L;
      when(groundStationService.findSatelliteByUserId(userId))
              .thenThrow(new RuntimeException("User not found"));

      // When & Then
      assertThrows(RuntimeException.class, () -> groundStationController.findAllSatellites(userId));
      verify(groundStationService, times(1)).findSatelliteByUserId(userId);
    }

    @Test
    @DisplayName("Should return empty satellite response for user with no satellites")
    void shouldReturnEmptySatelliteResponseForUserWithNoSatellites() {
      // Given
      Long userId = 2L;
      SatelliteByGroundStationResponse emptyResponse = new SatelliteByGroundStationResponse();
      when(groundStationService.findSatelliteByUserId(userId)).thenReturn(emptyResponse);

      // When
      ResponseEntity<SatelliteByGroundStationResponse> response = groundStationController.findAllSatellites(userId);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(emptyResponse, response.getBody());
      verify(groundStationService, times(1)).findSatelliteByUserId(userId);
    }
  }

  @Nested
  @DisplayName("Edge Cases and Validation Tests")
  class EdgeCasesTests {

    @Test
    @DisplayName("Should handle null ground station input gracefully")
    void shouldHandleNullGroundStationInputGracefully() {
      // Given
      when(groundStationService.saveGroundStation(null))
              .thenThrow(new IllegalArgumentException("Ground station cannot be null"));

      // When & Then
      assertThrows(IllegalArgumentException.class, () -> groundStationController.createGroundStation(null));
      verify(groundStationService, times(1)).saveGroundStation(null);
    }

    @Test
    @DisplayName("Should handle ground station with minimal required fields")
    void shouldHandleGroundStationWithMinimalRequiredFields() {
      // Given
      GroundStation minimalStation = new GroundStation();
      minimalStation.setGroundStation_Name("Minimal Station");
      minimalStation.setGroundStation_Email("minimal@station.com");

      when(groundStationService.saveGroundStation(any(GroundStation.class))).thenReturn(minimalStation);

      // When
      ResponseEntity<GroundStation> response = groundStationController.createGroundStation(minimalStation);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals("Minimal Station", response.getBody().getGroundStation_Name());
      assertEquals("minimal@station.com", response.getBody().getGroundStation_Email());
    }

    @Test
    @DisplayName("Should handle invalid email format")
    void shouldHandleInvalidEmailFormat() {
      // Given
      GroundStation stationWithInvalidEmail = new GroundStation();
      stationWithInvalidEmail.setGroundStation_Name("Test Station");
      stationWithInvalidEmail.setGroundStation_Email("invalid-email-format");

      when(groundStationService.saveGroundStation(any(GroundStation.class)))
              .thenThrow(new IllegalArgumentException("Invalid email format"));

      // When & Then
      assertThrows(IllegalArgumentException.class,
              () -> groundStationController.createGroundStation(stationWithInvalidEmail));
      verify(groundStationService, times(1)).saveGroundStation(any(GroundStation.class));
    }

    @Test
    @DisplayName("Should handle invalid geographic coordinates")
    void shouldHandleInvalidGeographicCoordinates() {
      // Given
      GroundStation stationWithInvalidCoords = new GroundStation();
      stationWithInvalidCoords.setGroundStation_Name("Test Station");
      stationWithInvalidCoords.setGroundStation_Email("test@station.com");
      stationWithInvalidCoords.setGroundStation_Latitude(200.0); // Invalid latitude (> 90)
      stationWithInvalidCoords.setGroundStation_Longitude(-200.0); // Invalid longitude (< -180)

      when(groundStationService.saveGroundStation(any(GroundStation.class)))
              .thenThrow(new IllegalArgumentException("Invalid geographic coordinates"));

      // When & Then
      assertThrows(IllegalArgumentException.class,
              () -> groundStationController.createGroundStation(stationWithInvalidCoords));
      verify(groundStationService, times(1)).saveGroundStation(any(GroundStation.class));
    }

    @Test
    @DisplayName("Should handle ground station update with null required fields")
    void shouldHandleGroundStationUpdateWithNullFields() {
      // Given
      Long stationId = 1L;
      GroundStation stationWithNullFields = new GroundStation();
      stationWithNullFields.setGroundStation_Name(null); // Required field is null
      stationWithNullFields.setGroundStation_Email(null); // Required field is null

      when(groundStationService.updateGroundStation(eq(stationId), any(GroundStation.class)))
              .thenThrow(new IllegalArgumentException("Required fields cannot be null"));

      // When
      ResponseEntity<GroundStation> response = groundStationController.updateGroundStation(stationId, stationWithNullFields);

      // Then
      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
      assertNull(response.getBody());
      verify(groundStationService, times(1)).updateGroundStation(eq(stationId), any(GroundStation.class));
    }

    @Test
    @DisplayName("Should handle ground station deletion with associated user")
    void shouldHandleGroundStationDeletionWithAssociatedUser() {
      // Given
      Long stationId = 1L;

      // Simulate that ground station has associated user that needs cleanup
      doThrow(new RuntimeException("Cannot delete ground station: associated user must be handled"))
              .when(groundStationService).deleteGroundStation(stationId);

      // When & Then
      assertThrows(RuntimeException.class, () -> groundStationController.deleteGroundStation(stationId));
      verify(groundStationService, times(1)).deleteGroundStation(stationId);
    }
  }

  @Nested
  @DisplayName("Business Logic and Integration Tests")
  class BusinessLogicTests {

    @Test
    @DisplayName("Should handle access level validation")
    void shouldHandleAccessLevelValidation() {
      // Given
      GroundStation stationWithInvalidAccessLevel = new GroundStation();
      stationWithInvalidAccessLevel.setGroundStation_Name("Test Station");
      stationWithInvalidAccessLevel.setGroundStation_Email("test@station.com");
      stationWithInvalidAccessLevel.setGroundStation_AccesLevel(-1); // Invalid access level

      when(groundStationService.saveGroundStation(any(GroundStation.class)))
              .thenThrow(new IllegalArgumentException("Invalid access level"));

      // When & Then
      assertThrows(IllegalArgumentException.class,
              () -> groundStationController.createGroundStation(stationWithInvalidAccessLevel));
      verify(groundStationService, times(1)).saveGroundStation(any(GroundStation.class));
    }

    @Test
    @DisplayName("Should handle concurrent ground station updates")
    void shouldHandleConcurrentGroundStationUpdates() {
      // Given
      Long stationId = 1L;
      GroundStation stationUpdate = new GroundStation();
      stationUpdate.setGroundStation_Name("Concurrent Update");

      // Simulate optimistic locking exception
      when(groundStationService.updateGroundStation(eq(stationId), any(GroundStation.class)))
              .thenThrow(new RuntimeException("Optimistic locking failure - ground station was modified by another transaction"));

      // When
      ResponseEntity<GroundStation> response = groundStationController.updateGroundStation(stationId, stationUpdate);

      // Then
      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
      assertNull(response.getBody());
      verify(groundStationService, times(1)).updateGroundStation(eq(stationId), any(GroundStation.class));
    }

    @Test
    @DisplayName("Should handle database timeout during ground station creation")
    void shouldHandleDatabaseTimeoutDuringGroundStationCreation() {
      // Given
      GroundStation newStation = new GroundStation();
      newStation.setGroundStation_Name("New Station");
      newStation.setGroundStation_Email("new@station.com");

      when(groundStationService.saveGroundStation(any(GroundStation.class)))
              .thenThrow(new RuntimeException("Database connection timeout"));

      // When & Then
      assertThrows(RuntimeException.class, () -> groundStationController.createGroundStation(newStation));
      verify(groundStationService, times(1)).saveGroundStation(any(GroundStation.class));
    }
  }

  @Nested
  @DisplayName("Cascade Delete and Relationship Management Tests")
  class CascadeDeleteAndRelationshipTests {

    @Test
    @DisplayName("Should handle cascade delete when deleting ground station with associated user")
    void shouldHandleCascadeDeleteWhenDeletingGroundStationWithAssociatedUser() {
      // Given
      Long stationId = 1L;
      User associatedUser = new User();
      associatedUser.setUserId(1L);
      associatedUser.setUser_name("Associated User");
      associatedUser.setGroundStation(testGroundStation);

      // Use doAnswer to verify the state during deletion
      doAnswer(invocation -> {
        // Verify that the user's ground station reference is properly cleared
        // This simulates what should happen in the service layer
        if (associatedUser.getGroundStation() != null) {
          associatedUser.setGroundStation(null);
        }
        return null;
      }).when(groundStationService).deleteGroundStation(stationId);

      // When
      ResponseEntity<Void> response = groundStationController.deleteGroundStation(stationId);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
      assertNull(response.getBody());

      // Verify that the deletion was called
      verify(groundStationService, times(1)).deleteGroundStation(stationId);

      // Verify that the user's ground station reference was cleared
      assertNull(associatedUser.getGroundStation());
    }

    @Test
    @DisplayName("Should handle cascade delete with orphan removal verification")
    void shouldHandleCascadeDeleteWithOrphanRemovalVerification() {
      // Given
      Long stationId = 1L;
      User orphanedUser = new User();
      orphanedUser.setUserId(2L);
      orphanedUser.setUser_name("Orphaned User");
      orphanedUser.setGroundStation(testGroundStation);

      // Simulate cascade delete behavior that should handle orphan removal
      doAnswer(invocation -> {
        // Verify that orphaned relationships are properly handled
        if (orphanedUser.getGroundStation() != null &&
                orphanedUser.getGroundStation().getGroundStation_id().equals(stationId)) {

          // Clear the relationship
          orphanedUser.setGroundStation(null);

          // In a real scenario, this might trigger additional cleanup
          System.out.println("Orphan relationship cleaned up for user: " + orphanedUser.getUserId());
        }
        return null;
      }).when(groundStationService).deleteGroundStation(stationId);

      // When
      ResponseEntity<Void> response = groundStationController.deleteGroundStation(stationId);

      // Then
      assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
      verify(groundStationService, times(1)).deleteGroundStation(stationId);

      // Verify orphan cleanup
      assertNull(orphanedUser.getGroundStation());
    }

    @Test
    @DisplayName("Should handle relationship update when assigning ground station to new user")
    void shouldHandleRelationshipUpdateWhenAssigningGroundStationToNewUser() {
      // Given
      Long stationId = 1L;

      // Old user currently associated with the ground station
      User oldUser = new User();
      oldUser.setUserId(1L);
      oldUser.setUser_name("Old User");
      oldUser.setGroundStation(testGroundStation);

      // New user to be associated
      User newUser = new User();
      newUser.setUserId(2L);
      newUser.setUser_name("New User");
      newUser.setGroundStation(null);

      // Updated ground station with new user
      GroundStation updatedStation = new GroundStation();
      updatedStation.setGroundStation_id(stationId);
      updatedStation.setGroundStation_Name("Updated Station");
      updatedStation.setGroundStation_Email("updated@station.com");
      updatedStation.setUser(newUser);

      // Use doAnswer to simulate relationship cleanup and establishment
      when(groundStationService.updateGroundStation(eq(stationId), any(GroundStation.class)))
              .thenAnswer(invocation -> {
                GroundStation inputStation = invocation.getArgument(1);

                // Simulate cleaning up old relationship
                if (oldUser.getGroundStation() != null) {
                  oldUser.setGroundStation(null);
                }

                // Simulate establishing new relationship
                if (inputStation.getUser() != null) {
                  inputStation.getUser().setGroundStation(inputStation);
                }

                return updatedStation;
              });

      // When
      ResponseEntity<GroundStation> response = groundStationController.updateGroundStation(stationId, updatedStation);

      // Then
      assertNotNull(response);
      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(updatedStation, response.getBody());

      // Verify that the old relationship was cleaned up
      assertNull(oldUser.getGroundStation());

      // Verify that the new relationship was established
      assertEquals(updatedStation, newUser.getGroundStation());

      verify(groundStationService, times(1)).updateGroundStation(eq(stationId), any(GroundStation.class));
    }

    @Test
    @DisplayName("Should handle bidirectional relationship consistency during update")
    void shouldHandleBidirectionalRelationshipConsistencyDuringUpdate() {
      // Given
      Long stationId = 1L;

      User currentUser = new User();
      currentUser.setUserId(1L);
      currentUser.setUser_name("Current User");

      GroundStation stationUpdate = new GroundStation();
      stationUpdate.setGroundStation_id(stationId);
      stationUpdate.setGroundStation_Name("Updated Station");
      stationUpdate.setUser(currentUser);

      // Mock the service to return updated station with proper relationships
      when(groundStationService.updateGroundStation(eq(stationId), any(GroundStation.class)))
              .thenAnswer(invocation -> {
                GroundStation inputStation = invocation.getArgument(1);

                // Ensure bidirectional relationship consistency
                if (inputStation.getUser() != null) {
                  inputStation.getUser().setGroundStation(inputStation);
                }

                return inputStation;
              });

      // When
      ResponseEntity<GroundStation> response = groundStationController.updateGroundStation(stationId, stationUpdate);

      // Then
      assertEquals(HttpStatus.OK, response.getStatusCode());

      // Verify bidirectional relationship consistency
      GroundStation resultStation = response.getBody();
      assertNotNull(resultStation);
      assertNotNull(resultStation.getUser());
      assertEquals(resultStation, resultStation.getUser().getGroundStation());
      assertEquals(currentUser.getUserId(), resultStation.getUser().getUserId());

      verify(groundStationService, times(1)).updateGroundStation(eq(stationId), any(GroundStation.class));
    }

    @Test
    @DisplayName("Should handle relationship update with null user assignment")
    void shouldHandleRelationshipUpdateWithNullUserAssignment() {
      // Given
      Long stationId = 1L;

      // Current user associated with the ground station
      User currentUser = new User();
      currentUser.setUserId(1L);
      currentUser.setUser_name("Current User");
      currentUser.setGroundStation(testGroundStation);

      // Updated station with null user (removing the relationship)
      GroundStation stationWithNullUser = new GroundStation();
      stationWithNullUser.setGroundStation_id(stationId);
      stationWithNullUser.setGroundStation_Name("Station Without User");
      stationWithNullUser.setUser(null);

      when(groundStationService.updateGroundStation(eq(stationId), any(GroundStation.class)))
              .thenAnswer(invocation -> {
                // Simulate cleaning up the relationship when user is set to null
                if (currentUser.getGroundStation() != null) {
                  currentUser.setGroundStation(null);
                }
                return stationWithNullUser;
              });

      // When
      ResponseEntity<GroundStation> response = groundStationController.updateGroundStation(stationId, stationWithNullUser);

      // Then
      assertEquals(HttpStatus.OK, response.getStatusCode());

      // Verify that the user's ground station reference was cleared
      assertNull(currentUser.getGroundStation());

      // Verify that the ground station has no user
      assertNull(response.getBody().getUser());

      verify(groundStationService, times(1)).updateGroundStation(eq(stationId), any(GroundStation.class));
    }

    @Test
    @DisplayName("Should handle cascade delete failure when user constraints exist")
    void shouldHandleCascadeDeleteFailureWhenUserConstraintsExist() {
      // Given
      Long stationId = 1L;

      // Simulate a scenario where deletion fails due to constraint violations
      doThrow(new RuntimeException("Cannot delete ground station: user has active satellite operations"))
              .when(groundStationService).deleteGroundStation(stationId);

      // When & Then
      assertThrows(RuntimeException.class, () -> groundStationController.deleteGroundStation(stationId));

      verify(groundStationService, times(1)).deleteGroundStation(stationId);
    }

    @Test
    @DisplayName("Should verify relationship state before and after update")
    void shouldVerifyRelationshipStateBeforeAndAfterUpdate() {
      // Given
      Long stationId = 1L;

      User oldUser = new User();
      oldUser.setUserId(1L);
      oldUser.setUser_name("Old User");

      User newUser = new User();
      newUser.setUserId(2L);
      newUser.setUser_name("New User");

      // Initial state: ground station associated with old user
      GroundStation initialStation = new GroundStation();
      initialStation.setGroundStation_id(stationId);
      initialStation.setUser(oldUser);
      oldUser.setGroundStation(initialStation);

      // Update: change association to new user
      GroundStation updateRequest = new GroundStation();
      updateRequest.setGroundStation_id(stationId);
      updateRequest.setGroundStation_Name("Updated Station");
      updateRequest.setUser(newUser);

      when(groundStationService.updateGroundStation(eq(stationId), any(GroundStation.class)))
              .thenAnswer(invocation -> {
                // Verify initial state
                assertEquals(initialStation, oldUser.getGroundStation());
                assertNull(newUser.getGroundStation());

                // Perform relationship update
                oldUser.setGroundStation(null);
                newUser.setGroundStation(updateRequest);
                updateRequest.setUser(newUser);

                return updateRequest;
              });

      // When
      ResponseEntity<GroundStation> response = groundStationController.updateGroundStation(stationId, updateRequest);

      // Then
      assertEquals(HttpStatus.OK, response.getStatusCode());

      // Verify final state
      assertNull(oldUser.getGroundStation());
      assertEquals(updateRequest, newUser.getGroundStation());
      assertEquals(newUser, response.getBody().getUser());

      verify(groundStationService, times(1)).updateGroundStation(eq(stationId), any(GroundStation.class));
    }
  }
}