package ca.justa.transaction.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.justa.transaction.bean.Program;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProgramServiceTest {
	
	private static Connection con;   
	// connect must keep as static because it is in memory database, 
    // if connection is closed, then all tables will disappear
	
	 private static ConfigurableApplicationContext context;
	 private static ProgramService programService ;
	
	@BeforeClass
	public static void globalSetUp() throws Exception {
		
		System.out.println( "  ==>> create table program... ");
	 
		// load the driver class
        Class.forName("org.h2.Driver");
        
        // connect to a in memory database, because of H2 feature, you don't need to install db sever or create db before do this 
        con = DriverManager.getConnection("jdbc:h2:mem:unittest", "sa", "");
        
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
		program.setName("from work");
		programService.addProgram(program);
	}
	
	@Test
	public void testAddManyPrograms() {		
		
		System.out.println( "  ==>> testAddManyPrograms... ");
		
		ProgramService programService = (ProgramService) context.getBean("programService");

		for(int i = 1; i <= 10000; i++){
			Program program = new Program();
			
			program.setId(i);
			program.setName("from work "+ i);
			programService.addProgram(program);			
		}
	}	

}
