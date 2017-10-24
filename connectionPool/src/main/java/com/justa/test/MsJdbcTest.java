package com.justa.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
			
			//setContext(con);

			JtdsJdbcTest.execQuery(con);

			
			con.close();
		}
		
		private static void setContext(Connection con) throws SQLException{
			
			// since sql server 2012, it has sp_set_session_context, which can be used in RLS(Row level security) function 
			PreparedStatement ps = con.prepareStatement("exec sp_set_session_context @key=N'VCAPSUserId', @value=?");  
			// call sp_set_session_context to set current user info which will be used by RLS function
			int userId =  13;
			ps.setInt(1, userId);
			boolean success  = ps.execute();
			
		}

}
