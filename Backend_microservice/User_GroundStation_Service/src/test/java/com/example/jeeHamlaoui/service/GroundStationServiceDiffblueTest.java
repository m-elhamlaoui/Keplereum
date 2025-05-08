package com.example.jeeHamlaoui.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.client.SatelliteClient;
import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.enums.UserStatus;
import com.example.jeeHamlaoui.repository.GroundStationRepository;

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

@ContextConfiguration(classes = {GroundStationService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GroundStationServiceDiffblueTest {
    @MockitoBean
    private GroundStationRepository groundStationRepository;

    @Autowired
    private GroundStationService groundStationService;

    @MockitoBean
    private SatelliteClient satelliteClient;

    /**
     * Test {@link GroundStationService#findAllGroundStations()}.
     * <ul>
     *   <li>Then return Empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link GroundStationService#findAllGroundStations()}
     */
    @Test
    @DisplayName("Test findAllGroundStations(); then return Empty")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List GroundStationService.findAllGroundStations()"})
    void testFindAllGroundStations_thenReturnEmpty() {
        // Arrange
        when(groundStationRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<GroundStation> actualFindAllGroundStationsResult = groundStationService.findAllGroundStations();

        // Assert
        verify(groundStationRepository).findAll();
        assertTrue(actualFindAllGroundStationsResult.isEmpty());
    }

    /**
     * Test {@link GroundStationService#findAllGroundStations()}.
     * <ul>
     *   <li>Then throw {@link RuntimeException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GroundStationService#findAllGroundStations()}
     */
    @Test
    @DisplayName("Test findAllGroundStations(); then throw RuntimeException")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List GroundStationService.findAllGroundStations()"})
    void testFindAllGroundStations_thenThrowRuntimeException() {
        // Arrange
        when(groundStationRepository.findAll()).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> groundStationService.findAllGroundStations());
        verify(groundStationRepository).findAll();
    }

    /**
     * Test {@link GroundStationService#findGroundStationById(Long)}.
     * <ul>
     *   <li>Then return {@link Optional} with {@link GroundStation#GroundStation()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GroundStationService#findGroundStationById(Long)}
     */
    @Test
    @DisplayName("Test findGroundStationById(Long); then return Optional with GroundStation()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Optional GroundStationService.findGroundStationById(Long)"})
    void testFindGroundStationById_thenReturnOptionalWithGroundStation() {
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
        Optional<GroundStation> ofResult = Optional.of(groundStation2);
        when(groundStationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Optional<GroundStation> actualFindGroundStationByIdResult = groundStationService.findGroundStationById(1L);

        // Assert
        verify(groundStationRepository).findById(eq(1L));
        assertSame(ofResult, actualFindGroundStationByIdResult);
    }

    /**
     * Test {@link GroundStationService#findGroundStationById(Long)}.
     * <ul>
     *   <li>Then throw {@link RuntimeException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GroundStationService#findGroundStationById(Long)}
     */
    @Test
    @DisplayName("Test findGroundStationById(Long); then throw RuntimeException")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Optional GroundStationService.findGroundStationById(Long)"})
    void testFindGroundStationById_thenThrowRuntimeException() {
        // Arrange
        when(groundStationRepository.findById(Mockito.<Long>any())).thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> groundStationService.findGroundStationById(1L));
        verify(groundStationRepository).findById(eq(1L));
    }

    /**
     * Test {@link GroundStationService#saveGroundStation(GroundStation)}.
     * <p>
     * Method under test: {@link GroundStationService#saveGroundStation(GroundStation)}
     */
    @Test
    @DisplayName("Test saveGroundStation(GroundStation)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"GroundStation GroundStationService.saveGroundStation(GroundStation)"})
    void testSaveGroundStation() {
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

        GroundStation groundStation3 = new GroundStation();
        groundStation3.setGroundStation_AccesLevel(1);
        groundStation3.setGroundStation_Description("Ground Station Description");
        groundStation3.setGroundStation_Email("jane.doe@example.org");
        groundStation3.setGroundStation_Latitude(10.0d);
        groundStation3.setGroundStation_Longitude(10.0d);
        groundStation3.setGroundStation_Name("Ground Station Name");
        groundStation3.setGroundStation_id(1L);
        groundStation3.setUser(user2);
        when(groundStationRepository.save(Mockito.<GroundStation>any())).thenReturn(groundStation3);

        User user3 = new User();
        user3.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user3.setEmail("jane.doe@example.org");
        user3.setGroundStation(new GroundStation());
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

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(groundStation4);
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation5 = new GroundStation();
        groundStation5.setGroundStation_AccesLevel(1);
        groundStation5.setGroundStation_Description("Ground Station Description");
        groundStation5.setGroundStation_Email("jane.doe@example.org");
        groundStation5.setGroundStation_Latitude(10.0d);
        groundStation5.setGroundStation_Longitude(10.0d);
        groundStation5.setGroundStation_Name("Ground Station Name");
        groundStation5.setGroundStation_id(1L);
        groundStation5.setUser(user4);

        // Act
        GroundStation actualSaveGroundStationResult = groundStationService.saveGroundStation(groundStation5);

        // Assert
        verify(groundStationRepository).save(isA(GroundStation.class));
        assertSame(groundStation3, actualSaveGroundStationResult);
    }

    /**
     * Test {@link GroundStationService#updateGroundStation(Long, GroundStation)}.
     * <ul>
     *   <li>Then return {@link GroundStation#GroundStation()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GroundStationService#updateGroundStation(Long, GroundStation)}
     */
    @Test
    @DisplayName("Test updateGroundStation(Long, GroundStation); then return GroundStation()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"GroundStation GroundStationService.updateGroundStation(Long, GroundStation)"})
    void testUpdateGroundStation_thenReturnGroundStation() {
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
        Optional<GroundStation> ofResult = Optional.of(groundStation2);

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

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(groundStation4);
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation5 = new GroundStation();
        groundStation5.setGroundStation_AccesLevel(1);
        groundStation5.setGroundStation_Description("Ground Station Description");
        groundStation5.setGroundStation_Email("jane.doe@example.org");
        groundStation5.setGroundStation_Latitude(10.0d);
        groundStation5.setGroundStation_Longitude(10.0d);
        groundStation5.setGroundStation_Name("Ground Station Name");
        groundStation5.setGroundStation_id(1L);
        groundStation5.setUser(user4);
        when(groundStationRepository.save(Mockito.<GroundStation>any())).thenReturn(groundStation5);
        when(groundStationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User user5 = new User();
        user5.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user5.setEmail("jane.doe@example.org");
        user5.setGroundStation(new GroundStation());
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setStatus(UserStatus.ACTIVE);
        user5.setUser_id(1L);
        user5.setUser_name("User name");

        GroundStation groundStation6 = new GroundStation();
        groundStation6.setGroundStation_AccesLevel(1);
        groundStation6.setGroundStation_Description("Ground Station Description");
        groundStation6.setGroundStation_Email("jane.doe@example.org");
        groundStation6.setGroundStation_Latitude(10.0d);
        groundStation6.setGroundStation_Longitude(10.0d);
        groundStation6.setGroundStation_Name("Ground Station Name");
        groundStation6.setGroundStation_id(1L);
        groundStation6.setUser(user5);

        User user6 = new User();
        user6.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user6.setEmail("jane.doe@example.org");
        user6.setGroundStation(groundStation6);
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setStatus(UserStatus.ACTIVE);
        user6.setUser_id(1L);
        user6.setUser_name("User name");

        GroundStation updatedGroundStation = new GroundStation();
        updatedGroundStation.setGroundStation_AccesLevel(1);
        updatedGroundStation.setGroundStation_Description("Ground Station Description");
        updatedGroundStation.setGroundStation_Email("jane.doe@example.org");
        updatedGroundStation.setGroundStation_Latitude(10.0d);
        updatedGroundStation.setGroundStation_Longitude(10.0d);
        updatedGroundStation.setGroundStation_Name("Ground Station Name");
        updatedGroundStation.setGroundStation_id(1L);
        updatedGroundStation.setUser(user6);

        // Act
        GroundStation actualUpdateGroundStationResult = groundStationService.updateGroundStation(1L, updatedGroundStation);

        // Assert
        verify(groundStationRepository).findById(eq(1L));
        verify(groundStationRepository).save(isA(GroundStation.class));
        assertSame(groundStation5, actualUpdateGroundStationResult);
    }

    /**
     * Test {@link GroundStationService#updateGroundStation(Long, GroundStation)}.
     * <ul>
     *   <li>Then throw {@link RuntimeException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GroundStationService#updateGroundStation(Long, GroundStation)}
     */
    @Test
    @DisplayName("Test updateGroundStation(Long, GroundStation); then throw RuntimeException")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"GroundStation GroundStationService.updateGroundStation(Long, GroundStation)"})
    void testUpdateGroundStation_thenThrowRuntimeException() {
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
        Optional<GroundStation> ofResult = Optional.of(groundStation2);
        when(groundStationRepository.save(Mockito.<GroundStation>any())).thenThrow(new RuntimeException("foo"));
        when(groundStationRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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

        GroundStation updatedGroundStation = new GroundStation();
        updatedGroundStation.setGroundStation_AccesLevel(1);
        updatedGroundStation.setGroundStation_Description("Ground Station Description");
        updatedGroundStation.setGroundStation_Email("jane.doe@example.org");
        updatedGroundStation.setGroundStation_Latitude(10.0d);
        updatedGroundStation.setGroundStation_Longitude(10.0d);
        updatedGroundStation.setGroundStation_Name("Ground Station Name");
        updatedGroundStation.setGroundStation_id(1L);
        updatedGroundStation.setUser(user4);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> groundStationService.updateGroundStation(1L, updatedGroundStation));
        verify(groundStationRepository).findById(eq(1L));
        verify(groundStationRepository).save(isA(GroundStation.class));
    }

    /**
     * Test {@link GroundStationService#deleteGroundStation(Long)}.
     * <ul>
     *   <li>Given {@link GroundStationRepository} {@link CrudRepository#deleteById(Object)} does nothing.</li>
     * </ul>
     * <p>
     * Method under test: {@link GroundStationService#deleteGroundStation(Long)}
     */
    @Test
    @DisplayName("Test deleteGroundStation(Long); given GroundStationRepository deleteById(Object) does nothing")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void GroundStationService.deleteGroundStation(Long)"})
    void testDeleteGroundStation_givenGroundStationRepositoryDeleteByIdDoesNothing() {
        // Arrange
        doNothing().when(groundStationRepository).deleteById(Mockito.<Long>any());

        // Act
        groundStationService.deleteGroundStation(1L);

        // Assert
        verify(groundStationRepository).deleteById(eq(1L));
    }

    /**
     * Test {@link GroundStationService#deleteGroundStation(Long)}.
     * <ul>
     *   <li>Then throw {@link RuntimeException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link GroundStationService#deleteGroundStation(Long)}
     */
    @Test
    @DisplayName("Test deleteGroundStation(Long); then throw RuntimeException")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"void GroundStationService.deleteGroundStation(Long)"})
    void testDeleteGroundStation_thenThrowRuntimeException() {
        // Arrange
        doThrow(new RuntimeException("foo")).when(groundStationRepository).deleteById(Mockito.<Long>any());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> groundStationService.deleteGroundStation(1L));
        verify(groundStationRepository).deleteById(eq(1L));
    }

    /**
     * Test {@link GroundStationService#findSatelliteByUserId(Long)}.
     * <p>
     * Method under test: {@link GroundStationService#findSatelliteByUserId(Long)}
     */
    @Test
    @DisplayName("Test findSatelliteByUserId(Long)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"SatelliteByGroundStationResponse GroundStationService.findSatelliteByUserId(Long)"})
    void testFindSatelliteByUserId() {
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
        Optional<GroundStation> ofResult = Optional.of(groundStation2);
        when(groundStationRepository.findByUser_UserId(anyLong())).thenReturn(ofResult);
        when(satelliteClient.findAllSatellitesByGroundStationId(Mockito.<Long>any()))
                .thenThrow(new RuntimeException("foo"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> groundStationService.findSatelliteByUserId(1L));
        verify(satelliteClient).findAllSatellitesByGroundStationId(eq(1L));
        verify(groundStationRepository).findByUser_UserId(eq(1L));
    }

    /**
     * Test {@link GroundStationService#findSatelliteByUserId(Long)}.
     * <p>
     * Method under test: {@link GroundStationService#findSatelliteByUserId(Long)}
     */
    @Test
    @DisplayName("Test findSatelliteByUserId(Long)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"SatelliteByGroundStationResponse GroundStationService.findSatelliteByUserId(Long)"})
    void testFindSatelliteByUserId2() {
        // Arrange
        Optional<GroundStation> emptyResult = Optional.empty();
        when(groundStationRepository.findByUser_UserId(anyLong())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> groundStationService.findSatelliteByUserId(1L));
        verify(groundStationRepository).findByUser_UserId(eq(1L));
    }

    /**
     * Test {@link GroundStationService#findSatelliteByUserId(Long)}.
     * <ul>
     *   <li>Then return Satellites Empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link GroundStationService#findSatelliteByUserId(Long)}
     */
    @Test
    @DisplayName("Test findSatelliteByUserId(Long); then return Satellites Empty")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"SatelliteByGroundStationResponse GroundStationService.findSatelliteByUserId(Long)"})
    void testFindSatelliteByUserId_thenReturnSatellitesEmpty() {
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
        Optional<GroundStation> ofResult = Optional.of(groundStation2);
        when(groundStationRepository.findByUser_UserId(anyLong())).thenReturn(ofResult);
        when(satelliteClient.findAllSatellitesByGroundStationId(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        // Act
        SatelliteByGroundStationResponse actualFindSatelliteByUserIdResult = groundStationService.findSatelliteByUserId(1L);

        // Assert
        verify(satelliteClient).findAllSatellitesByGroundStationId(eq(1L));
        verify(groundStationRepository).findByUser_UserId(eq(1L));
        assertTrue(actualFindSatelliteByUserIdResult.getSatellites().isEmpty());
    }
}
