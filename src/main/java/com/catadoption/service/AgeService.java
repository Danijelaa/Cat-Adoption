package com.catadoption.service;

import java.util.List;
import java.util.Optional;

import com.catadoption.model.Age;

public interface AgeService {

	List<Age> allAges();
	void addAge(Age age);
	Optional<Age> searchById(Long id);
}
