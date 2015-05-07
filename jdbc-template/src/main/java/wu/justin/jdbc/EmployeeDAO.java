package wu.justin.jdbc;


public interface EmployeeDAO {

	public void insert(Employee employee);

	public Employee findById(int id);
}
