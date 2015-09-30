package ca.justa.transaction.service;

import ca.justa.transaction.bean.Program;



public interface ProgramService {

	public void addProgram(Program program) ;
	public Program getProgramById(Integer id) ;
}
