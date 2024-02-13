package com.lcwa.electonic.store.dto;

import com.lcwa.electonic.store.validate.ImageNameValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

	private String userId;
	
	@Size(min=3 ,max = 35,message = "Invalid User Name!!")
	private String name;
	
	//@Email(message = "Invalid User Email!!")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid User Email !!")
	@NotBlank(message = "Email is required")
	private String email;
	@NotBlank(message = "Password required")
	private String password;
	@Size(min=4 ,max = 6,message = "Invalid gender!!")
	private String gender;
	@NotBlank(message = "Write something about message")
	private String about;
	@ImageNameValid
	private String imageName;
}
