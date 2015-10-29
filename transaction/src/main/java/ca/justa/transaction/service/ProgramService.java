package ca.justa.transaction.service;

import java.io.IOException;
import java.util.List;

import ca.justa.transaction.bean.Contract;
import ca.justa.transaction.bean.Program;



public interface ProgramService {

	public void addProgram(Program program) ;
	public void addProgramAndContract(Program program, Contract contract) throws IOException ;
	public Program getProgramById(Integer id) ;
	public List<Contract> getContractsByProgramId(Integer id) ;
	
}
