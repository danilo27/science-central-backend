package com.services.repoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Editor;
import com.repositories.EditorRepository;
import com.services.repo.EditorService;

@Service
public class EditorServiceImpl implements EditorService {
	
	@Autowired
	EditorRepository repo;
	
	@Override
	public List<Editor> findAll() {
		return repo.findAll();
	}

	@Override
	public Editor save(Editor arg) {
		return repo.save(arg);
	}

	@Override
	public void delete(Editor arg) {
		repo.delete(arg);
		
	}

	@Override
	public void deleteAll() {
		repo.deleteAll();
		
	}

}
