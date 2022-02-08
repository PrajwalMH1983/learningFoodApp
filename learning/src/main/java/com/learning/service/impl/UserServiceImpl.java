package com.learning.service.impl;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.repository.UserRepository;
import com.learning.service.UserService;
import com.learning.dto.Login;
import com.learning.repository.LoginRepository;
import com.learning.service.LoginService;
import com.learning.dto.Register;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private LoginService service;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = AlreadyExistsException.class)
	public Register addUser(Register register) throws AlreadyExistsException {
		// TODO Auto-generated method stub
		boolean status = repository.existsByEmail(register.getEmail()) ;
		if(status) {
			throw new AlreadyExistsException("this record already exists");
		}
		Register register2 = repository.save(register);
		if (register2 != null) {
			Login login = new Login(register.getEmail(), register.getPassword(), register2);
			if(loginRepository.existsByUserName(register.getEmail())) {
				throw new AlreadyExistsException("this record already exists");
			}
			String result = service.addCredentials(login);
			if(result == "success") {
					//return "record added in register and login";
				return register2;
			}
			else {
				return null;
			}
		}	
		else {
			return null;
		}
		
		}	
	
	@Override
	public String authenticateUser(Register register)  {
		boolean result = repository.existsByNameAndPassword(register.getName(),register.getPassword()) ;
		if(result) {
			return "user exists";
		}
		else
		{
			return "user does not exist";
		}
		
	}

	@Override
	public Register updateUser(int id, Register register) throws IdNotFoundException {
		
		// TODO Auto-generated method stub
		
		if(!this.repository.existsById(id))
			throw new IdNotFoundException( "id not found");
		
		return repository.save(register);
		}

	@Override
	public Register getUserById(int id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<Register> optional= repository.findById(id);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("id not found");
		}
		else
		{
			return optional.get();
		}
	}

	@Override
	public Register[] getAllUsers() {
		// TODO Auto-generated method stub
		List<Register> list = repository.findAll();
		Register[] array = new Register[list.size()];
		return list.toArray(array);
	}

	@Override
	public String deleteUserById(int id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Register optional;
		Register register = new Register();
		try {
			optional = this.getUserById(id);
			if(optional==null) {
				throw new IdNotFoundException("id not found");
			}
			else {
				repository.deleteById(id);
				return "User deleted successfully";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IdNotFoundException(e.getMessage());
		}
	}

	@Override
	public Optional<List<Register>> getAllUserDetails() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(repository.findAll());
	}
}
