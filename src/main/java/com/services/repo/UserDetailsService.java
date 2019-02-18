package com.services.repo;

import java.util.List;

import com.model.UserDetails;

public interface UserDetailsService {
	List<UserDetails> findAll();
	UserDetails save(UserDetails arg0);
	void delete(UserDetails arg0);
	void deleteAll();
}
