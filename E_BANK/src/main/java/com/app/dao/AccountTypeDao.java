package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.account.AccountType;

@Repository
public interface AccountTypeDao extends JpaRepository<AccountType, String> {
	Optional<AccountType> findByAccTypeName(String accType);

	boolean existsByAccTypeName(String acc);
}
