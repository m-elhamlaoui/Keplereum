package com.example.jeeHamlaoui.Blockchain_Service.controller;

import com.example.jeeHamlaoui.Blockchain_Service.model.Block;
import com.example.jeeHamlaoui.Blockchain_Service.model.dto.BlockDTO;
import com.example.jeeHamlaoui.Blockchain_Service.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/blocks")
@CrossOrigin(origins = "*")
public class BlockController {
    private final BlockService blockService;

    @Autowired
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping("/latest")
    public Optional<Block> getLatestBlock() {
        return blockService.getLatestBlock();
    }

    @GetMapping("/{height}")
    public Optional<Block> getBlockByHeight(@PathVariable Long height) {
        return blockService.getBlockByHeight(height);
    }

    @GetMapping("/hash/{hash}")
    public Optional<Block> getBlockByHash(@PathVariable String hash) {
        return blockService.getBlockByHash(hash);
    }
    @PostMapping
    public ResponseEntity<Void> saveBlock(@RequestBody BlockDTO block) {
        if (blockService.blockExists(block.getNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (blockService.blockExists(block.getHash())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(blockService.createBlock(block));
    }
}