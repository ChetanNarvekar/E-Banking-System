package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entity.bank.Bank;

@Repository
public interface BankDao extends JpaRepository<Bank,String > {
	@Query(value="select * from Bank  LIMIT 1",nativeQuery=true)
	Optional<Bank> getBankDetails();
	
}
