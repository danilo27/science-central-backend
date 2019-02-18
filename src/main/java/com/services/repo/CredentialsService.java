package com.services.repo;

import java.util.List;

import com.model.Credentials;
import com.model.Editor;

public interface CredentialsService {
	List<Credentials> findAll();
	Credentials save(Credentials arg0);
	void delete(Credentials arg0);
	void deleteAll();
}
