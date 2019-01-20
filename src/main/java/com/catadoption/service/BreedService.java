package com.catadoption.service;

import java.util.List;
import java.util.Optional;

import com.catadoption.model.Breed;

public interface BreedService {

	List<Breed> allBreeds();
	void addBreed(Breed breed);
	Optional<Breed> searchById(Long id);
}
