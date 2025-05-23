package com.justa.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

//usually we use mybatis-config.xml to do this job
// but now to make simple, we use hard java code to do this
public class MyFactory {

	public static SqlSessionFactory buildqlSessionFactory() {
	    DataSource dataSource = new PooledDataSource("org.h2.Driver", "jdbc:h2:mem:unittest", "sa", "");	    
	 
	    Environment environment = new Environment("Development", new JdbcTransactionFactory(), dataSource);
	         
	    Configuration configuration = new Configuration(environment);
	    configuration.addMapper(PersonMapper.class);
	    configuration.addMapper(AddressMapper.class);
	    
	    // ...
	 
	    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
	    return builder.build(configuration);
	}
}
