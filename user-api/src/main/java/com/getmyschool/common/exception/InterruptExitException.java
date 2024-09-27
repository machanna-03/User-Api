package com.getmyschool.common.exception;

import java.util.LinkedHashMap;

/**
 * 
 * @author UVITL-2
 *
 */
public class InterruptExitException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private LinkedHashMap<String, String> returnMap;

	public InterruptExitException(LinkedHashMap<String, String> returnMap) {
		super();
		this.returnMap = returnMap;
	}

	public LinkedHashMap<String, String> getReturnMap() {
		return returnMap;
	}

	public void setReturnMap(LinkedHashMap<String, String> returnMap) {
		this.returnMap = returnMap;
	}

}