package com.learning.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByUserName(String userName);
	Boolean existsByEmail(String email);
	Boolean existsByUserNameAndPassword(String name, String password);
	Boolean existsByUserName(String userName);
	boolean existsById(int id);
		
}
