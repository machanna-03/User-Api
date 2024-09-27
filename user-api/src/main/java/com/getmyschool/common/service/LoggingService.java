package com.getmyschool.common.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface LoggingService {

	public StringBuilder displayReq(HttpServletRequest request, Object body) throws JsonProcessingException;

	public StringBuilder displayResp(HttpServletRequest request, HttpServletResponse response, Object body)
			throws JsonProcessingException;

}