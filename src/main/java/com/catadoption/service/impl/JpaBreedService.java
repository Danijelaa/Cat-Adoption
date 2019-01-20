package com.catadoption.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catadoption.model.Breed;
import com.catadoption.repository.BreedRepository;
import com.catadoption.service.BreedService;

@Service
public class JpaBreedService implements BreedService{

	@Autowired
	private BreedRepository breedRepository;
	@Override
	public List<Breed> allBreeds() {
		return breedRepository.findAll();
	}

	@Override
	public void addBreed(Breed breed) {
		breedRepository.save(breed);
	}

	@Override
	public Optional<Breed> searchById(Long id) {
		return breedRepository.findById(id);
	}

}
