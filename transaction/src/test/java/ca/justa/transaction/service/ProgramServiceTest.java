package ca.justa.transaction.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.exception.DataException;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import ca.justa.transaction.bean.Contract;
import ca.justa.transaction.bean.Program;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProgramServiceTest {
	
	//private static Connection con;   
	// connect must keep as static because it is in memory database, 
    // if connection is closed, then all tables will disappear or use  DB_CLOSE_DELAY=-1
	
	 private static ConfigurableApplicationContext context;
	 private static ProgramService programService ;
	
	@BeforeClass
	public static void globalSetUp() throws Exception {
		
		System.out.println( "  ==>> create table program... ");
	 
		// load the driver class
        Class.forName("org.h2.Driver");
        
        // connect to an in-memory database, because of H2 feature, you don't need to install db sever or create db before do this 
        Connection con = DriverManager.getConnection("jdbc:h2:mem:unittest;MODE=MSSQLServer;DB_CLOSE_DELAY=-1", "sa", "");
        
        //  By default, closing the last connection to a database closes the database. 
        // For an in-memory database, this means the content is lost. 
        // To keep the database open, add ;DB_CLOSE_DELAY=-1 to the database URL. 
        // To keep the content of an in-memory database as long as the virtual machine is alive, use jdbc:h2:mem:test;DB_CLOSE_DELAY=-1.
        
        // TODO load jdbc config from common.properties
        
        
        // here you create the table
        String s = "CREATE TABLE program (id INTEGER, name char(50))";
        Statement sst = con.createStatement();
        sst.executeUpdate(s); 
        
     // this one is set on global to avoid multiple tables initialization 
        
        
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        programService = (ProgramService) context.getBean("programService");
	}

	@Test
	public void testAddProgram() {		
		
		System.out.println( "  ==>> testAddProgram... ");

		Program program = new Program();
		
		program.setId(99999);
		program.setName("from work gghjas");
		programService.addProgram(program);
	}
	
	@Test
	public void testAddProgramAndContract() throws IOException {		
		
		System.out.println( "  ==>> testAddProgramAndContract... ");

		Program program = new Program();
		
		Integer programId = 88888;
		program.setId(programId);
		program.setName("program88888");
		
		Contract contract = new Contract();
		contract.setName("contract888888");
		programService.addProgramAndContract(program, contract);
		
		
		// now verify them
		Program program2 = programService.getProgramById(programId);		
		assertEquals(program2.getName(), program.getName());
		
		List<Contract> contracts = programService.getContractsByProgramId(programId);
		assertEquals(contracts.size(), 1);
		assertEquals(contracts.get(0).getName(), contract.getName());		
		
		
		
	}	
	
	@Test
	public void testAddProgramAndContractFailAndRollBack() throws IOException {		
		
		System.out.println( "  ==>> testAddProgramAndContractFailAndRollBack... ");

		Program program = new Program();
		
		Integer programId = 777777;
		program.setId(programId);
		program.setName("program77777");
		
		Contract contract = new Contract();
		contract.setName("contract name is too long , more than 24 bytes");  // contract name is too long , will cause a SQLException
		try{
			programService.addProgramAndContract(program, contract);
			fail("addProgramAndContract should throw DataException ");
		}catch(DataException|DataIntegrityViolationException|PersistenceException e){
			System.out.println(e.getMessage());
		}
		
		// now verify them
		Program program2 = programService.getProgramById(programId);		
		assertNull(program2);
		
		List<Contract> contracts = programService.getContractsByProgramId(programId);  // roll back both  JDBC and JPA jobs
		assertEquals(contracts.size(), 0);
		
	}		
	
	@Test
	public void testAddProgramAndContractRunTimeExceptionFailAndRollBack() throws IOException {		
		
		System.out.println( "  ==>> testAddProgramAndContractRunTimeExceptionFailAndRollBack... ");

		Program program = new Program();
		
		Integer programId = 5555555; // I will throw a runtime exception when programId is 5555555
		program.setId(programId);
		program.setName("program555555");
		
		Contract contract = new Contract();
		contract.setName("contract 555555");  
		try{
			programService.addProgramAndContract(program, contract);
			fail("testAddProgramAndContractRunTimeExceptionFailAndRollBack should throw DataException ");
		}catch(RuntimeException e){
			System.out.println(e.getMessage());
		}
		
		// now verify them		
		Program program2 = programService.getProgramById(programId);		
		assertNull(program2);
		
		List<Contract> contracts = programService.getContractsByProgramId(programId);  // roll back both  JDBC and JPA jobs
		assertEquals(contracts.size(), 0);
		
	}	
	
	@Test
	public void testAddProgramAndContractApplicationExceptionFailAndHalfCommit() throws IOException {		
		
		System.out.println( "  ==>> testAddProgramAndContractApplicationExceptionFailAndHalfCommit... ");

		Program program = new Program();
		
		Integer programId = 4444444; // I will throw a checked Exception when programId is 4444444
		program.setId(programId);
		program.setName("program4444");
		
		Contract contract = new Contract();
		contract.setName("contract 444");  
		try{
			programService.addProgramAndContract(program, contract);
			fail("testAddProgramAndContractRunTimeExceptionFailAndRollBack should throw DataException ");
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		
		// now verify them
		Program program2 = programService.getProgramById(programId);		
		assertNotNull(program2);
		
		List<Contract> contracts = programService.getContractsByProgramId(programId);  // roll back both  JDBC and JPA jobs
		assertEquals(contracts.size(), 0);
		
	}	
	
	@Test
	public void testAddProgramAndContractShortReturn() throws IOException {		
		
		System.out.println( "  ==>> testAddProgramAndContractShortReturn... ");

		Program program = new Program();
		
		Integer programId = 6666666; // it will skip contract
		program.setId(programId);
		program.setName("program66666");
		
		Contract contract = new Contract();
		contract.setName("contract 666");  
		programService.addProgramAndContract(program, contract);			
		
		// now verify them
		Program program2 = programService.getProgramById(programId);		
		assertNotNull(program2);
		
		List<Contract> contracts = programService.getContractsByProgramId(programId);  // roll back both  JDBC and JPA jobs
		assertEquals(contracts.size(), 0);
		
	}		
	
	@Test
	public void testAddProgramAndContractCheckExceptionFailAndRollBack() throws IOException {		
		
		System.out.println( "  ==>> testAddProgramAndContractRunTimeExceptionFailAndRollBack... ");

		Program program = new Program();
		
		Integer programId = 5555555; // I will throw a runtime exception when programId is 5555555
		program.setId(programId);
		program.setName("program555555");
		
		Contract contract = new Contract();
		contract.setName("contract 5555");  
		try{
			programService.addProgramAndContract2(program, contract);
			fail("testAddProgramAndContractRunTimeExceptionFailAndRollBack should throw DataException ");
		}catch(RuntimeException e){
			System.out.println(e.getMessage());
		}
		
		// now verify them
		Program program2 = programService.getProgramById(programId);		
		assertNull(program2);
		
		List<Contract> contracts = programService.getContractsByProgramId(programId);  // roll back both  JDBC and JPA jobs
		assertEquals(contracts.size(), 0);
		
	}		
	
	
	@Test
	public void testAddManyPrograms() {		
		
		System.out.println( "  ==>> testAddManyPrograms... ");		

		for(int i = 1; i <= 1000; i++){
			Program program = new Program();
			
			program.setId(i);
			program.setName("from work "+ i);
			programService.addProgram(program);			
		}
	}	
	
	@Test
	public void testQueryProgram() {		
		// this will always run after add methods because of  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
		System.out.println( "  ==>> testQueryProgram... ");
		

			Program program = programService.getProgramById(99999);
			assertEquals(program.getName(), "from work gghjas");
	}
		

}
