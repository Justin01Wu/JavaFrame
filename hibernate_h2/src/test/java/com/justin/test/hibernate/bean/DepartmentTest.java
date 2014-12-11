package com.justin.test.hibernate.bean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.justin.test.hibernate.HibernateUtil;

/**
 * 
 * this test case show you we can test Hibernate Session and EntityManager(JPA) in the same test case
 *
 */
public class DepartmentTest {
	
	private static Log log = LogFactory.getLog(DepartmentTest.class);
	
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

}
