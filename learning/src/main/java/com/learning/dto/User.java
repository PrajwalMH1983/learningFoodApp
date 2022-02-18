package com.learning.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.learning.utils.CustomListSerializer;

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
@Table(name = "user" , uniqueConstraints = {@UniqueConstraint(columnNames = "username") , @UniqueConstraint(columnNames = "email")})
public class User implements Comparable<User>{
	
	@Id 
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Size(max=50)
	@NotBlank
	private String userName;
	
	@Size(max = 70)
	@NotBlank
	private String firstName;
	
	@Size(max = 70)
	@NotBlank
	private String lastName;
	
	@Size(max=50)
	@Email
	private String email;
	
	@Size(max=100)
	@NotBlank
	private String password;
	
	@Size(max=100)
	private String address;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId"),
	inverseJoinColumns = @JoinColumn(name = "roleId"))
	private Set<Role> roles = new HashSet<>();

	
	@OneToOne(mappedBy = "user" , cascade = CascadeType.ALL , fetch=FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @JsonSerialize(using = CustomListSerializer.class)
    @JoinColumn(name = "userId")
	@JsonIgnore
	private Login login;




	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.id.compareTo(o.getId());
	}
	
	public User(String userName,String email,
			String password ,String firstName , String lastName) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
