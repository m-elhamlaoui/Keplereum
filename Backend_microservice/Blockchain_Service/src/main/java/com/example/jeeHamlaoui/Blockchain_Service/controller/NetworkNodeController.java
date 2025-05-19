package com.example.jeeHamlaoui.Blockchain_Service.controller;

import com.example.jeeHamlaoui.Blockchain_Service.model.NetworkNode;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.NetworkNodeDTO;
import com.example.jeeHamlaoui.Blockchain_Service.service.NetworkNodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/nodes")
@CrossOrigin(origins = "*")
public class NetworkNodeController {
    private final NetworkNodeService networkNodeService;

    public NetworkNodeController(NetworkNodeService networkNodeService) {
        this.networkNodeService = networkNodeService;
    }

    @PostMapping
    public ResponseEntity<NetworkNode> registerNode(@RequestBody NetworkNodeDTO node) {
        NetworkNode savedNode = networkNodeService.createNode(node);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNode);
    }
    /*
    @PostMapping("/batch")
    public ResponseEntity<NetworkNode> registerNode(@RequestBody List<NetworkNodeDTO> node) {
        NetworkNode savedNode = networkNodeService.createNode(node);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNode);
    }
    */
    @GetMapping("/authorities")
    public ResponseEntity<List<NetworkNode>> getAuthorityNodes() {
        List<NetworkNode> nodes = networkNodeService.getAuthorityNodes();
        return ResponseEntity.ok(nodes);
    }

    @GetMapping("/validators")
    public ResponseEntity<List<NetworkNode>> getActiveValidators(
            @RequestParam(defaultValue = "10") int minBlocks) {
        List<NetworkNode> validators = networkNodeService.getActiveValidators(minBlocks);
        return ResponseEntity.ok(validators);
    }

    @PutMapping("/{publicKey}/activity")
    public ResponseEntity<Void> updateLastActive(@PathVariable String publicKey) {
        networkNodeService.updateLastActive(publicKey);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{publicKey}")
    public ResponseEntity<NetworkNode> getNode(@PathVariable String publicKey) {
        return networkNodeService.getNodeByPublicKey(publicKey)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}