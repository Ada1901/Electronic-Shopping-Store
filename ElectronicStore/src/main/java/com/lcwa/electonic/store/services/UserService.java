package com.lcwa.electonic.store.services;

import java.util.List;

import com.lcwa.electonic.store.dto.PageableResponse;
import com.lcwa.electonic.store.dto.UserDto;

public interface UserService {
 
	//create 
	
	 UserDto createUser(UserDto userdto);
	 
	//update
	
	 UserDto updateUser(UserDto userdto,String userId);
	 
	//delete
	
	 void deleteUser(String userId);
	 
	//get all user
	 
	 PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy ,String sortDir);
	
	//get single user by id
	 
	 UserDto getUserById(String userId);
	 
	//get single user by email
	 
	 UserDto getUserByEmai(String email);
	
	 //search users
	 
	 List<UserDto> searchUser(String keyword);
	 
}
