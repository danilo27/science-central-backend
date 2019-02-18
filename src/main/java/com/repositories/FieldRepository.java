package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.FieldOfScience;
 

@Repository
public interface FieldRepository  extends JpaRepository<FieldOfScience, String> {

}
