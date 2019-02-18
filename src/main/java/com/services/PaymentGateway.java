package com.services;

import java.util.Calendar;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dto.Email;
import com.repositories.EmailRepo;
import com.services.repo.SubscriptionService;

import com.repositories.CartRepository;

import com.dto.StringDto;
import com.model.Cart;

@Service 
public class PaymentGateway implements JavaDelegate{
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	SubscriptionService subService;
	
	@Autowired
	EmailRepo emailRepo;
	
	@Autowired
	CartRepository cartRepository;
	
	@Bean
	public RestTemplate restTemp() {
	    return new RestTemplate();
	}
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Cart cart = new Cart();
		cart.getItemDetails().put("status", "inProcess");
		cart.setMerchantId(cart.getItemDetails().get("merchantId"));
		cart.setMerchantPassword(cart.getItemDetails().get("merchantPas"));
		cart.setStatus("subscription");
		cart = cartRepository.save(cart);
		cart.setMerchantOrderId(cart.getId());
		cart.setMerchantTimestamp(Calendar.getInstance().getTime());
		cart = cartRepository.save(cart);
		 
		 
		String fooResourceUrl = "http://localhost:8080/api/pc/sendCart";
		ResponseEntity<Cart> response = restTemp().postForEntity(fooResourceUrl, cart, Cart.class);//notify PC to save new transaction
		 
		Long cartIdInPc = response.getBody().getId();
		StringDto dto = new StringDto("value","http://localhost:4200?t="+cartIdInPc); //id cart-a u PC-u 
		System.out.println("[NC]" + dto.toString());
 	
	}
}
