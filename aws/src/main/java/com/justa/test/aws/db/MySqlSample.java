package com.justa.test.aws.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlSample {

	private static String ServerName = "localhost";
	private static String PORT = "3306";
	private static String DB = "testDb";
	private static String User = "admin";
	private static String Password = "admin123";
	

	public static void main(String args[]) throws ClassNotFoundException, SQLException {

		String serverName = System.getenv("ServerName");
		if (serverName != null ) {
			ServerName = serverName ;
		}
		String port = System.getenv("Port");
		if (port != null ) {
			PORT = port ;
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
		if (password != null ) {
			Password=password;
		}
		
		String dbUrl = "jdbc:mysql://"+ ServerName + ":" + PORT + "/"+ DB;
		System.out.println(dbUrl);
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(dbUrl, User, Password);		

		Statement stmt = con.createStatement();
		DatabaseMetaData dbm = con.getMetaData();
		System.out.println("==>    check if [Human] table is there...");
		ResultSet tables = dbm.getTables(null, null, "Human", null);
		if (tables.next()) {
		  // Table exists
		} else {
		  // Table does not exist
			System.out.println("==>    creating [Human] table and add some data...");
			
			stmt.executeUpdate("CREATE TABLE Human ( ID int NOT NULL, name varchar(255) NOT NULL, PRIMARY KEY (ID) )");
			stmt.executeUpdate("insert into Human values(1, 'Justin')");
			stmt.executeUpdate("insert into Human values(2, 'Yan')");
			stmt.executeUpdate("insert into Human values(3, 'Rita')");
		}

		ResultSet rs = stmt.executeQuery("select * from Human ");
		while (rs.next())
			System.out.println(rs.getInt("Id") + "  " + rs.getString("name") );
		con.close();

	}

}
