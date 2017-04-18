package com.justa.test;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.SQLException;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

// it comes from 
// https://examples.javacodegeeks.com/enterprise-java/hikaricp/hikaricp-connection-pooling-example/
public class HikariCPTest {

	private static DataSource datasource;
	
	//private static String jdbcUrl = "jdbc:jtds:sqlserver://CAVCEXPDB01:1433/Vcaps_qa01_9088_20170316";
	private static String jdbcUrl = "jdbc:sqlserver://CAVCEXPDB01:1433;databaseName=Vcaps_qa01_9088_20170316";
	

	public static DataSource getDataSource() throws MBeanRegistrationException, InstanceNotFoundException, MalformedObjectNameException, InstanceAlreadyExistsException, NotCompliantMBeanException	{

		if (datasource == null)		{

			HikariConfig config = new HikariConfig();
			
			//config.setDataSourceClassName("net.sourceforge.jtds.jdbc.Driver");			

			config.setJdbcUrl(jdbcUrl);
			
			config.setUsername("vcapstest");
			config.setPassword("vcapstest");
			config.setMinimumIdle(5);
			config.setMaximumPoolSize(10);
			//config.setConnectionTimeout(0);			

			config.setAutoCommit(false);

			config.addDataSourceProperty("cachePrepStmts", "true");

			config.addDataSourceProperty("prepStmtCacheSize", "250");

			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

			HikariDataSource hikariDataSource = new HikariDataSource(config);
			
			datasource =  hikariDataSource;
			
		       //register the pool MBean for monitor
			MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			HikariPoolMXBean hikariPoolMXBean = hikariDataSource.getHikariPoolMXBean();
	        String value = "HikariPoolMXBean";
	        String domain = "com.justa.jmx";
	        ObjectName objectName = new ObjectName(domain, "Hikari", value);
			if(server.isRegistered(objectName)){
				System.out.println("unregister old Log4jConsole...");
				server.unregisterMBean(objectName);
			}
			server.registerMBean(hikariPoolMXBean, objectName);

		}

		return datasource;

	}
	
	private static void printStatus(DataSource dataSource){
		if(dataSource instanceof HikariDataSource){
			String msgTemplate = "numActive= %d, idleNum = %d";
			HikariPoolMXBean hikariPoolMXBean = ((HikariDataSource)dataSource).getHikariPoolMXBean();
			
			// has a bug, will get wrong number, TODO fix it
			String msg = String.format(msgTemplate, hikariPoolMXBean.getActiveConnections(), hikariPoolMXBean.getIdleConnections()); 
			System.out.println(msg);

			
		}

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, MBeanRegistrationException, InstanceNotFoundException, MalformedObjectNameException, InstanceAlreadyExistsException, NotCompliantMBeanException	{
		
		// load the driver class
		//Class.forName("net.sourceforge.jtds.jdbc.Driver");

		
		DataSource dataSource = HikariCPTest.getDataSource();
		
		printStatus(dataSource);
		
		Connection connection = dataSource.getConnection();
		JtdsJdbcTest.execQuery(connection);
		
		printStatus(dataSource);
		
		connection.close();
		
		printStatus(dataSource);
		
		System.out.println();


	}

}
