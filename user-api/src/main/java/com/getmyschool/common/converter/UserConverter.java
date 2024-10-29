package com.getmyschool.common.converter;

import org.springframework.stereotype.Component;

import com.getmyschool.common.domain.User;
import com.getmyschool.common.dto.UserDTO;

@Component
public class UserConverter {

	/**
	 * convert UserDTO to User
	 * 
	 * @param UserDTO
	 * @return
	 */

	public static User getUserByUserDTO(UserDTO userDTO) {
		User user = new User();

		user.setId(userDTO.getId());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setName(userDTO.getName());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setCollegeId(userDTO.getCollegeId());
		user.setStatus(userDTO.getStatus());
		user.setCreatedDate(userDTO.getCreatedDate());
		user.setCreatedBy(userDTO.getCreatedBy());
		user.setUpdatedDate(userDTO.getUpdatedDate());
		user.setUpdatedBy(userDTO.getUpdatedBy());
		return user;
	}

	/**
	 * convert User to UserDTO
	 * 
	 * @param User
	 * @return
	 */

	public static UserDTO getUserDTOByUser(User user) {
		UserDTO dto = new UserDTO();

		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		dto.setName(user.getName());
		dto.setPhoneNumber(user.getPhoneNumber());
		dto.setStatus(user.getStatus());
		dto.setCollegeId(user.getCollegeId());
		dto.setCreatedDate(user.getCreatedDate());
		dto.setCreatedBy(user.getCreatedBy());
		dto.setUpdatedDate(user.getUpdatedDate());
		dto.setUpdatedBy(user.getUpdatedBy());
		return dto;
	}

}
