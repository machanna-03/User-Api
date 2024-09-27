package com.getmyschool.common.converter;

import com.getmyschool.common.domain.Role;
import com.getmyschool.common.dto.RoleDTO;

public class RoleConverter {

	/**
	 * convert Role to RoleDTO
	 * 
	 * @param Role
	 * @return
	 */

	public static RoleDTO getRoleDTOByRole(Role role) {
		RoleDTO dto = new RoleDTO();

		dto.setId(role.getId());
		dto.setUserId(role.getUserId());
		dto.setRole(role.getRole());
		dto.setStatus(role.getStatus());
		dto.setCreatedDate(role.getCreatedDate());
		dto.setCreatedBy(role.getCreatedBy());
		dto.setUpdatedDate(role.getUpdatedDate());
		dto.setUpdatedBy(role.getUpdatedBy());

		return dto;
	}

	/**
	 * convert RoleDTO to Role
	 * 
	 * @param RoleDTO
	 * @return
	 */

	public static Role getRoleByRoleDTO(RoleDTO roleDTO) {
		Role role = new Role();

		role.setId(roleDTO.getId());
		role.setUserId(roleDTO.getUserId());
		role.setRole(roleDTO.getRole());
		role.setStatus(roleDTO.getStatus());
		role.setCreatedDate(roleDTO.getCreatedDate());
		role.setCreatedBy(roleDTO.getCreatedBy());
		role.setUpdatedDate(roleDTO.getUpdatedDate());
		role.setUpdatedBy(roleDTO.getUpdatedBy());

		return role;
	}

}
