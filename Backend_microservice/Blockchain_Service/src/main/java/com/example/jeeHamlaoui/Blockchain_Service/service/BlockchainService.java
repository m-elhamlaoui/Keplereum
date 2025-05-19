package com.example.jeeHamlaoui.Blockchain_Service.service;

import com.example.jeeHamlaoui.Blockchain_Service.web3j.RpcClientSingleton;
import com.example.jeeHamlaoui.Blockchain_Service.web3j.Web3JSingleton;
import com.example.jeeHamlaoui.Blockchain_Service.contracts.SatelliteSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.tx.RawTransactionManager;
import org.web3j.crypto.Credentials;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Numeric;

import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlockchainService {
    private final Web3JSingleton web3JSingleton;
    private final RpcClientSingleton rpcClientSingleton;
    private final ObjectMapper objectMapper;

    public BlockchainService(Web3JSingleton web3JSingleton, RpcClientSingleton rpcClientSingleton, ObjectMapper objectMapper) {
        this.web3JSingleton = web3JSingleton;
        this.rpcClientSingleton = rpcClientSingleton;
        this.objectMapper = objectMapper;
    }

    // Get all mempool transactions
    public List<JsonNode> getPendingTransactions() throws Exception {
        HttpURLConnection con = rpcClientSingleton.getConnection();

        String requestBody = """
            {
              "jsonrpc": "2.0",
              "method": "txpool_content",
              "params": [],
              "id": 1
            }
        """;

        try (OutputStream os = con.getOutputStream()) {
            os.write(requestBody.getBytes());
            os.flush();
        }

        JsonNode response = objectMapper.readTree(con.getInputStream());

        // Navigate to result.pending
        JsonNode pending = response.path("result").path("pending");

        List<JsonNode> allPendingTxs = new ArrayList<>();

        // Structure: pending[sender][nonce] = transaction
        for (Iterator<String> senders = pending.fieldNames(); senders.hasNext(); ) {
            String sender = senders.next();
            JsonNode nonceMap = pending.get(sender);

            for (Iterator<String> nonces = nonceMap.fieldNames(); nonces.hasNext(); ) {
                String nonce = nonces.next();
                JsonNode tx = nonceMap.get(nonce);
                allPendingTxs.add(tx);
            }
        }

        return allPendingTxs;
    }

    // Get a block by number
    public EthBlock getBlockByNumber(BigInteger blockNumber) throws Exception {
        return web3JSingleton.getWeb3jInstance()
                .ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNumber), true)
                .send();
    }

    // Get Validator Queue
    public List<String> getValidatorQueue() throws Exception {
        Request<?, EthAccounts> request = web3JSingleton.getWeb3jInstance().ethAccounts();
        ((Request<?, ?>) request).setMethod("clique_getSigners");
        request.setParams(Collections.emptyList());

        EthAccounts response = request.send();
        return response.getAccounts();
    }

    // Publish a transaction
    public String publishTransaction(String privateKey, String toAddress, BigInteger value,
                                     BigInteger gasPrice, BigInteger gasLimit) throws Exception {

        // Get the chain ID dynamically (or hardcode it if you know the network)
        BigInteger chainId = web3JSingleton.getWeb3jInstance().ethChainId().send().getChainId();

        // 3. Create credentials from the private key
        Credentials credentials = Credentials.create(privateKey);

        // 3. Build RawTransaction (like web3.js)
        BigInteger nonce = web3JSingleton.getWeb3jInstance()
                .ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.PENDING)
                .send()
                .getTransactionCount();

        RawTransaction rawTx = RawTransaction.createEtherTransaction(
                nonce,
                gasPrice,
                gasLimit,
                toAddress,
                value
        );

        // 4. Sign Offline (like web3.eth.accounts.signTransaction)
        byte[] signedMessage = TransactionEncoder.signMessage(rawTx, chainId.longValue(), credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        // 5. Send Signed Payload (like web3.eth.sendSignedTransaction)
        EthSendTransaction response = web3JSingleton.getWeb3jInstance()
                .ethSendRawTransaction(hexValue)
                .send();

        // 6. Error Handling (same as before)
        if (response.hasError()) {
            throw new RuntimeException("Transaction failed: " + response.getError().getMessage());
        }

        return response.getTransactionHash();
    }

    // Get a transaction by hash (with event details if applicable)
    public Map<String, Object> getTransactionByHash(String transactionHash) throws Exception {
        // Set up the connection to the RPC node
        HttpURLConnection con = rpcClientSingleton.getConnection();

        // Create the request body for 'eth_getTransactionByHash'
        String requestBodyTx = objectMapper.writeValueAsString(Map.of(
                "jsonrpc", "2.0",
                "method", "eth_getTransactionByHash",
                "params", List.of(transactionHash),
                "id", 1
        ));

        // Send the request to get the transaction
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = con.getOutputStream()) {
            os.write(requestBodyTx.getBytes());
            os.flush();
        }

        // Get the response and parse the transaction
        JsonNode txResponse = objectMapper.readTree(con.getInputStream());
        JsonNode transaction = txResponse.path("result");

        if (transaction.isNull()) {
            throw new RuntimeException("Transaction not found.");
        }

        // Create the request body for 'eth_getTransactionReceipt'
        String requestBodyReceipt = objectMapper.writeValueAsString(Map.of(
                "jsonrpc", "2.0",
                "method", "eth_getTransactionReceipt",
                "params", List.of(transactionHash),
                "id", 1
        ));

        // Send the request to get the receipt
        con = rpcClientSingleton.getConnection();  // Reinitialize the connection
        try (OutputStream os = con.getOutputStream()) {
            os.write(requestBodyReceipt.getBytes());
            os.flush();
        }

        // Get the response and parse the receipt
        JsonNode receiptResponse = objectMapper.readTree(con.getInputStream());
        JsonNode receipt = receiptResponse.path("result");

        // Return both the transaction and the receipt (or "pending" if no receipt)
        return Map.of(
                "transaction", transaction,
                "receipt", receipt.isNull() ? "pending" : receipt
        );
    }
}