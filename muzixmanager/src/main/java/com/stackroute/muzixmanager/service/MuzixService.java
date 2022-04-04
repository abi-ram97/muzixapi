package com.stackroute.muzixmanager.service;

import java.util.List;

import com.stackroute.muzixmanager.domain.Muzix;
import com.stackroute.muzixmanager.exception.MuzixTrackAlreadyExistsException;
import com.stackroute.muzixmanager.exception.MuzixTrackNotFoundException;

public interface MuzixService {
	boolean saveMuzix(Muzix muzix) throws MuzixTrackAlreadyExistsException;
	boolean deleteMuzixById(int id) throws MuzixTrackNotFoundException;
	List<Muzix> getMyMuzixTracks(String userId);
}
