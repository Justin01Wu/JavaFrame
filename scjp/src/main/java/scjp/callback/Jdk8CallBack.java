package scjp.callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Jdk8CallBack {
	
	
    public static void main(String[] args){

    	String msg ="call back print this message11";
    	
    	callThisWithMessage(msg, (msg2) -> System.out.println("Message received: "+msg2));    	
    	// Lambda function, it means creating anonymous OnClickListener with method onClick(msg2){System.out.println("Message received: "+msg2}
    	
    	List<Integer> integers = new ArrayList<>(); 
    	integers.add(12);
    	integers.add(35);
        
        //convert to comma separated strings
        String ids = integers.stream().map(Object::toString).collect(Collectors.joining(","));
        System.out.println(ids);
        
        
        List<Student> list = getStudents(); 
        Map<Integer, String> map = list.stream().collect(Collectors.toMap(Student::getId, Student::getName));
        System.out.println(map.get(34));
        


    }
    
    private static void callThisWithMessage(String msg , OnClickListener callBack) {
    	callBack.onClick(msg);
    }

    
    private static List<Student> getStudents(){
    	Student s;
    	List<Student> list = new ArrayList<>();
    	s =  new Student(12,"sdd");
    	list.add(s);
    	s =  new Student(34,"g3d7gr");
    	list.add(s);
    	
    	return list;
    	
    }

}
