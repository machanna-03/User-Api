package com.getmyschool.common.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getmyschool.common.converter.UserConverter;
import com.getmyschool.common.dao.UserDao;
import com.getmyschool.common.domain.User;
import com.getmyschool.common.dto.UserDTO;
import com.getmyschool.common.exception.ResourceNotFoundException;
import com.getmyschool.common.repository.UserRepository;

@Transactional
@Service("UserDaoImpl")
public class UserDaoImpl implements UserDao {

	private Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(UserDTO usersDTO) {
		User users = UserConverter.getUserByUserDTO(usersDTO);
		return userRepository.save(users);
	}

	@Override
	public List<User> getAllUser(UserDTO usersDTO) {
		List<User> returnList = null;
		StringBuffer sqlQuery = new StringBuffer("from User a where 1=1");

		if (null != usersDTO.getId())
			sqlQuery.append(" AND a.id = :id");
		if (null != usersDTO.getStatus())
			sqlQuery.append(" AND a.status = :status");
		if (null != usersDTO.getEmail())
			sqlQuery.append(" AND a.email = :email");

		sqlQuery.append(" order by a.id DESC");
		Query query = entityManager.createQuery(sqlQuery.toString());

		if (null != usersDTO.getId())
			query.setParameter("id", usersDTO.getId());
		if (null != usersDTO.getStatus())
			query.setParameter("status", usersDTO.getStatus());
		if (null != usersDTO.getEmail())
			query.setParameter("email", usersDTO.getEmail());

		// query.setFirstResult(userDTO.getOffset());
		/// query.setMaxResults(userDTO.getLimit());

		returnList = query.getResultList();

		return returnList;
	}

	@Override
	public User getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent())
			throw new ResourceNotFoundException("The user is not found in the system. id:" + id);
		return user.get();
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}

}
