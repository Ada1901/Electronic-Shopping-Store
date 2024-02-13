package com.lcwa.electonic.store.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lcwa.electonic.store.dto.PageableResponse;
import com.lcwa.electonic.store.dto.UserDto;
import com.lcwa.electonic.store.entities.User;
import com.lcwa.electonic.store.exception.ResourceNotFoundException;
import com.lcwa.electonic.store.helper.Helper;
import com.lcwa.electonic.store.repositories.UserRepository;
import com.lcwa.electonic.store.services.UserService;

import lombok.Builder;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	public UserDto createUser(UserDto userdto) {

		// Generate unique id in String format
		String userId = UUID.randomUUID().toString();
		userdto.setUserId(userId);
		// dto -> Entity
		User user = dtoToEntity(userdto);
		User savedUser = userRepository.save(user);
		// Entity -> Dto
		UserDto newDto = entityTodto(savedUser);
		return newDto;
	}

	public UserDto updateUser(UserDto userdto, String userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
		user.setName(userdto.getName());
		user.setGender(userdto.getGender());
		user.setPassword(userdto.getPassword());
		user.setAbout(userdto.getAbout());
		user.setImageName(userdto.getImageName());

		User update = userRepository.save(user);
		UserDto updateDto = entityTodto(update);
		return updateDto;
	}

	public void deleteUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
		// delete user
		userRepository.delete(user);
	}

	public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// PageNumber Default starts from Zero
		Sort sort= (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<User> page = userRepository.findAll(pageable);
		
		
		PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
		return response;
	}

	public UserDto getUserById(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
		return entityTodto(user);
	}

	public UserDto getUserByEmai(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not Found with email"));
		return entityTodto(user);
	}

	public List<UserDto> searchUser(String keyword) {
		List<User> user = userRepository.findByNameContaining(keyword);
		List<UserDto> dtoList = user.stream().map(users -> entityTodto(users)).collect(Collectors.toList());
		return dtoList;
	}

	private UserDto entityTodto(User savedUser) {

//		UserDto userdto = UserDto.builder().userId(savedUser.getUserId()).name(savedUser.getName())
//				.email(savedUser.getEmail()).password(savedUser.getPassword()).about(savedUser.getAbout())
//				.gender(savedUser.getGender()).imageName(savedUser.getImageName()).build();
		return mapper.map(savedUser, UserDto.class);
	}

	private User dtoToEntity(UserDto userdto) {
//		User user = User.builder().userId(userdto.getUserId()).name(userdto.getName()).email(userdto.getEmail())
//				.password(userdto.getPassword()).about(userdto.getAbout()).gender(userdto.getGender())
//				.imageName(userdto.getImageName()).build();
		return mapper.map(userdto, User.class);
	}
}
