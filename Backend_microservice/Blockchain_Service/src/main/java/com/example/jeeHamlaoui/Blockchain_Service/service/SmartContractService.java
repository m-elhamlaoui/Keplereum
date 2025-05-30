package com.example.jeeHamlaoui.Blockchain_Service.service;

import com.example.jeeHamlaoui.Blockchain_Service.utils.UtilityClass;
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
import java.util.*;

@Service
public class SmartContractService {
    private final Web3JSingleton web3JSingleton;
    private final String contractAddress;
    private final String defaultPrivate;

    public SmartContractService(Web3JSingleton web3JSingleton, @Value("${blockchain.contract-address}") String contractAddress, @Value("${blockchain.serverside-signing-private-key}") String defaultPrivate) {
        this.web3JSingleton = web3JSingleton;
        this.contractAddress = contractAddress;
        this.defaultPrivate = defaultPrivate;
    }

    public String submitAlert(String privateKey, String alertType, BigInteger latitude, BigInteger longitude) throws Exception {
        Credentials credentials = Credentials.create(privateKey);

        BigInteger chainId = web3JSingleton.getWeb3jInstance().ethChainId().send().getChainId();

        TransactionManager transactionManager = new RawTransactionManager(web3JSingleton.getWeb3jInstance(), credentials, chainId.longValue());

        SatelliteSystem contractWithChainId = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), transactionManager, new DefaultGasProvider());

        TransactionReceipt transactionReceipt = contractWithChainId.submitAlert(alertType, latitude, longitude).send();

        return transactionReceipt.getTransactionHash();
    }

    public Map<String, String> confirmAlert(String privateKey, String alertId) throws Exception {
        byte[] bytesAlertId = UtilityClass.hexToBytes(alertId);

        if (bytesAlertId.length != 32) {
            throw new IllegalArgumentException("alertId must be exactly 32 bytes long");
        }

        Credentials credentials = Credentials.create(privateKey);

        BigInteger chainId = web3JSingleton.getWeb3jInstance().ethChainId().send().getChainId();

        TransactionManager transactionManager = new RawTransactionManager(
                web3JSingleton.getWeb3jInstance(),
                credentials,
                chainId.longValue()
        );

        SatelliteSystem contract = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), transactionManager, new DefaultGasProvider());

        TransactionReceipt receipt = contract.confirmAlert(bytesAlertId).send();

        return Map.of(
                "transactionHash", receipt.getTransactionHash(),
                "message", "Alert confirmed successfully"
        );
    }

    public Map<String, String> triggerAction(String privateKey, String satellite, BigInteger action, String alertId) throws Exception {
        byte[] bytesAlertId = UtilityClass.hexToBytes(alertId);

        Credentials credentials = Credentials.create(privateKey);

        BigInteger chainId = web3JSingleton.getWeb3jInstance().ethChainId().send().getChainId();

        TransactionManager transactionManager = new RawTransactionManager(
                web3JSingleton.getWeb3jInstance(),
                credentials,
                chainId.longValue()
        );

        SatelliteSystem contract = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), transactionManager, new DefaultGasProvider());

        TransactionReceipt receipt = contract.triggerAction(satellite, action, bytesAlertId).send();

        return Map.of(
                "transactionHash", receipt.getTransactionHash(),
                "message", "Action triggered successfully"
        );
    }

    public List<Map<String, Object>> getAllGlobalAlerts() throws Exception {
        String defaultPrivateKey = defaultPrivate;
        Credentials credentials = Credentials.create(defaultPrivateKey);
        SatelliteSystem contract = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), credentials, new DefaultGasProvider());

        BigInteger latestBlock = web3JSingleton.getWeb3jInstance()
                .ethBlockNumber()
                .send()
                .getBlockNumber();

        EthFilter submittedFilter = new EthFilter(
                DefaultBlockParameter.valueOf(BigInteger.ZERO),
                DefaultBlockParameter.valueOf(latestBlock),
                contractAddress
        );
        submittedFilter.addSingleTopic(EventEncoder.encode(SatelliteSystem.ALERTSUBMITTED_EVENT));

        List<EthLog.LogResult> submittedLogResults = web3JSingleton.getWeb3jInstance()
                .ethGetLogs(submittedFilter)
                .send()
                .getLogs();

        EthFilter confirmedFilter = new EthFilter(
                DefaultBlockParameter.valueOf(BigInteger.ZERO),
                DefaultBlockParameter.valueOf(latestBlock),
                contractAddress
        );
        confirmedFilter.addSingleTopic(EventEncoder.encode(SatelliteSystem.ALERTCONFIRMED_EVENT));

        List<EthLog.LogResult> confirmedLogResults = web3JSingleton.getWeb3jInstance()
                .ethGetLogs(confirmedFilter)
                .send()
                .getLogs();

        Map<String, List<Map<String, Object>>> confirmationsMap = new HashMap<>();

        for (EthLog.LogResult logResult : confirmedLogResults) {
            EthLog.LogObject log = (EthLog.LogObject) logResult.get();
            List<String> topics = log.getTopics();

            String alertId = topics.get(1).substring(2); //-- Removed "0x" prefix --//
            String confirmer = "0x" + topics.get(2).substring(26);

            confirmationsMap.computeIfAbsent(alertId, k -> new ArrayList<>()).add(Map.of(
                    "publicAddress", confirmer,
                    "confirmationHash", log.getTransactionHash()
            ));
        }

        List<Map<String, Object>> globalAlerts = new ArrayList<>();
        for (EthLog.LogResult logResult : submittedLogResults) {
            EthLog.LogObject log = (EthLog.LogObject) logResult.get();
            SatelliteSystem.AlertSubmittedEventResponse event = contract.getAlertSubmittedEventFromLog(log);

            String normalizedAlertId = UtilityClass.bytesToHex(event.alertId);

            globalAlerts.add(Map.of(
                    "alertId", normalizedAlertId,
                    "alertType", event.alertType,
                    "latitude", event.latitude,
                    "longitude", event.longitude,
                    "transactionHash", log.getTransactionHash(),
                    "confirmations", confirmationsMap.getOrDefault(normalizedAlertId, new ArrayList<>())
            ));
        }

        return globalAlerts;
    }

    public Map<String, Object> getAlertById(String alertIdHex) throws Exception {
        byte[] alertIdBytes = UtilityClass.hexToBytes(alertIdHex);
        String defaultPrivateKey = defaultPrivate;
        Credentials credentials = Credentials.create(defaultPrivateKey);

        SatelliteSystem contract = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), credentials, new DefaultGasProvider());

        List<String> confirmers = contract.getAlertConfirmers(alertIdBytes).send();

        return Map.of(
                "alertId", alertIdHex,
                "confirmers", confirmers
        );
    }

    public List<Map<String, Object>> getAllValidations() throws Exception {
        String defaultPrivateKey = defaultPrivate;
        Credentials credentials = Credentials.create(defaultPrivateKey);
        SatelliteSystem contract = SatelliteSystem.load(contractAddress, web3JSingleton.getWeb3jInstance(), credentials, new DefaultGasProvider());

        BigInteger latestBlock = web3JSingleton.getWeb3jInstance()
                .ethBlockNumber()
                .send()
                .getBlockNumber();

        EthFilter actionFilter = new EthFilter(
                DefaultBlockParameter.valueOf(BigInteger.ZERO),
                DefaultBlockParameter.valueOf(latestBlock),
                contractAddress
        );
        actionFilter.addSingleTopic(EventEncoder.encode(SatelliteSystem.ACTIONTRIGGERED_EVENT));

        List<EthLog.LogResult> actionLogResults = web3JSingleton.getWeb3jInstance()
                .ethGetLogs(actionFilter)
                .send()
                .getLogs();

        List<Map<String, Object>> validations = new ArrayList<>();
        for (EthLog.LogResult logResult : actionLogResults) {
            EthLog.LogObject log = (EthLog.LogObject) logResult.get();
            List<String> topics = log.getTopics();

            String alertId = topics.get(2).substring(2);
            String toAddress = "0x" + topics.get(1).substring(26);

            String data = log.getData();
            if (data == null || data.length() < 64) {
                System.err.println("Invalid data field: " + data);
                continue;
            }

            try {
                if (data.startsWith("0x")) {
                    data = data.substring(2);
                }

                BigInteger actionTypeValue = new BigInteger(data.substring(0, 64), 16);
                String actionType = actionTypeValue.equals(BigInteger.ZERO) ? "SWITCH_ORBIT" : "SWITCH_SENSOR";

                String transactionHash = log.getTransactionHash();

                String validatorAddress = web3JSingleton.getWeb3jInstance()
                        .ethGetTransactionByHash(transactionHash)
                        .send()
                        .getTransaction()
                        .orElseThrow(() -> new RuntimeException("Transaction not found"))
                        .getFrom();

                validations.add(Map.of(
                        "alertId", alertId,
                        "actionType", actionType,
                        "validatorAddress", validatorAddress,
                        "toAddress", toAddress,
                        "transactionHash", transactionHash
                ));
            } catch (NumberFormatException e) {
                System.err.println("Failed to parse actionType from data: " + data);
            }
        }

        return validations;
    }

}