package com.example.jeeHamlaoui.Blockchain_Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BlockNotFoundException extends RuntimeException {
    public BlockNotFoundException(Long blockHeight) {
        super("Block Not Found: " + blockHeight);
    }
}
