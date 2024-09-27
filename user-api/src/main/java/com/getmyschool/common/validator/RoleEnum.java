package com.getmyschool.common.validator;

import java.util.Arrays;

public enum RoleEnum {

	SUPER_ADMIN("Super Admin"), ADMIN("Admin"), TEAM_LEADER("Team Leader"), TELECALLER("Telecaller"),
	BLOG_AUTHOR("Blog Author"), PARTNER("Partner"), PRINICIPAL("Principal"), INTERNS("Interns"),
	DATA_ASSURE("DataAssure"), SEO_DATA_PRO("SEODataPro");

	private String role;

	RoleEnum(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static boolean isInEnum(String value, Class<RoleEnum> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).anyMatch(e -> e.getRole().equals(value));
	}
}
