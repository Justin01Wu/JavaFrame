package ca.justa.transaction.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ca.justa.transaction.bean.Program;


public class ProgramService {
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public Program getProgramById(){
		return new Program();
	}

}
