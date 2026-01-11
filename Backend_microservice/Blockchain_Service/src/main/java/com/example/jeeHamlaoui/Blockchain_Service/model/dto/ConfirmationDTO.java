package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionType;

public class ConfirmationDTO extends TransactionTypeDTO {
    @Override
    public TransactionType getType() {
        return TransactionType.CONFIRMATION;
    }
}