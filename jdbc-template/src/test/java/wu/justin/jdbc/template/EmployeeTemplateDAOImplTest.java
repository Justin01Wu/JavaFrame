package wu.justin.jdbc.template;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import wu.justin.jdbc.Employee;
import wu.justin.jdbc.old.EmployeeDAOImplTest;

public class EmployeeTemplateDAOImplTest {

	@BeforeClass
	public static void globalSetUp() throws Exception {

		EmployeeDAOImplTest.createEmployeeTable();
	}
	
	//@Test
	public void testInsert() {

		// This example comes from
		// http://examples.javacodegeeks.com/enterprise-java/spring/jdbc/spring-jdbctemplate-example/

		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		EmployeeTemplateDAOImpl employeeDAO = (EmployeeTemplateDAOImpl) context.getBean("employeeTemplateDAO");
		Employee employee1 = new Employee(123, "Justin Wu", 30);
		employeeDAO.insert(employee1);
		
		Employee employee2 = employeeDAO.findById(123);
		
		assertEquals(employee2.getId(), employee1.getId());
		assertEquals(employee2.getName(), employee1.getName());
		assertEquals(employee2.getAge(), employee1.getAge());
		
		System.out.println(employee2);
		context.close();
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testInsertFailure() {

		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		EmployeeTemplateDAOImpl employeeDAO = (EmployeeTemplateDAOImpl) context.getBean("employeeTemplateDAO");
		Employee employee1 = new Employee(123, "Justin Wu name is too long, Justin Wu name is too long", 30);
		employeeDAO.insert(employee1);

		context.close();
	}

}
