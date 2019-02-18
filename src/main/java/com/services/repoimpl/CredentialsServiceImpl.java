package com.services.repoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Credentials;
import com.repositories.CredentialsRepository;
import com.services.repo.CredentialsService;

@Service
public class CredentialsServiceImpl implements CredentialsService{
	
	@Autowired
	CredentialsRepository repo;
	
	@Override
	public List<Credentials> findAll() {
		return repo.findAll();
	}

	@Override
	public Credentials save(Credentials arg0) {
		return repo.save(arg0);
	}

	@Override
	public void delete(Credentials arg0) {
		repo.delete(arg0);
		
	}

	@Override
	public void deleteAll() {
		repo.deleteAll();
		
	}

}
