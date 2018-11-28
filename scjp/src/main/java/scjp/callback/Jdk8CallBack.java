package scjp.callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Jdk8CallBack {
	
	
    public static void main(String[] args){

    	String msg ="call back print this message11";
    	
    	callThisWithMessage(msg, (msg2) -> System.out.println("Message received: "+msg2));    	
    	// Lambda function, it means creating anonymous OnClickListener with method onClick(msg2){System.out.println("Message received: "+msg2}
    	
    	List<Integer> integers = new ArrayList<>(); 
    	integers.add(12);
    	integers.add(35);
        
        // convert to comma separated strings
        String ids = integers.stream().map(Object::toString).collect(Collectors.joining(","));
        System.out.println(ids);
        
        // convert list to map on Collectors.toMap
        List<Student> list = getStudents(); 
        Map<Integer, String> map = list.stream().collect(Collectors.toMap(Student::getId, Student::getName));
        System.out.println(map.get(34));
        
        // convert list to map2 on forEach    
        Map<Integer, Student> map2 = new HashMap<>();
        list.forEach(e->map2.put(e.getId(), e));
        System.out.println(map2.get(34).getName());
        
        // convert list to map2 on Collectors.toMap for itself   
        Map<Integer, Student> map3 = list.stream().collect(Collectors.toMap(Student::getId, student -> student));
        System.out.println(map3.get(34).getName());
        
        // convert list to map2 on Collectors.toMap for itself 
        Map<Integer, Student> map4 = list.stream().collect(Collectors.toMap(Student::getId, Function.identity()));
        System.out.println(map4.get(34).getName());
        

        
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
