package com.stackroute.accountmanager.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.accountmanager.model.User;



@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class UserRepositoryTest {
	private UserRepository userRepo;
	
	private User user;
	@Autowired
	public void setRepo(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Before()
	public void setUp() {
		user = new User("john12","john","f","john124");
	}
	@Test
	public void testForRegisterUserSuccess() {
		userRepo.save(user);
		User object = userRepo.getOne(user.getUserId());
		assertEquals(object.getFirstName(),user.getFirstName());
	}

}
