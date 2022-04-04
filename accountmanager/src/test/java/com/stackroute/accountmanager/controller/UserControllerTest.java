package com.stackroute.accountmanager.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.accountmanager.exception.UserAlreadyExistsException;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.service.SecurityTokenGenerator;
import com.stackroute.accountmanager.service.UserService;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@MockBean
	private SecurityTokenGenerator tokenGenerator;
	
	User user;
	
	@Before()
	public void setUp() {
		user = new User("john12","john","f","john124");
	}
	
	@Test
	public void testForRegisterUser() throws Exception {
		when(userService.saveUser(user)).thenReturn(true);
		mockMvc.perform(post("/api/v1/userservice/register").contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(user))).andExpect(status().isCreated());
		verify(userService,times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);
	}
	
	@Test
	public void testForRegisterUserFailure() throws Exception {
		when(userService.saveUser(Mockito.any(User.class))).thenThrow(new UserAlreadyExistsException("User Already exists"));
		mockMvc.perform(post("/api/v1/userservice/register").contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(user))).andExpect(status().isConflict());
		verify(userService,times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);
	}
	
	@Test
	public void testForLoginUser() throws Exception {
		when(userService.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(user);
		mockMvc.perform(post("/api/v1/userservice/login").contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(user))).andExpect(status().isOk());
		verify(userService,times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
		verifyNoMoreInteractions(userService);
	}
	
	@Test
	public void testForLoginUserNull() throws Exception {
		User user1  = new User();
		mockMvc.perform(post("/api/v1/userservice/login").contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(user1))).andExpect(status().isUnauthorized());
	}
	

	@Test
	public void testForLoginUserFailure() throws Exception {
		User user1  = new User("test","user","testuser","testuser");
		when(userService.findByUserIdAndPassword(Mockito.any(String.class), Mockito.any(String.class)))
			.thenThrow(new UserNotFoundException("User not found"));
		mockMvc.perform(post("/api/v1/userservice/login").contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(user1))).andExpect(status().isUnauthorized());
		verify(userService,times(1)).findByUserIdAndPassword(user1.getUserId(), user1.getPassword());
		verifyNoMoreInteractions(userService);
	}
	
	private String jsonToString(Object object) {
		String result;
		final ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			result = "JSON parsing error";
		}
		return result;
	}


}
