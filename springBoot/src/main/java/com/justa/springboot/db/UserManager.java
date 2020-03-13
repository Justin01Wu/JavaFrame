package com.justa.springboot.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        //process here
    	System.out.println(" triggered by user saving: " + event.getUser().getId());
    }
}
