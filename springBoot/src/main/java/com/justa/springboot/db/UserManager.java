package com.justa.springboot.db;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.server.ResponseStatusException;

import com.justa.springboot.model.User;
import com.justa.springboot.model.UserEvent;

@Component
public class UserManager {

    // You need this Spring bean
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
	@Autowired 
	private UserRepository userRepository;

    @Transactional
    public void saveUser(User newUser) {
        
    	
        userRepository.save(newUser);
        UserEvent event = new UserEvent(newUser);
        if(newUser.getEmail() != null && !newUser.getEmail().contains("@")) {
        	String msg = "unexpected email: " + newUser.getEmail();
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg, null);
        	// https://www.baeldung.com/spring-response-status-exception
        }
        applicationEventPublisher.publishEvent(event);
    }

    @TransactionalEventListener
    public void doAfterCommit(UserEvent event){
        //process event here only after a successful committing
    	System.out.println(" triggered by user saving: " + event.getUser().getId());
    	// this sample comes from 
    	// https://stackoverflow.com/questions/32677747/events-after-transaction-commit-spring-jpa
    }
    
    
}
