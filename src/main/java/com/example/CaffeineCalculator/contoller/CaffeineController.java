package com.example.CaffeineCalculator.contoller;

import com.example.CaffeineCalculator.domain.CaffeineProduct;
import com.example.CaffeineCalculator.domain.CaffeineProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes("totalCaffeine")
public class CaffeineController {

	//instances of repositories and services
	private final CaffeineProductRepository caffeineProductRepository;
	private final CaffeineCalculationService caffeineCalculationService;

	@Autowired
	public CaffeineController(CaffeineProductRepository caffeineProductRepository,
			CaffeineCalculationService caffeineCalculationService) {
		this.caffeineProductRepository = caffeineProductRepository;
		this.caffeineCalculationService = caffeineCalculationService;
	}

	//attribute for total caffeine, initialized to 0
	@ModelAttribute("totalCaffeine")
	public Integer totalCaffeine() {
		return 0;
	}

	//display the CaffeineCalculator page
	@GetMapping("/caffeine")
	public String showCaffeineProducts(Model model, @ModelAttribute("totalCaffeine") Integer totalCaffeine) {
		//fetch all caffeine products from the repository
		List<CaffeineProduct> caffeineProducts = (List<CaffeineProduct>) caffeineProductRepository.findAll();

		//add attributes to the model for rendering in the view
		model.addAttribute("caffeineProducts", caffeineProducts);
		model.addAttribute("totalCaffeine", totalCaffeine);

		//render the CaffeineCalculator page
		return "CaffeineCalculator";
	}

	//mapping for calculating caffeine intake
	@PostMapping("/calculate")
	public String calculateCaffeine(@RequestParam(name = "selectedProduct") Long selectedProductId,
			@RequestParam(name = "amount") int amount, Model model,
			@ModelAttribute("totalCaffeine") Integer totalCaffeine) {

		//fetch the selected product by its ID
		CaffeineProduct selectedProduct = caffeineProductRepository.findById(selectedProductId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + selectedProductId));

		//calculate the caffeine intake for the selected product and amount
		int calculatedCaffeine = caffeineCalculationService.calculateCaffeine(selectedProduct.getCaffeineContent(),
				amount);

		//update the total caffeine intake
		totalCaffeine += calculatedCaffeine;

		//add attributes to the model for rendering in the view
		model.addAttribute("calculatedCaffeine", calculatedCaffeine);
		model.addAttribute("selectedProduct", selectedProduct);
		model.addAttribute("totalCaffeine", totalCaffeine);

		//fetches all caffeine products again for the dropdown list
		List<CaffeineProduct> caffeineProducts = (List<CaffeineProduct>) caffeineProductRepository.findAll();
		model.addAttribute("caffeineProducts", caffeineProducts);

		//render the caffeine page without redirecting
		return "CaffeineCalculator";
	}

	//mapping for displaying the form to add a new caffeine product
	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("newCaffeineProduct", new CaffeineProduct());
		return "AddCaffeineProduct";
	}

	//mapping for processing the addition of a new caffeine product
	@PostMapping("/add")
	public String addCaffeineProduct(@ModelAttribute("newCaffeineProduct") CaffeineProduct newCaffeineProduct) {
		caffeineProductRepository.save(newCaffeineProduct);
		// Redirect to the /caffeine endpoint after adding a new product
		return "redirect:/caffeine";
	}

	//mapping for displaying the list of caffeine products for deletion
	@GetMapping("/delete")
	public String showDeleteList(Model model) {
		List<CaffeineProduct> caffeineProducts = (List<CaffeineProduct>) caffeineProductRepository.findAll();
		model.addAttribute("caffeineProducts", caffeineProducts);
		return "DeleteCaffeineProduct";
	}

	//mapping for deleting a specific caffeine product
	@GetMapping("/delete/{id}")
	public String deleteCaffeineProduct(@PathVariable("id") Long id) {
		caffeineProductRepository.deleteById(id);
		// Redirect to the /caffeine endpoint after deleting a product
		return "redirect:/caffeine";
	}
}
