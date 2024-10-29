package com.getmyschool.common.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.getmyschool.common.domain.Role;

public interface LoginService extends UserDetailsService {

	UserDetails loadUserByUserEmail(String email) throws UsernameNotFoundException;

	List<Role> getAllUserRoles(Long userId);

	public boolean isUserAccessible(Long userId, List<String> roles);
}
