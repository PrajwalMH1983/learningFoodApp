package com.learning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learning.dto.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;

@Service
public interface UserService {
	public User addUser(User register) throws AlreadyExistsException;
	public User updateUser(int id, User register) throws IdNotFoundException;
	public User getUserById(int id) throws IdNotFoundException;
	public User[] getAllUsers();
	public String deleteUserById(int id) throws IdNotFoundException;
	public Optional<List<User>> getAllUserDetails() ;
	String authenticateUser(User register);
}
