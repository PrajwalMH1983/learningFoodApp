package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.Register;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.service.UserService;

@RestController 
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody Register register) throws AlreadyExistsException {
	
			Register result =userService.addUser(register);
			System.out.println(result);
			return ResponseEntity.status(201).body(result);
			
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") int id) throws IdNotFoundException{
		Register register=userService.getUserById(id);
		return ResponseEntity.ok(register);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllUserDetails() {
		Optional <List<Register>> optional =userService.getAllUserDetails();
		
		if(optional.isEmpty()) {
			Map<String,String> map = new HashMap<>();
			map.put("message", "no record found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
			
		}
		return ResponseEntity.ok(optional.get());

	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@RequestBody Register register){
		String result= userService.authenticateUser(register);
		System.out.println(result);
		Map<String,String> map = new HashMap<>();
		map.put("message", result);
		return ResponseEntity.status(201).body(map);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") int id,@RequestBody Register register) throws IdNotFoundException{
		Register result = userService.updateUser(id, register);
		return ResponseEntity.status(201).body(result);
	}
	
	@DeleteMapping("/delete/{id}")
		public ResponseEntity<?> DeleteUserById(@PathVariable("id") int id) throws IdNotFoundException{
		String result = userService.deleteUserById(id);
		System.out.println(result);
		Map<String, String> map = new HashMap<>();
		map.put("message", "User deleted successfully");
		return ResponseEntity.status(201).body(result);
	}
	
	
}
