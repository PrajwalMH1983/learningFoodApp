package com.learning.service;

import com.learning.dto.Login;
import com.learning.dto.Role;

public interface LoginService {
	public String addCredentials(Login login);
	public String deleteCredentials(String userName);
	public String changePassword(String userName, String password);
	public String changeRole(String userName, Role role);
}
