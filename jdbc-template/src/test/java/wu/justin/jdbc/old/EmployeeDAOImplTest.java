package wu.justin.jdbc.old;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wu.justin.jdbc.Employee;
import wu.justin.jdbc.old.EmployeeDAOImpl;

public class EmployeeDAOImplTest {

	public static final String CreateTableStr = "CREATE TABLE Employee (ID INTEGER NOT NULL AUTO_INCREMENT,"
			+ "NAME VARCHAR(100) NOT NULL,"
			+ "AGE INT(10) UNSIGNED NOT NULL,"
			+ "PRIMARY KEY (ID)" + ")";

	@BeforeClass
	public static void globalSetUp() throws Exception {

		createEmployeeTable();
	}

	public static final void createEmployeeTable() throws ClassNotFoundException, SQLException {

		// load the driver class
		Class.forName("org.h2.Driver");

		// connect to a in memory database, because of H2 feature, you don't
		// need to install db sever or create db before do this
		Connection con = DriverManager.getConnection("jdbc:h2:mem:mytest",	"sa", "");

		// here you create the table
		Statement sst = con.createStatement();
		sst.executeUpdate(CreateTableStr);

	}

	@Test
	public void testInsert() {

		// This example comes from
		// http://examples.javacodegeeks.com/enterprise-java/spring/jdbc/spring-jdbctemplate-example/

		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		EmployeeDAOImpl employeeDAO = (EmployeeDAOImpl) context.getBean("employeeDAO");
		Employee employee1 = new Employee(123, "Justin Wu", 30);
		employeeDAO.insert(employee1);
		Employee employee2 = employeeDAO.findById(123);
		assertEquals(employee2.getId(), employee1.getId());
		assertEquals(employee2.getName(), employee1.getName());
		assertEquals(employee2.getAge(), employee1.getAge());
		System.out.println(employee2);
		context.close();
	}

}
