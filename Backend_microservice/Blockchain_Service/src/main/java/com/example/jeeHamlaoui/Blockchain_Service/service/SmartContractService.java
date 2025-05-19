package com.example.jeeHamlaoui.Blockchain_Service.service;

import com.example.jeeHamlaoui.Blockchain_Service.web3j.Web3JSingleton;
import com.example.jeeHamlaoui.Blockchain_Service.contracts.SatelliteSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tuples.generated.Tuple7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class SmartContractService {
    private final Web3JSingleton web3JSingleton;
    private final String contractAddress;

    public SmartContractService(Web3JSingleton web3JSingleton, @Value("${blockchain.contract-address}") String contractAddress) {
        this.web3JSingleton = web3JSingleton;
        this.contractAddress = contractAddress;
    }

    // Submit an alert
    public String submitAlert(String privateKey, String alertType, BigInteger latitude, BigInteger longitude) throws Exception {
        Credentials credentials = Credentials.create(privateKey);

        // Fetch the chain ID from the Ethereum network
        BigInteger chainId = web3JSingleton.getWeb3jInstance().ethChainId().send().getChainId();

        // Create a transaction manager with the chainId to enforce EIP-155 replay protection
        TransactionManager transactionManager = new RawTransactionManager(web3JSingleton.getWeb3jInstance(), credentials, chainId.longValue());

        // Now, load the contract with the new transaction manager that includes the chainId
        SatelliteSystem contractWithChainId = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), transactionManager, new DefaultGasProvider());

        // Send the transaction
        TransactionReceipt transactionReceipt = contractWithChainId.submitAlert(alertType, latitude, longitude).send();

        // Return the transaction hash
        return transactionReceipt.getTransactionHash();
    }

    // Confirm an alert
    public Map<String, String> confirmAlert(String privateKey, byte[] alertId) throws Exception {
        // Ensure the alertId is padded to 32 bytes
        if (alertId.length != 32) {
            throw new IllegalArgumentException("alertId must be exactly 32 bytes long");
        }

        Credentials credentials = Credentials.create(privateKey);

        // Fetch the chain ID from the Ethereum network
        BigInteger chainId = web3JSingleton.getWeb3jInstance().ethChainId().send().getChainId();

        // Create a transaction manager with the chain ID
        TransactionManager transactionManager = new RawTransactionManager(
                web3JSingleton.getWeb3jInstance(),
                credentials,
                chainId.longValue()
        );

        // Load the contract with the transaction manager
        SatelliteSystem contract = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), transactionManager, new DefaultGasProvider());

        // Send the transaction
        TransactionReceipt receipt = contract.confirmAlert(alertId).send();

        // Return the transaction hash and a success message
        return Map.of(
                "transactionHash", receipt.getTransactionHash(),
                "message", "Alert confirmed successfully"
        );
    }

    // Trigger an action
    public Map<String, String> triggerAction(String privateKey, String satellite, BigInteger action, byte[] alertId) throws Exception {
        Credentials credentials = Credentials.create(privateKey);

        // Fetch the chain ID from the Ethereum network
        BigInteger chainId = web3JSingleton.getWeb3jInstance().ethChainId().send().getChainId();

        // Create a transaction manager with the chain ID
        TransactionManager transactionManager = new RawTransactionManager(
                web3JSingleton.getWeb3jInstance(),
                credentials,
                chainId.longValue()
        );

        // Load the contract with the transaction manager
        SatelliteSystem contract = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), transactionManager, new DefaultGasProvider());

        // Send the transaction
        TransactionReceipt receipt = contract.triggerAction(satellite, action, alertId).send();

        // Return the transaction hash and a success message
        return Map.of(
                "transactionHash", receipt.getTransactionHash(),
                "message", "Action triggered successfully"
        );
    }

    // Get all alerts with confirmations
    public List<Map<String, Object>> getAllAlertsWithConfirmations() throws Exception {
        String defaultPrivateKey = "0xef43f9f547e1a70532ba05824bda6e90d58f1d8c44aeb75ec1354839ad4b7738"; // Replace with a valid private key
        Credentials credentials = Credentials.create(defaultPrivateKey);
        SatelliteSystem contract = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), credentials, new DefaultGasProvider());

        BigInteger alertCount = contract.getAlertCount().send();
        List<Map<String, Object>> alerts = new ArrayList<>();

        for (BigInteger i = BigInteger.ZERO; i.compareTo(alertCount) < 0; i = i.add(BigInteger.ONE)) {
            // Fetch alert ID
            byte[] alertId = contract.alertIds(i).send();

            // Fetch alert details using the alerts(byte[]) method
            Tuple7<String, String, BigInteger, BigInteger, BigInteger, BigInteger, Boolean> alertDetails = contract.alerts(alertId).send();

            // Extract details from the tuple
            String alertType = alertDetails.component2(); // Assuming the second component is the alert type
            BigInteger latitude = alertDetails.component3(); // Assuming the third component is latitude
            BigInteger longitude = alertDetails.component4(); // Assuming the fourth component is longitude

            // Fetch confirmers
            List<String> confirmers = contract.getAlertConfirmers(alertId).send();

            // Add all details to the response
            alerts.add(Map.of(
                    "alertId", Base64.getEncoder().encodeToString(alertId), // Encode alertId as Base64 for readability
                    "alertType", alertType,
                    "latitude", latitude,
                    "longitude", longitude,
                    "confirmers", confirmers
            ));
        }

        return alerts;
    }

    // Get a specific alert by ID
    public Map<String, Object> getAlertById(String alertId) throws Exception {
        SatelliteSystem contract = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), (Credentials) null, new DefaultGasProvider());

        List<String> confirmers = contract.getAlertConfirmers(alertId.getBytes()).send();

        return Map.of(
                "alertId", alertId,
                "confirmers", confirmers
        );
    }

    public List<Map<String, Object>> getAllAlertSubmittedEvents() throws Exception {
        String defaultPrivateKey = "0xef43f9f547e1a70532ba05824bda6e90d58f1d8c44aeb75ec1354839ad4b7738";
        Credentials credentials = Credentials.create(defaultPrivateKey);
        SatelliteSystem contract = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), credentials, new DefaultGasProvider());

        BigInteger latestBlock = web3JSingleton.getWeb3jInstance()
                .ethBlockNumber()
                .send()
                .getBlockNumber();

        EthFilter filter = new EthFilter(
                DefaultBlockParameter.valueOf(BigInteger.ZERO),
                DefaultBlockParameter.valueOf(latestBlock),
                contractAddress
        );
        filter.addSingleTopic(EventEncoder.encode(SatelliteSystem.ALERTSUBMITTED_EVENT));

        // Use a blocking approach to wait for all events
        List<EthLog.LogResult> logResults = web3JSingleton.getWeb3jInstance()
                .ethGetLogs(filter)
                .send()
                .getLogs();

        List<Map<String, Object>> alertEvents = new ArrayList<>();

        for (EthLog.LogResult logResult : logResults) {
            EthLog.LogObject log = (EthLog.LogObject) logResult.get();
            SatelliteSystem.AlertSubmittedEventResponse event = contract.getAlertSubmittedEventFromLog(log);
            alertEvents.add(Map.of(
                    "alertId", event.alertId,
                    "alertType", event.alertType,
                    "latitude", event.latitude,
                    "longitude", event.longitude,
                    "sender", event.sender,
                    "transactionHash", log.getTransactionHash()
            ));
        }

        return alertEvents;
    }

}