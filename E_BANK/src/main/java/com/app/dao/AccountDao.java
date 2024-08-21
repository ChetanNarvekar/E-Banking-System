package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.account.Account;
import com.app.entity.enums.AccountStatus;

@Repository
public interface AccountDao extends JpaRepository<Account, String > {
	@Query("select a from Account a where a.status= 'DEACTIVATED'")
	List<Account>getAllDeactivatedAccount();
	
	@Query("select a from Account a where a.status= 'SUSPENDED'")
	List<Account>getAllSuspendedAccount();
	
	@Query("select a from Account a where a.status= 'ACTIVATED' ")
	List<Account>getAllActivatedAccount();
	
	Optional<Account>findByStatus(AccountStatus status);
	
	@Query("SELECT u FROM Account u WHERE u.request = :request")
    Account findAccountByRequestId(@Param("request") String request);

	Account findByAccountNo(String accountNo);
	
	


}
