package com.example.jeeHamlaoui.Blockchain_Service.web3j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class RpcClientSingleton {

    private final URL rpcUrl;

    public RpcClientSingleton(@Value("${blockchain.load-balancer-url}") String nodeUrl) {
        try {
            this.rpcUrl = new URL(nodeUrl);
        } catch (Exception e) {
            throw new RuntimeException("Invalid RPC URL: " + nodeUrl, e);
        }
    }

    public HttpURLConnection getConnection() throws IOException {
        HttpURLConnection con = (HttpURLConnection) rpcUrl.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        return con;
    }

    public String getRpcUrl() {
        return rpcUrl.toString();
    }
}
