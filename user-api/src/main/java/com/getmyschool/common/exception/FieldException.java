package com.getmyschool.common.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FieldException extends RuntimeException {

	private static final long serialVersionUID = -6985739455135667861L;

	private List<ObjectError> errorObjects;

	public FieldException(String exception) {
		super(exception);
	}

	public FieldException(List<ObjectError> errorObjects) {
		super();
		this.errorObjects = errorObjects;
	}

	public List<ObjectError> getErrorObjects() {
		return errorObjects;
	}

	public void setErrorObjects(List<ObjectError> errorObjects) {
		this.errorObjects = errorObjects;
	}

}
