package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Magazine;
import com.model.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
	 

}
