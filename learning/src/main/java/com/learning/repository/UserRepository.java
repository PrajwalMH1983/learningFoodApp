package com.learning.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.Register;


@Repository
public interface UserRepository extends JpaRepository<Register, Integer> {

		Boolean existsByEmail(String email);
		Boolean existsByNameAndPassword(String name, String password);
		boolean existsById(int id);
		
}
