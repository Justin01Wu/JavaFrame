package com.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


// this is a dynamic proxy connection pool sample
// it comes from http://www.jointforce.com/jfperiodical/article/3808


public class ConnectionPool  {
	
	public static Connection  getConnection() throws ClassNotFoundException, SQLException{
		
        Class.forName("org.h2.Driver");
        
        // connect to a in memory database, because of H2 feature, you don't need to install db sever or create db before do this 
        Connection con = DriverManager.getConnection("jdbc:h2:mem:unittest", "sa", "");
        
        // TODO implement ConnectionPool
		
		ClassLoader loader = con.getClass().getClassLoader();
		
		Class<?>[] classes = con.getClass().getInterfaces();
		InvocationHandler handler = new MyInvocationHandler(con);

		Object con2 =  Proxy.newProxyInstance(loader, classes, handler);
		return (Connection)con2;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {	

        
		Connection con = ConnectionPool.getConnection();		
        String s = "CREATE TABLE test (id INTEGER, name char(50), last_name char(50), age INTEGER)";
        Statement sst = con.createStatement();
        sst.executeUpdate(s);         
		con.close();
		
		try (Connection me = ConnectionPool.getConnection()) { // this grammar start
	        String s2 = "CREATE TABLE test2 (id INTEGER, name char(50), last_name char(50), age INTEGER)";
	        Statement sst2 = con.createStatement();
	        sst2.executeUpdate(s2); 
		}
	}

}
