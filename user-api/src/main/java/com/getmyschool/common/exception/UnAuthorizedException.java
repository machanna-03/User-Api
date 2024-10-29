package com.getmyschool.common.exception;

public class UnAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 2874511010386441607L;
	private String errorMessage;

	public UnAuthorizedException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
