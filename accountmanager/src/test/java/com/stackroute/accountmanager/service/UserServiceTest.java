package com.stackroute.accountmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stackroute.accountmanager.exception.UserAlreadyExistsException;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.repository.UserRepository;



public class UserServiceTest {
	@Mock
	private UserRepository userRepo;
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
	private User user;
	
	Optional<User> options;
	
	@Before()
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		user = new User("john12","john","f","john124");
		options = Optional.of(user);
	}

	@Test
	public void testForRegisterSuccess() throws UserAlreadyExistsException{
		when(userRepo.save(user)).thenReturn(user);
		boolean flag = userServiceImpl.saveUser(user);
		assertEquals("Cannot register user",true,flag);
		verify(userRepo,times(1)).save(user);
	}
	@Test(expected = UserAlreadyExistsException.class)
	public void testForRegisterFailure() throws UserAlreadyExistsException{
		when(userRepo.findById(user.getUserId())).thenReturn(options);
		when(userRepo.save(user)).thenReturn(user);
		userServiceImpl.saveUser(user);
	}
	@Test
	public void testForValidateSuccess() throws UserNotFoundException {
		when(userRepo.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(user);
		User object = userServiceImpl.findByUserIdAndPassword(user.getUserId(), user.getPassword());
		assertNotNull(object);
		assertEquals("Cannot validate user",user.getUserId(),object.getUserId());
		verify(userRepo,times(1)).findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}
	@Test(expected = UserNotFoundException.class)
	public void testForValidateFailure() throws UserNotFoundException {
		when(userRepo.findByUserIdAndPassword(user.getUserId(), user.getPassword())).thenReturn(null);
		userServiceImpl.findByUserIdAndPassword(user.getUserId(), user.getPassword());
	}

}
