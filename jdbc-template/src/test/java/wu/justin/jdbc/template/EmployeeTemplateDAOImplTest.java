package wu.justin.jdbc.template;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EmployeeTemplateDAOImplTest {

	@BeforeClass
	public static void globalSetUp() throws Exception {

		EmployeeDAOTest.createEmployeeTable();
	}
	
	@Test
	public void testInsert() {

		// This example comes from
		// http://examples.javacodegeeks.com/enterprise-java/spring/jdbc/spring-jdbctemplate-example/

		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		EmployeeTemplateDAOImpl employeeTemplateDAO = (EmployeeTemplateDAOImpl) context.getBean("employeeTemplateDAO");
		Employee employee1 = new Employee(123, "Justin Wu", 30);
		employeeTemplateDAO.insert(employee1);
		
		
		EmployeeDAOImpl employeeDAO = (EmployeeDAOImpl) context.getBean("employeeDAO");
		Employee employee2 = employeeDAO.findById(123);
		System.out.println(employee2);
		context.close();
	}

}
