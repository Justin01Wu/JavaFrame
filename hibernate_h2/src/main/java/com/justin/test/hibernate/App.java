package com.justin.test.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justin.test.hibernate.bean.Department;
import com.justin.test.hibernate.bean.Employee;

public class App {
	
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
	
	public static void main(String[] args){
		
		//testDeptEmployee();
		
		testHibernateWork();

	}
	
	public static void testDeptEmployee(){
 		
		EntityManager entityManager = factory.createEntityManager();	
		
        entityManager.getTransaction().begin();
        
		Department department = new Department("hr");
		
		log.info(department.getId().toString());
		
		entityManager.persist(department);
		
		log.info(department.getId().toString());
		

		Employee employee = new Employee("simple employee", department);
		
		entityManager.persist(employee);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
		
	}
	
	
	public static void testHibernateWork(){
		
		// from  https://www.informit.com/guides/content.aspx?g=java&seqNum=575
		// and http://www.programcreek.com/java-api-examples/index.php?api=org.hibernate.jdbc.Work
		
		
		EntityManager entityManager = factory.createEntityManager();
		entityManager.getTransaction().begin();
		org.hibernate.Session session = (Session) entityManager.getDelegate();
		
		session.doWork(new Work(){		    
		    public void execute(Connection conn) throws SQLException{
		    	jdbcInsertDept(conn);
		    }
		  }
		);
		
		entityManager.getTransaction().commit();
		
		log.info("done: insert department from WORK");
		
		entityManager.close();
		
		
	}
	
    private static void jdbcInsertDept( Connection conn) throws SQLException {
    	
    	log.info("insert department from WORK...");
    	
    	String sql = "INSERT INTO department ( NAME) VALUES ( ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, "from work");

		ps.executeUpdate();
      
    }
	 
 
}
