package com.getmyschool.college.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.getmyschool.college.service.UserService;
import com.getmyschool.college.validator.UserValidator;
import com.getmyschool.common.contant.Constant;
import com.getmyschool.common.dto.UserDTO;
import com.getmyschool.common.exception.FieldException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	private LinkedHashMap<String, Object> returnMap;

	private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

//	@RequestMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//	public ResponseEntity<LinkedHashMap<String, Object>> signup(@RequestBody UserDTO userDTO,
//			HttpServletRequest request, BindingResult result) throws Exception {
//		HashMap<String, String> map = new HashMap<String, String>();
//		MapBindingResult err = new MapBindingResult(map, UserDTO.class.getName());
//		userValidator.signup(userDTO, err);
//		List<ObjectError> list = err.getAllErrors();
//		if (list.size() > 0)
//			throw new FieldException(list);
//
//		userService.signup(userDTO);
//		returnMap = new LinkedHashMap<String, Object>();
//		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
//		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
//		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
//	}

	@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> login(@RequestBody UserDTO userDTO, HttpServletRequest request,
			BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, UserDTO.class.getName());
		userValidator.login(userDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		returnMap = userService.login(userDTO);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> addUser(@RequestBody UserDTO userDTO,
			HttpServletRequest request, BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, UserDTO.class.getName());
		userValidator.saveUser(userDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		userService.saveUser(userDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> changePassword(@RequestBody UserDTO userDTO,
			HttpServletRequest request, BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, UserDTO.class.getName());
		userValidator.changePassword(userDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		userService.changePassword(userDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getUsers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getUsers(@RequestBody UserDTO userDTO,
			HttpServletRequest request, BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, UserDTO.class.getName());
		userValidator.getAllUsers(userDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		List<UserDTO> users = userService.getAllUsers(userDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("users", users);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> getUser(@RequestBody UserDTO userDTO,
			HttpServletRequest request, BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, UserDTO.class.getName());
		userValidator.getUserById(userDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		UserDTO users = userService.getUserById(userDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		returnMap.put("users", users);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/updateUser", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> updateUser(@RequestBody UserDTO userDTO,
			HttpServletRequest request, BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, UserDTO.class.getName());
		userValidator.updateUser(userDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		userService.updateUser(userDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@RequestMapping(value = "/updateUserRoles", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public ResponseEntity<LinkedHashMap<String, Object>> updateUserRoles(@RequestBody UserDTO userDTO,
			HttpServletRequest request, BindingResult result) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		MapBindingResult err = new MapBindingResult(map, UserDTO.class.getName());
		userValidator.updateUserRoles(userDTO, err);
		List<ObjectError> list = err.getAllErrors();
		if (list.size() > 0)
			throw new FieldException(list);

		userService.updateUserRoles(userDTO);
		returnMap = new LinkedHashMap<String, Object>();
		returnMap.put("responseCode", Constant.SUCCESSFULL_CODE);
		returnMap.put("responseMessage", Constant.SUCCESSFULL_MSG);
		return ResponseEntity.status(HttpStatus.OK).body(returnMap);
	}

	@Bean
	public UserValidator getUsersValidator() {
		return new UserValidator();
	}

}
