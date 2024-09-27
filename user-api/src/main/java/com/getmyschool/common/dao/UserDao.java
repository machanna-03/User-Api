package com.getmyschool.common.dao;

import java.util.List;

import com.getmyschool.common.domain.User;
import com.getmyschool.common.dto.UserDTO;

public interface UserDao {

	public User saveUser(UserDTO usersDTO);

	public List<User> getAllUser(UserDTO usersDTO);

	public User getUserById(Long id);

	public User getUserByEmail(String email);
}
