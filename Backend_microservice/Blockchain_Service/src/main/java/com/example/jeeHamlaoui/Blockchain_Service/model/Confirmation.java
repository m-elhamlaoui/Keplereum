package com.example.jeeHamlaoui.Blockchain_Service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "confirmations")
public class Confirmation extends AbstractTransactionType {

    // Inherits 'createdAt' from parent
    // Add additional fields if needed
}