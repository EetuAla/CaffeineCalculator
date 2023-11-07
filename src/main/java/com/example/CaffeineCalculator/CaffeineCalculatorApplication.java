package com.example.CaffeineCalculator;

import com.example.CaffeineCalculator.domain.CaffeineProduct;
import com.example.CaffeineCalculator.domain.CaffeineProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CaffeineCalculatorApplication {

	private static final Logger log = LoggerFactory.getLogger(CaffeineCalculatorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CaffeineCalculatorApplication.class, args);
	}
	
	@Bean
	//add some example data to the database
	public CommandLineRunner caffeineDemo(CaffeineProductRepository repository) {
		return (args) -> {
			log.info("Save a couple of caffeine products");

			CaffeineProduct tea = new CaffeineProduct("Tea", 30);
			repository.save(tea);

			CaffeineProduct coffee = new CaffeineProduct("Coffee", 100);
			repository.save(coffee);
			
			CaffeineProduct celsius = new CaffeineProduct("Celsius", 200);
			repository.save(celsius);
			
			CaffeineProduct redBullSmall = new CaffeineProduct("Red Bull (250ml)", 80);
			repository.save(redBullSmall);
			
			CaffeineProduct redbull = new CaffeineProduct("Red Bull (355ml)", 114);
			repository.save(redbull);

			log.info("Fetch all caffeine products");
			for (CaffeineProduct product : repository.findAll()) {
				log.info(product.toString());
			}
		};
	}
}
