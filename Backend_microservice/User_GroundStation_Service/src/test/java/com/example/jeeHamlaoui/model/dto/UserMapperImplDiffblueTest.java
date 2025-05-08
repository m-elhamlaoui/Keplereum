package com.example.jeeHamlaoui.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserMapperImpl.class})
@ExtendWith(SpringExtension.class)
class UserMapperImplDiffblueTest {
    @Autowired
    private UserMapperImpl userMapperImpl;

    /**
     * Test {@link UserMapperImpl#toDto(User)}.
     * <ul>
     *   <li>Then return GroundStation User GroundStation User is {@link User#User()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserMapperImpl#toDto(User)}
     */
    @Test
    @DisplayName("Test toDto(User); then return GroundStation User GroundStation User is User()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"UserDto UserMapperImpl.toDto(User)"})
    void testToDto_thenReturnGroundStationUserGroundStationUserIsUser() {
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

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(new User());

        User user2 = new User();
        user2.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user2.setEmail("jane.doe@example.org");
        user2.setGroundStation(groundStation3);
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setStatus(UserStatus.ACTIVE);
        user2.setUser_id(1L);
        user2.setUser_name("User name");

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(user2);

        User user3 = new User();
        user3.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user3.setEmail("jane.doe@example.org");
        user3.setGroundStation(groundStation4);
        user3.setId(1L);
        user3.setPassword("iloveyou");
        user3.setStatus(UserStatus.ACTIVE);
        user3.setUser_id(1L);
        user3.setUser_name("User name");

        GroundStation groundStation5 = new GroundStation();
        groundStation5.setGroundStation_AccesLevel(1);
        groundStation5.setGroundStation_Description("Ground Station Description");
        groundStation5.setGroundStation_Email("jane.doe@example.org");
        groundStation5.setGroundStation_Latitude(10.0d);
        groundStation5.setGroundStation_Longitude(10.0d);
        groundStation5.setGroundStation_Name("Ground Station Name");
        groundStation5.setGroundStation_id(1L);
        groundStation5.setUser(user3);
        User user4 = mock(User.class);
        when(user4.getGroundStation()).thenReturn(groundStation5);
        when(user4.getStatus()).thenReturn(UserStatus.ACTIVE);
        when(user4.getUser_id()).thenReturn(1L);
        when(user4.getEmail()).thenReturn("jane.doe@example.org");
        when(user4.getUser_name()).thenReturn("User name");
        when(user4.getCreated_at()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        doNothing().when(user4).setCreated_at(Mockito.<Instant>any());
        doNothing().when(user4).setEmail(Mockito.<String>any());
        doNothing().when(user4).setGroundStation(Mockito.<GroundStation>any());
        doNothing().when(user4).setId(Mockito.<Long>any());
        doNothing().when(user4).setPassword(Mockito.<String>any());
        doNothing().when(user4).setStatus(Mockito.<UserStatus>any());
        doNothing().when(user4).setUser_id(Mockito.<Long>any());
        doNothing().when(user4).setUser_name(Mockito.<String>any());
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(groundStation2);
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        // Act
        UserDto actualToDtoResult = userMapperImpl.toDto(user4);

        // Assert
        verify(user4).getCreated_at();
        verify(user4).getEmail();
        verify(user4).getGroundStation();
        verify(user4).getStatus();
        verify(user4).getUser_id();
        verify(user4).getUser_name();
        verify(user4).setCreated_at(isA(Instant.class));
        verify(user4).setEmail(eq("jane.doe@example.org"));
        verify(user4).setGroundStation(isA(GroundStation.class));
        verify(user4).setId(eq(1L));
        verify(user4).setPassword(eq("iloveyou"));
        verify(user4).setStatus(eq(UserStatus.ACTIVE));
        verify(user4).setUser_id(eq(1L));
        verify(user4).setUser_name(eq("User name"));
        assertEquals(user3, actualToDtoResult.getGroundStation().getUser().getGroundStation().getUser());
    }

    /**
     * Test {@link UserMapperImpl#toDto(User)}.
     * <ul>
     *   <li>Then return GroundStation User is {@link User#User()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserMapperImpl#toDto(User)}
     */
    @Test
    @DisplayName("Test toDto(User); then return GroundStation User is User()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"UserDto UserMapperImpl.toDto(User)"})
    void testToDto_thenReturnGroundStationUserIsUser() {
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

        // Act and Assert
        assertEquals(user2, userMapperImpl.toDto(user2).getGroundStation().getUser());
    }

    /**
     * Test {@link UserMapperImpl#toDto(User)}.
     * <ul>
     *   <li>When {@code null}.</li>
     *   <li>Then return {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserMapperImpl#toDto(User)}
     */
    @Test
    @DisplayName("Test toDto(User); when 'null'; then return 'null'")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"UserDto UserMapperImpl.toDto(User)"})
    void testToDto_whenNull_thenReturnNull() {
        // Arrange, Act and Assert
        assertNull(userMapperImpl.toDto(null));
    }

    /**
     * Test {@link UserMapperImpl#toEntity(UserDto)}.
     * <ul>
     *   <li>When {@code null}.</li>
     *   <li>Then return {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserMapperImpl#toEntity(UserDto)}
     */
    @Test
    @DisplayName("Test toEntity(UserDto); when 'null'; then return 'null'")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"User UserMapperImpl.toEntity(UserDto)"})
    void testToEntity_whenNull_thenReturnNull() {
        // Arrange, Act and Assert
        assertNull(userMapperImpl.toEntity(null));
    }

    /**
     * Test {@link UserMapperImpl#toEntity(UserDto)}.
     * <ul>
     *   <li>When {@link UserDto#UserDto()}.</li>
     *   <li>Then return GroundStation is {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserMapperImpl#toEntity(UserDto)}
     */
    @Test
    @DisplayName("Test toEntity(UserDto); when UserDto(); then return GroundStation is 'null'")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"User UserMapperImpl.toEntity(UserDto)"})
    void testToEntity_whenUserDto_thenReturnGroundStationIsNull() {
        // Arrange and Act
        User actualToEntityResult = userMapperImpl.toEntity(new UserDto());

        // Assert
        assertNull(actualToEntityResult.getGroundStation());
        assertNull(actualToEntityResult.getStatus());
        assertNull(actualToEntityResult.getId());
        assertNull(actualToEntityResult.getUser_id());
        assertNull(actualToEntityResult.getEmail());
        assertNull(actualToEntityResult.getPassword());
        assertNull(actualToEntityResult.getUser_name());
        assertNull(actualToEntityResult.getCreated_at());
    }
}
