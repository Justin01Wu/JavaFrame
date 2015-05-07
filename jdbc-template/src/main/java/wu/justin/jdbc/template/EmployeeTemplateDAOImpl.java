package wu.justin.jdbc.template;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeTemplateDAOImpl implements JDBCEmployeeDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insert(Employee employee) {

		String sql = "INSERT INTO EMPLOYEE "
				+ "(ID, NAME, AGE) VALUES (?, ?, ?)";

		jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(
				sql,
				new Object[] { employee.getId(), employee.getName(),
						employee.getAge() });
	}

	public Employee findById(int id) {

		String sql = "SELECT * FROM EMPLOYEE WHERE ID = ?";

		jdbcTemplate = new JdbcTemplate(dataSource);
		Employee employee = (Employee) jdbcTemplate.queryForObject(sql,
				new Object[] { id }, new EmployeeRowMapper());

		return employee;
	}

}
