package wu.justin.jdbc.template;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import wu.justin.jdbc.Employee;
import wu.justin.jdbc.EmployeeDAO;

public class EmployeeTemplateDAOImpl implements EmployeeDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertEmp;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
		insertEmp =  new SimpleJdbcInsert(dataSource)
			.withTableName("employee").usingGeneratedKeyColumns("id");   
		
	}

	public void insert(Employee employee) {

		String sql = "INSERT INTO EMPLOYEE (ID, NAME, AGE) VALUES (?, ?, ?)";

		jdbcTemplate.update(sql, employee.getId(), employee.getName(), employee.getAge());
		
	}
	
	public int create(String name, Integer age) {
		
        Map<String, Object> parameters = new HashMap<String, Object>();  
        parameters.put("name", name);  
        parameters.put("age", age);  
        
		Number pk = insertEmp.executeAndReturnKey(parameters);
		
		System.out.println("Created Record with pk = " + pk);
		return pk.intValue();
	}

	public Employee findById(int id) {

		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";
		Employee employee = (Employee) jdbcTemplate.queryForObject(sql,	new Object[] { id }, new EmployeeRowMapper());

		return employee;
	}

}
