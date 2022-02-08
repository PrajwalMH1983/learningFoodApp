package com.learning.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Table(name = "food")
public class Food {

	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 
	
	@Size(max=50)
	@NotBlank
	private String foodName;
	
	
	@NotNull
	private int foodCost;
	
	@Size(max=100)
	@NotBlank
	private String foodType;
	
	
	@NotBlank
	private String foodPic;
}
