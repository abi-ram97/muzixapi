package com.stackroute.muzixmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stackroute.muzixmanager.domain.Muzix;
import com.stackroute.muzixmanager.exception.MuzixTrackAlreadyExistsException;
import com.stackroute.muzixmanager.exception.MuzixTrackNotFoundException;
import com.stackroute.muzixmanager.repository.MuzixRepository;
@Service
public class MuzixServiceImpl implements MuzixService {
	final transient MuzixRepository muzixRepo;
	
	public MuzixServiceImpl(MuzixRepository muzixRepo) {
		this.muzixRepo = muzixRepo;
	}

	@Override
	public boolean saveMuzix(Muzix muzix) throws MuzixTrackAlreadyExistsException {
		final Optional<Muzix> object = muzixRepo.findById(muzix.getId());
		if(object.isPresent())
			throw new MuzixTrackAlreadyExistsException("couldn't save track, already exists");
		muzixRepo.save(muzix);
		return true;
	}

	@Override
	public boolean deleteMuzixById(int id) throws MuzixTrackNotFoundException {
		final Muzix muzix = muzixRepo.findById(id).orElse(null);
		if(muzix == null)
			throw new MuzixTrackNotFoundException("couldn't find track, not found");
		muzixRepo.delete(muzix);
		return true;
	}

	@Override
	public List<Muzix> getMyMuzixTracks(String userId) {
		return muzixRepo.findAllByUserId(userId);
	}

}
