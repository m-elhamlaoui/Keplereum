package com.example.jeeHamlaoui.Blockchain_Service.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "abstract_transaction_types")
@DiscriminatorColumn(name = "transaction_type")
public abstract class AbstractTransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "transactionType")
    private BlockTransaction blockTransaction;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    // Synchronization method
    public void setBlockTransaction(BlockTransaction blockTransaction) {
        if (this.blockTransaction != null) {
            this.blockTransaction.setTransactionType(null);  // Unlink old reference
        }
        this.blockTransaction = blockTransaction;
        if (blockTransaction != null && blockTransaction.getTransactionType() != this) {
            blockTransaction.setTransactionType(this);  // Link new reference
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BlockTransaction getBlockTransaction() {
        return blockTransaction;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
// Getters and setters
}