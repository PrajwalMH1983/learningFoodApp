package com.learning.service;

import com.learning.dto.Role;

public interface RoleService {

	public String addRole(Role role);
	
	public void deleteRole(int roleId);
}
