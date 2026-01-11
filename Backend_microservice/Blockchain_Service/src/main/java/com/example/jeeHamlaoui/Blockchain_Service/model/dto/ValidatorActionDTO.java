package com.example.jeeHamlaoui.Blockchain_Service.model.dto;

import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.ActionType;
import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.TransactionType;

public class ValidatorActionDTO extends TransactionTypeDTO {
    private ActionType actionType;

    @Override
    public TransactionType getType() {
        return TransactionType.VALIDATOR_ACTION;
    }
    // Getters and setters

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}