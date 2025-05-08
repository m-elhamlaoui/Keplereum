package com.example.jeeHamlaoui.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.model.GroundStation;
import com.example.jeeHamlaoui.model.User;
import com.example.jeeHamlaoui.model.enums.UserStatus;
import com.example.jeeHamlaoui.service.GroundStationService;
import com.example.jeeHamlaoui.service.SatelliteByGroundStationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {GroundStationController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GroundStationControllerDiffblueTest {
    @Autowired
    private GroundStationController groundStationController;

    @MockitoBean
    private GroundStationService groundStationService;

    /**
     * Test {@link GroundStationController#getAllGroundStations()}.
     * <p>
     * Method under test: {@link GroundStationController#getAllGroundStations()}
     */
    @Test
    @DisplayName("Test getAllGroundStations()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"org.springframework.http.ResponseEntity GroundStationController.getAllGroundStations()"})
    void testGetAllGroundStations() throws Exception {
        // Arrange
        when(groundStationService.findAllGroundStations()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/groundstations");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(groundStationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link GroundStationController#getGroundStationById(Long)}.
     * <p>
     * Method under test: {@link GroundStationController#getGroundStationById(Long)}
     */
    @Test
    @DisplayName("Test getGroundStationById(Long)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"org.springframework.http.ResponseEntity GroundStationController.getGroundStationById(Long)"})
    void testGetGroundStationById() throws Exception {
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
        when(groundStationService.findGroundStationById(Mockito.<Long>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/groundstations/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(groundStationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"groundStation_id\":1,\"groundStation_Name\":\"Ground Station Name\",\"groundStation_Email\":\"jane.doe@example"
                                        + ".org\",\"groundStation_AccesLevel\":1,\"groundStation_Latitude\":10.0,\"groundStation_Longitude\":10.0,"
                                        + "\"groundStation_Description\":\"Ground Station Description\"}"));
    }

    /**
     * Test {@link GroundStationController#createGroundStation(GroundStation)}.
     * <p>
     * Method under test: {@link GroundStationController#createGroundStation(GroundStation)}
     */
    @Test
    @DisplayName("Test createGroundStation(GroundStation)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({
            "org.springframework.http.ResponseEntity GroundStationController.createGroundStation(GroundStation)"})
    void testCreateGroundStation() throws Exception {
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
        when(groundStationService.saveGroundStation(Mockito.<GroundStation>any())).thenReturn(groundStation3);

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(new User());

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

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(groundStation5);
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation6 = new GroundStation();
        groundStation6.setGroundStation_AccesLevel(1);
        groundStation6.setGroundStation_Description("Ground Station Description");
        groundStation6.setGroundStation_Email("jane.doe@example.org");
        groundStation6.setGroundStation_Latitude(10.0d);
        groundStation6.setGroundStation_Longitude(10.0d);
        groundStation6.setGroundStation_Name("Ground Station Name");
        groundStation6.setGroundStation_id(1L);
        groundStation6.setUser(user4);
        String content = (new ObjectMapper()).writeValueAsString(groundStation6);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/groundstations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(groundStationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"groundStation_id\":1,\"groundStation_Name\":\"Ground Station Name\",\"groundStation_Email\":\"jane.doe@example"
                                        + ".org\",\"groundStation_AccesLevel\":1,\"groundStation_Latitude\":10.0,\"groundStation_Longitude\":10.0,"
                                        + "\"groundStation_Description\":\"Ground Station Description\"}"));
    }

    /**
     * Test {@link GroundStationController#updateGroundStation(Long, GroundStation)}.
     * <p>
     * Method under test: {@link GroundStationController#updateGroundStation(Long, GroundStation)}
     */
    @Test
    @DisplayName("Test updateGroundStation(Long, GroundStation)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({
            "org.springframework.http.ResponseEntity GroundStationController.updateGroundStation(Long, GroundStation)"})
    void testUpdateGroundStation() throws Exception {
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
        when(groundStationService.updateGroundStation(Mockito.<Long>any(), Mockito.<GroundStation>any()))
                .thenReturn(groundStation3);

        GroundStation groundStation4 = new GroundStation();
        groundStation4.setGroundStation_AccesLevel(1);
        groundStation4.setGroundStation_Description("Ground Station Description");
        groundStation4.setGroundStation_Email("jane.doe@example.org");
        groundStation4.setGroundStation_Latitude(10.0d);
        groundStation4.setGroundStation_Longitude(10.0d);
        groundStation4.setGroundStation_Name("Ground Station Name");
        groundStation4.setGroundStation_id(1L);
        groundStation4.setUser(new User());

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

        User user4 = new User();
        user4.setCreated_at(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        user4.setEmail("jane.doe@example.org");
        user4.setGroundStation(groundStation5);
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setStatus(UserStatus.ACTIVE);
        user4.setUser_id(1L);
        user4.setUser_name("User name");

        GroundStation groundStation6 = new GroundStation();
        groundStation6.setGroundStation_AccesLevel(1);
        groundStation6.setGroundStation_Description("Ground Station Description");
        groundStation6.setGroundStation_Email("jane.doe@example.org");
        groundStation6.setGroundStation_Latitude(10.0d);
        groundStation6.setGroundStation_Longitude(10.0d);
        groundStation6.setGroundStation_Name("Ground Station Name");
        groundStation6.setGroundStation_id(1L);
        groundStation6.setUser(user4);
        String content = (new ObjectMapper()).writeValueAsString(groundStation6);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/groundstations/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(groundStationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"groundStation_id\":1,\"groundStation_Name\":\"Ground Station Name\",\"groundStation_Email\":\"jane.doe@example"
                                        + ".org\",\"groundStation_AccesLevel\":1,\"groundStation_Latitude\":10.0,\"groundStation_Longitude\":10.0,"
                                        + "\"groundStation_Description\":\"Ground Station Description\"}"));
    }

    /**
     * Test {@link GroundStationController#deleteGroundStation(Long)}.
     * <p>
     * Method under test: {@link GroundStationController#deleteGroundStation(Long)}
     */
    @Test
    @DisplayName("Test deleteGroundStation(Long)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"org.springframework.http.ResponseEntity GroundStationController.deleteGroundStation(Long)"})
    void testDeleteGroundStation() throws Exception {
        // Arrange
        doNothing().when(groundStationService).deleteGroundStation(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/groundstations/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(groundStationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Test {@link GroundStationController#findAllSatellites(Long)}.
     * <p>
     * Method under test: {@link GroundStationController#findAllSatellites(Long)}
     */
    @Test
    @DisplayName("Test findAllSatellites(Long)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"org.springframework.http.ResponseEntity GroundStationController.findAllSatellites(Long)"})
    void testFindAllSatellites() throws Exception {
        // Arrange
        when(groundStationService.findSatelliteByUserId(Mockito.<Long>any()))
                .thenReturn(new SatelliteByGroundStationResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/groundstations/satellites/{user-id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(groundStationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"satellites\":null}"));
    }
}
