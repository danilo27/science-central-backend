package com.services.repo;

import java.util.List;

import com.model.FieldOfScience;
import com.model.Magazine;

public interface FieldService {
	List<FieldOfScience> findAll();
	FieldOfScience save(FieldOfScience arg0);
	void delete(FieldOfScience arg0);
	void deleteAll();
}
