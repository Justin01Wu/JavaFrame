package com.justa.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

// it comes from 
// https://tomcat.apache.org/tomcat-7.0-doc/jdbc-pool.html
public class TomcatPoolTest {
	
	//public static final String DRIVER_CLASS_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String DRIVER_CLASS_NAME = "net.sourceforge.jtds.jdbc.Driver";

	//private static final String DB_URL = "jdbc:sqlserver://CAVCEXPDB01:1433;databaseName=Vcaps_qa01_9088_20170316";
	private static final String DB_URL = "jdbc:jtds:sqlserver://CAVCEXPDB01:1433/Vcaps_qa01_9088_20170316";

	private static final String DB_USER = "vcapstest";

	private static final String DB_PASSWORD = "vcapstest";

	private static final int CONN_POOL_SIZE = 5;

	public static DataSource getDataSource()	{
		
        PoolProperties p = new PoolProperties();
        p.setUrl(DB_URL);
        p.setDriverClassName(DRIVER_CLASS_NAME);
        p.setUsername(DB_USER);
        p.setPassword(DB_PASSWORD);
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
          "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
          "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(p);

		
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
		
		String msgTemplate = "numActive= %d, idleNum = %d";
		
					
		String msg = String.format(msgTemplate, dataSource.getNumActive(), dataSource.getNumIdle()); 
		System.out.println(msg);

	}

}
