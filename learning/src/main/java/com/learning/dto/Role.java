package com.learning.dto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
public class Role {
	
	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int roleId;

	
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private EROLE roleName;
	
	
}

