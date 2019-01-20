package com.catadoption.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.catadoption.model.Cat;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long>{

	//Page<Maca> findAll(Pageable pageRequest);
	Page<Cat> findByApprovedByAdmin(Boolean approvedByAdmin, Pageable pageRequest);
	Cat findByIdAndApprovedByAdmin(Long id, Boolean approvedByAdmin);
	@Query("SELECT c FROM Cat c WHERE c.approvedByAdmin=true AND (:sex IS NULL OR c.sex LIKE :sex) AND (:colorId IS NULL OR c.color.id=:colorId) AND (:locationId IS NULL OR c.location.id=:locationId) AND (:breedId IS NULL OR c.breed.id=:breedId) AND (:ageId IS NULL OR c.age.id=:ageId)")
	Page<Cat> search(@Param("sex") String sex, @Param("colorId") Long colorId, @Param("locationId") Long locationId, @Param("breedId") Long breedId, @Param("ageId") Long ageId,Pageable pageRequest);
	@Query("SELECT c FROM Cat c WHERE c.user.username LIKE :user")
	List<Cat> findByUser(@Param("user") String username);
	List<Cat> findByApprovedByAdmin(Boolean approvedByAdmin);
}
