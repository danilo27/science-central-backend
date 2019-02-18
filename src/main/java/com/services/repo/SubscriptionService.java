package com.services.repo;

import java.util.List;

import com.model.Magazine;
import com.model.Subscription;

public interface SubscriptionService {
	List<Subscription> findAll();
	Subscription save(Subscription arg0);
	void delete(Subscription arg0);
	Subscription findById(Long arg0);
	void deleteAll();
}	
