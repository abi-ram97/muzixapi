package com.stackroute.muzixmanager.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.muzixmanager.domain.Muzix;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class MuzixRepositoryTest {
	@Autowired
	transient MuzixRepository muzixRepo;
	
	private Muzix muzix;
	
	@Before
	public void setUp(){
		muzix = new Muzix("name","url","image","iurl","user");
	}
	@After
	public void tearDown(){
		muzixRepo.deleteAllInBatch();
	}
	
	@Test
	public void testForSaveMuzix() {
		muzixRepo.save(muzix);
		final Muzix object = muzixRepo.getOne(muzix.getId());
		assertEquals("Save failed",object.getName(),muzix.getName());
	}
	@Test
	public void testForGetMyTracks(){
		muzixRepo.save(muzix);
		muzixRepo.save(new Muzix("name1","url1","image1","iurl1","user"));
		final List<Muzix> list = muzixRepo.findAllByUserId("user");
		assertEquals("Get My tracks failed",list.size(),2);
	}
	@Test
	public void testForDeleteMuzixById() {
		muzixRepo.save(muzix);
		final Muzix object = muzixRepo.getOne(muzix.getId());
		assertEquals(object.getName(),muzix.getName());
		muzixRepo.delete(muzix);
		assertEquals(Optional.empty(),muzixRepo.findById(muzix.getId()));
	}

}
