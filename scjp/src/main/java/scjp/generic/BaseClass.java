

package scjp.generic;


public class BaseClass<T extends Number> {
	
	public T max(T a, T b){
		if(a.intValue() > b.intValue()){
			return a;
		}else{
			return b; 
		}
	}

}
