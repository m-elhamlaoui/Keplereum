package com.example.jeeHamlaoui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.dto.UserDto;
import com.example.jeeHamlaoui.model.enums.UserStatus;
import com.example.jeeHamlaoui.service.UserService;
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
@DisplayName("UserController Tests")
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private UserDto testUserDto;
    private GroundStation testGroundStation;

    @BeforeEach
    void setUp() {
        // Create test ground station
        testGroundStation = new GroundStation();
        testGroundStation.setGroundStation_id(1L);
        testGroundStation.setGroundStation_Name("Test Ground Station");
        testGroundStation.setGroundStation_Description("Test Description");
        testGroundStation.setGroundStation_Email("test@groundstation.com");
        testGroundStation.setGroundStation_Latitude(40.7128);
        testGroundStation.setGroundStation_Longitude(-74.0060);
        testGroundStation.setGroundStation_AccesLevel(1);

        // Create test user
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setUser_name("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPassword("testPassword");
        testUser.setStatus(UserStatus.ACTIVE);
        testUser.setCreated_at(Instant.now());
        testUser.setGroundStation(testGroundStation);

        // Create test UserDto
        testUserDto = new UserDto();
        testUserDto.setUserId(1L);
        testUserDto.setUser_name("Test User");
        testUserDto.setEmail("test@example.com");
        testUserDto.setStatus(UserStatus.ACTIVE);
        testUserDto.setCreated_at(testUser.getCreated_at());
        testUserDto.setGroundStation(testGroundStation);
    }

    @Nested
    @DisplayName("Get All Users Tests")
    class GetAllUsersTests {

        @Test
        @DisplayName("Should return all users when users exist")
        void shouldReturnAllUsersWhenUsersExist() {
            // Given
            List<UserDto> expectedUsers = Arrays.asList(testUserDto);
            when(userService.getAllUsers()).thenReturn(expectedUsers);

            // When
            ResponseEntity<List<UserDto>> response = userController.getAllUsers();

            // Then
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(expectedUsers, response.getBody());
            assertEquals(1, response.getBody().size());
            verify(userService, times(1)).getAllUsers();
        }

        @Test
        @DisplayName("Should return empty list when no users exist")
        void shouldReturnEmptyListWhenNoUsersExist() {
            // Given
            when(userService.getAllUsers()).thenReturn(Arrays.asList());

            // When
            ResponseEntity<List<UserDto>> response = userController.getAllUsers();

            // Then
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().isEmpty());
            verify(userService, times(1)).getAllUsers();
        }
    }

    @Nested
    @DisplayName("Get User By ID Tests")
    class GetUserByIdTests {

        @Test
        @DisplayName("Should return user when user exists")
        void shouldReturnUserWhenUserExists() {
            // Given
            Long userId = 1L;
            when(userService.getUserById(userId)).thenReturn(Optional.of(testUser));

            // When
            ResponseEntity<User> response = userController.getUserById(userId);

            // Then
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(testUser, response.getBody());
            verify(userService, times(1)).getUserById(userId);
        }

        @Test
        @DisplayName("Should return 404 when user does not exist")
        void shouldReturn404WhenUserDoesNotExist() {
            // Given
            Long userId = 999L;
            when(userService.getUserById(userId)).thenReturn(Optional.empty());

            // When
            ResponseEntity<User> response = userController.getUserById(userId);

            // Then
            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(userService, times(1)).getUserById(userId);
        }
    }

    @Nested
    @DisplayName("Create User Tests")
    class CreateUserTests {

        @Test
        @DisplayName("Should create user successfully")
        void shouldCreateUserSuccessfully() {
            // Given
            when(userService.saveUser(any(User.class))).thenReturn(testUserDto);

            // When
            ResponseEntity<UserDto> response = userController.createUser(testUser);

            // Then
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(testUserDto, response.getBody());
            assertEquals("Test User", response.getBody().getUser_name());
            assertEquals("test@example.com", response.getBody().getEmail());
            verify(userService, times(1)).saveUser(any(User.class));
        }

        @Test
        @DisplayName("Should handle service exception during user creation")
        void shouldHandleServiceExceptionDuringUserCreation() {
            // Given
            when(userService.saveUser(any(User.class))).thenThrow(new RuntimeException("Database error"));

            // When & Then
            assertThrows(RuntimeException.class, () -> userController.createUser(testUser));
            verify(userService, times(1)).saveUser(any(User.class));
        }
    }

    @Nested
    @DisplayName("Update User Tests")
    class UpdateUserTests {

        @Test
        @DisplayName("Should update user successfully")
        void shouldUpdateUserSuccessfully() {
            // Given
            Long userId = 1L;
            User updatedUser = new User();
            updatedUser.setUser_name("Updated User");
            
            when(userService.updateUser(eq(userId), any(User.class))).thenReturn(testUser);
            when(userService.saveUser(any(User.class))).thenReturn(testUserDto);

            // When
            ResponseEntity<UserDto> response = userController.updateUser(userId, updatedUser);

            // Then
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(testUserDto, response.getBody());
            verify(userService, times(1)).updateUser(eq(userId), any(User.class));
            verify(userService, times(1)).saveUser(any(User.class));
        }

        @Test
        @DisplayName("Should return 404 when user to update does not exist")
        void shouldReturn404WhenUserToUpdateDoesNotExist() {
            // Given
            Long userId = 999L;
            when(userService.updateUser(eq(userId), any(User.class)))
                .thenThrow(new RuntimeException("User not found"));

            // When
            ResponseEntity<UserDto> response = userController.updateUser(userId, testUser);

            // Then
            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(userService, times(1)).updateUser(eq(userId), any(User.class));
            verify(userService, never()).saveUser(any(User.class));
        }
    }

    @Nested
    @DisplayName("Delete User Tests")
    class DeleteUserTests {

        @Test
        @DisplayName("Should delete user successfully")
        void shouldDeleteUserSuccessfully() {
            // Given
            Long userId = 1L;
            doNothing().when(userService).deleteUser(userId);

            // When
            ResponseEntity<Void> response = userController.deleteUser(userId);

            // Then
            assertNotNull(response);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertNull(response.getBody());
            verify(userService, times(1)).deleteUser(userId);
        }

        @Test
        @DisplayName("Should handle service exception during user deletion")
        void shouldHandleServiceExceptionDuringUserDeletion() {
            // Given
            Long userId = 1L;
            doThrow(new RuntimeException("Database error")).when(userService).deleteUser(userId);

            // When & Then
            assertThrows(RuntimeException.class, () -> userController.deleteUser(userId));
            verify(userService, times(1)).deleteUser(userId);
        }
    }

    @Nested
    @DisplayName("Edge Cases and Validation Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle null user input gracefully")
        void shouldHandleNullUserInputGracefully() {
            // Given
            when(userService.saveUser(null)).thenThrow(new IllegalArgumentException("User cannot be null"));

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> userController.createUser(null));
            verify(userService, times(1)).saveUser(null);
        }

        @Test
        @DisplayName("Should handle user with minimal required fields")
        void shouldHandleUserWithMinimalRequiredFields() {
            // Given
            User minimalUser = new User();
            minimalUser.setUser_name("Minimal User");
            minimalUser.setEmail("minimal@example.com");

            UserDto minimalDto = new UserDto();
            minimalDto.setUser_name("Minimal User");
            minimalDto.setEmail("minimal@example.com");

            when(userService.saveUser(any(User.class))).thenReturn(minimalDto);

            // When
            ResponseEntity<UserDto> response = userController.createUser(minimalUser);

            // Then
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Minimal User", response.getBody().getUser_name());
            assertEquals("minimal@example.com", response.getBody().getEmail());
        }

        @Test
        @DisplayName("Should handle invalid email format")
        void shouldHandleInvalidEmailFormat() {
            // Given
            User userWithInvalidEmail = new User();
            userWithInvalidEmail.setUser_name("Test User");
            userWithInvalidEmail.setEmail("invalid-email-format");
            userWithInvalidEmail.setPassword("password");
            
            when(userService.saveUser(any(User.class)))
                .thenThrow(new IllegalArgumentException("Invalid email format"));

            // When & Then
            assertThrows(IllegalArgumentException.class, () -> userController.createUser(userWithInvalidEmail));
            verify(userService, times(1)).saveUser(any(User.class));
        }

        @Test
        @DisplayName("Should handle user update with null required fields")
        void shouldHandleUserUpdateWithNullFields() {
            // Given
            Long userId = 1L;
            User userWithNullFields = new User();
            userWithNullFields.setUser_name(null); // Required field is null
            userWithNullFields.setEmail(null);     // Required field is null
            
            when(userService.updateUser(eq(userId), any(User.class)))
                .thenThrow(new IllegalArgumentException("Required fields cannot be null"));

            // When
            ResponseEntity<UserDto> response = userController.updateUser(userId, userWithNullFields);

            // Then
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(userService, times(1)).updateUser(eq(userId), any(User.class));
        }

        @Test
        @DisplayName("Should handle user update with invalid ground station")
        void shouldHandleUserUpdateWithInvalidGroundStation() {
            // Given
            Long userId = 1L;
            GroundStation invalidGroundStation = new GroundStation();
            invalidGroundStation.setGroundStation_id(999L); // Non-existent ground station
            invalidGroundStation.setGroundStation_Name("Invalid Station");
            
            User userWithInvalidGroundStation = new User();
            userWithInvalidGroundStation.setUser_name("Test User");
            userWithInvalidGroundStation.setEmail("test@example.com");
            userWithInvalidGroundStation.setGroundStation(invalidGroundStation);
            
            when(userService.updateUser(eq(userId), any(User.class)))
                .thenThrow(new IllegalArgumentException("Ground station does not exist"));

            // When
            ResponseEntity<UserDto> response = userController.updateUser(userId, userWithInvalidGroundStation);

            // Then
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(userService, times(1)).updateUser(eq(userId), any(User.class));
        }

        @Test
        @DisplayName("Should handle user deletion with associated ground station")
        void shouldHandleUserDeletionWithAssociatedGroundStation() {
            // Given
            Long userId = 1L;
            
            // Simulate that user has associated ground station that needs cleanup
            doThrow(new RuntimeException("Cannot delete user: associated ground station must be handled"))
                .when(userService).deleteUser(userId);

            // When & Then
            assertThrows(RuntimeException.class, () -> userController.deleteUser(userId));
            verify(userService, times(1)).deleteUser(userId);
        }
    }

    @Nested
    @DisplayName("State Transition Tests")
    class StateTransitionTests {

        @Test
        @DisplayName("Should handle user status transitions from ACTIVE to INACTIVE")
        void shouldHandleUserStatusTransitions() {
            // Given
            Long userId = 1L;
            User userToUpdate = new User();
            userToUpdate.setUserId(userId);
            userToUpdate.setUser_name("Test User");
            userToUpdate.setEmail("test@example.com");
            userToUpdate.setStatus(UserStatus.INACTIVE); // Changing from ACTIVE to INACTIVE
            
            User updatedUser = new User();
            updatedUser.setUserId(userId);
            updatedUser.setUser_name("Test User");
            updatedUser.setEmail("test@example.com");
            updatedUser.setStatus(UserStatus.INACTIVE);
            
            UserDto updatedUserDto = new UserDto();
            updatedUserDto.setUserId(userId);
            updatedUserDto.setUser_name("Test User");
            updatedUserDto.setEmail("test@example.com");
            updatedUserDto.setStatus(UserStatus.INACTIVE);
            
            when(userService.updateUser(eq(userId), any(User.class))).thenReturn(updatedUser);
            when(userService.saveUser(any(User.class))).thenReturn(updatedUserDto);

            // When
            ResponseEntity<UserDto> response = userController.updateUser(userId, userToUpdate);

            // Then
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(UserStatus.INACTIVE, response.getBody().getStatus());
            verify(userService, times(1)).updateUser(eq(userId), any(User.class));
            verify(userService, times(1)).saveUser(any(User.class));
        }

        @Test
        @DisplayName("Should handle invalid status transition")
        void shouldHandleInvalidStatusTransition() {
            // Given
            Long userId = 1L;
            User userWithInvalidStatus = new User();
            userWithInvalidStatus.setUserId(userId);
            userWithInvalidStatus.setUser_name("Test User");
            userWithInvalidStatus.setEmail("test@example.com");
            userWithInvalidStatus.setStatus(null); // Invalid status
            
            when(userService.updateUser(eq(userId), any(User.class)))
                .thenThrow(new IllegalArgumentException("Invalid user status"));

            // When
            ResponseEntity<UserDto> response = userController.updateUser(userId, userWithInvalidStatus);

            // Then
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(userService, times(1)).updateUser(eq(userId), any(User.class));
        }

        @Test
        @DisplayName("Should handle status transition with business rule validation")
        void shouldHandleStatusTransitionWithBusinessRuleValidation() {
            // Given
            Long userId = 1L;
            User userToDeactivate = new User();
            userToDeactivate.setUserId(userId);
            userToDeactivate.setUser_name("Test User");
            userToDeactivate.setEmail("test@example.com");
            userToDeactivate.setStatus(UserStatus.INACTIVE);
            
            // Simulate business rule: cannot deactivate user with active ground station operations
            when(userService.updateUser(eq(userId), any(User.class)))
                .thenThrow(new RuntimeException("Cannot deactivate user with active ground station operations"));

            // When
            ResponseEntity<UserDto> response = userController.updateUser(userId, userToDeactivate);

            // Then
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(userService, times(1)).updateUser(eq(userId), any(User.class));
        }
    }

    @Nested
    @DisplayName("Concurrent Access and Performance Tests")
    class ConcurrencyTests {

        @Test
        @DisplayName("Should handle concurrent user updates")
        void shouldHandleConcurrentUserUpdates() {
            // Given
            Long userId = 1L;
            User userUpdate1 = new User();
            userUpdate1.setUser_name("Update 1");
            userUpdate1.setEmail("update1@example.com");
            
            // Simulate optimistic locking exception
            when(userService.updateUser(eq(userId), any(User.class)))
                .thenThrow(new RuntimeException("Optimistic locking failure - user was modified by another transaction"));

            // When
            ResponseEntity<UserDto> response = userController.updateUser(userId, userUpdate1);

            // Then
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
            verify(userService, times(1)).updateUser(eq(userId), any(User.class));
        }

        @Test
        @DisplayName("Should handle database timeout during user creation")
        void shouldHandleDatabaseTimeoutDuringUserCreation() {
            // Given
            User newUser = new User();
            newUser.setUser_name("New User");
            newUser.setEmail("new@example.com");
            
            when(userService.saveUser(any(User.class)))
                .thenThrow(new RuntimeException("Database connection timeout"));

            // When & Then
            assertThrows(RuntimeException.class, () -> userController.createUser(newUser));
            verify(userService, times(1)).saveUser(any(User.class));
        }
    }
}