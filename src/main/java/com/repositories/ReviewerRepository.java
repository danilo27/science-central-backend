package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Credentials;
import com.model.Reviewer;

@Repository
public interface ReviewerRepository  extends JpaRepository<Reviewer, Long> {
	public Reviewer findByUserUsername(String s);
}