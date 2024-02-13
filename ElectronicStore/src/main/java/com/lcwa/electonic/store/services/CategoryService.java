package com.lcwa.electonic.store.services;

import com.lcwa.electonic.store.dto.CategoryDto;
import com.lcwa.electonic.store.dto.PageableResponse;

public interface CategoryService {

	//create
	CategoryDto create(CategoryDto categoryDto);
	
	//update
	CategoryDto update(CategoryDto categoryDto,String categoryId);
	
	//delete
	void delete(String categoryId);
	
	//get All
	PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	//get single category details
	CategoryDto get(String categoryId);
	
}
