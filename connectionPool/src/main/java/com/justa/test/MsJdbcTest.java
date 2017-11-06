package com.justa.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MsJdbcTest {	
		
	    //private static String jdbcUrl = "jdbc:sqlserver://CAVCEXPDB01:1433;databaseName=Vcaps_qa01_9088_20170316";
		private static String jdbcUrl = "jdbc:sqlserver://CAVCDBTST03:1433;databaseName=vcaps_QA_20170430;user=vcapstest;password=vcapstest"; 

		public static void main(String[] args) throws ClassNotFoundException,
				SQLException {


			// load the driver class
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			//Connection con = DriverManager.getConnection(jdbcUrl,"vcapstest", "vcapstest");
			Connection con = DriverManager.getConnection(jdbcUrl);
			
			JdbcUtil.setContext(con);
			//JdbcUtil.readSession(con);   //will fail

			JtdsJdbcTest.execQuery(con);

			
			con.close();
		}
		

}
