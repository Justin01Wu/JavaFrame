package com.justa.test.aws.message;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Because it is a Lamdba function, so we don't need connection pool for database.
 * But build up a new Db connection is time costly and limited, so maybe we can try AWS database proxy in Lambda console
 *
 */
public class MySqlLambda implements RequestHandler<Object,Date> {
	
	private static String ServerName = "localhost";
	private static String PORT = "3306";
	private static String DB = "testDb";
	private static String User = "admin";
	private static String Password = "dbPassword";
	
	@Override
	public Date handleRequest(Object input, Context context)  {	
		String serverName = System.getenv("ServerName");
		if (serverName != null ) {
			ServerName = serverName ;
		}
		
		String dbName = System.getenv("DbName");
		if (dbName != null ) {
			DB = dbName ;
		}
		// TODO use Encryption configuration to get Db password
		
		try {
			callMySql(ServerName, DB);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private void callMySql(String ServerName, String dbName) throws ClassNotFoundException, SQLException {
		String dbUrl = "jdbc:mysql://"+ ServerName + ":" + PORT + "/"+ dbName;
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
		
		System.out.println("==>    query [Human] table ");
		ResultSet rs = stmt.executeQuery("select * from Human ");
		while (rs.next()) {
			System.out.println(rs.getInt("Id") + "  " + rs.getString("name") );
		}
		con.close();
	}
}
