package com.stackroute.muzixmanager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.muzixmanager.domain.Muzix;
import com.stackroute.muzixmanager.exception.MuzixTrackAlreadyExistsException;
import com.stackroute.muzixmanager.exception.MuzixTrackNotFoundException;
import com.stackroute.muzixmanager.service.MuzixServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(MuzixController.class)
public class MuzixControllerTest {
	private MockMvc mockMvc;
	@MockBean
	private MuzixServiceImpl muzixService;
	@InjectMocks
	private MuzixController controller;
	
	private Muzix muzix;
	
	List<Muzix> list;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		muzix = new Muzix(1,"name","url","image","iurl","artist","alpha");
		list = new ArrayList<Muzix>(2);
		list.add(muzix);
	}
	
	@Test
	public void testForSaveMuzixSuccess() throws Exception {
		when(muzixService.saveMuzix(muzix)).thenReturn(true);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbHBoYSIsImlhdCI6MTU1MzA3ODM3NH0.rfad_yk7nZBRhB8jOFGAtMDIZVCdUyesXQo3fRX5Nl0";
		mockMvc.perform(post("/api/v1/muzixservice/muzix").header("authorization", "Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON).content(jsonToString(muzix)))
				.andExpect(status().isCreated());
		verify(muzixService,times(1)).saveMuzix(any(Muzix.class));
		verifyNoMoreInteractions(muzixService);
	}
	
	@Test
	public void testForSaveMuzixSuccessFailure() throws Exception {
		when(muzixService.saveMuzix(any(Muzix.class))).thenThrow(new 
				MuzixTrackAlreadyExistsException("Track already exists"));
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbHBoYSIsImlhdCI6MTU1MzA3ODM3NH0.rfad_yk7nZBRhB8jOFGAtMDIZVCdUyesXQo3fRX5Nl0";
		mockMvc.perform(post("/api/v1/muzixservice/muzix").header("authorization", "Bearer "+token)
				.contentType(MediaType.APPLICATION_JSON).content(jsonToString(muzix)))
				.andExpect(status().isConflict());
		verify(muzixService,times(1)).saveMuzix(any(Muzix.class));
		verifyNoMoreInteractions(muzixService);
	}
	
	@Test
	public void testForGetMyMuzixTracks() throws Exception {
		when(muzixService.getMyMuzixTracks(muzix.getUserId())).thenReturn(list);
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbHBoYSIsImlhdCI6MTU1MzA3ODM3NH0.rfad_yk7nZBRhB8jOFGAtMDIZVCdUyesXQo3fRX5Nl0";
		mockMvc.perform(get("/api/v1/muzixservice/muzix").header("authorization", "Bearer "+token))
				.andExpect(status().isOk());
		verify(muzixService,times(1)).getMyMuzixTracks(muzix.getUserId());
		verifyNoMoreInteractions(muzixService);
	}
	@Test
	public void testForDeleteByIdSuccess() throws Exception {
		when(muzixService.deleteMuzixById(1)).thenReturn(true);
		mockMvc.perform(delete("/api/v1/muzixservice/muzix/{id}",1)).andExpect(status().isOk());
		verify(muzixService,times(1)).deleteMuzixById(1);
		verifyNoMoreInteractions(muzixService);
	}
	@Test
	public void testForDeleteByIdFailure() throws Exception {
		when(muzixService.deleteMuzixById(any(Integer.class))).thenThrow(
				new MuzixTrackNotFoundException("Track not found"));
		mockMvc.perform(delete("/api/v1/muzixservice/muzix/{id}",1)).andExpect(status().isConflict());
		verify(muzixService,times(1)).deleteMuzixById(any(Integer.class));
		verifyNoMoreInteractions(muzixService);
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
