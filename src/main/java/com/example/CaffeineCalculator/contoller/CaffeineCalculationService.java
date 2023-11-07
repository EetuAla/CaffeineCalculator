package com.example.CaffeineCalculator.contoller;

import org.springframework.stereotype.Service;
//service to calculate the caffeine content and amount
@Service
public class CaffeineCalculationService {
	
    public Integer calculateCaffeine(Integer caffeineContent, int amount) {
        return caffeineContent * amount;
    }
}
