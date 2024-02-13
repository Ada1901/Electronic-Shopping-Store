package com.lcwa.electonic.store.services;

import com.lcwa.electonic.store.dto.PageableResponse;
import com.lcwa.electonic.store.dto.ProductDto;
import com.lcwa.electonic.store.entities.Category;

public interface ProductService {

	//create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto, String productId);

    //delete
    void delete(String productId);

    //get single

    ProductDto get(String productId);

    //get all
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all : live
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search product
    PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);
    
    //create Product with category
    ProductDto createWithCategory(ProductDto productDto,String categoryId);
    
    //update category with product
    ProductDto updateCategory(String productId,String categoryId);
}
