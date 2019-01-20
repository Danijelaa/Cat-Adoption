package com.catadoption.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catadoption.model.Age;
import com.catadoption.repository.AgeRepository;
import com.catadoption.service.AgeService;

@Service
public class JpaAgeService implements AgeService{

	@Autowired
	private AgeRepository ageRepository;
	@Override
	public List<Age> allAges() {
		return ageRepository.findAll();
	}

	@Override
	public void addAge(Age age) {
		ageRepository.save(age);
	}

	@Override
	public Optional<Age> searchById(Long id) {
		return ageRepository.findById(id);
	}

}
