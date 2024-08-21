package com.app.dto;

import java.time.LocalDateTime;

public class ApiResponse {
	private String msg;
	private LocalDateTime timestamp;
	
	public ApiResponse(String msg)
	{
		this.msg=msg;
		timestamp=LocalDateTime.now();
	}
}
