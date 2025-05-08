package com.example.jeeHamlaoui.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.dto.UserDto;
import com.example.jeeHamlaoui.model.dto.UserMapper;
import com.example.jeeHamlaoui.model.enums.UserStatus;
import com.example.jeeHamlaoui.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserServiceDiffblueTest {
    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Test {@link UserService#saveUser(User)}.
     * <ul>
     *   <li>Given {@link UserMapper} {@link UserMapper#toDto(User)} return {@link UserDto#UserDto()}.</li>
     *   <li>Then return {@link UserDto#UserDto()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#saveUser(User)}
     */
    @Test
    @DisplayName("Test saveUser(User); given UserMapper toDto(User) return UserDto(); then return UserDto()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"UserDto UserService.saveUser(User)"})
    void testSaveUser_givenUserMapperToDtoReturnUserDto_thenReturnUserDto() {
        // Arrange
        User user = new User();
        user.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user.setEmail("jane.doe@example.org");
        user.setGroundStation(new GroundStation());
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setStatus(UserStatus.ACTIVE);
        user.setUser_id(1L);
        user.setUser_name("User name");

        GroundStation groundStation = new GroundStation();
        groundStation.setGroundStation_AccesLevel(1);
        groundStation.setGroundStation_Description("Ground Station Description");
        groundStation.setGroundStation_Email("jane.doe@example.org");
        groundStation.setGroundStation_Latitude(10.0d);
        groundStation.setGroundStation_Longitude(10.0d);
        groundStation.setGroundStation_Name("Ground Station Name");
        groundStation.setGroundStation_id(1L);
        groundStation.setUser(user);

        User user2 = new User();
        user2.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user2.setEmail("jane.doe@example.org");
        user2.setGroundStation(groundStation);
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setStatus(UserStatus.ACTIVE);
        user2.setUser_id(1L);
        user2.setUser_name("User name");

        GroundStation groundStation2 = new GroundStation();
        groundStation2.setGroundStation_AccesLevel(1);
        groundStation2.setGroundStation_Description("Ground Station Description");
        groundStation2.setGroundStation_Email("jane.doe@example.org");
        groundStation2.setGroundStation_Latitude(10.0d);
        groundStation2.setGroundStation_Longitude(10.0d);
        groundStation2.setGroundStation_Name("Ground Station Name");
        groundStation2.setGroundStation_id(1L);
        groundStation2.setUser(user2);

        User user3 = new User();
        user3.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user3.setEmail("jane.doe@example.org");
        user3.setGroundStation(groundStation2);
        user3.setId(1L);
        user3.setPassword("iloveyou");
        user3.setStatus(UserStatus.ACTIVE);
        user3.setUser_id(1L);
        user3.setUser_name("User name");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user3);
        UserDto userDto = new UserDto();
        when(userMapper.toDto(Mockito.<User>any())).thenReturn(userDto);

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(new User());

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(groundStation3);
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user4);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(groundStation4);
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");

        // Act
        UserDto actualSaveUserResult = userService.saveUser(user5);

        // Assert
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).save(isA(User.class));
        assertSame(userDto, actualSaveUserResult);
    }

    /**
     * Test {@link UserService#saveUser(User)}.
     * <ul>
     *   <li>Then throw {@link RuntimeException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#saveUser(User)}
     */
    @Test
    @DisplayName("Test saveUser(User); then throw RuntimeException")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"UserDto UserService.saveUser(User)"})
    void testSaveUser_thenThrowRuntimeException() {
        // Arrange
        User user = new User();
        user.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user.setEmail("jane.doe@example.org");
        user.setGroundStation(new GroundStation());
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setStatus(UserStatus.ACTIVE);
        user.setUser_id(1L);
        user.setUser_name("User name");

        GroundStation groundStation = new GroundStation();
        groundStation.setGroundStation_AccesLevel(1);
        groundStation.setGroundStation_Description("Ground Station Description");
        groundStation.setGroundStation_Email("jane.doe@example.org");
        groundStation.setGroundStation_Latitude(10.0d);
        groundStation.setGroundStation_Longitude(10.0d);
        groundStation.setGroundStation_Name("Ground Station Name");
        groundStation.setGroundStation_id(1L);
        groundStation.setUser(user);

        User user2 = new User();
        user2.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user2.setEmail("jane.doe@example.org");
        user2.setGroundStation(groundStation);
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setStatus(UserStatus.ACTIVE);
        user2.setUser_id(1L);
        user2.setUser_name("User name");

        GroundStation groundStation2 = new GroundStation();
        groundStation2.setGroundStation_AccesLevel(1);
        groundStation2.setGroundStation_Description("Ground Station Description");
        groundStation2.setGroundStation_Email("jane.doe@example.org");
        groundStation2.setGroundStation_Latitude(10.0d);
        groundStation2.setGroundStation_Longitude(10.0d);
        groundStation2.setGroundStation_Name("Ground Station Name");
        groundStation2.setGroundStation_id(1L);
        groundStation2.setUser(user2);

        User user3 = new User();
        user3.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user3.setEmail("jane.doe@example.org");
        user3.setGroundStation(groundStation2);
        user3.setId(1L);
        user3.setPassword("iloveyou");
        user3.setStatus(UserStatus.ACTIVE);
        user3.setUser_id(1L);
        user3.setUser_name("User name");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user3);
        when(userMapper.toDto(Mockito.<User>any())).thenThrow(new RuntimeException("foo"));

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(new User());

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(groundStation3);
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user4);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(groundStation4);
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.saveUser(user5));
        verify(userMapper).toDto(isA(User.class));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Test {@link UserService#getUserById(Long)}.
     * <ul>
     *   <li>Then return {@link Optional} with {@link User#User()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getUserById(Long)}
     */
    @Test
    @DisplayName("Test getUserById(Long); then return Optional with User()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Optional UserService.getUserById(Long)"})
    void testGetUserById_thenReturnOptionalWithUser() {
        // Arrange
        GroundStation groundStation = new GroundStation();
        groundStation.setGroundStation_AccesLevel(1);
        groundStation.setGroundStation_Description("Ground Station Description");
        groundStation.setGroundStation_Email("jane.doe@example.org");
        groundStation.setGroundStation_Latitude(10.0d);
        groundStation.setGroundStation_Longitude(10.0d);
        groundStation.setGroundStation_Name("Ground Station Name");
        groundStation.setGroundStation_id(1L);
        groundStation.setUser(new User());

        User user = new User();
        user.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user.setEmail("jane.doe@example.org");
        user.setGroundStation(groundStation);
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setStatus(UserStatus.ACTIVE);
        user.setUser_id(1L);
        user.setUser_name("User name");

        GroundStation groundStation2 = new GroundStation();
        groundStation2.setGroundStation_AccesLevel(1);
        groundStation2.setGroundStation_Description("Ground Station Description");
        groundStation2.setGroundStation_Email("jane.doe@example.org");
        groundStation2.setGroundStation_Latitude(10.0d);
        groundStation2.setGroundStation_Longitude(10.0d);
        groundStation2.setGroundStation_Name("Ground Station Name");
        groundStation2.setGroundStation_id(1L);
        groundStation2.setUser(user);

        User user2 = new User();
        user2.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user2.setEmail("jane.doe@example.org");
        user2.setGroundStation(groundStation2);
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setStatus(UserStatus.ACTIVE);
        user2.setUser_id(1L);
        user2.setUser_name("User name");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Optional<User> actualUserById = userService.getUserById(1L);

        // Assert
        verify(userRepository).findById(eq(1L));
        assertSame(ofResult, actualUserById);
    }

    /**
     * Test {@link UserService#getUserById(Long)}.
     * <ul>
     *   <li>Then throw {@link RuntimeException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getUserById(Long)}
     */
    @Test
    @DisplayName("Test getUserById(Long); then throw RuntimeException")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Optional UserService.getUserById(Long)"})
    void testGetUserById_thenThrowRuntimeException() {
        // Arrange
        when(userRepository.findById(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Test {@link UserService#getUserByEmail(String)}.
     * <ul>
     *   <li>Then return {@link Optional} with {@link User#User()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getUserByEmail(String)}
     */
    @Test
    @DisplayName("Test getUserByEmail(String); then return Optional with User()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Optional UserService.getUserByEmail(String)"})
    void testGetUserByEmail_thenReturnOptionalWithUser() {
        // Arrange
        GroundStation groundStation = new GroundStation();
        groundStation.setGroundStation_AccesLevel(1);
        groundStation.setGroundStation_Description("Ground Station Description");
        groundStation.setGroundStation_Email("jane.doe@example.org");
        groundStation.setGroundStation_Latitude(10.0d);
        groundStation.setGroundStation_Longitude(10.0d);
        groundStation.setGroundStation_Name("Ground Station Name");
        groundStation.setGroundStation_id(1L);
        groundStation.setUser(new User());

        User user = new User();
        user.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user.setEmail("jane.doe@example.org");
        user.setGroundStation(groundStation);
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setStatus(UserStatus.ACTIVE);
        user.setUser_id(1L);
        user.setUser_name("User name");

        GroundStation groundStation2 = new GroundStation();
        groundStation2.setGroundStation_AccesLevel(1);
        groundStation2.setGroundStation_Description("Ground Station Description");
        groundStation2.setGroundStation_Email("jane.doe@example.org");
        groundStation2.setGroundStation_Latitude(10.0d);
        groundStation2.setGroundStation_Longitude(10.0d);
        groundStation2.setGroundStation_Name("Ground Station Name");
        groundStation2.setGroundStation_id(1L);
        groundStation2.setUser(user);

        User user2 = new User();
        user2.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user2.setEmail("jane.doe@example.org");
        user2.setGroundStation(groundStation2);
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setStatus(UserStatus.ACTIVE);
        user2.setUser_id(1L);
        user2.setUser_name("User name");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Optional<User> actualUserByEmail = userService.getUserByEmail("jane.doe@example.org");

        // Assert
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
        assertSame(ofResult, actualUserByEmail);
    }

    /**
     * Test {@link UserService#getUserByEmail(String)}.
     * <ul>
     *   <li>Then throw {@link RuntimeException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getUserByEmail(String)}
     */
    @Test
    @DisplayName("Test getUserByEmail(String); then throw RuntimeException")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Optional UserService.getUserByEmail(String)"})
    void testGetUserByEmail_thenThrowRuntimeException() {
        // Arrange
        when(userRepository.findByEmail(Mockito.<String>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.getUserByEmail("jane.doe@example.org"));
        verify(userRepository).findByEmail(eq("jane.doe@example.org"));
    }

    /**
     * Test {@link UserService#deleteUser(Long)}.
     * <ul>
     *   <li>Given {@link UserRepository} {@link CrudRepository#deleteById(Object)} does nothing.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    @DisplayName("Test deleteUser(Long); given UserRepository deleteById(Object) does nothing")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void UserService.deleteUser(Long)"})
    void testDeleteUser_givenUserRepositoryDeleteByIdDoesNothing() {
        // Arrange
        doNothing().when(userRepository).deleteById(Mockito.<Long>any());

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository).deleteById(eq(1L));
    }

    /**
     * Test {@link UserService#deleteUser(Long)}.
     * <ul>
     *   <li>Then throw {@link RuntimeException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    @DisplayName("Test deleteUser(Long); then throw RuntimeException")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void UserService.deleteUser(Long)"})
    void testDeleteUser_thenThrowRuntimeException() {
        // Arrange
        doThrow(new RuntimeException("foo")).when(userRepository).deleteById(Mockito.<Long>any());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
        verify(userRepository).deleteById(eq(1L));
    }

    /**
     * Test {@link UserService#updateUser(Long, User)}.
     * <ul>
     *   <li>Given {@link UserRepository} {@link CrudRepository#save(Object)} return {@link User#User()}.</li>
     *   <li>Then return {@link User#User()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#updateUser(Long, User)}
     */
    @Test
    @DisplayName("Test updateUser(Long, User); given UserRepository save(Object) return User(); then return User()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"User UserService.updateUser(Long, User)"})
    void testUpdateUser_givenUserRepositorySaveReturnUser_thenReturnUser() {
        // Arrange
        GroundStation groundStation = new GroundStation();
        groundStation.setGroundStation_AccesLevel(1);
        groundStation.setGroundStation_Description("Ground Station Description");
        groundStation.setGroundStation_Email("jane.doe@example.org");
        groundStation.setGroundStation_Latitude(10.0d);
        groundStation.setGroundStation_Longitude(10.0d);
        groundStation.setGroundStation_Name("Ground Station Name");
        groundStation.setGroundStation_id(1L);
        groundStation.setUser(new User());

        User user = new User();
        user.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user.setEmail("jane.doe@example.org");
        user.setGroundStation(groundStation);
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setStatus(UserStatus.ACTIVE);
        user.setUser_id(1L);
        user.setUser_name("User name");

        GroundStation groundStation2 = new GroundStation();
        groundStation2.setGroundStation_AccesLevel(1);
        groundStation2.setGroundStation_Description("Ground Station Description");
        groundStation2.setGroundStation_Email("jane.doe@example.org");
        groundStation2.setGroundStation_Latitude(10.0d);
        groundStation2.setGroundStation_Longitude(10.0d);
        groundStation2.setGroundStation_Name("Ground Station Name");
        groundStation2.setGroundStation_id(1L);
        groundStation2.setUser(user);

        User user2 = new User();
        user2.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user2.setEmail("jane.doe@example.org");
        user2.setGroundStation(groundStation2);
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setStatus(UserStatus.ACTIVE);
        user2.setUser_id(1L);
        user2.setUser_name("User name");
        Optional<User> ofResult = Optional.of(user2);

        User user3 = new User();
        user3.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user3.setEmail("jane.doe@example.org");
        user3.setGroundStation(new GroundStation());
        user3.setId(1L);
        user3.setPassword("iloveyou");
        user3.setStatus(UserStatus.ACTIVE);
        user3.setUser_id(1L);
        user3.setUser_name("User name");

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(user3);

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(groundStation3);
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user4);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(groundStation4);
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user5);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        GroundStation groundStation5 = new GroundStation();
        groundStation5.setGroundStation_AccesLevel(1);
        groundStation5.setGroundStation_Description("Ground Station Description");
        groundStation5.setGroundStation_Email("jane.doe@example.org");
        groundStation5.setGroundStation_Latitude(10.0d);
        groundStation5.setGroundStation_Longitude(10.0d);
        groundStation5.setGroundStation_Name("Ground Station Name");
        groundStation5.setGroundStation_id(1L);
        groundStation5.setUser(new User());

        User user6 = new User();
        user6.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user6.setEmail("jane.doe@example.org");
        user6.setGroundStation(groundStation5);
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setStatus(UserStatus.ACTIVE);
        user6.setUser_id(1L);
        user6.setUser_name("User name");

        GroundStation groundStation6 = new GroundStation();
        groundStation6.setGroundStation_AccesLevel(1);
        groundStation6.setGroundStation_Description("Ground Station Description");
        groundStation6.setGroundStation_Email("jane.doe@example.org");
        groundStation6.setGroundStation_Latitude(10.0d);
        groundStation6.setGroundStation_Longitude(10.0d);
        groundStation6.setGroundStation_Name("Ground Station Name");
        groundStation6.setGroundStation_id(1L);
        groundStation6.setUser(user6);

        User updatedUser = new User();
        updatedUser.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        updatedUser.setEmail("jane.doe@example.org");
        updatedUser.setGroundStation(groundStation6);
        updatedUser.setId(1L);
        updatedUser.setPassword("iloveyou");
        updatedUser.setStatus(UserStatus.ACTIVE);
        updatedUser.setUser_id(1L);
        updatedUser.setUser_name("User name");

        // Act
        User actualUpdateUserResult = userService.updateUser(1L, updatedUser);

        // Assert
        verify(userRepository).findById(eq(1L));
        verify(userRepository).save(isA(User.class));
        assertSame(user5, actualUpdateUserResult);
    }

    /**
     * Test {@link UserService#updateUser(Long, User)}.
     * <ul>
     *   <li>Then throw {@link RuntimeException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#updateUser(Long, User)}
     */
    @Test
    @DisplayName("Test updateUser(Long, User); then throw RuntimeException")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"User UserService.updateUser(Long, User)"})
    void testUpdateUser_thenThrowRuntimeException() {
        // Arrange
        GroundStation groundStation = new GroundStation();
        groundStation.setGroundStation_AccesLevel(1);
        groundStation.setGroundStation_Description("Ground Station Description");
        groundStation.setGroundStation_Email("jane.doe@example.org");
        groundStation.setGroundStation_Latitude(10.0d);
        groundStation.setGroundStation_Longitude(10.0d);
        groundStation.setGroundStation_Name("Ground Station Name");
        groundStation.setGroundStation_id(1L);
        groundStation.setUser(new User());

        User user = new User();
        user.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user.setEmail("jane.doe@example.org");
        user.setGroundStation(groundStation);
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setStatus(UserStatus.ACTIVE);
        user.setUser_id(1L);
        user.setUser_name("User name");

        GroundStation groundStation2 = new GroundStation();
        groundStation2.setGroundStation_AccesLevel(1);
        groundStation2.setGroundStation_Description("Ground Station Description");
        groundStation2.setGroundStation_Email("jane.doe@example.org");
        groundStation2.setGroundStation_Latitude(10.0d);
        groundStation2.setGroundStation_Longitude(10.0d);
        groundStation2.setGroundStation_Name("Ground Station Name");
        groundStation2.setGroundStation_id(1L);
        groundStation2.setUser(user);

        User user2 = new User();
        user2.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user2.setEmail("jane.doe@example.org");
        user2.setGroundStation(groundStation2);
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setStatus(UserStatus.ACTIVE);
        user2.setUser_id(1L);
        user2.setUser_name("User name");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.save(Mockito.<User>any())).thenThrow(new RuntimeException("User not found"));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(new User());

        User user3 = new User();
        user3.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user3.setEmail("jane.doe@example.org");
        user3.setGroundStation(groundStation3);
        user3.setId(1L);
        user3.setPassword("iloveyou");
        user3.setStatus(UserStatus.ACTIVE);
        user3.setUser_id(1L);
        user3.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user3);

        User updatedUser = new User();
        updatedUser.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        updatedUser.setEmail("jane.doe@example.org");
        updatedUser.setGroundStation(groundStation4);
        updatedUser.setId(1L);
        updatedUser.setPassword("iloveyou");
        updatedUser.setStatus(UserStatus.ACTIVE);
        updatedUser.setUser_id(1L);
        updatedUser.setUser_name("User name");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, updatedUser));
        verify(userRepository).findById(eq(1L));
        verify(userRepository).save(isA(User.class));
    }

    /**
     * Test {@link UserService#getAllUsers()}.
     * <ul>
     *   <li>Given {@link UserMapper}.</li>
     *   <li>Then return Empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    @DisplayName("Test getAllUsers(); given UserMapper; then return Empty")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List UserService.getAllUsers()"})
    void testGetAllUsers_givenUserMapper_thenReturnEmpty() {
        // Arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<UserDto> actualAllUsers = userService.getAllUsers();

        // Assert
        verify(userRepository).findAll();
        assertTrue(actualAllUsers.isEmpty());
    }

    /**
     * Test {@link UserService#getAllUsers()}.
     * <ul>
     *   <li>Then throw {@link RuntimeException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    @DisplayName("Test getAllUsers(); then throw RuntimeException")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List UserService.getAllUsers()"})
    void testGetAllUsers_thenThrowRuntimeException() {
        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> (new UserService(userRepository, UserMapper.INSTANCE)).getAllUsers());
        verify(userRepository).findAll();
    }
}
