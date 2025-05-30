package com.example.jeeHamlaoui.Blockchain_Service.model;

import com.example.jeeHamlaoui.Blockchain_Service.model.enumerate.ActionType;
import jakarta.persistence.*;

@Entity
@Table(name = "validator_actions")
@DiscriminatorValue("VALIDATOR_ACTION")
public class ValidatorAction extends AbstractTransactionType {

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    public ValidatorAction() {
    }

    public ValidatorAction(ActionType actionType) {
        this.actionType = actionType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
// Getters and setters
}