package com.catadoption.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catadoption.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

}
