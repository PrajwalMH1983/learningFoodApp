package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.naming.InvalidNameException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.Food;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.service.UserService;
import com.learning.payload.response.MessageResponse;
import com.learning.repository.FoodRepository;
import com.learning.service.FoodService;
import com.learning.exception.InvalidIdLengthException;

@CrossOrigin("*")
@RestController 
@RequestMapping("/api/food")
public class FoodController {
	@Autowired
	FoodService foodService;

	@Autowired
	FoodRepository foodRepository;
	
	@PostMapping("/addFood")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addFood(@Valid @RequestBody Food food) throws AlreadyExistsException {		
		Food result =foodService.addFood(food);
		System.out.println(result);
		return ResponseEntity.status(201).body(result);
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/getFood/{foodName}")
	public ResponseEntity<?> getFood(@PathVariable("foodName") String foodName) {

		if (foodRepository.existsByFoodName(foodName)) {
			Optional<Food> food1 = foodRepository.findByFoodName(foodName);
			return ResponseEntity.ok(food1);
		}
		return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Food with name: "+foodName+" does not exists!"));
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable("id") int id) throws IdNotFoundException{
		Food food=foodService.getFoodById(id);
		return ResponseEntity.ok(food);
	}
	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateFood(@PathVariable("id") int id,@RequestBody Food food) throws IdNotFoundException{
		Food result = foodService.updateFood(id, food);
		return ResponseEntity.status(201).body(result);
	}
	
	@DeleteMapping("/delete/{id}")
		public ResponseEntity<?> DeleteUserById(@PathVariable("id") int id) throws IdNotFoundException{
		String result = foodService.deleteFoodItemById(id);
		System.out.println(result);
		Map<String, String> map = new HashMap<>();
		map.put("message", "User deleted successfully");
		return ResponseEntity.status(201).body(result);
	}
	
	@GetMapping("/type/{foodType}")
	public ResponseEntity<?> getAllByFoodType(@PathVariable("foodType") String foodType) {
		Optional<List<Food>> optional = foodService.getFoodByType(foodType);
		return ResponseEntity.ok(optional);
	}
	
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getAllFoodDetails() throws InvalidNameException, InvalidIdLengthException {
		Optional<List<Food>> optional = foodService.getAllFoodDetails();
		
		if(optional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("No movies found"));
		}
		return ResponseEntity.ok(optional.get());
	}
}
