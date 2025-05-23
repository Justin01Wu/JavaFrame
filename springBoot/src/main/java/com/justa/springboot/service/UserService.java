package com.justa.springboot.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.server.ResponseStatusException;

import com.justa.springboot.db.PositionEnum;
import com.justa.springboot.db.UserNativeRepository;
import com.justa.springboot.db.UserRepository;
import com.justa.springboot.model.User;
import com.justa.springboot.model.UserEvent;

@Service
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
    // You need this Spring bean
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    
	@Autowired 
	private UserRepository userRepository;
	
    @Autowired
    UserNativeRepository userNativeRepo;
    
	@Autowired 
	ComplianceService cs;

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
        	String msg = "unexpected email format: " + newUser.getEmail();
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg, null);
        	// https://www.baeldung.com/spring-response-status-exception
        }
        
    }
    
    @Transactional(Transactional.TxType.NEVER)
    public List<User> getUserByNameAndPosition(String name, PositionEnum position) {        
    	
    	boolean isCompliant = cs.isCompliantUw(name);
    	LOG.info( name + " isCompliant = " + isCompliant);
    	
    	// by default, Spring AOP will use CGLIB to generate proxy for ComplianceService because it has no interface
    	// by default, Spring AOP will use JDK dynamic proxy to generate proxy for userRepository because it has interfaces
    	// please set break point here to see the difference
    	
        return userRepository.getUsersByNameAndPosition(name, position);
        
    }
    
    @Transactional(Transactional.TxType.NEVER)
    public List<User> getUserByPosition( PositionEnum position) {
        return userNativeRepo.getUsersByPosition( position);
    }
    
    @Transactional(Transactional.TxType.NEVER)
    public Integer getUserCount( ) {    	    	
        return userNativeRepo.countUsers();
    }
    
    @Transactional(Transactional.TxType.NEVER)
    public Map<String, Object> findByIdAsMap(Integer id ) {    	    	
        return userNativeRepo.findByIdAsMap(id);
    }

    @Transactional(Transactional.TxType.NEVER)
    public List<Map<String, Object>> findAllAsMaps() {    	    	
        return userNativeRepo.findAllAsMaps();
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
