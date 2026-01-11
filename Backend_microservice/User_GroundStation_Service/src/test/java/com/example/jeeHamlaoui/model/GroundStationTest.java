package com.example.jeeHamlaoui.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.model.enums.UserStatus;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GroundStationTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GroundStation#GroundStation()}
   *   <li>{@link GroundStation#setGroundStation_AccesLevel(Integer)}
   *   <li>{@link GroundStation#setGroundStation_Description(String)}
   *   <li>{@link GroundStation#setGroundStation_Email(String)}
   *   <li>{@link GroundStation#setGroundStation_Latitude(Double)}
   *   <li>{@link GroundStation#setGroundStation_Longitude(Double)}
   *   <li>{@link GroundStation#setGroundStation_Name(String)}
   *   <li>{@link GroundStation#setGroundStation_id(Long)}
   *   <li>{@link GroundStation#setUser(User)}
   *   <li>{@link GroundStation#getGroundStation_AccesLevel()}
   *   <li>{@link GroundStation#getGroundStation_Description()}
   *   <li>{@link GroundStation#getGroundStation_Email()}
   *   <li>{@link GroundStation#getGroundStation_Latitude()}
   *   <li>{@link GroundStation#getGroundStation_Longitude()}
   *   <li>{@link GroundStation#getGroundStation_Name()}
   *   <li>{@link GroundStation#getGroundStation_id()}
   *   <li>{@link GroundStation#getUser()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GroundStation.<init>()",
      "void GroundStation.<init>(Long, String, String, Integer, Double, Double, String, User)",
      "Integer GroundStation.getGroundStation_AccesLevel()", "String GroundStation.getGroundStation_Description()",
      "String GroundStation.getGroundStation_Email()", "Double GroundStation.getGroundStation_Latitude()",
      "Double GroundStation.getGroundStation_Longitude()", "String GroundStation.getGroundStation_Name()",
      "Long GroundStation.getGroundStation_id()", "User GroundStation.getUser()",
      "void GroundStation.setGroundStation_AccesLevel(Integer)",
      "void GroundStation.setGroundStation_Description(String)", "void GroundStation.setGroundStation_Email(String)",
      "void GroundStation.setGroundStation_Latitude(Double)", "void GroundStation.setGroundStation_Longitude(Double)",
      "void GroundStation.setGroundStation_Name(String)", "void GroundStation.setGroundStation_id(Long)",
      "void GroundStation.setUser(User)"})
  void testGettersAndSetters() {
    // Arrange and Act
    GroundStation actualGroundStation = new GroundStation();
    actualGroundStation.setGroundStation_AccesLevel(1);
    actualGroundStation.setGroundStation_Description("Ground Station Description");
    actualGroundStation.setGroundStation_Email("jane.doe@example.org");
    actualGroundStation.setGroundStation_Latitude(10.0d);
    actualGroundStation.setGroundStation_Longitude(10.0d);
    actualGroundStation.setGroundStation_Name("Ground Station Name");
    actualGroundStation.setGroundStation_id(1L);
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
    user.setUserId(1L);
    user.setPassword("iloveyou");
    user.setStatus(UserStatus.ACTIVE);
    user.setUserId(1L);
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
    user2.setUserId(1L);
    user2.setPassword("iloveyou");
    user2.setStatus(UserStatus.ACTIVE);
    user2.setUserId(1L);
    user2.setUser_name("User name");
    actualGroundStation.setUser(user2);
    Integer actualGroundStation_AccesLevel = actualGroundStation.getGroundStation_AccesLevel();
    String actualGroundStation_Description = actualGroundStation.getGroundStation_Description();
    String actualGroundStation_Email = actualGroundStation.getGroundStation_Email();
    Double actualGroundStation_Latitude = actualGroundStation.getGroundStation_Latitude();
    Double actualGroundStation_Longitude = actualGroundStation.getGroundStation_Longitude();
    String actualGroundStation_Name = actualGroundStation.getGroundStation_Name();
    Long actualGroundStation_id = actualGroundStation.getGroundStation_id();
    User actualUser = actualGroundStation.getUser();

    // Assert
    assertEquals("Ground Station Description", actualGroundStation_Description);
    assertEquals("Ground Station Name", actualGroundStation_Name);
    assertEquals("jane.doe@example.org", actualGroundStation_Email);
    assertEquals(1, actualGroundStation_AccesLevel.intValue());
    assertEquals(10.0d, actualGroundStation_Latitude.doubleValue());
    assertEquals(10.0d, actualGroundStation_Longitude.doubleValue());
    assertEquals(1L, actualGroundStation_id.longValue());
    assertSame(user2, actualUser);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>Given {@link GroundStation#GroundStation()} GroundStation_AccesLevel is one.</li>
   *   <li>When one.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GroundStation#GroundStation(Long, String, String, Integer, Double, Double, String, User)}
   *   <li>{@link GroundStation#GroundStation()}
   *   <li>{@link GroundStation#setGroundStation_AccesLevel(Integer)}
   *   <li>{@link GroundStation#setGroundStation_Description(String)}
   *   <li>{@link GroundStation#setGroundStation_Email(String)}
   *   <li>{@link GroundStation#setGroundStation_Latitude(Double)}
   *   <li>{@link GroundStation#setGroundStation_Longitude(Double)}
   *   <li>{@link GroundStation#setGroundStation_Name(String)}
   *   <li>{@link GroundStation#setGroundStation_id(Long)}
   *   <li>{@link GroundStation#setUser(User)}
   *   <li>{@link GroundStation#getGroundStation_AccesLevel()}
   *   <li>{@link GroundStation#getGroundStation_Description()}
   *   <li>{@link GroundStation#getGroundStation_Email()}
   *   <li>{@link GroundStation#getGroundStation_Latitude()}
   *   <li>{@link GroundStation#getGroundStation_Longitude()}
   *   <li>{@link GroundStation#getGroundStation_Name()}
   *   <li>{@link GroundStation#getGroundStation_id()}
   *   <li>{@link GroundStation#getUser()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; given GroundStation() GroundStation_AccesLevel is one; when one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GroundStation.<init>()",
      "void GroundStation.<init>(Long, String, String, Integer, Double, Double, String, User)",
      "Integer GroundStation.getGroundStation_AccesLevel()", "String GroundStation.getGroundStation_Description()",
      "String GroundStation.getGroundStation_Email()", "Double GroundStation.getGroundStation_Latitude()",
      "Double GroundStation.getGroundStation_Longitude()", "String GroundStation.getGroundStation_Name()",
      "Long GroundStation.getGroundStation_id()", "User GroundStation.getUser()",
      "void GroundStation.setGroundStation_AccesLevel(Integer)",
      "void GroundStation.setGroundStation_Description(String)", "void GroundStation.setGroundStation_Email(String)",
      "void GroundStation.setGroundStation_Latitude(Double)", "void GroundStation.setGroundStation_Longitude(Double)",
      "void GroundStation.setGroundStation_Name(String)", "void GroundStation.setGroundStation_id(Long)",
      "void GroundStation.setUser(User)"})
  void testGettersAndSetters_givenGroundStationGroundStation_AccesLevelIsOne_whenOne() {
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
    user.setUserId(1L);
    user.setPassword("iloveyou");
    user.setStatus(UserStatus.ACTIVE);
    user.setUserId(1L);
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
    user2.setUserId(1L);
    user2.setPassword("iloveyou");
    user2.setStatus(UserStatus.ACTIVE);
    user2.setUserId(1L);
    user2.setUser_name("User name");

    // Act
    GroundStation actualGroundStation = new GroundStation(1L, "Ground Station Name", "jane.doe@example.org", 1, 10.0d,
        10.0d, "Ground Station Description", user2);
    actualGroundStation.setGroundStation_AccesLevel(1);
    actualGroundStation.setGroundStation_Description("Ground Station Description");
    actualGroundStation.setGroundStation_Email("jane.doe@example.org");
    actualGroundStation.setGroundStation_Latitude(10.0d);
    actualGroundStation.setGroundStation_Longitude(10.0d);
    actualGroundStation.setGroundStation_Name("Ground Station Name");
    actualGroundStation.setGroundStation_id(1L);
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
    user3.setUserId(1L);
    user3.setPassword("iloveyou");
    user3.setStatus(UserStatus.ACTIVE);
    user3.setUserId(1L);
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
    User user4 = new User();
    user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    user4.setEmail("jane.doe@example.org");
    user4.setGroundStation(groundStation4);
    user4.setUserId(1L);
    user4.setPassword("iloveyou");
    user4.setStatus(UserStatus.ACTIVE);
    user4.setUserId(1L);
    user4.setUser_name("User name");
    actualGroundStation.setUser(user4);
    Integer actualGroundStation_AccesLevel = actualGroundStation.getGroundStation_AccesLevel();
    String actualGroundStation_Description = actualGroundStation.getGroundStation_Description();
    String actualGroundStation_Email = actualGroundStation.getGroundStation_Email();
    Double actualGroundStation_Latitude = actualGroundStation.getGroundStation_Latitude();
    Double actualGroundStation_Longitude = actualGroundStation.getGroundStation_Longitude();
    String actualGroundStation_Name = actualGroundStation.getGroundStation_Name();
    Long actualGroundStation_id = actualGroundStation.getGroundStation_id();
    User actualUser = actualGroundStation.getUser();

    // Assert
    assertEquals("Ground Station Description", actualGroundStation_Description);
    assertEquals("Ground Station Name", actualGroundStation_Name);
    assertEquals("jane.doe@example.org", actualGroundStation_Email);
    assertEquals(1, actualGroundStation_AccesLevel.intValue());
    assertEquals(10.0d, actualGroundStation_Latitude.doubleValue());
    assertEquals(10.0d, actualGroundStation_Longitude.doubleValue());
    assertEquals(1L, actualGroundStation_id.longValue());
    assertSame(user4, actualUser);
  }
}
