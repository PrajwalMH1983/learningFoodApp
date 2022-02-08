package com.learning.service.impl;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Role;
import com.learning.repository.RoleRepository;
import com.learning.service.RoleService;

@Service
public class RoleServiceImpl  implements RoleService{

	@Autowired
	RoleRepository roleRepository;
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String addRole(Role role) {
		// TODO Auto-generated method stub
		Role role2= roleRepository.save(role);
		return null;
	}

	@Override
	public void deleteRole(int roleId) {
		// TODO Auto-generated method stub
		
	}
		
	}


