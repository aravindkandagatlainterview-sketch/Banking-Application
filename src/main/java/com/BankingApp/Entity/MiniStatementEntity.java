package com.BankingApp.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class MiniStatementEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long txnId;

    private long accountId;
    private String accountHolderName;

    private String txnType; 
    private double amount;
    private double balanceAfter;

    private LocalDateTime txnDate;


    public MiniStatementEntity(long accountId, String accountHolderName,
                               String txnType, double amount,
                               double balanceAfter) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.txnType = txnType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.txnDate = LocalDateTime.now();
    }

	
}
