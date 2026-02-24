package com.BankingApp.RepoLayer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BankingApp.Entity.MiniStatementEntity;

public interface MiniStatementRepo extends JpaRepository<MiniStatementEntity, Long>{

}
