package com.justa.springboot.db;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

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
        applicationEventPublisher.publishEvent(event);
    }

    @TransactionalEventListener
    public void doAfterCommit(UserEvent event){
        //process event here
    	System.out.println(" triggered by user saving: " + event.getUser().getId());
    	// this sample comes from 
    	// https://stackoverflow.com/questions/32677747/events-after-transaction-commit-spring-jpa
    }
}
