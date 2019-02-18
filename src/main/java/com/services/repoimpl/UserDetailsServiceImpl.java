package com.services.repoimpl;

 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.UserDetails;
import com.repositories.UserDetailsRepository;
import com.services.repo.UserDetailsService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserDetailsRepository repo;
	
	@Override
	public List<UserDetails> findAll() {
		return repo.findAll();
	}

	@Override
	public UserDetails save(UserDetails arg0) {
		return repo.save(arg0);
	}

	@Override
	public void delete(UserDetails arg0) {
		repo.delete(arg0);
	}

	@Override
	public void deleteAll() {
		repo.deleteAll();
		
	}

}
