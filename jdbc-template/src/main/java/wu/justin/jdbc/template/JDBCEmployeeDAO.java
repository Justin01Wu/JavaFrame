package wu.justin.jdbc.template;

public interface JDBCEmployeeDAO {
	
	public void insert(Employee employee);
	public Employee findById(int id);
	
}
