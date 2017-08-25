package scjp.enums;


public interface LookupById<E extends Enum<E>> {
	
	public int getId();
	
	
	// Since JDK 1.8, we can add static method on interface
	// We can declare generic E can extends multiple class like :  E extends A & B  
	public static <E extends Enum<E> & LookupById<E>> E lookupById2(Class<E> enumClass, Integer id){
			if(id == null) 	return null;

			for(E one: enumClass.getEnumConstants()){
				if(one.getId() == id){
					return one;
				}
			}
			return null;
		 
	 }
	
	// Since JDK 1.8, we can add static method on interface
	public static <E extends Enum<E> & LookupById<E>> E lookupById2( Integer id){
		if(id == null) 	return null;

//		for(E one: E.values()){   // compile error on this line:  The method values() is undefined for the type E
//                                // values is added by compiler, didn't exist on any java class
// 								  // see here for details https://stackoverflow.com/questions/13659217/where-is-the-documentation-for-the-values-method-of-enum
//			if(one.getId() == id){
//				return one;
//			}
//		}
		return null;
	 
 }

}