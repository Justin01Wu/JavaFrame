package com.justa.springboot.db;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.server.ResponseStatusException;

import com.justa.springboot.Application;
import com.justa.springboot.model.User;
import com.justa.springboot.model.UserEvent;

@Component
public class UserManager {

	private static final Logger LOG = LoggerFactory.getLogger(UserManager.class);
	
    // You need this Spring bean
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
	@Autowired 
	private UserRepository userRepository;

    @Transactional
    public void saveUser(User newUser) {
        
    	
        userRepository.save(newUser);
        if(newUser.getId() %2 == 0) {
            UserEvent event = new UserEvent(newUser);
            applicationEventPublisher.publishEvent(event);        	
        }else {
        	LOG.info("won't trigger user saving event because userId is odd: " + newUser.getId());
        }
        
        if(newUser.getEmail() != null && !newUser.getEmail().contains("@")) {
        	String msg = "unexpected email: " + newUser.getEmail();
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg, null);
        	// https://www.baeldung.com/spring-response-status-exception
        }
        
    }

    @TransactionalEventListener
    public void doAfterCommit(UserEvent event){
        //process event here only after a successful committing
    	LOG.info("triggered by user saving: " + event.getUser().getId());
    	// this sample comes from 
    	// https://stackoverflow.com/questions/32677747/events-after-transaction-commit-spring-jpa
    }
    
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void doAfterFail(UserEvent event){
        //process event here only after a failed committing
    	LOG.warn("user saving fails: " + event.getUser().getName());
    	
    	// https://spring.io/blog/2015/02/11/better-application-events-in-spring-framework-4-2
    }
    
    
}
