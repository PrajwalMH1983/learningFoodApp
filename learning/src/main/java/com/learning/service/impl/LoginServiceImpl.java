package com.learning.service.impl;

import java.io.IOException;
import java.util.Optional;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Login;
import com.learning.dto.Role;
import com.learning.dto.User;
import com.learning.exception.IdNotFoundException;

import com.learning.repository.LoginRepository;
import com.learning.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {


	@Autowired
	private LoginRepository repository ;
	
	@Override
	public String addCredentials(Login login) {
		// TODO Auto-generated method stub
		Login login2 = repository.save(login);
		if (login2 != null) {
			return "success";
		} else {
			return "fail";
		}
	}

	@Override
	public String deleteCredentials(String userName) {
		// TODO Auto-generated method stub
		
		Optional<Login> optional;
		try {
			optional = repository.findById(userName);
			if(optional.isEmpty()) {
				throw new IdNotFoundException("record not found");
			}
			else {
				repository.deleteById(userName);
				return "login record deleted";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}

	@Override
	public String changePassword(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changeRole(String userName, Role role) {
		// TODO Auto-generated method stub
		return null;
	}

}