package com.lcwa.electonic.store.services.impl;



import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lcwa.electonic.store.dto.CategoryDto;
import com.lcwa.electonic.store.dto.PageableResponse;
import com.lcwa.electonic.store.entities.Category;
import com.lcwa.electonic.store.exception.ResourceNotFoundException;
import com.lcwa.electonic.store.helper.Helper;
import com.lcwa.electonic.store.repositories.CategoryRepository;
import com.lcwa.electonic.store.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private  CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public CategoryDto create(CategoryDto categoryDto) {
		
		//creating Category Id randomly
		String categoryId = UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);
		
		Category category = mapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		return mapper.map(savedCategory,CategoryDto.class);
	}

	
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {
		//get category by given id
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given id!!"));
		//update category detail
		category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatedCategory = categoryRepository.save(category);
		return mapper.map(updatedCategory, CategoryDto.class);
	}

	
	public void delete(String categoryId) {
		//get category by given id
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given id!!"));
		categoryRepository.delete(category);		
	}

	
	public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable= PageRequest.of(pageNumber, pageSize,sort);
		Page<Category> page = categoryRepository.findAll(pageable);
		PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
		return pageableResponse;
	}

	@Override
	public CategoryDto get(String categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found with given id!!"));
		return mapper.map(category, CategoryDto.class);
	}

}
