package com.example.jeeHamlaoui.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.model.enums.UserStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UserDiffblueTest {
    /**
     * Test getters and setters.
     * <p>
     * Methods under test:
     * <ul>
     *   <li>{@link User#User()}
     *   <li>{@link User#setCreated_at(Instant)}
     *   <li>{@link User#setEmail(String)}
     *   <li>{@link User#setGroundStation(GroundStation)}
     *   <li>{@link User#setId(Long)}
     *   <li>{@link User#setPassword(String)}
     *   <li>{@link User#setStatus(UserStatus)}
     *   <li>{@link User#setUser_id(Long)}
     *   <li>{@link User#setUser_name(String)}
     *   <li>{@link User#getCreated_at()}
     *   <li>{@link User#getEmail()}
     *   <li>{@link User#getGroundStation()}
     *   <li>{@link User#getId()}
     *   <li>{@link User#getPassword()}
     *   <li>{@link User#getStatus()}
     *   <li>{@link User#getUser_id()}
     *   <li>{@link User#getUser_name()}
     * </ul>
     */
    @Test
    @DisplayName("Test getters and setters")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void User.<init>()",
            "void User.<init>(Long, String, String, String, UserStatus, GroundStation, Instant)",
            "Instant User.getCreated_at()", "String User.getEmail()", "GroundStation User.getGroundStation()",
            "Long User.getId()", "String User.getPassword()", "UserStatus User.getStatus()", "Long User.getUser_id()",
            "String User.getUser_name()", "void User.setCreated_at(Instant)", "void User.setEmail(String)",
            "void User.setGroundStation(GroundStation)", "void User.setId(Long)", "void User.setPassword(String)",
            "void User.setStatus(UserStatus)", "void User.setUser_id(Long)", "void User.setUser_name(String)"})
    void testGettersAndSetters() {
        // Arrange and Act
        User actualUser = new User();
        actualUser.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualUser.setEmail("jane.doe@example.org");
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
        actualUser.setGroundStation(groundStation2);
        actualUser.setId(1L);
        actualUser.setPassword("iloveyou");
        actualUser.setStatus(UserStatus.ACTIVE);
        actualUser.setUser_id(1L);
        actualUser.setUser_name("User name");
        Instant actualCreated_at = actualUser.getCreated_at();
        String actualEmail = actualUser.getEmail();
        GroundStation actualGroundStation = actualUser.getGroundStation();
        Long actualId = actualUser.getId();
        String actualPassword = actualUser.getPassword();
        UserStatus actualStatus = actualUser.getStatus();
        Long actualUser_id = actualUser.getUser_id();

        // Assert
        assertEquals("User name", actualUser.getUser_name());
        assertEquals("iloveyou", actualPassword);
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals(1L, actualId.longValue());
        assertEquals(1L, actualUser_id.longValue());
        assertEquals(UserStatus.ACTIVE, actualStatus);
        assertSame(groundStation2, actualGroundStation);
        assertSame(actualCreated_at.EPOCH, actualCreated_at);
    }

    /**
     * Test getters and setters.
     * <ul>
     *   <li>Then return {@link User#User()}.</li>
     * </ul>
     * <p>
     * Methods under test:
     * <ul>
     *   <li>{@link User#User(Long, String, String, String, UserStatus, GroundStation, Instant)}
     *   <li>{@link User#User()}
     *   <li>{@link User#setCreated_at(Instant)}
     *   <li>{@link User#setEmail(String)}
     *   <li>{@link User#setGroundStation(GroundStation)}
     *   <li>{@link User#setId(Long)}
     *   <li>{@link User#setPassword(String)}
     *   <li>{@link User#setStatus(UserStatus)}
     *   <li>{@link User#setUser_id(Long)}
     *   <li>{@link User#setUser_name(String)}
     *   <li>{@link User#getCreated_at()}
     *   <li>{@link User#getEmail()}
     *   <li>{@link User#getGroundStation()}
     *   <li>{@link User#getId()}
     *   <li>{@link User#getPassword()}
     *   <li>{@link User#getStatus()}
     *   <li>{@link User#getUser_id()}
     *   <li>{@link User#getUser_name()}
     * </ul>
     */
    @Test
    @DisplayName("Test getters and setters; then return User()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void User.<init>()",
            "void User.<init>(Long, String, String, String, UserStatus, GroundStation, Instant)",
            "Instant User.getCreated_at()", "String User.getEmail()", "GroundStation User.getGroundStation()",
            "Long User.getId()", "String User.getPassword()", "UserStatus User.getStatus()", "Long User.getUser_id()",
            "String User.getUser_name()", "void User.setCreated_at(Instant)", "void User.setEmail(String)",
            "void User.setGroundStation(GroundStation)", "void User.setId(Long)", "void User.setPassword(String)",
            "void User.setStatus(UserStatus)", "void User.setUser_id(Long)", "void User.setUser_name(String)"})
    void testGettersAndSetters_thenReturnUser() {
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

        // Act
        User actualUser = new User(1L, "User name", "iloveyou", "jane.doe@example.org", UserStatus.ACTIVE, groundStation2,
                LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualUser.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        actualUser.setEmail("jane.doe@example.org");
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
        actualUser.setGroundStation(groundStation4);
        actualUser.setId(1L);
        actualUser.setPassword("iloveyou");
        actualUser.setStatus(UserStatus.ACTIVE);
        actualUser.setUser_id(1L);
        actualUser.setUser_name("User name");
        Instant actualCreated_at = actualUser.getCreated_at();
        String actualEmail = actualUser.getEmail();
        GroundStation actualGroundStation = actualUser.getGroundStation();
        Long actualId = actualUser.getId();
        String actualPassword = actualUser.getPassword();
        UserStatus actualStatus = actualUser.getStatus();
        Long actualUser_id = actualUser.getUser_id();

        // Assert
        assertEquals("User name", actualUser.getUser_name());
        assertEquals("iloveyou", actualPassword);
        assertEquals("jane.doe@example.org", actualEmail);
        assertEquals(1L, actualId.longValue());
        assertEquals(1L, actualUser_id.longValue());
        assertEquals(UserStatus.ACTIVE, actualStatus);
        assertEquals(user2, actualUser);
        assertSame(groundStation4, actualGroundStation);
        assertSame(actualCreated_at.EPOCH, actualCreated_at);
    }

    /**
     * Test {@link User#equals(Object)}, and {@link User#hashCode()}.
     * <ul>
     *   <li>When other is equal.</li>
     *   <li>Then return equal.</li>
     * </ul>
     * <p>
     * Methods under test:
     * <ul>
     *   <li>{@link User#equals(Object)}
     *   <li>{@link User#hashCode()}
     * </ul>
     */
    @Test
    @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"boolean User.equals(Object)", "int User.hashCode()"})
    void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
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

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(new GroundStation());
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(user4);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(groundStation3);
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user5);

        User user6 = new User();
        user6.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user6.setEmail("jane.doe@example.org");
        user6.setGroundStation(groundStation4);
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setStatus(UserStatus.ACTIVE);
        user6.setUser_id(1L);
        user6.setUser_name("User name");

        // Act and Assert
        assertEquals(user3, user6);
        int expectedHashCodeResult = user3.hashCode();
        assertEquals(expectedHashCodeResult, user6.hashCode());
    }

    /**
     * Test {@link User#equals(Object)}, and {@link User#hashCode()}.
     * <ul>
     *   <li>When other is same.</li>
     *   <li>Then return equal.</li>
     * </ul>
     * <p>
     * Methods under test:
     * <ul>
     *   <li>{@link User#equals(Object)}
     *   <li>{@link User#hashCode()}
     * </ul>
     */
    @Test
    @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"boolean User.equals(Object)", "int User.hashCode()"})
    void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
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

        // Act and Assert
        assertEquals(user3, user3);
        int expectedHashCodeResult = user3.hashCode();
        assertEquals(expectedHashCodeResult, user3.hashCode());
    }

    /**
     * Test {@link User#equals(Object)}.
     * <ul>
     *   <li>When other is different.</li>
     *   <li>Then return not equal.</li>
     * </ul>
     * <p>
     * Method under test: {@link User#equals(Object)}
     */
    @Test
    @DisplayName("Test equals(Object); when other is different; then return not equal")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"boolean User.equals(Object)", "int User.hashCode()"})
    void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
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
        user3.setStatus(UserStatus.INACTIVE);
        user3.setUser_id(1L);
        user3.setUser_name("User name");

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(new GroundStation());
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(user4);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(groundStation3);
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user5);

        User user6 = new User();
        user6.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user6.setEmail("jane.doe@example.org");
        user6.setGroundStation(groundStation4);
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setStatus(UserStatus.ACTIVE);
        user6.setUser_id(1L);
        user6.setUser_name("User name");

        // Act and Assert
        assertNotEquals(user3, user6);
    }

    /**
     * Test {@link User#equals(Object)}.
     * <ul>
     *   <li>When other is different.</li>
     *   <li>Then return not equal.</li>
     * </ul>
     * <p>
     * Method under test: {@link User#equals(Object)}
     */
    @Test
    @DisplayName("Test equals(Object); when other is different; then return not equal")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"boolean User.equals(Object)", "int User.hashCode()"})
    void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
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
        user3.setUser_name("42");

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(new GroundStation());
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(user4);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(groundStation3);
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user5);

        User user6 = new User();
        user6.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user6.setEmail("jane.doe@example.org");
        user6.setGroundStation(groundStation4);
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setStatus(UserStatus.ACTIVE);
        user6.setUser_id(1L);
        user6.setUser_name("User name");

        // Act and Assert
        assertNotEquals(user3, user6);
    }

    /**
     * Test {@link User#equals(Object)}.
     * <ul>
     *   <li>When other is different.</li>
     *   <li>Then return not equal.</li>
     * </ul>
     * <p>
     * Method under test: {@link User#equals(Object)}
     */
    @Test
    @DisplayName("Test equals(Object); when other is different; then return not equal")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"boolean User.equals(Object)", "int User.hashCode()"})
    void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
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
        user3.setPassword("Password");
        user3.setStatus(UserStatus.ACTIVE);
        user3.setUser_id(1L);
        user3.setUser_name("User name");

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(new GroundStation());
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(user4);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(groundStation3);
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user5);

        User user6 = new User();
        user6.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user6.setEmail("jane.doe@example.org");
        user6.setGroundStation(groundStation4);
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setStatus(UserStatus.ACTIVE);
        user6.setUser_id(1L);
        user6.setUser_name("User name");

        // Act and Assert
        assertNotEquals(user3, user6);
    }

    /**
     * Test {@link User#equals(Object)}.
     * <ul>
     *   <li>When other is different.</li>
     *   <li>Then return not equal.</li>
     * </ul>
     * <p>
     * Method under test: {@link User#equals(Object)}
     */
    @Test
    @DisplayName("Test equals(Object); when other is different; then return not equal")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"boolean User.equals(Object)", "int User.hashCode()"})
    void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
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
        user3.setUser_id(2L);
        user3.setUser_name("User name");

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(new GroundStation());
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(user4);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(groundStation3);
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user5);

        User user6 = new User();
        user6.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user6.setEmail("jane.doe@example.org");
        user6.setGroundStation(groundStation4);
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setStatus(UserStatus.ACTIVE);
        user6.setUser_id(1L);
        user6.setUser_name("User name");

        // Act and Assert
        assertNotEquals(user3, user6);
    }

    /**
     * Test {@link User#equals(Object)}.
     * <ul>
     *   <li>When other is different.</li>
     *   <li>Then return not equal.</li>
     * </ul>
     * <p>
     * Method under test: {@link User#equals(Object)}
     */
    @Test
    @DisplayName("Test equals(Object); when other is different; then return not equal")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"boolean User.equals(Object)", "int User.hashCode()"})
    void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
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
        user3.setEmail("john.smith@example.org");
        user3.setGroundStation(groundStation2);
        user3.setId(1L);
        user3.setPassword("iloveyou");
        user3.setStatus(UserStatus.ACTIVE);
        user3.setUser_id(1L);
        user3.setUser_name("User name");

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(new GroundStation());
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(user4);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(groundStation3);
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user5);

        User user6 = new User();
        user6.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user6.setEmail("jane.doe@example.org");
        user6.setGroundStation(groundStation4);
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setStatus(UserStatus.ACTIVE);
        user6.setUser_id(1L);
        user6.setUser_name("User name");

        // Act and Assert
        assertNotEquals(user3, user6);
    }

    /**
     * Test {@link User#equals(Object)}.
     * <ul>
     *   <li>When other is different.</li>
     *   <li>Then return not equal.</li>
     * </ul>
     * <p>
     * Method under test: {@link User#equals(Object)}
     */
    @Test
    @DisplayName("Test equals(Object); when other is different; then return not equal")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"boolean User.equals(Object)", "int User.hashCode()"})
    void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
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
        user3.setCreated_at(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user3.setEmail("jane.doe@example.org");
        user3.setGroundStation(groundStation2);
        user3.setId(1L);
        user3.setPassword("iloveyou");
        user3.setStatus(UserStatus.ACTIVE);
        user3.setUser_id(1L);
        user3.setUser_name("User name");

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(new GroundStation());
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(user4);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(groundStation3);
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user5);

        User user6 = new User();
        user6.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user6.setEmail("jane.doe@example.org");
        user6.setGroundStation(groundStation4);
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setStatus(UserStatus.ACTIVE);
        user6.setUser_id(1L);
        user6.setUser_name("User name");

        // Act and Assert
        assertNotEquals(user3, user6);
    }

    /**
     * Test {@link User#equals(Object)}.
     * <ul>
     *   <li>When other is {@code null}.</li>
     *   <li>Then return not equal.</li>
     * </ul>
     * <p>
     * Method under test: {@link User#equals(Object)}
     */
    @Test
    @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"boolean User.equals(Object)", "int User.hashCode()"})
    void testEquals_whenOtherIsNull_thenReturnNotEqual() {
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

        // Act and Assert
        assertNotEquals(user3, null);
    }

    /**
     * Test {@link User#equals(Object)}.
     * <ul>
     *   <li>When other is wrong type.</li>
     *   <li>Then return not equal.</li>
     * </ul>
     * <p>
     * Method under test: {@link User#equals(Object)}
     */
    @Test
    @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"boolean User.equals(Object)", "int User.hashCode()"})
    void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
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

        // Act and Assert
        assertNotEquals(user3, "Different type to User");
    }
}
