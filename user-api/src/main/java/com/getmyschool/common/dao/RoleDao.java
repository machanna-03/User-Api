package com.getmyschool.common.dao;

import java.util.List;

import com.getmyschool.common.domain.Role;
import com.getmyschool.common.dto.RoleDTO;

public interface RoleDao {

	public Role saveRole(RoleDTO roleDTO);

	public void deleteRoleByUserId(Long userId);

	public List<Role> getAllRoles(RoleDTO roleDTO);

}
