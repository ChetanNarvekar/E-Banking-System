package com.app.loan.dao;

import java.util.List;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.loan.entities.Request;

public interface RequestDao extends JpaRepository<Request, String>{
	
	@Query("SELECT u FROM Request u WHERE u.status = 'P'")
	List<Request> findAllByStatusWithPending();

	@Query("SELECT s FROM Request s WHERE s.status = 'R' ")
	List<Request> findAllByStatusWithRequested();
	
	@Query("SELECT u FROM Request u WHERE u.status = 'A'")
	List<Request> findAllByStatusWithApproved();
	
	@Query("SELECT u FROM Request u WHERE u.status = 'D'")
	List<Request> findAllByStatusWithDeclined();

	List<Request> findByAccountAccountNo(String accountNo);


}
