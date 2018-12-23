package com.shoppingcart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.model.Category;
import com.shoppingcart.model.Item;
import com.shoppingcart.model.Response;
import com.shoppingcart.service.ShoppingService;


@RestController
@RequestMapping("/shopping")
public class ShoppingController {

	@Autowired
	ShoppingService shoppingService;

	List<Category> categoryList = new ArrayList<Category>();

	@GetMapping(value="/getcategory")
	@ResponseStatus(value=HttpStatus.OK)
	public Response getAllCategory() throws Exception {
		if (!shoppingService.getAllCategory().isEmpty()) {
		return new Response("Done", shoppingService.getAllCategory());
	}
		else {
			return new Response("No Category is Available");
		}
	}
		

	@PostMapping(value = "/createcategory")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Response postCategory(@RequestBody @Valid Category category) {
		return new Response( shoppingService.createCategory(category));
	}
	
	
	@GetMapping(value="/getitem")
	@ResponseStatus(value=HttpStatus.OK)
	public Response getItemforCategory(@RequestParam @NotNull String categoryTitle) throws Exception {
		
		if (!shoppingService.getAllItemsForCategory(categoryTitle).isEmpty()) {
			return new Response("Done", shoppingService.getAllItemsForCategory(categoryTitle));
		}
			else {
				return new Response("No Item is Available for the Category");
			}
		}	
		
	

	@PostMapping(value = "/createItem")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Response postItem(@RequestBody @Valid Item item) {
		return new Response(shoppingService.createItem(item));
	}
}
