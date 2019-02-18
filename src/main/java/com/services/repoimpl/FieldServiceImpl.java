package com.services.repoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.FieldOfScience;
import com.repositories.FieldRepository;
import com.services.repo.FieldService;

@Service
public class FieldServiceImpl implements FieldService{
	
	@Autowired
	FieldRepository repo;
	
	@Override
	public List<FieldOfScience> findAll() {
		return repo.findAll();
	}

	@Override
	public FieldOfScience save(FieldOfScience arg0) {
		return repo.save(arg0);
	}

	@Override
	public void delete(FieldOfScience arg0) {
		repo.delete(arg0);
		
	}

	@Override
	public void deleteAll() {
		repo.deleteAll();
		
	}

}
