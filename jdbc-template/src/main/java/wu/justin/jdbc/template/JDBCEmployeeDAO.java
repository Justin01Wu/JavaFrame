package wu.justin.jdbc.template;

import wu.justin.jdbc.Employee;

public interface JDBCEmployeeDAO {
	
	public void insert(Employee employee);
	public Employee findById(int id);
	
}
