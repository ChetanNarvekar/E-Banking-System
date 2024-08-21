    package com.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.BankDao;
import com.app.dto.BankDTO;
import com.app.entity.bank.Bank;
import com.app.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class BankServiceImpl implements BankService {

    @Autowired
    private BankDao bankDao;

    @Autowired
    private ModelMapper mapper;

    /**
     * Adds a new bank.
     *
     * @param bank the BankDTO containing bank details.
     * @return a success message.
     */
    @Override
    public String addBank(BankDTO bank) {
        Bank bankEntity = mapper.map(bank, Bank.class);
        bankDao.save(bankEntity);
        return "Bank added successfully";
    }

    /**
     * Retrieves the bank details.
     *
     * @return the Bank entity.
     * @throws ResourceNotFoundException if the bank is not found.
     */
    @Override
    public Bank getBankDetails() {
        return bankDao.getBankDetails().orElseThrow(() -> new ResourceNotFoundException("Bank not found"));
    }

    @Override
	public List<Bank> getAllBankDetails() {
		// TODO Auto-generated method stub
		return bankDao.findAll();
	}
    
}
