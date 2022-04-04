package com.stackroute.muzixmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stackroute.muzixmanager.domain.Muzix;
import com.stackroute.muzixmanager.exception.MuzixTrackAlreadyExistsException;
import com.stackroute.muzixmanager.exception.MuzixTrackNotFoundException;
import com.stackroute.muzixmanager.repository.MuzixRepository;

public class MuzixServiceImplTest {
	@Mock
	private MuzixRepository muzixRepo;
	private Muzix muzix;
	@InjectMocks
	private MuzixServiceImpl service;
	
	Optional<Muzix> options;
	
	@Before
	public void setUpMock() {
		MockitoAnnotations.initMocks(this);
		muzix = new Muzix(1,"name","url","image","iurl","artist","user");
		options = Optional.of(muzix);
	}
	
	@Test
	public void testMockCreation() {
		assertNotNull("jpaRepository creation failed: use @InjectMocks",muzix);
	}
	
	@Test
	public void testForSaveMuzixSuccess() throws MuzixTrackAlreadyExistsException {
		when(muzixRepo.save(muzix)).thenReturn(muzix);
		final boolean flag = service.saveMuzix(muzix);
		assertTrue("save movie failed",flag);
		verify(muzixRepo,times(1)).save(muzix);
		verify(muzixRepo,times(1)).findById(1);
	}
	
	@Test(expected = MuzixTrackAlreadyExistsException.class)
	public void testForSaveMuzixFailure() throws MuzixTrackAlreadyExistsException {
		when(muzixRepo.findById(1)).thenReturn(options);
		final boolean flag = service.saveMuzix(muzix);
		assertFalse("save movie failed",flag);
		verify(muzixRepo,times(1)).findById(1);
	}
	
	@Test
	public void testForDeleteSuccess() throws MuzixTrackNotFoundException {
		when(muzixRepo.findById(1)).thenReturn(options);
		doNothing().when(muzixRepo).delete(muzix);
		final boolean flag = service.deleteMuzixById(1);
		assertTrue("Delete movie failed",flag);
		verify(muzixRepo,times(1)).delete(muzix);
		verify(muzixRepo,times(1)).findById(1);
	}
	
	@Test(expected=MuzixTrackNotFoundException.class)
	public void testForDeleteFailure() throws MuzixTrackNotFoundException {
		when(muzixRepo.findById(1)).thenReturn(Optional.empty());
		service.deleteMuzixById(1);
		verify(muzixRepo,times(1)).findById(1);
	}
	
	@Test
	public void testForGetMyTracks() {
		List<Muzix> muzixList = new ArrayList<Muzix>(2);
		when(muzixRepo.findAllByUserId(muzix.getUserId())).thenReturn(muzixList);
		final List<Muzix> list = service.getMyMuzixTracks(muzix.getUserId());
		assertEquals(muzixList,list);
		verify(muzixRepo,times(1)).findAllByUserId(muzix.getUserId());
	}

}
