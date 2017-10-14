package scjp.generic;

import java.util.Collection;


// this is an sample to customized a generic , 
//  here <Integer> is a real class 
// and original FF is a fake class

// it comes from https://stackoverflow.com/questions/46739734/how-to-customize-a-generic-type
public class CustomizedGeneric implements GenericOnInterface<Integer> {
	
	public boolean add(Integer o){
    	return true;
    }
    
    public boolean addAll(Collection<? extends Integer> c){
    	return true;
    }
}
