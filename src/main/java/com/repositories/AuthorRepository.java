package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Author;
import com.model.Credentials;

@Repository
public interface AuthorRepository  extends JpaRepository<Author, Long> {
	Author findByCredentialsUsername(String username);
}
