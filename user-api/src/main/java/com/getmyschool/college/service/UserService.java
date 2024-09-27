package com.getmyschool.college.service;

import java.util.LinkedHashMap;
import java.util.List;

import com.getmyschool.common.dto.UserDTO;

public interface UserService {

//	public void signup(UserDTO userDTO);

	public LinkedHashMap<String, Object> login(UserDTO userDTO);

	public void saveUser(UserDTO userDTO);

	public List<UserDTO> getAllUsers(UserDTO userDTO);

	public UserDTO getUserById(UserDTO userDTO);

	public void updateUser(UserDTO userDTO);

	public void updateUserRoles(UserDTO userDTO);

	public void changePassword(UserDTO userDTO);

}
