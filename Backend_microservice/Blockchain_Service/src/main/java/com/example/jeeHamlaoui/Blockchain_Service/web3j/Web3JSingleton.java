package com.example.jeeHamlaoui.Blockchain_Service.web3j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Component
public class Web3JSingleton {
    private final Web3j web3j;

    public Web3JSingleton(@Value("${blockchain.load-balancer-url}") String nodeUrl) {
        this.web3j = Web3j.build(new HttpService(nodeUrl));
    }

    public Web3j getWeb3jInstance() {
        return web3j;
    }
}