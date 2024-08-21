package com.app.loan.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.loan.dao.RequestDao;
import com.app.loan.dto.ApiResponse;
import com.app.loan.dto.CollateralDto;
import com.app.loan.entities.Collateral;
import com.app.loan.entities.Request;


/**
 * Implementation of the `CollateralService` interface.
 * This class provides the concrete implementation of the service methods for managing collateral data.
 */
@Service
@Transactional
public class CollateralServiceImpl implements CollateralService{
	
	//collDao
	
	// DAO for accessing request data from the database
	@Autowired
	private RequestDao reqDao;
	
	
    // ModelMapper for mapping between DTOs and entities
	@Autowired
	private ModelMapper mapper;

	
	 /**
     * Adds a new collateral to the system.
     * This method processes the provided `CollateralDto` to create and associate a new `Collateral` entity with
     * a specific loan request.
     *
     * @param collateralDto The data transfer object (DTO) containing information about the collateral to be added.
     *                      It includes details such as asset, value, description, and requestId.
     * @return An `ApiResponse` object containing the result of the operation, including a success message.
     */
	@Override
	public ApiResponse addCollateral(CollateralDto collateralDto) {
		
		
        // Retrieve the corresponding Request entity using the request ID from the DTO
		Request req = reqDao.findById(collateralDto.getRequestId()).orElseThrow();
		
        // Map the DTO to a Collateral entity
		Collateral collateral = mapper.map(collateralDto, Collateral.class);
		
        // Associate the new collateral with the retrieved request
		req.addCollateral(collateral);
		
        // Return a response indicating that the collateral was successfully added
		return new ApiResponse("Added a new Collateral");
	}
	
	
	
}
