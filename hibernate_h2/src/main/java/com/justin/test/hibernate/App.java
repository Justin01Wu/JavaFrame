package com.justin.test.hibernate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.justin.test.hibernate.bean.Department;
import com.justin.test.hibernate.bean.Employee;

public class App {
	
	public static void main(String[] args){
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit"); 		
		EntityManager entityManager = factory.createEntityManager();	
		
        entityManager.getTransaction().begin();
        
		Department department = new Department("hr");
		
		System.out.println(department.getId());
		
		entityManager.persist(department);
		
		System.out.println(department.getId());
		

		Employee employee = new Employee("simple employee", department);
		
		entityManager.persist(employee);
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
	}
	 
 
}
