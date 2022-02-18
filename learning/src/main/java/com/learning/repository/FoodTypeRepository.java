package com.learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.dto.FoodType;

public interface FoodTypeRepository extends JpaRepository<FoodType, String> {

}
