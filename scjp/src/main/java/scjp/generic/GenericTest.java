package scjp.generic;

import java.util.ArrayList;
import java.util.List;

class MyStudent {
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "Student["+id + ", "+name+"]";
	}
}

class MyDao {
	public <T extends MyStudent> List<T> findAllStudentsBySchoolId(Integer schoolId, Class<T> type) throws InstantiationException, IllegalAccessException {
		List<T> rows = new ArrayList<T>();
		if(schoolId == null || schoolId <= 0){
			return rows;
		}
		
		T student;
		for(int i=1;i<=2;i++) {
			student = type.newInstance();   // this line will call new MyStudent();
			student.setId(i);
			student.setName("name"+i);
			rows.add(student);
		}
		return rows;
	}
}

class MyStudent2  extends MyStudent {
	
	@Override
	public String getName() {
		return "MyStudent2: " + super.getName();
	}

}

public class GenericTest {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException{

		MyDao myDao = new MyDao();
		
		
		
		System.out.println("  ===1===  ");
		
		List<MyStudent> students = myDao.findAllStudentsBySchoolId(12, MyStudent.class); 
		for(MyStudent student: students){
			System.out.println(student.toString());
			System.out.println(student.getName());
		}
		
		
		
		System.out.println("  ===2===  ");
		
		List<MyStudent2> student2s;
		// student2s = myDao.findAllStudentsBySchoolId(12, MyStudent2.class); // this line will get compile error
		student2s = myDao.findAllStudentsBySchoolId(12, MyStudent2.class);		
		
		for(MyStudent2 student: student2s){
			System.out.println(student.toString());
			System.out.println(student.getName());
		}
		
		
		
		System.out.println("  ===3===  ");
		
		//List<Object> student4s = myDao.findAllStudentsBySchoolId(12, MyStudent2.class);  // this line will get compile error
		List<?> student3s = myDao.findAllStudentsBySchoolId(12, MyStudent2.class);
		for(Object student: student3s){
			
			System.out.println(student.toString());
			
			MyStudent2 student2 = (MyStudent2)student;
			System.out.println(student2.getName());
		}
		
		
	}

}
