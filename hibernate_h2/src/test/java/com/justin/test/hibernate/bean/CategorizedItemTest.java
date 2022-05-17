package com.justin.test.hibernate.bean;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategorizedItemTest {
	
	private static Logger log = LoggerFactory.getLogger(CategorizedItemTest.class);
	
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
	public void testCreateCategorizedItem() {
		
		log.debug("    ==> testCreateCategorizedItem");
		
		entityManager.getTransaction().begin();
		Category category = new Category();
		category.setName("cat22");
		Item item = new Item();
		item.setName("item33");
		
		entityManager.persist(category);
		entityManager.persist(item);		
		CategorizedItem categorizedItem = new CategorizedItem("Justin", category, item);
		
		entityManager.persist(categorizedItem);
		assertNotNull(categorizedItem.getId());
		assertNotNull(categorizedItem.getId().getCategoryId());  
		assertNotNull(categorizedItem.getId().getItemId());
		
		entityManager.getTransaction().commit();

	}

}
