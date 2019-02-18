package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Comment;
import com.model.Paper;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	 

}
