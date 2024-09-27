package com.getmyschool.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.getmyschool.common.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Modifying
	@Query("delete from Role u where u.userId=:userId")
	void deleteRoleByUserId(@Param("userId") Long userId);
}
