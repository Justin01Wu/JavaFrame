package com.justa.springboot.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:service.properties") 
// will load compliance.user.list from application firstly, then load from service.properties if didn't find it 
public class ComplianceService {
	
	private static final Logger log = LoggerFactory.getLogger(ComplianceService.class);

    private String[] userList;
    private static final String SetName = "${compliance.user.list}";

    // to do unit test, we inject value on constructor
    public ComplianceService(@Value(SetName) String users ) {
    	
    	log.info("Constructor in ComplianceService");
        if(users == null){
            String msg = String.format("Can't find %s in config.properties", SetName);
            throw new IllegalStateException(msg);
        }
        userList = users.split(",");
    }

    protected boolean isCompliantUw(final String uwFullName) {
        if ((null==uwFullName) || uwFullName.isEmpty()) {
            return false;
        }
        for (String user : this.userList) {
            if (uwFullName.contains(user)) {
                return true;
            }
        }
        return false;
    }

	public String[] getUserList() {
		return userList;
	}
	
	@PostConstruct
	public void init() {
	   log.info("@PostConstruct in ComplianceService");
	}


}

