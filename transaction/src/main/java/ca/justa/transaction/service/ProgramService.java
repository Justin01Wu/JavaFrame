package ca.justa.transaction.service;

import ca.justa.transaction.bean.Contract;
import ca.justa.transaction.bean.Program;



public interface ProgramService {

	public void addProgram(Program program) ;
	public void addProgramAndContract(Program program, Contract contract) ;
	public Program getProgramById(Integer id) ;
}
