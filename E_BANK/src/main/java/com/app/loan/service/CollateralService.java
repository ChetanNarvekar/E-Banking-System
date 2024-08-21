package com.app.loan.service;

import com.app.loan.dto.ApiResponse;
import com.app.loan.dto.CollateralDto;


/**
 * Service interface for handling operations related to `Collateral` entities.
 * This interface defines the methods available for interacting with collateral data.
 */
public interface CollateralService {
	
	
	/**
     * Adds a new collateral to the system.
     * This method processes the provided `CollateralDto` to create and persist a new `Collateral` entity.
     *
     * @param collateralDto The data transfer object (DTO) containing information about the collateral to be added.
     *                      It includes details such as asset, value, and description.
     * @return An `ApiResponse` object containing the result of the operation, including any messages or status information.
     */
	ApiResponse addCollateral(CollateralDto collateralDto);
}
