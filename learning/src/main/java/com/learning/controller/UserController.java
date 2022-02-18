package com.learning.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.payload.request.LoginRequest;
import com.learning.payload.request.SignUpRequest;
import com.learning.payload.response.JwtResponse;
import com.learning.payload.response.MessageResponse;
import com.learning.repository.RoleRepository;
import com.learning.repository.UserRepository;
import com.learning.security.jwt.JwtUtils;
import com.learning.security.services.UserDetailsImpl;
import com.learning.service.UserService;
import com.learning.dto.EROLE;
import com.learning.dto.Role;

@RestController 
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
						, loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateToken(authentication);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetailsImpl.getAuthorities()
				.stream()
				.map(i->i.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, 
				userDetailsImpl.getId(),
				userDetailsImpl.getUsername(),
				userDetailsImpl.getEmail(), 
				roles));
	}
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		
		System.out.println(signUpRequest.getUserName());
		System.out.println(signUpRequest.getEmail());
		if(userRepository.existsByUserName(signUpRequest.getUserName())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error : UserName is already taken !"));
		}
		
		if(userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error : Email is already taken !"));
		}
		
		
		
		
		//User's account
		User user = new User(signUpRequest.getUserName(),
				signUpRequest.getEmail(),
				passwordEncoder.encode(signUpRequest.getPassword()),
				signUpRequest.getFirstName(),
				signUpRequest.getLastName());
		
		
		
		 
		Set<String> strRoles = signUpRequest.getRole();
		
		for (String role : strRoles) {
			System.out.println(role);
		}
		
		Set<Role> roles = new HashSet<>();
		
		if(strRoles == null) {
			Role userRole = roleRepository.findByRoleName(EROLE.ROLE_USER)
					.orElseThrow(()->new RuntimeException("Error : Role not found "));
			roles.add(userRole);
		}
		else {
			strRoles.forEach(e -> {
				switch (e) {
				case "admin":
					Role roleAdmin = roleRepository.findByRoleName(EROLE.ROLE_ADMIN)
						.orElseThrow(()-> new RuntimeException("Error : Role Not Found "));
					roles.add(roleAdmin);
					break;

				default:
					Role userRole = roleRepository.findByRoleName(EROLE.ROLE_USER)
						.orElseThrow(()->new RuntimeException("Error : Role not found "));
					roles.add(userRole);
					break;
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.status(201).body(new MessageResponse("User created Successfully"));
	}
		

	@PostMapping("/register")
	public ResponseEntity<?> addUser(@Valid @RequestBody User register) throws AlreadyExistsException {
	
			User result =userService.addUser(register);
			System.out.println(result);
			return ResponseEntity.status(201).body(result);
			
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") int id) throws IdNotFoundException{
		User register=userService.getUserById(id);
		return ResponseEntity.ok(register);
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllUserDetails() {
		Optional <List<User>> optional =userService.getAllUserDetails();
		
		if(optional.isEmpty()) {
			Map<String,String> map = new HashMap<>();
			map.put("message", "no record found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
			
		}
		return ResponseEntity.ok(optional.get());

	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@RequestBody User register){
		String result= userService.authenticateUser(register);
		System.out.println(result);
		Map<String,String> map = new HashMap<>();
		map.put("message", result);
		return ResponseEntity.status(201).body(map);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") int id,@RequestBody User register) throws IdNotFoundException{
		User result = userService.updateUser(id, register);
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
