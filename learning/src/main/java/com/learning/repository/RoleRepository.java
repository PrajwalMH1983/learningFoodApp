package com.learning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.dto.Role;
import com.learning.dto.EROLE;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByRoleName(EROLE roleName);
}
