package com.justa.library.test.ews;

import static org.junit.Assert.*;

import org.junit.Test;

import microsoft.exchange.webservices.data.core.ExchangeService;

public class ExchangeEventUtilTest {

	@Test
	public void testArpit() throws Exception {
		
    	String email="Arpit.Jain@validusresearch.com";    	
    	
    	boolean isWorkingElsewhere = false;
    	try(ExchangeService service = ExchangeEventUtil.getExchangeService()){
    		isWorkingElsewhere = ExchangeEventUtil.getWorkingElsewhereStatus(service, email);	
    	}
    	if(isWorkingElsewhere) {
    		System.out.println(email + " is working elsewhere");	
    	}

	}
	
	@Test
	public void testJeff() throws Exception {
		
		String email="Jeff.Clements@validusre.bm";
		
    	boolean isWorkingElsewhere = false;
    	try(ExchangeService service = ExchangeEventUtil.getExchangeService()){
    		isWorkingElsewhere = ExchangeEventUtil.getWorkingElsewhereStatus(service, email);	
    	}
		if(isWorkingElsewhere) {
			System.out.println(email + " is working elsewhere");	
		}
	}
	
	@Test
	public void testkristi() throws Exception {
		
		String email="kristi.champ@validusresearch.com";
    	boolean isWorkingElsewhere = false;
    	try(ExchangeService service = ExchangeEventUtil.getExchangeService()){
    		isWorkingElsewhere = ExchangeEventUtil.getWorkingElsewhereStatus(service, email);	
    	}
		if(isWorkingElsewhere) {
			System.out.println(email + " is working elsewhere");	
		}
		assertTrue(isWorkingElsewhere);
	}
	
	@Test
	public void testJustin() throws Exception {
		
		String email="justin.wu@validusresearch.com";
    	boolean isWorkingElsewhere = false;
    	try(ExchangeService service = ExchangeEventUtil.getExchangeService()){
    		isWorkingElsewhere = ExchangeEventUtil.getWorkingElsewhereStatus(service, email);	
    	}
		if(isWorkingElsewhere) {
			System.out.println(email + " is working elsewhere");	
		}
		assertTrue(isWorkingElsewhere);
	}

	

}
