package com.app.loan.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Data Transfer Object (DTO) for representing a standardized API response.
 * This class includes a message and a time-stamp for when the response was created.
 */

@NoArgsConstructor
@Getter
@Setter
public class ApiResponse {
	
	
	/**
     * A message providing details about the API response.
     * This can be used to convey information or status to the client.
     */
    @NotBlank(message = "Message cannot be blank")
	private String message;
	
	/**
     * The time-stamp indicating when the API response was created.
     * This helps track when the response was generated.
     */
	private LocalDateTime timeStamp;

	
	/**
     * Constructor to create an ApiResponse with a specified message.
     * The time-stamp is automatically set to the current date and time.
     *
     * @param message The message to be included in the API response.
     */
	public ApiResponse(String message) {
		super();
		this.message = message;
		this.timeStamp = LocalDateTime.now();
	}

}
