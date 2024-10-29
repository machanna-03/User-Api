package com.getmyschool.common.utils;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.getmyschool.common.service.LoginService;

@Component
public class UserUtils {

	@Resource(name = "LoginServiceImpl")
	private LoginService loginService;

	/**
	 * Returns userid from Context..Check in the JWT request filter...
	 * 
	 * @return
	 */
	public Long getLogedInUser() {
		Long userId = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!"anonymousUser".equals(authentication.getPrincipal())) {
			User springUser = (User) authentication.getPrincipal();
			userId = Long.parseLong(springUser.getPassword());
		}
		return userId;
	}

}
