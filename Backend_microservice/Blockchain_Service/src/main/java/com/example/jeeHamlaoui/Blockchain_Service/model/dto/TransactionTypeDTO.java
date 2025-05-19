package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AlertDTO.class, name = "ALERT"),
        @JsonSubTypes.Type(value = ConfirmationDTO.class, name = "CONFIRMATION"),
        @JsonSubTypes.Type(value = ValidatorActionDTO.class, name = "VALIDATOR_ACTION")
})
public abstract class TransactionTypeDTO {
    public abstract TransactionType getType();
}
