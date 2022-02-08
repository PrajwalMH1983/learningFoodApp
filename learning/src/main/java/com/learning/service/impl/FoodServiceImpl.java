package com.learning.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.dto.Food;
import com.learning.dto.Register;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.repository.FoodRepository;
import com.learning.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService{

	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	@org.springframework.transaction.annotation.Transactional(rollbackFor = AlreadyExistsException.class)
	public Food addFood(Food food) throws AlreadyExistsException {
		// TODO Auto-generated method stub
		boolean status = foodRepository.existsByFoodName(food.getFoodName()) ;
		if(status) {
			throw new AlreadyExistsException("this record already exists");
		}
		Food food2 = foodRepository.save(food);
		if (food2 != null) {
		
					
				return food2;
			}
			else {
				return null;
			}
	}

	@Override
	public Food updateFood(int id, Food food) throws IdNotFoundException {
		// TODO Auto-generated method stub
		if(this.foodRepository.existsById(id)==false) {
			throw new IdNotFoundException( "id not found");
		}
			return foodRepository.save(food);
	}

	@Override
	public Food getFoodById(int id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Optional<Food> optional= foodRepository.findById(id);
		if(optional.isEmpty()) {
			throw new IdNotFoundException("id not found");
		}
		else
		{
			return optional.get();
		}
	}

	@Override
	public Food[] getAllFoodItems() {
		// TODO Auto-generated method stub
		List<Food> list = foodRepository.findAll();
		Food[] array = new Food[list.size()];
		return list.toArray(array);
	}

	@Override
	public String deleteFoodItemById(int id) throws IdNotFoundException {
		// TODO Auto-generated method stub
		Food optional;
		Food food= new Food();
		try {
			optional = this.getFoodById(id);
			if(optional==null) {
				throw new IdNotFoundException("id not found");
			}
			else {
				foodRepository.deleteById(id);
				return "Food item deleted successfully";
			}
		} catch (IdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IdNotFoundException(e.getMessage());
		}
	}

	@Override
	public Optional<List<Food>> getAllFoodDetails() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(foodRepository.findAll());
	}

}
