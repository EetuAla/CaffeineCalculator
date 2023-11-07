package com.example.CaffeineCalculator.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CaffeineProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Integer caffeineContent;

	public CaffeineProduct() {
	}

	//Constructor
	public CaffeineProduct(String name, Integer caffeineContent) {
		this.name = name;
		this.caffeineContent = caffeineContent;
	}

	//Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCaffeineContent() {
		return caffeineContent;
	}

	public void setCaffeineContent(Integer caffeineContent) {
		this.caffeineContent = caffeineContent;
	}
}
