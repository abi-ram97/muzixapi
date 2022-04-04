package com.stackroute.muzixmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stackroute.muzixmanager.domain.Muzix;

public interface MuzixRepository extends JpaRepository<Muzix, Integer> {
	List<Muzix> findAllByUserId(String userId);
}
