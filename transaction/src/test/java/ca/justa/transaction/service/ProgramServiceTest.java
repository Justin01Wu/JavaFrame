package ca.justa.transaction.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ca.justa.transaction.bean.Program;

public class ProgramServiceTest {
	
	private static Connection con;   
	// connect must keep as static because it is in memory database, 
    // if connection is closed, then all tables will disappear
	
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
	}

	@Test
	public void testAddProgram() {
		
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProgramService programService = (ProgramService) context.getBean("programService");

		Program program = new Program();
		
		program.setId(1234);
		program.setName("from work");
		programService.addProgram(program);
	}

}
