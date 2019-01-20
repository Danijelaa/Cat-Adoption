package com.catadoption.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catadoption.model.Age;

@Repository
public interface AgeRepository extends JpaRepository<Age, Long>{

}
