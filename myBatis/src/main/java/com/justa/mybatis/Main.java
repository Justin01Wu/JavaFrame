package com.justa.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		
		createTables(con);
		
		SqlSessionFactory factory = MyFactory.buildqlSessionFactory();
		try(SqlSession session = factory.openSession()) {
			
			System.out.println(session.getConnection().getAutoCommit());
		    
			Person p = createPerson();		    
		    
		    PersonMapper mapper = session.getMapper(PersonMapper.class);   
		    int personId = mapper.save(p);		    

		    AddressMapper mapper2 = session.getMapper(AddressMapper.class);
		    for(Address a: p.getAddresses()) {
		    	a.setPersonId(personId);
			    mapper2.save(a);
		    }
		    
		    session.commit();		    
		    
		    System.out.println("inserted a person");
		    
		    System.out.println(session.getConnection().getAutoCommit());
		    
		    Person p2 = mapper.getPersonById(personId);
		    System.out.println("got a person:" + p2);
		    
		}
		
		con.close();
		
	}
	
	private static void createTables(Connection con) throws SQLException {
		
        String s = "CREATE TABLE Person (personId IDENTITY, name varchar(50), status int, gender varchar(10),birthday TIMESTAMP)";
        Statement sst = con.createStatement();
        sst.executeUpdate(s);		
	    System.out.println("created a table [person]");	    

        s = "CREATE TABLE address (addressId IDENTITY, streetAddress varchar(50), personId int)";
        sst = con.createStatement();
        sst.executeUpdate(s);		
	    System.out.println("created a table [address]");

	}
	
	private static Person createPerson() {
	    Person p = new Person();		    
	    p.setName("Justin");
	    p.setBirthday(new Date());
	    p.setStatus(StatusEnum.Active);
	    p.setGender(GenderEnum.Female);
	    
	    List<Address> addressList = createAddress();
	    p.setAddresses(addressList);
	    return p;
	}
	
	private static List<Address> createAddress() {
		
		List<Address> result = new ArrayList<>();
		Address a = new Address();		    
	    a.setStreetAddress("10 Main street");
	    result.add(a);
	    
	    a = new Address();		    
	    a.setStreetAddress("31 Yong street");
	    result.add(a);
	    return result;
	 }


}
