package com.getmyschool.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.getmyschool.common.dao.UserDao;
import com.getmyschool.common.domain.Role;
import com.getmyschool.common.domain.User;

@Service("LoginServiceImpl")
public class LoginServiceImpl implements LoginService {

	@PersistenceContext
	private EntityManager entityManager;

	@Resource(name = "UserDaoImpl")
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDetails loadUserByUserEmail(String email) throws UsernameNotFoundException {
		User user = userDao.getUserByEmail(email);
		UserDetails returnUserDetails = null;
		if (null != user) {
			if (null != user) {
				org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(
						user.getEmail(), user.getId().toString(), new ArrayList<>());
				returnUserDetails = (UserDetails) springUser;
			}
		}

		return returnUserDetails;
	}

	@Override
	public List<Role> getAllUserRoles(Long userId) {
		List<Role> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from Role r where r.userId =:userId");
		Query query = entityManager.createQuery(sqlQuery.toString());
		query.setParameter("userId", userId);
		returnList = query.getResultList();
		return returnList;
	}

	@Override
	public boolean isUserAccessible(Long userId, List<String> roles) {
		List<Role> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from Role r where r.userId =:userId AND r.role IN (:roles)");
		Query query = entityManager.createQuery(sqlQuery.toString());
		query.setParameter("userId", userId);
		query.setParameter("roles", roles);
		returnList = query.getResultList();
		if (null != returnList && returnList.size() > 0) {
			return true;
		}
		return false;
	}

}
