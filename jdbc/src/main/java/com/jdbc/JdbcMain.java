package com.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


// this is a dynamic proxy connection pool sample
// it comes from http://www.jointforce.com/jfperiodical/article/3808


public class JdbcMain  {
	
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

        
		Connection con = JdbcMain.getConnection();		
        String s = "CREATE TABLE test (id INTEGER, name char(50), last_name char(50), age INTEGER)";
        Statement sst = con.createStatement();
        sst.executeUpdate(s);  
        
        String sql = "insert into test (id , name ) values(1,'justin')";
        sst = con.createStatement();
        sst.executeUpdate(sql);
        
        String sql2 = "select * from test  where id  =1";
        sst = con.createStatement();
        ResultSet rs = sst.executeQuery(sql2);
        if(rs.next()) {
        	System.out.print(rs.getInt("id"));
        	System.out.println(" " + rs.getString("name"));
        }

        
		con.close();
		
		try (Connection me = JdbcMain.getConnection()) { // this grammar start
	        String s2 = "CREATE TABLE test2 (id INTEGER, name char(50), last_name char(50), age INTEGER)";
	        Statement sst2 = con.createStatement();
	        sst2.executeUpdate(s2); 
		}
	}

}
