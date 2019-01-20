package com.catadoption.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catadoption.model.Breed;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long>{

}
