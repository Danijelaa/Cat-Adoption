package com.catadoption.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import com.catadoption.model.Cat;

public interface CatService {

	Page<Cat> allCats(int page);
	Page<Cat> search(@Param("sex") String sex, @Param("colorId") Long colorId, @Param("locationId") Long locationId, @Param("breedId") Long breedId, @Param("ageId") Long ageId, int page);
	void addCat(Cat cat);
	void deleteCat(Long id);
	Cat searchByIdForInfo(Long id);
	Optional<Cat> searchById(Long id);
	List<Cat> findByUser(String username);
	List<Cat> contentToBeApproved();
	void approveContent(Long id);
	Boolean existsById(Long id);
}
