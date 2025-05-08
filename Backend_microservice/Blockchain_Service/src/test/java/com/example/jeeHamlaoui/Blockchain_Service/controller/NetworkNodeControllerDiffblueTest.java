package com.example.jeeHamlaoui.Blockchain_Service.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.NetworkNodeDTO;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;
import com.example.jeeHamlaoui.Blockchain_Service.service.NetworkNodeService;
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
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {NetworkNodeController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class NetworkNodeControllerDiffblueTest {
  @Autowired
  private NetworkNodeController networkNodeController;

  @MockitoBean
  private NetworkNodeService networkNodeService;

  /**
   * Test {@link NetworkNodeController#registerNode(NetworkNodeDTO)}.
   * <ul>
   *   <li>Given {@link NetworkNodeRepository} {@link CrudRepository#save(Object)} return {@link NetworkNode#NetworkNode()}.</li>
   *   <li>Then calls {@link CrudRepository#save(Object)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NetworkNodeController#registerNode(NetworkNodeDTO)}
   */
  @Test
  @DisplayName("Test registerNode(NetworkNodeDTO); given NetworkNodeRepository save(Object) return NetworkNode(); then calls save(Object)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity NetworkNodeController.registerNode(NetworkNodeDTO)"})
  void testRegisterNode_givenNetworkNodeRepositorySaveReturnNetworkNode_thenCallsSave() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    NetworkNode networkNode = new NetworkNode();
    networkNode.setAuthorityStatus(true);
    networkNode.setBlocksValidated(1);
    networkNode.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    networkNode.setNodeName("Node Name");
    networkNode.setPublicKey("Public Key");
    networkNode.setReceivedTransactions(new ArrayList<>());
    networkNode.setSentTransactions(new ArrayList<>());
    NetworkNodeRepository networkNodeRepository = mock(NetworkNodeRepository.class);
    when(networkNodeRepository.save(Mockito.<NetworkNode>any())).thenReturn(networkNode);
    NetworkNodeController networkNodeController = new NetworkNodeController(
        new NetworkNodeService(networkNodeRepository));

    NetworkNodeDTO node = new NetworkNodeDTO();
    node.setAuthorityStatus(true);
    node.setBlocksValidated(1);
    node.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    node.setNodeName("Node Name");
    node.setPublicKey("Public Key");

    // Act
    ResponseEntity<NetworkNode> actualRegisterNodeResult = networkNodeController.registerNode(node);

    // Assert
    verify(networkNodeRepository).save(isA(NetworkNode.class));
    HttpStatusCode statusCode = actualRegisterNodeResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(201, actualRegisterNodeResult.getStatusCodeValue());
    assertEquals(HttpStatus.CREATED, statusCode);
    assertTrue(actualRegisterNodeResult.hasBody());
    assertTrue(actualRegisterNodeResult.getHeaders().isEmpty());
    assertSame(networkNode, actualRegisterNodeResult.getBody());
  }

  /**
   * Test {@link NetworkNodeController#registerNode(NetworkNodeDTO)}.
   * <ul>
   *   <li>Then calls {@link NetworkNodeService#createNode(NetworkNodeDTO)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link NetworkNodeController#registerNode(NetworkNodeDTO)}
   */
  @Test
  @DisplayName("Test registerNode(NetworkNodeDTO); then calls createNode(NetworkNodeDTO)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity NetworkNodeController.registerNode(NetworkNodeDTO)"})
  void testRegisterNode_thenCallsCreateNode() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    NetworkNode networkNode = new NetworkNode();
    networkNode.setAuthorityStatus(true);
    networkNode.setBlocksValidated(1);
    networkNode.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    networkNode.setNodeName("Node Name");
    networkNode.setPublicKey("Public Key");
    networkNode.setReceivedTransactions(new ArrayList<>());
    networkNode.setSentTransactions(new ArrayList<>());
    NetworkNodeService networkNodeService = mock(NetworkNodeService.class);
    when(networkNodeService.createNode(Mockito.<NetworkNodeDTO>any())).thenReturn(networkNode);
    NetworkNodeController networkNodeController = new NetworkNodeController(networkNodeService);

    NetworkNodeDTO node = new NetworkNodeDTO();
    node.setAuthorityStatus(true);
    node.setBlocksValidated(1);
    node.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    node.setNodeName("Node Name");
    node.setPublicKey("Public Key");

    // Act
    ResponseEntity<NetworkNode> actualRegisterNodeResult = networkNodeController.registerNode(node);

    // Assert
    verify(networkNodeService).createNode(isA(NetworkNodeDTO.class));
    HttpStatusCode statusCode = actualRegisterNodeResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(201, actualRegisterNodeResult.getStatusCodeValue());
    assertEquals(HttpStatus.CREATED, statusCode);
    assertTrue(actualRegisterNodeResult.hasBody());
    assertTrue(actualRegisterNodeResult.getHeaders().isEmpty());
    assertSame(networkNode, actualRegisterNodeResult.getBody());
  }

  /**
   * Test {@link NetworkNodeController#getAuthorityNodes()}.
   * <p>
   * Method under test: {@link NetworkNodeController#getAuthorityNodes()}
   */
  @Test
  @DisplayName("Test getAuthorityNodes()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity NetworkNodeController.getAuthorityNodes()"})
  void testGetAuthorityNodes() throws Exception {
    // Arrange
    when(networkNodeService.getAuthorityNodes()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/nodes/authorities");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(networkNodeController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link NetworkNodeController#getActiveValidators(int)}.
   * <p>
   * Method under test: {@link NetworkNodeController#getActiveValidators(int)}
   */
  @Test
  @DisplayName("Test getActiveValidators(int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity NetworkNodeController.getActiveValidators(int)"})
  void testGetActiveValidators() throws Exception {
    // Arrange
    when(networkNodeService.getActiveValidators(anyInt())).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/nodes/validators");
    MockHttpServletRequestBuilder requestBuilder = getResult.param("minBlocks", String.valueOf(1));

    // Act and Assert
    MockMvcBuilders.standaloneSetup(networkNodeController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link NetworkNodeController#updateLastActive(String)}.
   * <p>
   * Method under test: {@link NetworkNodeController#updateLastActive(String)}
   */
  @Test
  @DisplayName("Test updateLastActive(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity NetworkNodeController.updateLastActive(String)"})
  void testUpdateLastActive() throws Exception {
    // Arrange
    doNothing().when(networkNodeService).updateLastActive(Mockito.<String>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/nodes/{publicKey}/activity",
        "Public Key");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(networkNodeController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  /**
   * Test {@link NetworkNodeController#getNode(String)}.
   * <p>
   * Method under test: {@link NetworkNodeController#getNode(String)}
   */
  @Test
  @DisplayName("Test getNode(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ResponseEntity NetworkNodeController.getNode(String)"})
  void testGetNode() throws Exception {
    // Arrange
    NetworkNode networkNode = new NetworkNode();
    networkNode.setAuthorityStatus(true);
    networkNode.setBlocksValidated(1);
    networkNode.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    networkNode.setNodeName("Node Name");
    networkNode.setPublicKey("Public Key");
    networkNode.setReceivedTransactions(new ArrayList<>());
    networkNode.setSentTransactions(new ArrayList<>());
    Optional<NetworkNode> ofResult = Optional.of(networkNode);
    when(networkNodeService.getNodeByPublicKey(Mockito.<String>any())).thenReturn(ofResult);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/nodes/{publicKey}",
        "Public Key");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(networkNodeController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content()
            .string(
                "{\"publicKey\":\"Public Key\",\"authorityStatus\":true,\"blocksValidated\":1,\"lastActive\":0.0,\"nodeName\":\"Node"
                    + " Name\"}"));
  }
}
