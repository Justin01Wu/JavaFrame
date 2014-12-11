package com.justin.test.hibernate.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmployeeTest {
	
	private static Log log = LogFactory.getLog(EmployeeTest.class);
	
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
	public void testCreateSimpleEmployeeAndUpdate() {
		
		log.debug("    ==> testCreateSimpleEmployeeAndUpdate");
		
		entityManager.getTransaction().begin();

		Department department = new Department("hr");
		assertNull(department.getId());
		entityManager.persist(department);
		assertNotNull(department.getId());	

		Employee employee = new Employee("simple employee", department);
		assertNull("employee should NOT have createdTime before save", employee.getCreatedTime());
		assertNull("employee should NOT have updatedTime before save", employee.getUpdatedTime());
		assertEquals(employee.getDepartment().getName(), "hr");
		assertNull("employee should NOT have pk before save", employee.getPk());
		
		entityManager.persist(employee);
		assertNotNull("employee should have pk after saving", employee.getPk());
		assertNotNull("employee should have createdTime after saving", employee.getCreatedTime());  
		assertNotNull("employee should have updatedTime after saving", employee.getUpdatedTime());
		
		entityManager.getTransaction().commit();
		
		testUpdateEmployee(employee.getPk());

	}
	
	/**
	 * 
	 * this test case has order requirement, so it is unstable,
	 * TODO figure out a way to make it stable
	 *
	 */
	private void testUpdateEmployee(Long  employeePk) {
		
		log.debug("    ==> testUpdateEmployee");
		
		entityManager.getTransaction().begin();
		Employee employee = entityManager.find(Employee.class, employeePk);		
		
		assertEquals(employee.getName(), "simple employee");
		assertEquals(employee.getPk(), employeePk);
		
		Session session  = ((Session)entityManager.getDelegate());
		
		assertFalse("session is NOT dirty if employee is not touched", session.isDirty());
		employee.setName("simple employee " + new Date());
		assertTrue("session is dirty after employee is changed", session.isDirty());
		
		entityManager.persist(employee);
		
		assertTrue("session is still dirty after employee is persisted", session.isDirty());
		
		entityManager.getTransaction().commit();
		assertFalse("session is NOT dirty after transaction is closed", session.isDirty());
		assertFalse(session.isDirty());

	}

	@Test
	public void testCreateEmployeeWithEmails() {
		
		log.debug("    ==> testCreateEmployeeWithEmails");

		entityManager.getTransaction().begin();

		Department department = new Department("java");
		entityManager.persist(department);

		Employee employee = new Employee("has emails", department);
		entityManager.persist(employee);
		
		// set emails
		List<String> emails = new ArrayList<String>();
		emails.add("justin1.wu@gmail.com");
		emails.add("wuyg719@gmail.com");
		employee.setEmails(emails);	
		
		// employee is already attached, so it will automatically save emails 		

		entityManager.getTransaction().commit();

	}
	
	@Test
	public void testCreateEmployeeWithCertificates() {
		
		log.debug("    ==> testCreateEmployeeWithCertificates");

		entityManager.getTransaction().begin();

		Department department = new Department("java");
		entityManager.persist(department);

		Employee employee = new Employee("has certificates", department);
		entityManager.persist(employee);
				
		// set certificates
		Map<Integer, String> certificates = new HashMap<Integer, String>();
		certificates.put(11, "SCJP");
		certificates.put(12, "SCWCD");		
		employee.setCertificates(certificates);
		
		// employee is already attached, so it will automatically save certificates

		entityManager.getTransaction().commit();


	}
	
	@Test
	public void testHibernateQueryOnEmployee() {
		
		log.debug("    ==> testHibernateQueryOnEmployee");
		
		Session session  = ((Session)entityManager.getDelegate());
		Query q = session.createQuery("From Employee ");		

		@SuppressWarnings("unchecked")
		List<Employee> resultList = q.list();
		
		assertTrue(resultList.size() > 0);
		System.out.println("num of employess:" + resultList.size());
		for (Employee next : resultList) {
			System.out.println("next employee: " + next);
		}
	}
	
	@Test
	public void testJPAQueryOnEmployee() {
		
		log.debug("    ==> testJPAQueryOnEmployee");
		
		javax.persistence.Query q = entityManager.createQuery("FROM Employee ", Employee.class);		

		@SuppressWarnings("unchecked")
		List<Employee> resultList = q.getResultList();
		
		assertTrue(resultList.size() > 0);
		System.out.println("num of employess:" + resultList.size());
		for (Employee next : resultList) {
			System.out.println("next employee: " + next);
		}
	}
	

	
}
