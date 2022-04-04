package com.stackroute.muzixmanager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.muzixmanager.domain.Muzix;
import com.stackroute.muzixmanager.exception.MuzixTrackAlreadyExistsException;
import com.stackroute.muzixmanager.exception.MuzixTrackNotFoundException;
import com.stackroute.muzixmanager.service.MuzixService;

import io.jsonwebtoken.Jwts;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/muzixservice")
public class MuzixController {
	@Autowired
	private MuzixService muzixService;
	
	@PostMapping("/muzix")
	public ResponseEntity<?> saveMuzix(@RequestBody Muzix muzix,HttpServletRequest request){
		ResponseEntity<?> responseEntity;
		final String authHeader = request.getHeader("authorization");
		final String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody().getSubject();
		try {
			muzix.setUserId(userId);
			muzixService.saveMuzix(muzix);
			responseEntity = new ResponseEntity<Muzix>(muzix,HttpStatus.CREATED);
		} catch (MuzixTrackAlreadyExistsException e) {
			responseEntity = new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	@GetMapping("/muzix")
	public ResponseEntity<?> getMyMuzixTracks(HttpServletRequest request){
		ResponseEntity<?> responseEntity;
		final String authHeader = request.getHeader("authorization");
		final String token = authHeader.substring(7);
		String userId = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody().getSubject();
		responseEntity = new ResponseEntity<List<Muzix>>(muzixService.getMyMuzixTracks(userId),HttpStatus.OK);
		return responseEntity;
	}
	
	@DeleteMapping("/muzix/{id}")
	public ResponseEntity<?> deleteMuzixById(@PathVariable("id")Integer id){
		ResponseEntity<?> responseEntity;
		try {
			muzixService.deleteMuzixById(id);
			responseEntity = new ResponseEntity<String>("Muzix Track deleted successfully",HttpStatus.OK);
		} catch (MuzixTrackNotFoundException e) {
			responseEntity = new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
}
