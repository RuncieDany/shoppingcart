package com.shoppingcart.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingcart.model.Category;
import com.shoppingcart.model.Item;
import com.shoppingcart.repository.ShoppingRepository;
import com.shoppingcart.service.ShoppingService;


@RunWith(SpringRunner.class)
@WebMvcTest(ShoppingController.class)
@ComponentScan(basePackages = "com.shoppingcart.*")
public class ShoppingControllerTest {

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private MockMvc mockMVC;
	@Autowired
	private ShoppingService shoppingService;
	@Autowired
	private ShoppingRepository mockShoppingRepository;

	private Category category;
	private Item item;
	
	private ObjectMapper objectMapper;

	@Before
	public void Setup() {
		this.mockMVC = MockMvcBuilders.webAppContextSetup(this.context).build();
		 category= new Category();	
		 item = new Item ();
		objectMapper=new ObjectMapper();		
	}

	@Test
	public void getAllCategories_Success() throws Exception {
		

		MvcResult mvcResult = mockMVC.perform(get("/shopping/getcategory"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].categoryTitle").value("Books"))
				.andExpect(jsonPath("$.data[1].itemList[1].price").value(120.59))
				.andReturn();	
		
		System.out.println(mvcResult.getResponse().getContentAsString());		
		

	}
	
	
	@Test
	public void getAllItems_Success() throws Exception {
		

		MvcResult mvcResult = mockMVC.perform(get("/shopping/getitem?categoryTitle=Books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[1].itemTitle").value("Raging Bull"))
				.andReturn();	
	
		
		System.out.println(mvcResult.getResponse().getContentAsString());	
		

	}
	
	
	
	@Test
	public void createCategory_Success() throws Exception {
		
		category.setCategoryTitle("Footwear");		
		MvcResult mvcResult = mockMVC.perform(post("/shopping/createcategory")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(category)))
				.andExpect(status().isCreated()).andReturn();
		
		System.out.println(mvcResult.getResponse().getContentAsString());	
		
		 mvcResult = mockMVC.perform(get("/shopping/getcategory"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[2].categoryTitle").value("Footwear"))
				.andExpect(jsonPath("$.data[1].itemList[1].price").value(120.59))
				.andReturn();
	
		
		System.out.println(mvcResult.getResponse().getContentAsString());	
		

	}

	@Test
	public void createItem_Success() throws Exception {
		
		item.setCategoryId(3L);
		item.setItemTitle("Toaster");
		item.setPrice(new BigDecimal (123.44));
		item.setText("Haier");	
		
				
		MvcResult mvcResult = mockMVC.perform(post("/shopping/createItem")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(item)))
				.andExpect(status().isCreated()).andReturn();
		
		 mvcResult = mockMVC.perform(get("/shopping/getitem?categoryTitle=Home Improvement"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data[2].itemTitle").value("Toaster"))
				.andReturn();
	
		
		System.out.println(mvcResult.getResponse().getContentAsString());	
		

	}
}
