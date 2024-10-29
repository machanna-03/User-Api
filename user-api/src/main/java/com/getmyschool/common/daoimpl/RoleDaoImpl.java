package com.getmyschool.common.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getmyschool.common.converter.RoleConverter;
import com.getmyschool.common.dao.RoleDao;
import com.getmyschool.common.domain.Role;
import com.getmyschool.common.dto.RoleDTO;
import com.getmyschool.common.repository.RoleRepository;

@Transactional
@Service("RoleDaoImpl")
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role saveRole(RoleDTO roleDTO) {
		Role role = RoleConverter.getRoleByRoleDTO(roleDTO);
		return roleRepository.save(role);
	}

	@Override
	public List<Role> getAllRoles(RoleDTO roleDTO) {
		List<Role> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from Role a where 1=1");

		if (null != roleDTO.getUserId())
			sqlQuery.append(" AND a.userId = :userId");
		if (null != roleDTO.getRole())
			sqlQuery.append(" AND a.role = :role");
		if (null != roleDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != roleDTO.getUserIdList() && roleDTO.getUserIdList().size() > 0)
			sqlQuery.append(" AND a.userId IN :userIdList");

		sqlQuery.append(" order by a.id DESC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != roleDTO.getUserId())
			query.setParameter("userId", roleDTO.getUserId());
		if (null != roleDTO.getRole())
			query.setParameter("role", roleDTO.getRole());
		if (null != roleDTO.getStatus())
			query.setParameter("status", roleDTO.getStatus());
		if (null != roleDTO.getUserIdList() && roleDTO.getUserIdList().size() > 0)
			query.setParameter("userIdList", roleDTO.getUserIdList());

		// query.setFirstResult(userDTO.getOffset());
		/// query.setMaxResults(userDTO.getLimit());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public void deleteRoleByUserId(Long userId) {
		roleRepository.deleteRoleByUserId(userId);
	}

}
