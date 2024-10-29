package com.getmyschool.college.serviceimpl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.getmyschool.college.config.JwtTokenUtil;
import com.getmyschool.college.service.UserService;
import com.getmyschool.common.cache.UserCache;
import com.getmyschool.common.contant.Constant;
import com.getmyschool.common.converter.UserConverter;
import com.getmyschool.common.dao.RoleDao;
import com.getmyschool.common.dao.UserDao;
import com.getmyschool.common.domain.Role;
import com.getmyschool.common.domain.User;
import com.getmyschool.common.dto.RoleDTO;
import com.getmyschool.common.dto.UserDTO;
import com.getmyschool.common.exception.FieldException;
import com.getmyschool.common.exception.InterruptExitException;
import com.getmyschool.common.exception.UnAuthorizedException;
import com.getmyschool.common.service.LoginService;
import com.getmyschool.common.validator.RoleEnum;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

	private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	LinkedHashMap<String, Object> returnMap = null;

	@Value("${baseUrl}")
	private String baseUrl;

	@Resource(name = "UserDaoImpl")
	private UserDao userDao;

	@Resource(name = "RoleDaoImpl")
	private RoleDao roleDao;

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserCache userCache;

	@Autowired
	public UserServiceImpl(PasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

//	@Override
//	public void signup(UserDTO userDTO) {
//
//		// Check user email already exists in db or not
//		User dbUser = userDao.getUserByEmail(userDTO.getEmail());
//		if (null != dbUser) {
//			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
//			errorMap.put(Constant.RESPONSE_CODE_KEY, "GM001");
//			errorMap.put(Constant.RESPONSE_MSG_KEY, "Email already exists");
//			throw new InterruptExitException(errorMap);
//		}
//
//		// save user details
//		userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
//		User user = userDao.saveUser(userDTO);
//		LOGGER.info("User added successfully with email" + user.getEmail());
//
//		// save role as student
//		userDTO.setCreatedBy(user.getId());
//		saveRole(userDTO, user, RoleEnum.STUDENT.getRole());
//
//	}

	@Override
	public LinkedHashMap<String, Object> login(UserDTO userDTO) {
		returnMap = new LinkedHashMap<String, Object>();

		// Check user exits in db or not
		User dbUser = userDao.getUserByEmail(userDTO.getEmail());
		if (null == dbUser)
			throw new UnAuthorizedException("No user found with this email::" + userDTO.getEmail());

		// Check given password matches with db password
		if (!bCryptPasswordEncoder.matches(userDTO.getPassword(), dbUser.getPassword()))
			throw new FieldException("Password missMatch for email::" + userDTO.getEmail());

		returnMap.put(Constant.RESPONSE_CODE_KEY, Constant.SUCCESSFULL_CODE);
		returnMap.put(Constant.RESPONSE_MSG_KEY, Constant.SUCCESSFULL_MSG);
		final String token = jwtTokenUtil.generateCustomToken(dbUser);
		returnMap.put("userId", dbUser.getId());
		returnMap.put("token", token);

		return returnMap;

	}

	@Override
	public void saveUser(UserDTO userDTO) {
		List<Role> roles = loginService.getAllUserRoles(userDTO.getCreatedBy());
		boolean adminFlag = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!(adminFlag))
			throw new UnAuthorizedException("LogedIn User does't have permission to save User Details.");

		// Check given email already exists in db or not
		User dbUser = userDao.getUserByEmail(userDTO.getEmail());
		if (null != dbUser) {
			LinkedHashMap<String, String> errorMap = new LinkedHashMap<String, String>();
			errorMap.put(Constant.RESPONSE_CODE_KEY, "GE001-1");
			errorMap.put(Constant.RESPONSE_MSG_KEY, "Email already exists");
			throw new InterruptExitException(errorMap);
		}

		// Step 1:: Generate a random password
		String password = "GetMyCollege@2024";

		// Step 2:: save user with password encrypted
		userDTO.setPassword(bCryptPasswordEncoder.encode(password));
		User user = userDao.saveUser(userDTO);
		LOGGER.info("user added successfully with email::" + user.getEmail());

		// Step 3: save user roles
		if (null != userDTO.getRoles() && userDTO.getRoles().size() > 0) {
			for (String role : userDTO.getRoles())
				saveRole(userDTO, user, role);
		}

	}

	@Override
	public List<UserDTO> getAllUsers(UserDTO userDTO) {
		List<UserDTO> returnList = new ArrayList<>();
		List<User> userList = userDao.getAllUser(userDTO);

		for (User user : userList) {
			UserDTO dbUserDTO = UserConverter.getUserDTOByUser(user);
			dbUserDTO.setPassword(null);

			// Step 1: get role details
			RoleDTO roleDTO = new RoleDTO();
			roleDTO.setUserId(user.getId());
			roleDTO.setStatus(Constant.STATUS_ACTIVE);
			List<Role> roles = roleDao.getAllRoles(roleDTO);

			// Step 2: Check if the user has the specified role
			List<String> requiredRoles = userDTO.getRoles();
			if (requiredRoles != null && !requiredRoles.isEmpty()) {
				boolean hasRole = roles.stream().anyMatch(role -> requiredRoles.contains(role.getRole()));
				if (!hasRole) {
					// Skip this user if it doesn't have the required role
					continue;
				}
			}

			dbUserDTO.setRoles(roles.stream().map(Role::getRole).collect(Collectors.toList()));
			returnList.add(dbUserDTO);
		}

		return returnList;
	}

	@Override
	public UserDTO getUserById(UserDTO userDTO) {
		List<Role> roles = loginService.getAllUserRoles(userDTO.getUpdatedBy());
		boolean adminAcccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAcccess) {
			if (!userDTO.getId().equals(userDTO.getUpdatedBy()))
				throw new UnAuthorizedException("LogedIn User does't have permission to get User Details.");
		}

		// Step 1: get user details.
		User user = userDao.getUserById(userDTO.getId());
		UserDTO returnDTO = UserConverter.getUserDTOByUser(user);
		returnDTO.setPassword(null);

		// Step 2: get role details
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setUserId(user.getId());
		roleDTO.setStatus(Constant.STATUS_ACTIVE);
		List<Role> list = roleDao.getAllRoles(roleDTO);
		returnDTO.setRoles(list.stream().map(Role::getRole).collect(Collectors.toList()));

		return returnDTO;
	}

	@Override
	public void updateUser(UserDTO userDTO) {
		List<Role> roles = loginService.getAllUserRoles(userDTO.getUpdatedBy());
		boolean adminAcccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAcccess) {
			if (!userDTO.getId().equals(userDTO.getUpdatedBy()))
				throw new UnAuthorizedException("LogedIn User does't have permission to update user Details.");
		}

		// Check user exists or not
		User users = userDao.getUserById(userDTO.getId());
		UserDTO dbUserDTO = UserConverter.getUserDTOByUser(users);

		if (null != userDTO.getEmail())
			dbUserDTO.setEmail(userDTO.getEmail());

		if (null != userDTO.getName())
			dbUserDTO.setName(userDTO.getName());

		if (null != userDTO.getPhoneNumber())
			dbUserDTO.setPhoneNumber(userDTO.getPhoneNumber());

		if (null != userDTO.getCollegeId())
			dbUserDTO.setCollegeId(userDTO.getCollegeId());

		dbUserDTO.setUpdatedBy(userDTO.getUpdatedBy());
		dbUserDTO.setUpdatedDate(userDTO.getUpdatedDate());
		userDao.saveUser(dbUserDTO);
		LOGGER.info("User details for user id " + dbUserDTO.getId() + " are updated successfully.");
	}

	@Override
	public void updateUserRoles(UserDTO userDTO) {
		List<Role> roles = loginService.getAllUserRoles(userDTO.getUpdatedBy());
		boolean adminAcccess = roles.stream().anyMatch(x -> x.getRole().equals(RoleEnum.SUPER_ADMIN.getRole())
				|| x.getRole().equals(RoleEnum.ADMIN.getRole()));
		if (!adminAcccess)
			throw new UnAuthorizedException("LogedIn User does't have permission to update UserRoles Details.");

		User user = userDao.getUserById(userDTO.getId());

		// Step 1:: delete exiting userRoles
		roleDao.deleteRoleByUserId(userDTO.getId());

		// Step 2:: insert new user roles
		for (String role : userDTO.getRoles())
			saveRole(userDTO, user, role);

		LOGGER.info("User roles for user id " + user.getId() + " are updated successfully.");

	}

	@Override
	public void changePassword(UserDTO userDTO) {

		// Step 1:Get user details
		User dbUser = userDao.getUserById(userDTO.getUpdatedBy());
		UserDTO dbUserDTO = UserConverter.getUserDTOByUser(dbUser);

		dbUserDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		dbUserDTO.setUpdatedBy(userDTO.getUpdatedBy());
		dbUserDTO.setUpdatedDate(userDTO.getUpdatedDate());
		userDao.saveUser(dbUserDTO);
		LOGGER.info("Password changed successfully for email " + dbUserDTO.getEmail());

	}

	private void saveRole(UserDTO userDTO, User user, String role) {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setUserId(user.getId());
		roleDTO.setRole(role);
		roleDTO.setStatus(Constant.STATUS_ACTIVE);
		roleDTO.setCreatedBy(userDTO.getCreatedBy());
		roleDTO.setCreatedDate(userDTO.getCreatedDate());
		roleDao.saveRole(roleDTO);

	}

}
