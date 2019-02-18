package com.services.repo;

import java.util.List;

import com.model.Editor;
import com.model.Magazine;

public interface EditorService {
	List<Editor> findAll();
	Editor save(Editor arg);
	void delete(Editor arg);
	void deleteAll();
}	
