package com.example.jeeHamlaoui.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.enums.UserStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UserDtoDiffblueTest {
    /**
     * Test getters and setters.
     * <p>
     * Methods under test:
     * <ul>
     *   <li>{@link UserDto#UserDto(Long, Instant, GroundStation, UserStatus, String, String)}
     *   <li>{@link UserDto#setCreated_at(Instant)}
     *   <li>{@link UserDto#setEmail(String)}
     *   <li>{@link UserDto#setGroundStation(GroundStation)}
     *   <li>{@link UserDto#setStatus(UserStatus)}
     *   <li>{@link UserDto#setUser_id(Long)}
     *   <li>{@link UserDto#setUser_name(String)}
     *   <li>{@link UserDto#getCreated_at()}
     *   <li>{@link UserDto#getEmail()}
     *   <li>{@link UserDto#getGroundStation()}
     *   <li>{@link UserDto#getStatus()}
     *   <li>{@link UserDto#getUser_id()}
     *   <li>{@link UserDto#getUser_name()}
     * </ul>
     */
    @Test
    @DisplayName("Test getters and setters")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void UserDto.<init>()",
            "void UserDto.<init>(Long, Instant, GroundStation, UserStatus, String, String)",
            "Instant UserDto.getCreated_at()", "String UserDto.getEmail()", "GroundStation UserDto.getGroundStation()",
            "UserStatus UserDto.getStatus()", "Long UserDto.getUser_id()", "String UserDto.getUser_name()",
            "void UserDto.setCreated_at(Instant)", "void UserDto.setEmail(String)",
            "void UserDto.setGroundStation(GroundStation)", "void UserDto.setStatus(UserStatus)",
            "void UserDto.setUser_id(Long)", "void UserDto.setUser_name(String)"})
    void testGettersAndSetters() {
        // Arrange
        Instant created_at = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

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

        // Act
        UserDto actualUserDto = new UserDto(1L, created_at, groundStation2, UserStatus.ACTIVE, "jane.doe@example.org",
                "User name");
        actualUserDto.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualUserDto.setEmail("jane.doe@example.org");
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
        actualUserDto.setGroundStation(groundStation4);
        actualUserDto.setStatus(UserStatus.ACTIVE);
        actualUserDto.setUser_id(1L);
        actualUserDto.setUser_name("User name");
        Instant actualCreated_at = actualUserDto.getCreated_at();
        String actualEmail = actualUserDto.getEmail();
        GroundStation actualGroundStation = actualUserDto.getGroundStation();
        UserStatus actualStatus = actualUserDto.getStatus();
        Long actualUser_id = actualUserDto.getUser_id();

        // Assert
        assertEquals("User name", actualUserDto.getUser_name());
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals(1L, actualUser_id.longValue());
        assertEquals(UserStatus.ACTIVE, actualStatus);
        assertSame(groundStation4, actualGroundStation);
        assertSame(actualCreated_at.EPOCH, actualCreated_at);
    }

    /**
     * Test getters and setters.
     * <p>
     * Methods under test:
     * <ul>
     *   <li>{@link UserDto#UserDto()}
     *   <li>{@link UserDto#setCreated_at(Instant)}
     *   <li>{@link UserDto#setEmail(String)}
     *   <li>{@link UserDto#setGroundStation(GroundStation)}
     *   <li>{@link UserDto#setStatus(UserStatus)}
     *   <li>{@link UserDto#setUser_id(Long)}
     *   <li>{@link UserDto#setUser_name(String)}
     *   <li>{@link UserDto#getCreated_at()}
     *   <li>{@link UserDto#getEmail()}
     *   <li>{@link UserDto#getGroundStation()}
     *   <li>{@link UserDto#getStatus()}
     *   <li>{@link UserDto#getUser_id()}
     *   <li>{@link UserDto#getUser_name()}
     * </ul>
     */
    @Test
    @DisplayName("Test getters and setters")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void UserDto.<init>()",
            "void UserDto.<init>(Long, Instant, GroundStation, UserStatus, String, String)",
            "Instant UserDto.getCreated_at()", "String UserDto.getEmail()", "GroundStation UserDto.getGroundStation()",
            "UserStatus UserDto.getStatus()", "Long UserDto.getUser_id()", "String UserDto.getUser_name()",
            "void UserDto.setCreated_at(Instant)", "void UserDto.setEmail(String)",
            "void UserDto.setGroundStation(GroundStation)", "void UserDto.setStatus(UserStatus)",
            "void UserDto.setUser_id(Long)", "void UserDto.setUser_name(String)"})
    void testGettersAndSetters2() {
        // Arrange and Act
        UserDto actualUserDto = new UserDto();
        actualUserDto.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualUserDto.setEmail("jane.doe@example.org");
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
        actualUserDto.setGroundStation(groundStation2);
        actualUserDto.setStatus(UserStatus.ACTIVE);
        actualUserDto.setUser_id(1L);
        actualUserDto.setUser_name("User name");
        Instant actualCreated_at = actualUserDto.getCreated_at();
        String actualEmail = actualUserDto.getEmail();
        GroundStation actualGroundStation = actualUserDto.getGroundStation();
        UserStatus actualStatus = actualUserDto.getStatus();
        Long actualUser_id = actualUserDto.getUser_id();

        // Assert
        assertEquals("User name", actualUserDto.getUser_name());
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals(1L, actualUser_id.longValue());
        assertEquals(UserStatus.ACTIVE, actualStatus);
        assertSame(groundStation2, actualGroundStation);
        assertSame(actualCreated_at.EPOCH, actualCreated_at);
    }
}
