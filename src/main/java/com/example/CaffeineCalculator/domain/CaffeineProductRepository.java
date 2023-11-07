package com.example.CaffeineCalculator.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CaffeineProductRepository extends CrudRepository<CaffeineProduct, Long> {
	// IN CASE OF COMPLICATED SQL QUERIES CAN USE @QUERY
	//@Query("SELECT * FROM student")
    
}