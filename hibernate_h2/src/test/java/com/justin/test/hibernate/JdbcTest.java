package com.justin.test.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class JdbcTest {
	
	@Test
	public void testCreateTable() throws ClassNotFoundException, SQLException {
        
		// load the driver class
        Class.forName("org.h2.Driver");
        
        // connect to a in memory database, because of H2 feature, you don't need to install db sever or create db before do this 
        Connection con = DriverManager.getConnection("jdbc:h2:mem:mytest", "sa", "");
       
        // here you create the table
        String s = "CREATE TABLE test (id INTEGER, name char(50), last_name char(50), age INTEGER)";
        Statement sst = con.createStatement();
        sst.executeUpdate(s); 
	}

}
