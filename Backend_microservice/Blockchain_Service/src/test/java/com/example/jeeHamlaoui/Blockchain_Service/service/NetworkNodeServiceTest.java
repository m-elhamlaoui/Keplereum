package com.example.jeeHamlaoui.Blockchain_Service.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.MethodsUnderTest;
import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.NetworkNodeDTO;
import com.example.jeeHamlaoui.Blockchain_Service.repository.NetworkNodeRepository;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NetworkNodeService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class NetworkNodeServiceTest {
    @MockitoBean
    private NetworkNodeRepository networkNodeRepository;

    @Autowired
    private NetworkNodeService networkNodeService;

    /**
     * Test {@link NetworkNodeService#createNode(NetworkNodeDTO)}.
     * <p>
     * Method under test: {@link NetworkNodeService#createNode(NetworkNodeDTO)}
     */
    @Test
    @DisplayName("Test createNode(NetworkNodeDTO)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"NetworkNode NetworkNodeService.createNode(NetworkNodeDTO)"})
    void testCreateNode() {
        // Arrange
        NetworkNode networkNode = new NetworkNode();
        networkNode.setAuthorityStatus(true);
        networkNode.setBlocksValidated(1);
        networkNode.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        networkNode.setNodeName("Node Name");
        networkNode.setPublicKey("Public Key");
        networkNode.setReceivedTransactions(new ArrayList<>());
        networkNode.setSentTransactions(new ArrayList<>());
        when(networkNodeRepository.save(Mockito.<NetworkNode>any())).thenReturn(networkNode);

        NetworkNodeDTO nodeDTO = new NetworkNodeDTO();
        nodeDTO.setAuthorityStatus(true);
        nodeDTO.setBlocksValidated(1);
        nodeDTO.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        nodeDTO.setNodeName("Node Name");
        nodeDTO.setPublicKey("Public Key");

        // Act
        NetworkNode actualCreateNodeResult = networkNodeService.createNode(nodeDTO);

        // Assert
        verify(networkNodeRepository).save(isA(NetworkNode.class));
        assertSame(networkNode, actualCreateNodeResult);
    }

    /**
     * Test {@link NetworkNodeService#createNode(NetworkNodeDTO)}.
     * <p>
     * Method under test: {@link NetworkNodeService#createNode(NetworkNodeDTO)}
     */
    @Test
    @DisplayName("Test createNode(NetworkNodeDTO)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"NetworkNode NetworkNodeService.createNode(NetworkNodeDTO)"})
    void testCreateNode2() {
        // Arrange
        NetworkNode networkNode = new NetworkNode();
        networkNode.setAuthorityStatus(true);
        networkNode.setBlocksValidated(1);
        networkNode.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        networkNode.setNodeName("Node Name");
        networkNode.setPublicKey("Public Key");
        networkNode.setReceivedTransactions(new ArrayList<>());
        networkNode.setSentTransactions(new ArrayList<>());
        when(networkNodeRepository.save(Mockito.<NetworkNode>any())).thenReturn(networkNode);

        NetworkNodeDTO nodeDTO = new NetworkNodeDTO();
        nodeDTO.setAuthorityStatus(true);
        nodeDTO.setBlocksValidated(1);
        nodeDTO.setLastActive(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        nodeDTO.setNodeName("Node Name");
        nodeDTO.setPublicKey("Public Key");

        // Act
        NetworkNode actualCreateNodeResult = networkNodeService.createNode(nodeDTO);

        // Assert
        verify(networkNodeRepository).save(isA(NetworkNode.class));
        assertSame(networkNode, actualCreateNodeResult);
    }

    /**
     * Test {@link NetworkNodeService#getAuthorityNodes()}.
     * <p>
     * Method under test: {@link NetworkNodeService#getAuthorityNodes()}
     */
    @Test
    @DisplayName("Test getAuthorityNodes()")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List NetworkNodeService.getAuthorityNodes()"})
    void testGetAuthorityNodes() {
        // Arrange
        when(networkNodeRepository.findByAuthorityStatus(Mockito.<Boolean>any())).thenReturn(new ArrayList<>());

        // Act
        List<NetworkNode> actualAuthorityNodes = networkNodeService.getAuthorityNodes();

        // Assert
        verify(networkNodeRepository).findByAuthorityStatus(eq(true));
        assertTrue(actualAuthorityNodes.isEmpty());
    }

    /**
     * Test {@link NetworkNodeService#getActiveValidators(int)}.
     * <p>
     * Method under test: {@link NetworkNodeService#getActiveValidators(int)}
     */
    @Test
    @DisplayName("Test getActiveValidators(int)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"List NetworkNodeService.getActiveValidators(int)"})
    void testGetActiveValidators() {
        // Arrange
        when(networkNodeRepository.findByBlocksValidatedGreaterThan(Mockito.<Integer>any())).thenReturn(new ArrayList<>());

        // Act
        List<NetworkNode> actualActiveValidators = networkNodeService.getActiveValidators(1);

        // Assert
        verify(networkNodeRepository).findByBlocksValidatedGreaterThan(eq(1));
        assertTrue(actualActiveValidators.isEmpty());
    }

    /**
     * Test {@link NetworkNodeService#getNodeByPublicKey(String)}.
     * <p>
     * Method under test: {@link NetworkNodeService#getNodeByPublicKey(String)}
     */
    @Test
    @DisplayName("Test getNodeByPublicKey(String)")
    @Tag("MaintainedByDiffblue")
    @MethodsUnderTest({"Optional NetworkNodeService.getNodeByPublicKey(String)"})
    void testGetNodeByPublicKey() {
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
        when(networkNodeRepository.findById(Mockito.<String>any())).thenReturn(ofResult);

        // Act
        Optional<NetworkNode> actualNodeByPublicKey = networkNodeService.getNodeByPublicKey("Public Key");

        // Assert
        verify(networkNodeRepository).findById(eq("Public Key"));
        assertSame(ofResult, actualNodeByPublicKey);
    }
}
