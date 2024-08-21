package com.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AccountTypeDao;
import com.app.dto.AccountTypeDTO;
import com.app.dto.ApiResponse;
import com.app.entity.account.AccountType;
import com.app.exceptions.ResourceNotFoundException;
import com.app.response.dto.AccountTypeAllDetailsDto;

@Service
@Transactional
public class AccountTypeSeviceImpl implements AccountTypeService {

    @Autowired
    private AccountTypeDao accountTypeDao;

    @Autowired
    private ModelMapper mapper;

    /**
     * Retrieves the details of a specific account type by its name.
     *
     * @param accountTypeName the name of the account type.
     * @return the AccountTypeDTO containing account type details.
     * @throws ResourceNotFoundException if the account type is not found.
     */
    @Override
    public AccountTypeAllDetailsDto getAccountType(String accountTypeName) {
        AccountType accountType = accountTypeDao.findByAccTypeName(accountTypeName)
                .orElseThrow(() -> new ResourceNotFoundException("No such account type"));
        return mapper.map(accountType, AccountTypeAllDetailsDto.class);
    }

    /**
     * Retrieves a list of all account types.
     *
     * @return a list of AccountType entities.
     */
    @Override
    public List<AccountType> getAllAccountType() {
        return accountTypeDao.findAll();
    }

    /**
     * Updates the interest rate of a specific account type.
     *
     * @param accountTypeName the name of the account type to update.
     * @param interestRate the new interest rate to set.
     * @return a success message if updated, otherwise a failure message.
     */
    @Override
    public String updateAccountType(String accountTypeName, float interestRate) {
        if (accountTypeDao.existsByAccTypeName(accountTypeName)) {
            AccountType accountType = accountTypeDao.findByAccTypeName(accountTypeName)
                    .orElseThrow(() -> new ResourceNotFoundException("No such account type"));
            accountType.setInterestRate(interestRate);
            accountTypeDao.save(accountType);
            return "Account type updated successfully";
        }
        return "Failed to update account type";
    }

    /**
     * Adds a new account type.
     *
     * @param accountTypeDto the AccountTypeDTO containing account type details.
     * @return an ApiResponse indicating success.
     */
    @Override
    public String addAccType(AccountTypeDTO accountTypeDto) {
        AccountType accountType = mapper.map(accountTypeDto, AccountType.class);
        if(accountTypeDao.existsByAccTypeName(accountType.getAccTypeName()))
        {
        	return "AccountType is already present";
        }
        accountTypeDao.save(accountType);
        return "success";
    }
}
