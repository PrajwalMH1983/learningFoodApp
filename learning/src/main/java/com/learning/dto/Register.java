package com.learning.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.learning.dto.Login;
import com.learning.dto.Role;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data 
@Setter
@AllArgsConstructor
@NoArgsConstructor


@ToString
@Entity 
@Table(name = "register")
public class Register {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Size(max=50)
	@Email
	private String email;
	
	@Size(max=50)
	@NotBlank
	private String name;
	
	@Size(max=100)
	@NotBlank
	private String password;
	
	@Size(max=100)
	@NotBlank
	private String address;
	
	

	
	@OneToOne(mappedBy = "register", cascade = CascadeType.ALL)
	private Login login;
}
