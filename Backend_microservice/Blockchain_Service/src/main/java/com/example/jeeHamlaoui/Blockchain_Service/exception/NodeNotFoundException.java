package com.example.jeeHamlaoui.Blockchain_Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NodeNotFoundException extends RuntimeException {
    public NodeNotFoundException(String senderPublicKey) {
        super("Node Not found with public key"+senderPublicKey);
    }
}
