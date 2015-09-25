package com.justin.test.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.justin.test.hibernate.bean.Department;

public class App {
	
	public static void main(String[] args){
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit"); 		
		EntityManager entityManager = factory.createEntityManager();	
		
        entityManager.getTransaction().begin();
        
		Department department = new Department("hr");
		
		System.out.println(department.getId());
		
		entityManager.persist(department);
		
		System.out.println(department.getId());
		
		entityManager.getTransaction().commit();
		
		entityManager.close();
	}
	 
 
}
