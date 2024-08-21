package com.app.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entity.customer.Customer;
import com.app.entity.enums.Role;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Long>{

	@Query("SELECT c FROM Customer c WHERE c.status = false")
    java.util.List<Customer> findByStatusFalse();

	Optional<Customer> findByEmail(String email);
	
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    
    List<Customer> findByRole(Role role);
}
 

