package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Editor;
import com.model.FieldOfScience;

@Repository
public interface EditorRepository  extends JpaRepository<Editor, Long> {

}