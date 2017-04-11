package com.justa.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;



public class Dbcp1Test {
	
	//public static final String DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String DRIVER_CLASS_NAME = "net.sourceforge.jtds.jdbc.Driver";

	//private static final String DB_URL = "jdbc:sqlserver://CAVCEXPDB01:1433;databaseName=Vcaps_qa01_9088_20170316";
	private static final String DB_URL = "jdbc:jtds:sqlserver://CAVCEXPDB01:1433/Vcaps_qa01_9088_20170316";

	private static final String DB_USER = "vcapstest";

	private static final String DB_PASSWORD = "vcapstest";

	private static final int CONN_POOL_SIZE = 5;

	public static DataSource getDataSource()	{
		
		BasicDataSource dataSource = new BasicDataSource();		
		dataSource.setDriverClassName(DRIVER_CLASS_NAME);
		dataSource.setUrl(DB_URL);
		dataSource.setUsername(DB_USER);
		dataSource.setPassword(DB_PASSWORD);
		dataSource.setInitialSize(CONN_POOL_SIZE);
		
		return dataSource;
	}
	
	public static void main(String[] args) throws SQLException 	{
		
		DataSource dataSource = getDataSource();
		
		printStatus(dataSource);
		
		Connection con = dataSource.getConnection();
		printStatus(dataSource);

		JtdsJdbcTest.execQuery(con);
		con.close();
		
		printStatus(dataSource);
	}
	
	private static void printStatus(DataSource dataSource){
		
		BasicDataSource basicDataSource;
		String msgTemplate = "numActive= %d, idleNum = %d";
		
		if(dataSource instanceof BasicDataSource){
			basicDataSource = (BasicDataSource)dataSource;					
			String msg = String.format(msgTemplate, basicDataSource.getNumActive(), basicDataSource.getNumIdle()); 
			System.out.println(msg);
		}
	}

}
