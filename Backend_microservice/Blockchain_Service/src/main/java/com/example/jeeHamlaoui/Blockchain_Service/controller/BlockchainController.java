package com.example.jeeHamlaoui.Blockchain_Service.controller;

import com.example.jeeHamlaoui.Blockchain_Service.service.BlockchainService;
import com.example.jeeHamlaoui.Blockchain_Service.service.SmartContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Blockchain CRUD Endpoints
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

    // POST: Submit a transaction
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

    // GET: Get a transaction by hash (with event details if applicable)
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

    // Smart Contract CRUD Endpoints
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
            // Trim the input to remove any extra spaces
            alertId = alertId.trim();

            // Validate the Base64-encoded alertId
            if (!isValidBase64(alertId)) {
                throw new IllegalArgumentException("Invalid Base64-encoded alertId");
            }

            // Decode the Base64-encoded alertId
            byte[] alertIdBytes = Base64.getDecoder().decode(alertId);

            // Validate the length of the decoded byte array
            if (alertIdBytes.length != 32) {
                throw new IllegalArgumentException("Decoded alertId must be exactly 32 bytes long");
            }

            // Call the service and return the response
            Map<String, String> response = smartContractService.confirmAlert(privateKey, alertIdBytes);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Handle invalid Base64 strings or length issues
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // Helper method to validate Base64 strings
    private boolean isValidBase64(String str) {
        try {
            Base64.getDecoder().decode(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @PostMapping("/contract/action")
    public ResponseEntity<Map<String, String>> triggerAction(@RequestParam String privateKey, @RequestParam String satellite,
                                                             @RequestParam BigInteger action, @RequestParam String alertId) {
        try {
            // Decode the Base64-encoded alertId
            byte[] alertIdBytes = Base64.getDecoder().decode(alertId);

            // Call the service and return the response
            Map<String, String> response = smartContractService.triggerAction(privateKey, satellite, action, alertIdBytes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // GET: All currently submitted alerts with confirmations
    @GetMapping("/alerts")
    public ResponseEntity<?> getAllAlertsWithConfirmations() {
        try {
            var alerts = smartContractService.getAllAlertsWithConfirmations();
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // GET: Alert fetch by ID, with confirmations
    @GetMapping("/alert/{id}")
    public ResponseEntity<?> getAlertById(@PathVariable String id) {
        try {
            var alertDetails = smartContractService.getAlertById(id);
            return ResponseEntity.ok(alertDetails);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // GET: Get all alert submitted events
    @GetMapping("/contract/alerts")
    public ResponseEntity<?> getAllAlertSubmittedEvents() {
        try {
            List<Map<String, Object>> alertEvents = smartContractService.getAllAlertSubmittedEvents();
            return ResponseEntity.ok(alertEvents);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}