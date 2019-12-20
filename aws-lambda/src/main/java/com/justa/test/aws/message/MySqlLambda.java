package com.justa.test.aws.message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class MySqlLambda implements RequestHandler<Object,String> {
	
	private static String ServerName = "localhost";
	private static String PORT = "3306";
	private static String DB = "testDb";
	private static String User = "admin";
	private static String Password = "admin123";
	
	@Override
	public String handleRequest(Object input, Context context)  {	
		String serverName = System.getenv("ServerName");
		if (serverName != null ) {
			ServerName = serverName ;
		}
		
		try {
			callMySql(ServerName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private void callMySql(String ServerName) throws ClassNotFoundException, SQLException {
		String dbUrl = "jdbc:mysql://"+ ServerName + ":" + PORT + "/"+ DB;
		System.out.println(dbUrl);
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(dbUrl, User, Password);		

		
		Statement stmt = con.createStatement();
//		stmt.executeUpdate("CREATE TABLE Human ( ID int NOT NULL, name varchar(255) NOT NULL, PRIMARY KEY (ID) )");
//		stmt.executeUpdate("insert into Human values(1, 'Justin')");
//		stmt.executeUpdate("insert into Human values(3, 'Rita')");
		ResultSet rs = stmt.executeQuery("select * from Human ");
		while (rs.next()) {
			System.out.println(rs.getInt("Id") + "  " + rs.getString("name") );
		}
		con.close();
	}
}
