package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
