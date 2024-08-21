package com.app.loan.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.loan.dao.LoanDetailsDao;
import com.app.loan.dto.ApiResponse;
import com.app.loan.dto.LoanDetailsDto;
import com.app.loan.entities.LoanDetails;
import com.app.loan.entities.Request;


/**
 * Implementation of the `LoanDetailsService` interface.
 * This class provides the concrete implementation for managing loan details.
 */
@Service
@Transactional
public class LoanDetailsServiceImpl implements LoanDetailsService{

	
    // DAO for accessing loan details data from the database
	@Autowired
	private LoanDetailsDao loTyDao;
	
	
    // ModelMapper for mapping between DTOs and entities
	@Autowired
	private ModelMapper mapper;
	
	
	/**
     * Adds new loan details to the system.
     * This method processes the provided `LoanDetailsDto` to create and persist a new `LoanDetails` entity.
     * 
     * It first checks if the loan type already exists in the database. If it does, it returns an appropriate response.
     * If the loan type does not exist, it maps the DTO to an entity, saves it, and returns a success response.
     * 
     * @param loanDetails The data transfer object (DTO) containing the details to be added.
     * @return An `ApiResponse` object indicating the result of the operation.
     */
	@Override
	public ApiResponse addNewLoanDetails(LoanDetailsDto loanDetails) {
		
        // Check if the loan type already exists in the database
		if(loTyDao.existsById(loanDetails.getLoanName())) {
			return new ApiResponse("Given type already present in table");
		}else {
            // Map the DTO to a LoanDetails entity
			LoanDetails loanDetailsC = mapper.map(loanDetails, LoanDetails.class);
            // Save the new LoanDetails entity to the database
			loTyDao.save(loanDetailsC);
            // Return a success response
			return new ApiResponse("LoanDetails Created");
		}
		
	}

	
	/**
     * Removes loan details from the system based on the provided loan type name.
     * This method deletes the `LoanDetails` entity identified by the given loan type name.
     * 
     * It first checks if the loan type exists in the database. If it does, it deletes the entity and returns a success response.
     * If the loan type does not exist, it returns an appropriate response.
     * 
     * @param loanTypeName The name of the loan type to be removed.
     * @return An `ApiResponse` object indicating the result of the operation.
     */
	@Override
	public ApiResponse removeLoanDretails(String loanTypeName) {
		
        // Check if the loan type exists in the database
		if(loTyDao.existsById(loanTypeName)) {
            // Delete the LoanDetails entity from the database
			loTyDao.deleteById(loanTypeName);
            // Return a success response
			return new ApiResponse("Deleted Successfully");
		}else {
            // Return a response indicating the entity was not found
			return new ApiResponse("LoanDetails is not present");
		}
	}


	@Override
	public List<Request> getAllLoansByLoanName(String loanName) {
		if(loTyDao.existsById(loanName)) {
			return loTyDao.findLoanDetailsByLoanName(loanName) ;
		}
		else{
			return null;
		}
		
	}

}
