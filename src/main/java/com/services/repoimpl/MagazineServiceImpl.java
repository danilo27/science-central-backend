package com.services.repoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Magazine;
import com.repositories.MagazineRepository;
import com.services.repo.MagazineService;

@Service
public class MagazineServiceImpl implements MagazineService{
	
	@Autowired
	MagazineRepository repo;

	@Override
	public List<Magazine> findAll() {
		return repo.findAll();
	}

	@Override
	public Magazine save(Magazine m) {
		return repo.save(m);
	}

	@Override
	public void delete(Magazine m) {
		repo.delete(m);
		
	}

	@Override
	public Magazine findOneByIssn(String issn) {
		return repo.findOneByIssn(issn);
	}

	@Override
	public void deleteAll() {
		repo.deleteAll();
		
	}
	
	
	
	
}
