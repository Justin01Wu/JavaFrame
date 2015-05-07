package wu.justin.jdbc;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wu.justin.jdbc.old.EmployeeDAOImpl;

public class App {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		EmployeeDAOImpl employeeDAO = (EmployeeDAOImpl) context.getBean("employeeDAO");
		Employee employee1 = new Employee(123, "javacodegeeks", 30);
		employeeDAO.insert(employee1);
		Employee employee2 = employeeDAO.findById(123);
		System.out.println(employee2);
		context.close();
	}
}
