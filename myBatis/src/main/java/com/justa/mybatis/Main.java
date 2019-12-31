package com.justa.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


// this is a MyBatis sample
// it comes from https://www.baeldung.com/mybatis
// and https://www.tutorialspoint.com/mybatis/index.htm


public class Main  {
	
	public static Connection  getConnection() throws ClassNotFoundException, SQLException{
		
        Class.forName("org.h2.Driver");
        
        // connect to a in memory database, because of H2 feature, you don't need to install db sever or create db before do this 
        Connection con = DriverManager.getConnection("jdbc:h2:mem:unittest", "sa", "");
        
		return con;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {	

        
		Connection con = Main.getConnection();		
        String s = "CREATE TABLE Person (personId IDENTITY, name varchar(50), status varchar(10), birthday TIMESTAMP)";
        Statement sst = con.createStatement();
        sst.executeUpdate(s);		
	    System.out.println("created a table [person]");
		
		SqlSessionFactory factory = MyFactory.buildqlSessionFactory();
		try(SqlSession session = factory.openSession()) {
			System.out.println(session.getConnection().getAutoCommit());
		    Person p = new Person();		    
		    p.setName("Justin");
		    p.setBirthday(new Date());
		    p.setStatus(StatusEnum.Active);
		    PersonMapper mapper = session.getMapper(PersonMapper.class);   
		    int personId = mapper.save(p);
		    session.commit();		    
		    
		    System.out.println("inserted a person");
		    
		    System.out.println(session.getConnection().getAutoCommit());
		    
		    Person p2 = mapper.getPersonById(personId);
		    System.out.println("got a person:" + p2);
		    
		}
		
		con.close();
		
	}


}
