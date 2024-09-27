package com.getmyschool.common.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.getmyschool.common.contant.Constant;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GetMySchoolException extends ResponseEntityExceptionHandler {

	private Map<String, String> errorsMap;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		// log.info("{} to {}", servletWebRequest.getHttpMethod(),
		// servletWebRequest.getRequest().getServletPath());
		String error = "Malformed JSON request";

		errorsMap = new LinkedHashMap<String, String>();
		errorsMap.put(Constant.RESPONSE_CODE_KEY, Constant.BAD_REQUEST_ERROR_CD);
		errorsMap.put(Constant.RESPONSE_MSG_KEY, error);
		return new ResponseEntity(errorsMap, HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorDetails error = new ErrorDetails(new Date(), "Server Error", request.getDescription(false));
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		errorsMap = new LinkedHashMap<String, String>();
		errorsMap.put(Constant.RESPONSE_CODE_KEY, "404");
		errorsMap.put(Constant.RESPONSE_MSG_KEY, ex.getMessage());
		return new ResponseEntity(errorsMap, HttpStatus.NOT_FOUND);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(FieldException.class)
	public final ResponseEntity<Object> fieldException(FieldException ex, WebRequest request) {
		StringBuilder buffer = null;
		errorsMap = new LinkedHashMap<String, String>();
		if (null != ex.getErrorObjects()) {
			for (ObjectError error : ex.getErrorObjects()) {
				if (error instanceof FieldError) {
					FieldError fieldError = (FieldError) error;
					if (buffer != null) {
						buffer.append(" ,");
					} else {
						buffer = new StringBuilder();
					}
					buffer.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage());
				} else if (error instanceof ObjectError) {
					if (buffer != null) {
						buffer.append(" ,");
					} else {
						buffer = new StringBuilder();
					}
					buffer.append(error.getObjectName()).append(" - ").append(error.getDefaultMessage());
				}
			}
		} else {
			buffer = new StringBuilder();
			buffer.append(ex.getMessage());
		}
		errorsMap.put(Constant.RESPONSE_CODE_KEY, Constant.BAD_REQUEST_ERROR_CD);
		errorsMap.put(Constant.RESPONSE_MSG_KEY, buffer.toString());
		return new ResponseEntity(errorsMap, HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(UnAuthorizedException.class)
	public final ResponseEntity<Object> UnAuthorizedException(UnAuthorizedException ex, WebRequest request) {
		errorsMap = new LinkedHashMap<String, String>();
		errorsMap.put(Constant.RESPONSE_CODE_KEY, "401");
		errorsMap.put(Constant.RESPONSE_MSG_KEY, ex.getErrorMessage());
		return new ResponseEntity(errorsMap, HttpStatus.UNAUTHORIZED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public final ResponseEntity<Object> maxUploadSizeExceededException(MaxUploadSizeExceededException ex,
			WebRequest request) {
		errorsMap = new LinkedHashMap<String, String>();
		errorsMap.put(Constant.RESPONSE_CODE_KEY, Constant.BAD_REQUEST_ERROR_CD);
		errorsMap.put(Constant.RESPONSE_MSG_KEY, "Upload file size exceeded than 5MB.");
		return new ResponseEntity(errorsMap, HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(InvalidFormatException.class)
	public final ResponseEntity<Object> invalidFormatException(InvalidFormatException ex, WebRequest request) {
		errorsMap = new LinkedHashMap<String, String>();
		errorsMap.put(Constant.RESPONSE_CODE_KEY, Constant.BAD_REQUEST_ERROR_CD);
		errorsMap.put(Constant.RESPONSE_MSG_KEY, "Upload file size exceeded than 5MB.");
		return new ResponseEntity(errorsMap, HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> DataIntegrityViolationException(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity("entity does not present", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InterruptExitException.class)
	protected ResponseEntity<Object> InterruptExitException(InterruptExitException ex) {
		return new ResponseEntity(ex.getReturnMap(), HttpStatus.OK);
	}

}