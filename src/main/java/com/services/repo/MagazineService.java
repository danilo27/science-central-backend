package com.services.repo;

import java.util.List;

import com.model.Magazine;
 
public interface MagazineService {
	List<Magazine> findAll();
	void deleteAll();
	Magazine save(Magazine m);
	void delete(Magazine m);
	Magazine findOneByIssn(String issn);
}
