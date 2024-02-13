package com.lcwa.electonic.store.services.impl;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.lcwa.electonic.store.dto.PageableResponse;
import com.lcwa.electonic.store.dto.ProductDto;
import com.lcwa.electonic.store.entities.Category;
import com.lcwa.electonic.store.entities.Product;
import com.lcwa.electonic.store.exception.ResourceNotFoundException;
import com.lcwa.electonic.store.helper.Helper;
import com.lcwa.electonic.store.repositories.CategoryRepository;
import com.lcwa.electonic.store.repositories.ProductRepository;
import com.lcwa.electonic.store.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CategoryRepository categoryRepository;

	public ProductDto create(ProductDto productDto) {

		Product product = mapper.map(productDto, Product.class);

		/* product id */
		String productId = UUID.randomUUID().toString();
		product.setProductId(productId);
		/* added */
		product.setAddedDate(new Date());
		Product saveProduct = productRepository.save(product);
		return mapper.map(saveProduct, ProductDto.class);
	}

	public ProductDto update(ProductDto productDto, String productId) {

		/* fetch the product of given id */
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found of given Id !!"));
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setQuantity(productDto.getQuantity());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());
		product.setProductImageNmae(productDto.getProductImageNmae());

		/* save the entity */
		Product updatedProduct = productRepository.save(product);
		return mapper.map(updatedProduct, ProductDto.class);
	}

	public void delete(String productId) {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found of given Id !!"));
		productRepository.delete(product);

	}

	public ProductDto get(String productId) {

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found of given Id !!"));
		return mapper.map(product, ProductDto.class);
	}

	public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findAll(pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByLiveTrue(pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy,
			String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);
		return Helper.getPageableResponse(page, ProductDto.class);
	}

	public ProductDto createWithCategory(ProductDto productDto, String categoryId) {

		// fetch the category from db:
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found !!"));
		Product product = mapper.map(productDto, Product.class);

		// product id
		String productId = UUID.randomUUID().toString();
		product.setProductId(productId);
		// added
		product.setAddedDate(new Date());
		product.setCategory(category);
		Product saveProduct = productRepository.save(product);
		return mapper.map(saveProduct, ProductDto.class);
	}

	public ProductDto updateCategory(String productId, String categoryId) {
		// product and category fetch
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product of given id not found !!"));
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category of given id not found !!"));
		product.setCategory(category);
		Product savedProduct = productRepository.save(product);
		return mapper.map(savedProduct, ProductDto.class);
	}

}
