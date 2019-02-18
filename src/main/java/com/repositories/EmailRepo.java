package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dto.Email;
import com.model.Editor;

@Repository
public interface EmailRepo  extends JpaRepository<Email, Long> {

}
