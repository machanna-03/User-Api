package com.getmyschool.common.service;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("LoggingServiceImpl")
public class LoggingServiceImpl implements LoggingService {

	Logger logger = LoggerFactory.getLogger("LoggingServiceImpl");

	private ObjectMapper mapper = new ObjectMapper();
	private StringBuilder builder = new StringBuilder();

	@Override
	public StringBuilder displayReq(HttpServletRequest request, Object body) throws JsonProcessingException {
		builder = new StringBuilder();

		Map<String, String> parameters = getParameters(request);
		Map<String, String> headers = getHeaders(request);

		builder.append("path=").append(request.getRequestURI());

		if (!headers.isEmpty()) {
			builder.append(", headers = [").append(headers).append("] ");
		}

		builder.append(", method = [").append(request.getMethod()).append("]");

		if (!parameters.isEmpty()) {
			builder.append(", parameters = [").append(parameters).append("] ");
		}

		mapper.setSerializationInclusion(Include.NON_NULL);
		if (!Objects.isNull(body)) {
			builder.append(", requestBody = [").append(mapper.writeValueAsString(body)).append("]");
		}
		return builder;

	}

	@Override
	public StringBuilder displayResp(HttpServletRequest request, HttpServletResponse response, Object body)
			throws JsonProcessingException {
		builder = new StringBuilder();
		builder.append(", responseBody = [").append(mapper.writeValueAsString(body)).append("]");
		return builder;
	}

	@SuppressWarnings("unused")
	private Map<String, String> getHeaders(HttpServletResponse response) {
		Map<String, String> headers = new HashMap<>();
		Collection<String> headerMap = response.getHeaderNames();
		for (String str : headerMap) {
			headers.put(str, response.getHeader(str));
		}
		return headers;
	}

	private Map<String, String> getHeaders(HttpServletRequest request) {
		Map<String, String> headers = new HashMap<>();
		Enumeration<String> headerMap = request.getHeaderNames();
		while (headerMap.hasMoreElements()) {
			String paramName = headerMap.nextElement();
			String paramValue = request.getHeader(paramName);
			headers.put(paramName, paramValue);
		}
		return headers;
	}

	private Map<String, String> getParameters(HttpServletRequest request) {
		Map<String, String> parameters = new HashMap<>();
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			String paramValue = request.getParameter(paramName);
			parameters.put(paramName, paramValue);
		}
		return parameters;
	}

}