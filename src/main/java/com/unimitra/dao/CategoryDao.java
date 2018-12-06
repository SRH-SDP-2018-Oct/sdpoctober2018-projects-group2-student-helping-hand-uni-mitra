package com.unimitra.dao;

import com.unimitra.exception.UnimitraException;

public interface CategoryDao {

	public int getCategoryIdFromCategoryName(String categoryName) throws UnimitraException;

}
