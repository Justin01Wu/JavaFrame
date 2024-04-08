package com.justin.test.hibernate.bean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.justin.test.hibernate.HibernateUtil;

/**
 * 
 * this test case show you we can test Hibernate Session and EntityManager(JPA) in the same test case
 *
 */
public class DepartmentTest {
	
	private static Logger log = LoggerFactory.getLogger(DepartmentTest.class);
	
	private static EntityManagerFactory factory;
	private EntityManager entityManager;
	
	@BeforeClass
	public static void globalSetUp() throws Exception {
		
		log.debug( "");
		log.debug( "");
		log.debug( "");
		log.debug( "  ==>> createEntityManagerFactory... ");
		factory = Persistence.createEntityManagerFactory("unit_test"); 
		// this one is set on global to avoid multiple tables initialization 
	}
	
	@Before
	public void setUp() throws Exception {
		log.debug( "");
		log.debug( "    ==>> createEntityManager... ");
		entityManager = factory.createEntityManager();		
	}
	
	@After
	public void tearDown() throws Exception {
		entityManager.close();
		log.debug( "    ==>> entityManager.closed ");
	}

	@Test
	public void testCreateDepartment() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();

		Department department = new Department("hr");
		assertNull(department.getId());
		session.save(department);
		assertNotNull(department.getId());	
		
		session.saveOrUpdate(department);
		assertNotNull(department.getId());
		
		session.getTransaction().commit();
		session.close();

	}
	
	@Test
	public void  testEntityManagerFactory(){
		
        assertNotNull(entityManager);
        entityManager.getTransaction().begin();
        
		Department department = new Department("hr");
		assertNull(department.getId());
		entityManager.persist(department);
		assertNotNull(department.getId());
		System.out.println(department.getId());
		
		entityManager.getTransaction().commit();

	}
	
	
	@Test 
	public void testHibernateWork(){
		
		// from  https://www.informit.com/guides/content.aspx?g=java&seqNum=575
		// and http://www.programcreek.com/java-api-examples/index.php?api=org.hibernate.jdbc.Work
		
		entityManager.getTransaction().begin();
		org.hibernate.Session session = (Session) entityManager.getDelegate();
		session.doWork(new Work(){		    
		    public void execute(Connection conn) throws SQLException{
		    	jdbcInsertDept(conn);
		    }
		  }
		);
		
		entityManager.getTransaction().commit();
		
		System.out.println("done: insert department from WORK");
	}
	
    private static void jdbcInsertDept( Connection conn) throws SQLException {    	
    	
    	
    	System.out.println("insert department from WORK... ");
    	
    	String sql = "INSERT INTO department (ID, NAME) VALUES (?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, 1234);
		ps.setString(2, "from work");

		ps.executeUpdate();
      
    }

}
