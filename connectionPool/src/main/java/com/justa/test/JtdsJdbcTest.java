package com.justa.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JtdsJdbcTest {

	private static String jdbcUrl = "jdbc:jtds:sqlserver://CAVCEXPDB01:1433/Vcaps_qa01_9088_20170316";
	
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {


		// load the driver class
		Class.forName("net.sourceforge.jtds.jdbc.Driver");

		// connect to DB
		Connection con = DriverManager.getConnection(jdbcUrl,"vcapstest", "vcapstest");

		execQuery(con);		
		
		con.close();
	}
	
	public static void execQuery(Connection con) throws SQLException{
		
		System.out.println("The Connection Object is of Class: "+ con.getClass());

		String sql = "SELECT top 5 * FROM vcapsUser";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			try(ResultSet resultSet = pstmt.executeQuery()){
				while (resultSet.next()) {
					System.out.println(resultSet.getInt(1) + ", "	+ resultSet.getString(2));
				}				
			}	
		}		
		
		con.close();
		
	}

}
