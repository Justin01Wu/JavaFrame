package com.justa.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


// this is a dynamic proxy connection pool sample
// it comes from http://www.jointforce.com/jfperiodical/article/3808


public class Main  {
	
	public static Connection  getConnection() throws ClassNotFoundException, SQLException{
		
        Class.forName("org.h2.Driver");
        
        // connect to a in memory database, because of H2 feature, you don't need to install db sever or create db before do this 
        Connection con = DriverManager.getConnection("jdbc:h2:mem:unittest", "sa", "");
        
		return con;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {	

        
		Connection con = Main.getConnection();		
        String s = "CREATE TABLE Person (personId INTEGER, name char(50), last_name char(50), age INTEGER)";
        Statement sst = con.createStatement();
        sst.executeUpdate(s);		
	    System.out.println("succeedfully create a table person");
		
		SqlSessionFactory factory = MyFactory.buildqlSessionFactory();
		try(SqlSession session = factory.openSession()) {
		    Person p = new Person();
		    p.setPersonId(1);
		    p.setName("Justin");
		    PersonMapper mapper = session.getMapper(PersonMapper.class);   
		    mapper.save(p);
		    System.out.println("succeedfully insert a person");
		    
		}
		
		con.close();
		
	}

}
