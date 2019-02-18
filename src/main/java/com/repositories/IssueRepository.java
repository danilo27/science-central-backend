package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Issue;
import com.model.Paper;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

}
