package com.shoppingcart.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.shoppingcart.model.CategorySnapshot;
@Component
public class CategorySnapshotMapper implements RowMapper<CategorySnapshot>{

	@Override
	public CategorySnapshot mapRow(ResultSet rs, int rownum) throws SQLException {
		
		CategorySnapshot categorySnapshot = new CategorySnapshot();
		categorySnapshot.setCategoryTitle(rs.getString("Category_Title"));
		categorySnapshot.setItemTitle(rs.getString("Item_Title"));
		categorySnapshot.setItemPrice(rs.getBigDecimal("Price"));
		categorySnapshot.setItemText(rs.getString("Text"));
		categorySnapshot.setCategoryId(rs.getLong("Category_Id"));
		categorySnapshot.setItemId(rs.getLong("Item_Id"));
		return categorySnapshot;
	}

}
