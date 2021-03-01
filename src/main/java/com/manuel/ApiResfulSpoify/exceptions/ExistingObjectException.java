package com.manuel.ApiResfulSpoify.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ExistingObjectException extends RuntimeException{
	private String exceptionDetail;
	
	public ExistingObjectException(String exceptionDetail ) {
		super(exceptionDetail);
		this.exceptionDetail=exceptionDetail;
	}
	
	public String getExceptionDetail() {
		return exceptionDetail;
	}

}
