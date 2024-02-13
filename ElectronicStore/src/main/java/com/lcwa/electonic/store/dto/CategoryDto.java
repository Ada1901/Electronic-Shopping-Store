package com.lcwa.electonic.store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

	private String categoryId;
	
	@NotBlank
	@Size(min =4,message = "Min 4 char required")
	private String title;
	@NotBlank(message = "description is required!!")
	
	private String description;
	@NotBlank(message = "cover Image is required!!")
	private String coverImage;
}
