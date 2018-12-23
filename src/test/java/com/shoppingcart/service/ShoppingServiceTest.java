package com.shoppingcart.service;
import org.junit.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.shoppingcart.model.Category;
import com.shoppingcart.model.CategorySnapshot;
import com.shoppingcart.model.Item;
import com.shoppingcart.repository.ShoppingRepository;
import org.springframework.test.context.junit4.*;



public class ShoppingServiceTest {

	@Mock
	private ShoppingRepository shoppingRepository;	
	
	@Mock
	private Item item;
	@Mock
	private Category category;
	
	@InjectMocks
	private ShoppingService shoppingService;
	
	@Mock
	private CategorySnapshot  categorySnapShot;
	
	private List <CategorySnapshot> categorySnapshotList;
	
	@Before
	public void Setup() {
		categorySnapShot=new CategorySnapshot();
		categorySnapshotList= new ArrayList ();
		categorySnapShot.setCategoryId(2L);
		categorySnapShot.setItemId(3L);
		categorySnapShot.setCategoryTitle("Electronics");
		categorySnapShot.setItemPrice(new BigDecimal(123.54));
		categorySnapShot.setItemTitle("TV");
		categorySnapShot.setItemText("Samsung");
		categorySnapshotList.add(categorySnapShot);	
		 MockitoAnnotations.initMocks(this);
		
	}
	
	
	@Test
	public void getAllItems_Success () throws Exception {		
		
		given(shoppingRepository.getItemsForCategory("Electronics")).willReturn(categorySnapshotList);
		assertThat(shoppingService.getAllItemsForCategory("Electronics").get(0).getItemId()).isEqualTo(3L);
		assertThat(shoppingService.getAllItemsForCategory("Electronics").get(0).getCategoryId()).isEqualTo(2L);
		assertThat(shoppingService.getAllItemsForCategory("Electronics").get(0).getItemTitle()).isEqualTo("TV");
		assertThat(shoppingService.getAllItemsForCategory("Electronics").get(0).getPrice()).isEqualTo(new BigDecimal(123.54));
		assertThat(shoppingService.getAllItemsForCategory("Electronics").get(0).getText()).isEqualTo("Samsung");			
		
	}
	
	@Test
	public void createItem_Success () throws Exception {		
		
		given(shoppingRepository.createItem(any(Item.class))).willReturn(1);
		assertThat(shoppingService.createItem(item)).isEqualTo("Item was successfully inserted");
		
	}
	
	@Test
	public void createItem_Failure() throws Exception {		
		
		given(shoppingRepository.createItem(any(Item.class))).willThrow(new SQLException());
		assertThat(shoppingService.createItem(item)).isEqualTo("Creation of Item Failed");
		
	}
	
	@Test
	public void createCategory_Failure() throws Exception {		
		
		given(shoppingRepository.createCategory(any(Category.class))).willThrow(new SQLException());
		assertThat(shoppingService.createCategory(category)).isEqualTo("Creation of Category Failed");
		
	}

}
