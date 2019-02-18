package com.services.repoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Subscription;
import com.repositories.MagazineRepository;
import com.repositories.SubscriptionRepository;
import com.services.repo.SubscriptionService;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
	
	@Autowired
	SubscriptionRepository repo;
	
	@Override
	public List<Subscription> findAll() {
		return repo.findAll();
	}

	@Override
	public Subscription save(Subscription arg0) {
		return repo.save(arg0);
	}

	@Override
	public void delete(Subscription arg0) {
		repo.delete(arg0);
		
	}

	@Override
	public Subscription findById(Long arg0) {
		return repo.findById(arg0).orElse(null);
	}

	@Override
	public void deleteAll() {
		repo.deleteAll();
		
	}

}
