package com.shoppingcart.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.shoppingcart.model.Category;
import com.shoppingcart.model.CategorySnapshot;
import com.shoppingcart.model.Item;
import com.shoppingcart.repository.ShoppingRepository;

@Service
public class ShoppingService {

	private final Logger LOGGER = LoggerFactory.getLogger(ShoppingService.class);
	@Autowired
	private ShoppingRepository shoppingRepository;

	public List<Category> getAllCategory() throws Exception {
		List<CategorySnapshot> categorySnapShotList = shoppingRepository.getAllCategories();
		String categoryTitle = categorySnapShotList.get(0).getCategoryTitle();
		List<Category> categoryList = new ArrayList<Category>();
		Category category = new Category();
		int listCount = 0;
		for (CategorySnapshot categorySnapshot : categorySnapShotList) {

			listCount++;
			if (!categorySnapshot.getCategoryTitle().equals(categoryTitle)) {
				categoryList.add(category);
				categoryTitle = categorySnapshot.getCategoryTitle();
				category = new Category();
			}

			if (categorySnapshot.getCategoryTitle().equals(categoryTitle)) {
				category.setCategoryTitle(categoryTitle);
				category.setCategoryId(categorySnapshot.getCategoryId());
				category.getItemList().add(mapCategorySnapshottoItem(categorySnapshot));
			}
			
			if (listCount==categorySnapShotList.size()) {
				categoryList.add(category);
				
			}

		}

		return categoryList;

	}

	private Item mapCategorySnapshottoItem(CategorySnapshot categorySnapshot) {

		Item item = new Item();
		item.setItemTitle(categorySnapshot.getItemTitle());
		item.setItemId(categorySnapshot.getItemId());
		item.setPrice(categorySnapshot.getItemPrice());
		item.setText(categorySnapshot.getItemText());
		item.setCategoryId(categorySnapshot.getCategoryId());

		return item;

	}

	public List<Item> getAllItemsForCategory(String categoryTitle) throws Exception {
		List<CategorySnapshot> categorySnapShotList = shoppingRepository.getItemsForCategory(categoryTitle);
		List<Item> itemList = new ArrayList();
		for (CategorySnapshot categorySnapshot : categorySnapShotList) {
			itemList.add(mapCategorySnapshottoItem(categorySnapshot));
		}

		return itemList;
	}

	public String createCategory(Category category) {

		try {

			int insertCount = shoppingRepository.createCategory(category);

			return "Category was successfully inserted";
		} catch (Exception exception) {
			LOGGER.error("Creation of Category Failed" + exception.getMessage());
			return "Creation of Category Failed";
		}
	}

	public String createItem(Item item) {

		try {

			int insertCount = shoppingRepository.createItem(item);

			return "Item was successfully inserted";
		} catch (Exception exception) {
			LOGGER.error("Creation of Item Failed" + exception.getMessage());
			return "Creation of Item Failed";
		}
	}
}
