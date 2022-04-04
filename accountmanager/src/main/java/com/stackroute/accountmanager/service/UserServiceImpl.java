package com.stackroute.accountmanager.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stackroute.accountmanager.exception.UserAlreadyExistsException;
import com.stackroute.accountmanager.exception.UserNotFoundException;
import com.stackroute.accountmanager.model.User;
import com.stackroute.accountmanager.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepo;
	
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public boolean saveUser(User user) throws UserAlreadyExistsException {
		Optional<User> dbUser = userRepo.findById(user.getUserId());
		if(dbUser.isPresent())
			throw new UserAlreadyExistsException("User Already Exists");
		userRepo.save(user);
		return true;
	}

	@Override
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {
		User user = userRepo.findByUserIdAndPassword(userId, password);
		if(user == null)
			throw new UserNotFoundException("UserId and Password mismatch");
		return user;
	}

}
