package com.justa.test.aws.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlSample {

	private static String serverUrl = "localhost:3306";
	private static String DB = "testDb";
	private static String User = "admin";
	private static String Password = "admin123";
	

	public static void main(String args[]) throws ClassNotFoundException, SQLException {

		String serverName = System.getenv("CacheServerName");
		String port = System.getenv("CachePort");

		if (serverName != null && port != null) {
			serverUrl = serverName + ":" + port;
		}
		String db = System.getenv("DB");
		if (db != null ) {
			DB=db;
		}
		String user = System.getenv("User");
		if (user != null ) {
			User=user;
		}
		String password = System.getenv("Password");
		if (db != null ) {
			Password=password;
		}
		
		String dbUrl = "jdbc:mysql://"+ serverUrl + "/"+ DB;
		System.out.println(dbUrl);
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(dbUrl, User, Password);		

		
		Statement stmt = con.createStatement();
		stmt.executeUpdate("CREATE TABLE Human ( ID int NOT NULL, name varchar(255) NOT NULL, PRIMARY KEY (ID) )");
		stmt.executeUpdate("insert into Human values(1, 'Justin')");
		stmt.executeUpdate("insert into Human values(3, 'Rita')");
		ResultSet rs = stmt.executeQuery("select * from Human )");
		while (rs.next())
			System.out.println(rs.getInt("Id") + "  " + rs.getString("name") );
		con.close();

	}

}
