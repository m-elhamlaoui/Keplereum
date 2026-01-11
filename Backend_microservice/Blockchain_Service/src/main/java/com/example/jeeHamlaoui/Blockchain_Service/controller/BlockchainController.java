package com.example.jeeHamlaoui.Blockchain_Service.controller;

import com.example.jeeHamlaoui.Blockchain_Service.service.BlockchainService;
import com.example.jeeHamlaoui.Blockchain_Service.service.SmartContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.jeeHamlaoui.Blockchain_Service.utils.UtilityClass;

import java.math.BigInteger;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/blockchain")
@CrossOrigin(origins = "*")
public class BlockchainController {
    private final BlockchainService blockchainService;
    private final SmartContractService smartContractService;

    public BlockchainController(BlockchainService blockchainService, SmartContractService smartContractService) {
        this.blockchainService = blockchainService;
        this.smartContractService = smartContractService;
    }

    @GetMapping("/mempool")
    public ResponseEntity<List<?>> getPendingTransactions() {
        try {
            return ResponseEntity.ok(blockchainService.getPendingTransactions());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of(e.getMessage()));
        }
    }

    @GetMapping("/block/{number}")
    public ResponseEntity<?> getBlockByNumber(@PathVariable BigInteger number) {
        try {
            return ResponseEntity.ok(blockchainService.getBlockByNumber(number));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/validators/queue")
    public ResponseEntity<List<String>> getValidatorQueue() {
        try {
            List<String> validatorQueue = blockchainService.getValidatorQueue();
            return ResponseEntity.ok(validatorQueue);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of(e.getMessage()));
        }
    }

    @PostMapping("/transaction")
    public ResponseEntity<String> submitTransaction(@RequestParam String privateKey, @RequestParam String toAddress,
                                                     @RequestParam BigInteger value, @RequestParam BigInteger gasPrice,
                                                     @RequestParam BigInteger gasLimit) {
        try {
            String transactionHash = blockchainService.publishTransaction(privateKey, toAddress, value, gasPrice, gasLimit);
            return ResponseEntity.ok(transactionHash);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/transaction")
    public ResponseEntity<?> getTransactionByHash(@RequestParam String hash) {
        try {
            var transactionDetails = blockchainService.getTransactionByHash(hash);
            return ResponseEntity.ok(transactionDetails);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    //**************************************** Smart Contracts ***********************************************//
    //********************************************************************************************************//

    @PostMapping("/contract/alert")
    public ResponseEntity<String> submitAlert(@RequestParam String privateKey, @RequestParam String alertType,
                                               @RequestParam BigInteger latitude, @RequestParam BigInteger longitude) {
        try {
            return ResponseEntity.ok(smartContractService.submitAlert(privateKey, alertType, latitude, longitude));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } 
    }

    @PostMapping("/contract/alert/confirm")
    public ResponseEntity<Map<String, String>> confirmAlert(@RequestParam String privateKey, @RequestParam String alertId) {
        try {
            Map<String, String> response = smartContractService.confirmAlert(privateKey, alertId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/contract/alert/action")
    public ResponseEntity<Map<String, String>> triggerAction(@RequestParam String privateKey, @RequestParam String satellite,
                                                             @RequestParam BigInteger action, @RequestParam String alertId) {
        try {
            Map<String, String> response = smartContractService.triggerAction(privateKey, satellite, action, alertId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/alerts")
    public ResponseEntity<?> getAllAlertsWithConfirmations() {
        try {
            var alerts = smartContractService.getAllGlobalAlerts();
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/alert/{id}")
    public ResponseEntity<?> getAlertById(@PathVariable String id) {
        try {
            var alertDetails = smartContractService.getAlertById(id);
            return ResponseEntity.ok(alertDetails);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/validations")
    public ResponseEntity<?> getAllValidations() {
        try {
            var validations = smartContractService.getAllValidations();
            return ResponseEntity.ok(validations);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}