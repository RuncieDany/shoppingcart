package com.shoppingcart.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.shoppingcart.model.Category;
import com.shoppingcart.model.CategorySnapshot;
import com.shoppingcart.model.Item;

@Repository
public class ShoppingRepository {
	private final Logger LOGGER = LoggerFactory.getLogger(ShoppingRepository.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private CategorySnapshotMapper categorySnapshotMapper;

	private static final String GET_ALL_CATEGORIES = "SELECT C.TITLE AS CATEGORY_TITLE,C.CATEGORY_ID,I.TITLE AS ITEM_TITLE,I.ITEM_ID,I.TEXT,I.PRICE"
			+ " FROM CATEGORY C LEFT JOIN ITEM I" + " ON I.CATEGORY_ID=C.CATEGORY_ID ";

	private static final String ORDER_BY_CATEGORYTITLE = "ORDER BY C.TITLE,I.TITLE";
	private static final String FILTER_BY_CATEGORYTITLE = "WHERE C.TITLE= :categoryTitle";	

	private static final String GET_CURRENT_CATEGORY_SEQUENCE = "SELECT MAX(CATEGORY_ID) FROM CATEGORY";
	private static final String INSERT_CATEGORY = "INSERT INTO CATEGORY" 
	        + "(CATEGORY_ID,TITLE) values"
			+ "(:category_id,:category_title)";
	private static final String GET_CURRENT_ITEM_SEQUENCE="SELECT MAX(ITEM_ID) FROM ITEM";
	private static final String INSERT_ITEM = "INSERT INTO ITEM"
	        + "(ITEM_ID,TITLE,TEXT,PRICE,CATEGORY_ID) values"
			+ "(:item_id,:title,:text,:price,:category_id)";

	public List<CategorySnapshot> getAllCategories() {

		return namedParameterJdbcTemplate.query(GET_ALL_CATEGORIES + ORDER_BY_CATEGORYTITLE, categorySnapshotMapper);

	}

	public List<CategorySnapshot> getItemsForCategory(String categoryTitle) {

		return namedParameterJdbcTemplate.query(GET_ALL_CATEGORIES + FILTER_BY_CATEGORYTITLE,
				queryparameter(categoryTitle), categorySnapshotMapper);

	}

	private Map<String, Object> queryparameter(String categoryTitle) {

		Map<String, Object> paramMap = new HashMap();
		paramMap.put("categoryTitle", categoryTitle);
		return paramMap;
	}

	private Map<String, Object> insertCategoryparameter(Category category) {

		Map<String, Object> paramMap = new HashMap();
		paramMap.put("category_id", getNextCategoryId());
		paramMap.put("category_title", category.getCategoryTitle());
		return paramMap;
	}
	
	private Map<String, Object> insertItemparameter(Item item) {

		Map<String, Object> paramMap = new HashMap();
		paramMap.put("item_id", getNextItemId());
		paramMap.put("category_id", item.getCategoryId());
		paramMap.put("title", item.getItemTitle());
		paramMap.put("price", item.getPrice());
		paramMap.put("text", item.getText());
		return paramMap;
	}

	private Long getNextCategoryId() {

		return (namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GET_CURRENT_CATEGORY_SEQUENCE, Long.class)
				+ 1);

	}
	
	private Long getNextItemId() {

		return (namedParameterJdbcTemplate.getJdbcOperations().queryForObject(GET_CURRENT_ITEM_SEQUENCE, Long.class)
				+ 1);

	}

	public int createCategory(Category category) throws Exception {

		try {

			return namedParameterJdbcTemplate.update(INSERT_CATEGORY, insertCategoryparameter(category));
		} catch (Exception exception) {
			LOGGER.error("Creation of Category Failed" + exception.getMessage());
			throw exception;
		}

	}
	
	
	public int createItem(Item item) throws Exception {

		try {

			return namedParameterJdbcTemplate.update(INSERT_ITEM, insertItemparameter(item));
		} catch (Exception exception) {
			LOGGER.error("Creation of Item Failed" + exception.getMessage());
			throw exception;
		}
}
}
