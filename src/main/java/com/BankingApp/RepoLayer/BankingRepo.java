package com.BankingApp.RepoLayer;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.BankingApp.Entity.AccountEntity;

@Repository
public interface BankingRepo extends JpaRepository<AccountEntity, Long> {

	AccountEntity findByAcholdername(String acholdername);
	boolean existsByAccountNumber(String accountNumber);
	AccountEntity findByAccountNumber(String accountNumber);
	boolean existsByPhonenumber(Long phonenumber); 
	
	
}
