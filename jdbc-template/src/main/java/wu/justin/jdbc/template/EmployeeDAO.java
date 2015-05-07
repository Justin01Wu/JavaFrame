package wu.justin.jdbc.template;

public interface EmployeeDAO {

	public void insert(Employee employee);

	public Employee findById(int id);
}
