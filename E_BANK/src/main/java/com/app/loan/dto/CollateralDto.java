package com.app.loan.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Data Transfer Object (DTO) for representing collateral details.
 * This class is used to transfer collateral information between different layers of the application.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CollateralDto {
	
	
	/**
     * The asset associated with the collateral.
     * This field represents the type of asset that is being used as collateral.
     */
    @NotBlank(message = "Asset cannot be blank")
	private String asset;
	
	
	/**
     * The value of the collateral asset.
     * This field indicates the monetary value assigned to the collateral.
     */
    @NotNull(message = "Value cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Value must be greater than 0")
	private float value;
	
	
	 /**
     * A description of the collateral.
     * This field provides additional details or context about the collateral asset.
     */
	private String description;
	
	
	 /**
     * The ID of the associated request.
     * This field is used for internal purposes and is excluded from the JSON serialization
     * when reading (i.e., not exposed to clients), but can be written to the server when
     * creating or updating collateral.
     */
	@JsonProperty(access = Access.WRITE_ONLY)
	private String requestId;
}
