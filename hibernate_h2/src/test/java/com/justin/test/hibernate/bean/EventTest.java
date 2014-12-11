package com.justin.test.hibernate.bean;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.junit.Test;

import com.justin.test.hibernate.HibernateUtil;
import com.justin.test.hibernate.bean.Event;
import com.justin.test.hibernate.bean.EventTypeEnum;

/**
 * test Event bean , see hibernate.cfg.xml for details 
 *
 */
public class EventTest {
	
	@Test
	public void testCreateEvent() {
		Session session = HibernateUtil.getSessionFactory().openSession();

		session.beginTransaction();
		Event event = new Event();

		event.setTitle("with Dr Li");
		event.setType(EventTypeEnum.Busy);

		session.save(event);
		session.getTransaction().commit();
		
		assertEquals(event.getTitle(), "with Dr Li");
		System.out.println("Great! Event was saved");
	}
}
