package com.justa.library.test.ews;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExchangeEventFinderTest {

	@Test
	public void testArpit() throws Exception {
		ExchangeEventFinder finder = new ExchangeEventFinder(true);
    	String email="Arpit.Jain@validusresearch.com";
    	boolean isWorkingElsewhere = finder.getWorkingElsewhereStatus(email);
    	if(isWorkingElsewhere) {
    		System.out.println(email + " is working elsewhere");	
    	}

	}
	
	@Test
	public void testJeff() throws Exception {
		ExchangeEventFinder finder = new ExchangeEventFinder(true);
		String email="Jeff.Clements@validusre.bm";
		boolean isWorkingElsewhere = finder.getWorkingElsewhereStatus(email);
		if(isWorkingElsewhere) {
			System.out.println(email + " is working elsewhere");	
		}
	}
	
	@Test
	public void testkristi() throws Exception {
		ExchangeEventFinder finder = new ExchangeEventFinder(true);
		String email="kristi.champ@validusresearch.com";
		boolean isWorkingElsewhere = finder.getWorkingElsewhereStatus(email);
		if(isWorkingElsewhere) {
			System.out.println(email + " is working elsewhere");	
		}
		assertTrue(isWorkingElsewhere);
	}
	
	@Test
	public void testJustin() throws Exception {
		ExchangeEventFinder finder = new ExchangeEventFinder(false);
		String email="justin.wu@validusresearch.com";
		boolean isWorkingElsewhere = finder.getWorkingElsewhereStatus(email);
		if(isWorkingElsewhere) {
			System.out.println(email + " is working elsewhere");	
		}
		assertTrue(isWorkingElsewhere);
	}

	

}
