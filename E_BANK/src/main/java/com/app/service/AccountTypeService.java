package com.app.service;

import java.util.List;

import com.app.dto.AccountTypeDTO;
import com.app.dto.ApiResponse;
import com.app.entity.account.AccountType;
import com.app.response.dto.AccountTypeAllDetailsDto;

public interface AccountTypeService {

    AccountTypeAllDetailsDto getAccountType(String acc);

    List<AccountType> getAllAccountType();
	String updateAccountType(String acc, float interestRate);
	String addAccType(AccountTypeDTO acc);
}
