package com.learning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learning.dto.Food;
//import com.learning.repository.FoodRepository;

import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;

@Service
public interface FoodService {
	public Food addFood(Food food) throws AlreadyExistsException;
	public Food updateFood(int id, Food food) throws IdNotFoundException;
	public Food getFoodById(int id) throws IdNotFoundException;
	public Food[] getAllFoodItems();
	public String deleteFoodItemById(int id) throws IdNotFoundException;
	public Optional<List<Food>> getAllFoodDetails() ;
}
