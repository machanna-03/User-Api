package com.getmyschool.college.validator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.getmyschool.common.contant.Constant;
import com.getmyschool.common.dto.UserDTO;
import com.getmyschool.common.utils.DateUtils;
import com.getmyschool.common.utils.UserUtils;
import com.getmyschool.common.validator.CustomValidator;
import com.getmyschool.common.validator.RoleEnum;

public class UserValidator implements Validator {

	private static final String BAD_REQUEST_ERROR_CD = Constant.BAD_REQUEST_ERROR_CD;
	private static final Pattern VALID_EAMIL_PATTERN = Pattern.compile(Constant.EMAIL_PATTERN);
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(Constant.PASSWORD_PATTERN);
	private static final Pattern MOBILE_PATTERN = Pattern.compile(Constant.MOBILE_PATTERN);

	private static final List<String> VALID_UPDATE_STATUS = Arrays.asList(Constant.STATUS_ACTIVE,
			Constant.STATUS_DELEATED);

	@Autowired
	private UserUtils userUtils;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

	}

	public void saveUser(UserDTO userDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (!CustomValidator.isValidPattern(VALID_EAMIL_PATTERN, userDTO.getEmail()))
			errors.rejectValue("email", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(userDTO.getName()))
			errors.rejectValue("name", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (!CustomValidator.isValidPattern(MOBILE_PATTERN, userDTO.getPhoneNumber()))
			errors.rejectValue("phoneNumber", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null == userDTO.getRoles() || userDTO.getRoles().size() == 0) {
			errors.rejectValue("roles", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");
		} else {
			if (userDTO.getRoles().stream().anyMatch(role -> !RoleEnum.isInEnum(role, RoleEnum.class)))
				errors.rejectValue("role", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");
		}

		userDTO.setStatus(Constant.STATUS_ACTIVE);
		userDTO.setCreatedDate(createdTime);
		userDTO.setCreatedBy(logedUserid);
	}

	public void getAllUsers(UserDTO userDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (null == userDTO.getStatus())
			userDTO.setStatus(Constant.STATUS_ACTIVE);

		userDTO.setUpdatedDate(createdTime);
		userDTO.setUpdatedBy(logedUserid);

	}

	public void getUserById(UserDTO userDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(userDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		userDTO.setUpdatedDate(createdTime);
		userDTO.setUpdatedBy(logedUserid);

	}

	public void updateUser(UserDTO userDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(userDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != userDTO.getEmail() && !CustomValidator.isValidPattern(VALID_EAMIL_PATTERN, userDTO.getEmail()))
			errors.rejectValue("email", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != userDTO.getName() && CustomValidator.isEmpty(userDTO.getName()))
			errors.rejectValue("name", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != userDTO.getPhoneNumber()
				&& !CustomValidator.isValidPattern(MOBILE_PATTERN, userDTO.getPhoneNumber()))
			errors.rejectValue("phoneNumber", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (null != userDTO.getStatus() && !VALID_UPDATE_STATUS.contains(userDTO.getStatus()))
			errors.rejectValue("status", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		userDTO.setUpdatedDate(createdTime);
		userDTO.setUpdatedBy(logedUserid);

	}

	public void updateUserRoles(UserDTO userDTO, Errors errors) {
		Long logedUserid = userUtils.getLogedInUser();
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (CustomValidator.isEmpty(userDTO.getId()))
			errors.rejectValue("id", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(userDTO.getRoles())) {
			errors.rejectValue("roles", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");
		} else {
			for (String role : userDTO.getRoles()) {
				if (CustomValidator.isEmpty(role))
					errors.rejectValue("roles", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

				userDTO.setCreatedDate(createdTime);
				userDTO.setCreatedBy(logedUserid);
				userDTO.setUpdatedDate(createdTime);
				userDTO.setUpdatedBy(logedUserid);
			}
		}

	}

	public void login(UserDTO userDTO, Errors errors) {

		if (!CustomValidator.isValidPattern(VALID_EAMIL_PATTERN, userDTO.getEmail()))
			errors.rejectValue("email", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(userDTO.getPassword()))
			errors.rejectValue("password", BAD_REQUEST_ERROR_CD,
					" should contain atleast 8 chars with one lowerCase, upperCase, special char and number ");

	}

	public void signup(UserDTO userDTO, Errors errors) {
		String createdTime = DateUtils.getAsiaLocalDateTimeInCustomFormat();

		if (!CustomValidator.isValidPattern(VALID_EAMIL_PATTERN, userDTO.getEmail()))
			errors.rejectValue("email", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (CustomValidator.isEmpty(userDTO.getName()))
			errors.rejectValue("name", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (!CustomValidator.isValidPattern(MOBILE_PATTERN, userDTO.getPhoneNumber()))
			errors.rejectValue("phoneNumber", BAD_REQUEST_ERROR_CD, "is an empty or not in valid format");

		if (!CustomValidator.isValidPattern(PASSWORD_PATTERN, userDTO.getPassword()))
			errors.rejectValue("password", BAD_REQUEST_ERROR_CD,
					" should contain atleast 8 chars with one lowerCase, upperCase, special char and number ");

		userDTO.setStatus(Constant.STATUS_ACTIVE);
		userDTO.setCreatedDate(createdTime);

	}

	public void changePassword(UserDTO userDTO, Errors errors) {
		String createdDate = DateUtils.getAsiaLocalDateTimeInCustomFormat();
		Long logedUserid = userUtils.getLogedInUser();

		if (CustomValidator.isEmpty(userDTO.getPassword()))
			errors.rejectValue("password", BAD_REQUEST_ERROR_CD,
					" should contain atleast 8 chars with one lowerCase, upperCase, special char and number ");

		userDTO.setUpdatedDate(createdDate);
		userDTO.setUpdatedBy(logedUserid);

	}

}
